package Retrefit;

import com.woman.RCTest.login.LoginInfo;

import Base.BaseResponse;
import Recharge.RechargeInfo;
import Recharge.wechatpay.WxpayInfo;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public interface RetrefitApi {

    @GET("user/getchargeoption")
    Observable<BaseResponse<RechargeInfo>> getRechargeInfo(@Query("type")int type,
                                                          @Query("token")String token);

    @GET("SMS/sendSMS")
    Observable<BaseResponse<String>> sendCaptcha(@Query("phone") String phone);

    @POST("user/registerByPhone")
    Observable<BaseResponse<LoginInfo>> registerByPhone(@Query("username") String username,
                                                        @Query("password") String password,
                                                        @Query("captcha") String captcha);

    @POST("user/loginphone")
    Observable<BaseResponse<LoginInfo>> login(@Query("username") String username,
                                              @Query("password") String password);

    @POST("user/autoLogin")
    Observable<BaseResponse<LoginInfo>> autoLogin(@Query("token") String token);

    @GET("payment/aliPay")
    Observable<BaseResponse<String>> RechargeAlipay(@Query("num") String account);
    @GET("payment/appWeixin")
    Observable<BaseResponse<WxpayInfo>>RechargeWechat(@Query("token")String token,
                                                      @Query("num")String num);


}
