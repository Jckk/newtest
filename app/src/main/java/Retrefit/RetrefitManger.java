package Retrefit;

import android.content.Context;

import com.woman.samecity.login.LoginInfo;

import Base.BaseResponse;
import Recharge.RechargeInfo;
import Recharge.wechatpay.WxpayInfo;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class RetrefitManger {

    private RetrefitApi api;
    public RetrefitManger(Context context){
        this.api=RetrofitHelper.getInstance(context).getServer();
    }
    //充值列表
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
    //自动登录
    public Observable<BaseResponse<LoginInfo>>authlogin(String token){
        return api.autoLogin(token);
    }
    //支付宝
    public  Observable<BaseResponse<String>>RechargeAlipay(String amount){
        return api.RechargeAlipay(amount);
    }

    public Observable<BaseResponse<WxpayInfo>>RechargeWechat(String token,String num){
        return api.RechargeWechat(token,num);
    }
}
