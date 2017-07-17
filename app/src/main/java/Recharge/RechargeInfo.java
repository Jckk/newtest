package Recharge;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class RechargeInfo {

    @SerializedName("coinbalance")
    private double coinBalance;
    private List<RechargeItem>list;


    public List<RechargeItem> getList() {
        return list;
    }

    public void setList(List<RechargeItem> list) {
        this.list = list;
    }
    public double getCoinBalance() {
        return coinBalance;
    }

    public void setCoinBalance(double coinBalance) {
        this.coinBalance = coinBalance;
    }
}
