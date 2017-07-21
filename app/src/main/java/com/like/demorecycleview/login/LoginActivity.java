package com.like.demorecycleview.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.like.demorecycleview.MainActivity;
import com.like.demorecycleview.R;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class LoginActivity extends Activity implements LoginInterface {
    private EditText ed_login_name, ed_login_pass;
    private Button btn_login,btn_register;
    private LoginPresenter loginpresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_select);
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
        btn_login= (Button) findViewById(R.id.login_by_username);
        btn_register= (Button) findViewById(R.id.btn_register);
    }

    private void init() {
        loginpresenter=new LoginPresenter(this,this);

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

    }
}
