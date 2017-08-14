package Recharge;

import android.app.Activity;
import android.content.Context;
import android.database.Observable;
import android.support.annotation.MainThread;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.Map;

import Base.BaseObserver;
import Base.BasePresenter;
import Base.BaseResponse;
import Recharge.wechatpay.WxpayInfo;
import Retrefit.RetrefitManger;
import Util.Url;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class RechargePresenter extends BasePresenter<RechargeInterface> {
    private RetrefitManger mretrefitManger;
    private Context mcontext;
    private Activity mactivity;
    private RechargeInfo mrechargeInfo;
    private RechargeInterface mrechargeInterface;
    private IWXAPI msgApi;

    public RechargePresenter(Activity activity,RechargeInterface rechargeInterface){
        super(rechargeInterface);
        mretrefitManger=new RetrefitManger(activity);
        this.mactivity=activity;
        msgApi= WXAPIFactory.createWXAPI(mactivity, Url.WX_APPID);
    }

    public void loadRechargeMap(int type,String token){
        Subscription subscription = mretrefitManger.rechargeinfo(type,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<RechargeInfo>>(getUiInterface()) {
                    @Override
                    public void onSuccess(BaseResponse<RechargeInfo> response) {
                            getUiInterface().showRechargeList(response.getData().getList());
                        Log.i("info", "onSuccess: "+response.getData().getList());
                    }
                });
        addSubscription(subscription);
    }
    //支付宝
    public  void RechargeAlipay(String amount){
        getUiInterface().showLoadingDialog();
        Subscription subscription=mretrefitManger.RechargeAlipay(amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<BaseResponse<String>, PayResult>() {
                    @Override
                    public PayResult call(BaseResponse<String> stringBaseResponse) {
                        String data=stringBaseResponse.getData();
                        PayTask paytask=new PayTask(mactivity);//调用支付宝
                        Map<String,String>result=paytask.payV2(data,true);//data服务器返回的数据，true表示可以有加载dialog
                        return new PayResult((Map<String,String>)result);
                    }
                })
                .compose(this.<PayResult>applyAsySchedulers())
                .subscribe(new Observer<PayResult>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        getUiInterface().dismissLoadingDialog();
                        BaseObserver.handleError(e, getUiInterface(), "info");
                    }
                    @Override
                    public void onNext(PayResult payResult) {
                        getUiInterface().dismissLoadingDialog();
                        switch (payResult.getResultStatus()){
                            case "9000":
                                Toast.makeText(mactivity,"充值成功",Toast.LENGTH_SHORT).show();
                                loadRechargeMap(2,"b2d57ac6b86baa2552a812a3ee68bf46");//刷新余额和列表
                                break;
                            case "8000":
                                Toast.makeText(mactivity,"充值处理中。。。",Toast.LENGTH_SHORT).show();
                                break;
                            case "6001":
                                Toast.makeText(mactivity,"充值取消",Toast.LENGTH_SHORT).show();
                                break;
                                default:
                                Toast.makeText(mactivity,"充值失败",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
        addSubscription(subscription);
    }
    //微信支付
    public  void RechargeWechat(String token,String num){
        getUiInterface().showLoadingDialog();
        Subscription sub=mretrefitManger.RechargeWechat(token,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<WxpayInfo>>(getUiInterface()) {
                    @Override
                    public void onSuccess(BaseResponse<WxpayInfo> response) {
                        //吊起微信支付
                        PayReq req=new PayReq();
                        req.appId=response.getData().getAppid();
                        req.partnerId=response.getData().getPartnerid();
                        req.prepayId=response.getData().getPrepayid();
                        req.nonceStr=response.getData().getNoncestr();
                        req.timeStamp=response.getData().getTimestamp();
                        req.packageValue=response.getData().getPackagee();
                        req.sign=response.getData().getSign();
                        msgApi.registerApp(Url.WX_APPID);// 将该app注册到微信
                        msgApi.sendReq(req);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        getUiInterface().dismissLoadingDialog();
                    }
                });
        addSubscription(sub);
    }

}
