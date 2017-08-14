package com.woman.samecity.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.woman.samecity.MainActivity;
import com.woman.samecity.R;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class RegisterActivity extends Activity implements RegisterInterface {

    private EditText ed_name,ed_pass,ed_verify;
    private Button btn_getverify,btn_register;
    private  RegisterPresenter regispresenter;
    String name,pass,verify;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        findview();
        click();
    }

    private void click() {



        btn_getverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=ed_name.getText().toString();
                pass=ed_pass.getText().toString();
                verify=ed_verify.getText().toString();
                regispresenter.getverifycode(name);

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=ed_name.getText().toString();
                pass=ed_pass.getText().toString();
                verify=ed_verify.getText().toString();
                regispresenter.register(name,pass,verify);
            }
        });
    }

    private void findview() {
        ed_name= (EditText) findViewById(R.id.ed_name);
        ed_pass= (EditText) findViewById(R.id.ed_pass);
        ed_verify= (EditText) findViewById(R.id.ed_verify);
        btn_getverify= (Button) findViewById(R.id.btn_get_verifycode);
        btn_register= (Button) findViewById(R.id.btn_register_1);
    }

    private void init() {
        regispresenter=new RegisterPresenter(this,this);
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
    public void gotomain() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
