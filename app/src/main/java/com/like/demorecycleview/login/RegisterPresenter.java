package com.like.demorecycleview.login;

import android.content.Context;

import com.like.demorecycleview.SP.LocalDataManger;

import Base.BaseObserver;
import Base.BasePresenter;
import Base.BaseResponse;
import Retrefit.RetrefitManger;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class RegisterPresenter extends BasePresenter<RegisterInterface> {
    private RetrefitManger manger;
    protected RegisterPresenter(Context context,RegisterInterface uiInterface) {
        super(uiInterface);
        manger=new RetrefitManger(context);
    }
    public void  getverifycode(String phone){
        Subscription sub=manger.sendCaptcha(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new BaseObserver<BaseResponse<String>>(getUiInterface()) {
                   @Override
                   public void onSuccess(BaseResponse<String> response) {
                   }
               });
        addSubscription(sub);
         }
    public void register(String username, String password, String captcha){
        Subscription suib=manger.registerByPhone(username,password,captcha)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginInfo>>(getUiInterface()) {
                    @Override
                    public void onSuccess(BaseResponse<LoginInfo> response) {
                        LocalDataManger.getInstance().saveLoginInfo(response.getData());
                        getUiInterface().gotomain();

                    }
                });
        addSubscription(suib);
    }


}
