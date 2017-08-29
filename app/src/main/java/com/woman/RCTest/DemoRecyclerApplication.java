package com.woman.RCTest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.woman.RCTest.chat.ChatNotify;
import com.woman.baidulocation.LocationService;
import com.woman.RCTest.SP.SpHelper;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class DemoRecyclerApplication extends Application {
    public LocationService locationService;
    public static final String TARGET_ID = "targetId";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static final String GROUP_ID = "groupId";
    public static final String CONV_TITLE = "conv_title";
    public static String PICTURE_DIR = "sdcard/xingmoxiu/pictures/";
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);//Fresco框架初始化
        SpHelper.init(this);//sharedprefence初始化
        locationService=new LocationService(getApplicationContext());//百度定位初始化
        //极光IM初始化
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this,true);//ture启动消息漫游记录
        new ChatNotify(getApplicationContext());//极光通知栏初始化
        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


}
