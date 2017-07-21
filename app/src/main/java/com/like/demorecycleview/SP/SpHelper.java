package com.like.demorecycleview.SP;

import android.content.Context;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.like.demorecycleview.login.LoginInfo;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class SpHelper {
    private static final String KEY_LOGIN_INFO = "loginInfo";
    private static final String PREFERENCE_FILE_NAME = "BeautyLivePrefs";
    private static final String KEY_IS_FIRST_RUN = "isFirstRun";


    public static void init(Context context){
        SharedPre.init(context.getApplicationContext(),PREFERENCE_FILE_NAME);
    }

//保存用户的登录数据。
    public static void setLoginInfo(LoginInfo loginInfo){
        String saveinfo=new Gson().toJson(loginInfo);
        SharedPre.set(KEY_LOGIN_INFO,loginInfo);
    }
    //判断是否是第一次登录
    public static boolean getIsFirstRun(){
        return  SharedPre.get(KEY_IS_FIRST_RUN,true);
    }
    //清空存储的登录数据

    public  static void removeLoginInfo(){
        SharedPre.remove(KEY_LOGIN_INFO);
    }
    //查询存储的用户登录数据，如果不存在则返回null。

    public static LoginInfo getloginInfo(){
        String saveinfo=SharedPre.getString(KEY_LOGIN_INFO);
        if (TextUtils.isEmpty(saveinfo)){
            return  null;
        }
        return new Gson().fromJson(saveinfo,LoginInfo.class);
    }

}
