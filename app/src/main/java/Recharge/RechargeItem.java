package Recharge;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class RechargeItem {
    @SerializedName("diamond")
    private String currencyAmount;
    private String msg;
    @SerializedName("rmb")
    private String rmbAmount;
    private String present;

    public String getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRmbAmount() {
        return rmbAmount;
    }

    public void setRmbAmount(String rmbAmount) {
        this.rmbAmount = rmbAmount;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

}
