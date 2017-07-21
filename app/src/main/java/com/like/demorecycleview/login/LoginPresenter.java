package com.like.demorecycleview.login;

import android.content.Context;

import com.like.demorecycleview.SP.LocalDataManger;

import Base.BaseObserver;
import Base.BasePresenter;
import Base.BaseResponse;
import Base.BaseUiInterface;
import Retrefit.RetrefitManger;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class LoginPresenter extends BasePresenter<LoginInterface> {

    private RetrefitManger mretrefitManger;
    protected LoginPresenter(Context context,LoginInterface uiInterface) {
        super(uiInterface);
        mretrefitManger=new RetrefitManger(context);

    }

    public void login(String name, String type) {
        Subscription subscription = mretrefitManger.login(name, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginInfo>>(getUiInterface()) {
                    @Override
                    public void onSuccess(BaseResponse<LoginInfo> response) {
                        LocalDataManger.getInstance().saveLoginInfo(response.getData());
                        getUiInterface().logintomain();
                    }
                });
        addSubscription(subscription);
    }
}
