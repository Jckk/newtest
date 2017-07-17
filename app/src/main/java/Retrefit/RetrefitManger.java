package Retrefit;

import android.content.Context;

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
   /* public Observable<PlugControl> PlugControl(String macname, String plugstate){
        return api.PlugControl(macname,plugstate);
    }
    public Observable<PlugTimeControl>PlugTimeControl(String macname,String time,String timemark,String controlstate){
        return api.PlugTimeControl(macname,time,timemark,controlstate);
    }
    public Observable<String>Plugswitch(String macname,String plugstate){
        return api.Plugswitch(macname,plugstate);
    }*/
   public  Observable<RechargeInfo>rechargeinfo(int type, String token){
       return  api.getRechargeInfo(type,token);
   }
}
