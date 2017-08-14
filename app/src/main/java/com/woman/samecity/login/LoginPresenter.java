package com.woman.samecity.login;

import android.content.Context;

import com.woman.samecity.SP.LocalDataManger;

import Base.BaseObserver;
import Base.BasePresenter;
import Base.BaseResponse;
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
                     /*   //极光登录
                        String jimname="RC"+LocalDataManger.getInstance().getLoginInfo().getUserId();
                        String jimpass="RC"+LocalDataManger.getInstance().getLoginInfo().getUserId();
                        JMessageClient.login(jimname, jimpass, new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i==0){
                                    Log.i("info", "gotResult: "+"极光登录成功");
                                }
                            }
                        });*/

                    }
                });
        addSubscription(subscription);
    }
}
