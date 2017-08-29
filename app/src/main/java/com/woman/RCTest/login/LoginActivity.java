package com.woman.RCTest.login;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.woman.RCTest.MainActivity;
import com.woman.RCTest.R;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class LoginActivity extends Activity implements LoginInterface {
    private EditText ed_login_name, ed_login_pass;
    private TextView login_select_privacy;
    private Button btn_login,btn_register;
    private LoginPresenter loginpresenter;
    //申请权限的请求码
    public static  final int COARSE_LOCATION_CODE=100;
    public  static final int FINE_LOCATION_CODE =101;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_select);
        //判断是否是6.0系统
       /* if (Build.VERSION.SDK_INT>=23){
            requestPermission();
        }*/
        init();
        find();
        listener();
    }

    private void listener() {
        RxView.clicks(btn_register)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                        startActivity(intent);
                    }
                });

        RxView.clicks(btn_login)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String name,pass;
                        name=ed_login_name.getText().toString();
                        pass=ed_login_pass.getText().toString();
                        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pass)){
                            Toast.makeText(LoginActivity.this, "账户名或密码不能为空", Toast.LENGTH_SHORT).show();
                        }
                        loginpresenter.login(name,pass);
                    }
                });
    }
    private void find() {
        ed_login_name= (EditText) findViewById(R.id.edit_user_name);
        ed_login_pass= (EditText) findViewById(R.id.edit_pass);
        login_select_privacy= (TextView) findViewById(R.id.login_select_privacy);
        btn_login= (Button) findViewById(R.id.login_by_username);
        btn_register= (Button) findViewById(R.id.btn_register);
        login_select_privacy.setText(getString(R.string.login_select_privacy,getString(R.string.app_name)));
    }

    private void init() {
        loginpresenter=new LoginPresenter(this,this);

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
    public void logintomain() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
