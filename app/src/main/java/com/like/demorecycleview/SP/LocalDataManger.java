package com.like.demorecycleview.SP;

import android.icu.text.LocaleDisplayNames;

import com.like.demorecycleview.login.LoginInfo;

/**
 * Created by Administrator on 2017/7/21 0021.
 */
 //管理本地数据的存储，抽象解耦非简单配置项的本地数据存储位置（File、DB、SP），例如用户信息。
public class LocalDataManger {
    //声明对象
    public static LocalDataManger instance;
    public LocalDataManger(){}
    //实例化LocalDataManager的对象
    public static  LocalDataManger getInstance(){
        if (instance==null){
            synchronized (LocalDataManger.class){
                if (instance==null){
                    instance=new LocalDataManger();
                }
            }
        }
        return instance;
    }
    private LoginInfo mloginInfo;
    public void saveLoginInfo(LoginInfo loginInfo){
        mloginInfo=loginInfo;
        SpHelper.setLoginInfo(loginInfo);//保存用户的登录信息
        //WsObjectPool.init(loginInfo);
    }

    public void clearLoginInfo(){
        mloginInfo = null;
        SpHelper.removeLoginInfo();
       // WsObjectPool.release();
    }
    //查询登录的存储信息
    public LoginInfo getLoginInfo() {
        if (mloginInfo == null) {
            mloginInfo = SpHelper.getloginInfo();
        }
        return mloginInfo;
    }

}
