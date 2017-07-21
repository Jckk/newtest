package Retrefit;

import android.content.Context;

import com.like.demorecycleview.login.LoginInfo;

import Base.BaseResponse;
import Recharge.RechargeInfo;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class RetrefitManger {

    private RetrefitApi api;
    public RetrefitManger(Context context){
        this.api=RetrofitHelper.getInstance(context).getServer();
    }
   public  Observable<BaseResponse<RechargeInfo>>rechargeinfo(int type, String token){
       return  api.getRechargeInfo(type,token);
   }
    //发送验证码
    public Observable<BaseResponse<String>> sendCaptcha(String phone){
        return api.sendCaptcha(phone);
    }
    //注册新街口
    public Observable<BaseResponse<LoginInfo>> registerByPhone(String username, String password, String captcha){
        return api.registerByPhone(username, password, captcha);
    }

    //登录
    public Observable<BaseResponse<LoginInfo>> login(String username, String password){
        return api.login(username, password);
    }
}
