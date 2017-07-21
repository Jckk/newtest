package Retrefit;

import com.like.demorecycleview.login.LoginInfo;

import Base.BasePresenter;
import Base.BaseResponse;
import Recharge.RechargeInfo;
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
}
