package com.woman.RCTest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.DaemonService;
import cn.jpush.android.service.PushService;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class BoardcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pushintent=new Intent(context,PushService.class);//启动极光推送的服务
        context.startService(pushintent);
        Intent demointent=new Intent(context, DaemonService.class);
        context.startService(demointent);

        //与极光推送服务连接的网络变化
        Bundle bundle=intent.getExtras();
        boolean connected=bundle.getBoolean(JPushInterface.EXTRA_CONNECTION_CHANGE,false);
        Log.i("info", "onReceive: "+connected);
        if (!connected){
            Intent pushintentv=new Intent(context,PushService.class);//启动极光推送的服务
            context.startService(pushintentv);
        }
        if (intent!=null&&intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network=manager.getActiveNetworkInfo();
            if (network==null){
                Toast.makeText(context,"网络连接异常",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
