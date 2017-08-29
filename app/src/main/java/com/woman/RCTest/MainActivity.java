package com.woman.RCTest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.jakewharton.rxbinding.view.RxView;
import com.woman.RCTest.chat.ChatActivity;
import com.woman.RCTest.chat.ChatListActivity;
import com.woman.baidulocation.LocationService;
import com.woman.RCTest.SP.LocalDataManger;
import com.woman.RCTest.login.LoginActivity;

import Util.Packages;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import rx.functions.Action1;

public class MainActivity extends Activity {
    private static final String TAG = "info";
    private Button btn_recharge,btn_loginout;
    private TextView tv_version,tv_baidu_location,tv_chat_with_service,tv_id,tv_chat_list;//版本，定位，客服，用户id,聊天列表
    //定位，只使用一次，用后即焚
    private LocationService mlocationService;
    private ResponseLocationListener mresponseLocationListener=new ResponseLocationListener();
    private String mcity,mprovince,mcountry,maddress;
    //申请权限的请求码
    public static  final int COARSE_LOCATION_CODE=100;
    public  static final int FINE_LOCATION_CODE =101;
    //极光
    private boolean JIMR=false;
    private boolean JIML=false;
    private String jimname,jimpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //判断是否是6.0系统
        if (Build.VERSION.SDK_INT>=23){
            requestPermission();
        }
        //一进来就定位请求
        requestlocation();
        init();//极光登录注册的操作
        btn_recharge= (Button) findViewById(R.id.btn_recyclerview);
        btn_loginout= (Button) findViewById(R.id.btn_loginout);
        tv_version= (TextView) findViewById(R.id.app_vesion);
        tv_baidu_location= (TextView) findViewById(R.id.baidulbs);
        tv_chat_with_service= (TextView) findViewById(R.id.tv_chat_with_service);
        tv_id= (TextView) findViewById(R.id.tv_id);
        tv_chat_list= (TextView) findViewById(R.id.tv_chat_list);
        tv_version.setText(getString(R.string.version, Packages.getVersionName(this)));
        //tv_id.setText(LocalDataManger.getInstance().getLoginInfo().getUserId());
        btn_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DemoRecyclerView.class);
                startActivity(intent);
            }
        });
        btn_loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDataManger.getInstance().clearLoginInfo();
                Intent intent =new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        RxView.clicks(tv_chat_with_service)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent =new Intent(MainActivity.this, ChatActivity.class);
                        startActivity(intent);
                    }
                });
        RxView.clicks(tv_chat_list)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent=new Intent(MainActivity.this, ChatListActivity.class);
                        startActivity(intent);

                    }
                });

    }

    private void init() {
        //jimname="RC"+LocalDataManger.getInstance().getLoginInfo().getUserId();
        //jimpass="RC"+LocalDataManger.getInstance().getLoginInfo().getUserId();
        //jimname="meilibo8406";
        //jimpass="meilibo8406";
        //jimname="meilibo8398";
        //jimpass="meilibo8398";
        jimname="RC10584";
        jimpass="RC10584";
        //jimname="RC10584";
        //jimpass="RC10584";

        if (JMessageClient.getMyInfo()==null){
            for (int i=0;i<3;i++){
                JIMregister();
                if (JIMR=true){
                    break;
                }
            }
            if (JIMR=true){
                for (int j=0;j<3;j++){
                    JIMlogin();
                    if (JIML=true){
                        break;
                    }
                }
            }
        }
    }

    private void JIMlogin() {
        JMessageClient.login(jimname, jimpass, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i==0){
                    JIML=true;
                    //Log.i("info","jg"+"极光登录成功");
                    Log.i(TAG, "gotResult: "+"极光登录成功");
                }
            }
        });
    }

    private void JIMregister() {
        JMessageClient.register(jimname, jimpass, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i==0){
                    JIMR=true;
                    //Log.i("info","jg"+"极光注册成功");
                    Log.i(TAG, "gotResult: "+"极光注册成功");
                }
                else {
                    if (s.equals("user exist")){
                        JIMR=true;
                       // Log.i("info","jg"+"极光账号已经存在");
                        Log.i(TAG, "gotResult: "+"极光账号已经存在");
                    }
                }
            }
        });

    }

    //动态申请权限
    private void requestPermission() {
        //判断是否打开GPS开关
        LocationManager locationmanger= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationmanger.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this,"GPS未打开，定位可能获取失败",Toast.LENGTH_SHORT);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //没有权限则申请
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},COARSE_LOCATION_CODE);

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //没有权限则申请
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_CODE);
        }
    }
    //请求权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //定位请求回调接口
    private class ResponseLocationListener implements BDLocationListener {
        //定位请求回调函数
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation!=null&&bdLocation.getLocType()!=BDLocation.TypeServerError){//TypeServerError定位失败，没有对应的位置信息
                mcity=bdLocation.getCity();
                mprovince=bdLocation.getProvince();
                mcountry=bdLocation.getCountry();
                maddress=bdLocation.getAddrStr();
                stopLocationservice();//关闭
                handler.sendEmptyMessage(1);

            }
            Log.i("info", "city: "+bdLocation.getCity());
            Log.i("info", "address: "+bdLocation.getAddrStr());
        }
        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }
    //定位请求
    private void requestlocation(){
        mlocationService=((DemoRecyclerApplication)getApplication()).locationService;
        mlocationService.registerListener(mresponseLocationListener);//注册定位监听函数
        mlocationService.setLocationOption(mlocationService.getDefaultLocationClientOption());//配置定位SDK各配置参数，比如定位模式、定位时间间隔、坐标系类型等
        mlocationService.start();//启动定位sdk
    }
    //尝试关闭定位服务，如果已经关闭则什么也不做
    private void stopLocationservice(){
        if (mlocationService!=null){
            mlocationService.unregisterListener(mresponseLocationListener);
            mlocationService.stop();//停止定位sdk
            mlocationService=null;
            mresponseLocationListener=null;
        }

    }
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                tv_baidu_location.setText(maddress);
            }

        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationservice();//关闭定位服务
    }
}
