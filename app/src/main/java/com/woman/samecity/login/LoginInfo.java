package com.woman.samecity.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class LoginInfo {
    private String token;
    private String nickname;
    @SerializedName("id")
    private String userId;
    @SerializedName("coinbalance")
    private double totalBalance;
    @SerializedName("curroomnum")
    private String currentRoomNum;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getCurrentRoomNum() {
        return currentRoomNum;
    }

    public void setCurrentRoomNum(String currentRoomNum) {
        this.currentRoomNum = currentRoomNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSnap() {
        return snap;
    }

    public void setSnap(String snap) {
        this.snap = snap;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWxunionid() {
        return wxunionid;
    }

    public void setWxunionid(String wxunionid) {
        this.wxunionid = wxunionid;
    }

    public String getApproveid() {
        return approveid;
    }

    public void setApproveid(String approveid) {
        this.approveid = approveid;
    }

    public String getUcuid() {
        return ucuid;
    }

    public void setUcuid(String ucuid) {
        this.ucuid = ucuid;
    }

    public double getBeanorignal() {
        return beanorignal;
    }

    public void setBeanorignal(double beanorignal) {
        this.beanorignal = beanorignal;
    }

    private String avatar;
    @SerializedName("emceelevel")
    private String level;
    private String snap;
    private String city;
    private String wxunionid;
    private String approveid;
    private String ucuid;
    private double beanorignal;

}
