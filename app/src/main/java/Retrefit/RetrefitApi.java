package Retrefit;

import Base.BasePresenter;
import Recharge.RechargeInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public interface RetrefitApi {

    @GET("user/getchargeoption")
    Observable<RechargeInfo> getRechargeInfo(@Query("type")int type,
                                                           @Query("token")String token);
}
