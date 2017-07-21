package Recharge;

import android.content.Context;
import android.database.Observable;
import android.support.annotation.MainThread;
import android.util.Log;

import java.io.IOException;

import Base.BaseObserver;
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

public class RechargePresenter extends BasePresenter<RechargeInterface> {
    private RetrefitManger mretrefitManger;
    private Context mcontext;
    private RechargeInfo mrechargeInfo;
    private RechargeInterface mrechargeInterface;

    public RechargePresenter(Context context,RechargeInterface rechargeInterface){
        super(rechargeInterface);
        mretrefitManger=new RetrefitManger(context);
        this.mcontext=context;
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

}
