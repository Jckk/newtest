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
                      /*  //注册极光
                        String jimname="RC"+LocalDataManger.getInstance().getLoginInfo().getUserId();
                        String jimpass="RC"+LocalDataManger.getInstance().getLoginInfo().getUserId();
                        JMessageClient.register(jimname, jimpass, new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i==0){
                                    Log.i("info", "gotResult:"+"极光注册成功");
                                }
                                else {
                                    if (s.equals("user exist")){
                                        Log.i("info", "gotResult: "+"极光用户已经存在");
                                    }
                                }
                            }
                        });*/

                    }
                });
        addSubscription(suib);
    }


}
