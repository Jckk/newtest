package com.woman.RCTest;

import android.content.Context;
import android.text.TextUtils;

import com.woman.RCTest.SP.LocalDataManger;
import com.woman.RCTest.login.LoginInfo;

import Base.BaseObserver;
import Base.BasePresenter;
import Base.BaseResponse;
import Retrefit.RetrefitManger;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class SplashPresenter extends BasePresenter<SplashInterface> {
    private RetrefitManger manger;
    protected SplashPresenter(Context context,SplashInterface uiInterface) {
        super(uiInterface);
        manger=new RetrefitManger(context);
    }

    public void autoLogin() {
        LoginInfo info = LocalDataManger.getInstance().getLoginInfo();
        if (info == null || TextUtils.isEmpty(info.getToken())) {
            getUiInterface().authloginfail();
            return ;
        }
        Subscription subscription = manger.authlogin(info.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginInfo>>(getUiInterface()) {
                    @Override
                    public void onSuccess(BaseResponse<LoginInfo> response) {
                        //update data, e.g. token, balance, etc
                        LocalDataManger.getInstance().saveLoginInfo(response.getData());
                        getUiInterface().auathloginsuccess();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        getUiInterface().authloginfail();
                    }

                    @Override
                    protected void onDataFailure(BaseResponse<LoginInfo> response) {
                        super.onDataFailure(response);
                        getUiInterface().authloginfail();
                    }
                });
        addSubscription(subscription);
    }
}
