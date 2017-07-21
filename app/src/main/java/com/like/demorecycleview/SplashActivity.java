package com.like.demorecycleview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.util.TimeUtils;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.like.demorecycleview.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class SplashActivity extends Activity {

    private SimpleDraweeView msimpledraweeview;
    private ImageView mimageview;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findview();
        init();
    }
    private void init() {
        //使用定时器延时0.5毫秒
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }, 500);
    }
    private void findview() {
        msimpledraweeview= (SimpleDraweeView) findViewById(R.id.spv_splash_img);
        msimpledraweeview.setImageURI(Uri.parse(("res:///" + R.drawable.ic_splash)));
       // mimageview= (ImageView) findViewById(R.id.img_splash_ic_1);
       // mimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_splash));
    }
}
