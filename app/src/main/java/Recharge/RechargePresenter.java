package Recharge;

import android.content.Context;
import android.database.Observable;
import android.support.annotation.MainThread;
import android.util.Log;

import java.io.IOException;

import Base.BasePresenter;
import Base.BaseResponse;
import Retrefit.RetrefitManger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class RechargePresenter extends BasePresenter {
    private RetrefitManger mretrefitManger;
    private Context mcontext;
    private RechargeInfo mrechargeInfo;
    private RechargeInterface mrechargeInterface;

    public RechargePresenter(Context context){
        mretrefitManger=new RetrefitManger(context);
        this.mcontext=context;
    }

    public void loadRechargeMap(int type,String token){
        Subscription subscription = mretrefitManger.rechargeinfo(type,token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RechargeInfo>(){
                    @Override
                    public void onCompleted() {
                        Log.i("info", "onCompleted: "+"请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("info", "onError: "+"请求失败");

                    }

                    @Override
                    public void onNext(RechargeInfo rechargeInfo) {
                        mrechargeInfo=rechargeInfo;
                        mrechargeInterface.showRechargeList(rechargeInfo.getList());
                        Log.i("info", "onNext: "+rechargeInfo);
                    }
                });
        addSubscription(subscription);

    }


}
