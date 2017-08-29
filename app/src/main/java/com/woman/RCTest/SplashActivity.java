package com.woman.RCTest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.woman.RCTest.SP.LocalDataManger;
import com.woman.RCTest.login.LoginActivity;
import com.woman.RCTest.login.LoginInfo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class SplashActivity extends Activity implements SplashInterface {

    private SimpleDraweeView msimpledraweeview;
    private ImageView mimageview;
    private SplashPresenter msplashPresenter;
    //申请权限的请求码
    public static  final int COARSE_LOCATION_CODE=100;
    public  static final int FINE_LOCATION_CODE =101;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findview();
        init();
    }
    private void init() {
        //使用定时器延时5秒
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
            }
        }, 5000);
        //判断是否是6.0系统
       /* if (Build.VERSION.SDK_INT>=23){
            requestPermission();
        }*/
        msplashPresenter=new SplashPresenter(this,this);
        LoginInfo info = LocalDataManger.getInstance().getLoginInfo();
        if (info == null || TextUtils.isEmpty(info.getToken())) {
            this.authloginfail();
            return ;
        }
        else {
            msplashPresenter.autoLogin();
        }
    }
    private void findview() {
        msimpledraweeview= (SimpleDraweeView) findViewById(R.id.spv_splash_img);
        msimpledraweeview.setImageURI(Uri.parse(("res:///" + R.drawable.ic_splash)));
       // mimageview= (ImageView) findViewById(R.id.img_splash_ic_1);
       // mimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_splash));
    }
    @Override
    public void showNetworkException() {
    }
    @Override
    public void showUnknownException() {
    }
    @Override
    public void showDataException(String msg) {
    }
    @Override
    public void showLoadingComplete() {
    }
    @Override
    public Dialog showLoadingDialog() {
        return null;
    }
    @Override
    public void dismissLoadingDialog() {
    }
    @Override
    public void authloginfail() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void auathloginsuccess() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    //动态申请权限
    private void requestPermission() {
        //判断是否打开GPS开关
        LocationManager locationmanger= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationmanger.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this,"GPS未打开，定位可能获取失败",Toast.LENGTH_SHORT);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //没有权限则申请
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},COARSE_LOCATION_CODE);

        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //没有权限则申请
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_CODE);
        }

    }

    //请求权限回调
 /*   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==COARSE_LOCATION_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //请求成功
            }
            else {
                finish();
            }
        }
    }*/
}
