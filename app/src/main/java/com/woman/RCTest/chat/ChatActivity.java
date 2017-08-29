package com.woman.RCTest.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.woman.RCTest.R;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import rx.functions.Action1;

/**
 * Created by like on 2017/8/1 0001.
 */

public class ChatActivity extends Activity {
    private TextView tv_chat,tv_send_msg,jmui_title;
    private EditText edv_msg;
    private RecyclerView chatrecycview;
    private ChatJmAdapter mchatJmAdapter;
    private Conversation mconversation;
    private List<Message>mlist=new ArrayList<Message>(){};
    private String nickname,username,targetkey;
    private boolean mIsSingle = true;//单聊
    private Conversation mconv;
    private boolean iskeyboard=true;//键盘输入
    private boolean isvoice=false;//语音输入
    private ImageButton img_input_way;
    private Button btn_press_voice;
    InputMethodManager mimm;//输入管理器
    private int flag=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tv_chat= (TextView) findViewById(R.id.tv_chat_msg);
        tv_send_msg= (TextView) findViewById(R.id.editText_send);
        jmui_title= (TextView) findViewById(R.id.jmui_title);
        edv_msg= (EditText) findViewById(R.id.edv_msg);
        chatrecycview= (RecyclerView) findViewById(R.id.rc_chat);
        img_input_way= (ImageButton) findViewById(R.id.imb_chat_voice);
        btn_press_voice= (Button) findViewById(R.id.btn_press_say);
        this.mimm= (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        mchatJmAdapter=new ChatJmAdapter(mlist,ChatActivity.this);
        chatrecycview.setLayoutManager(new LinearLayoutManager(this));
        chatrecycview.setAdapter(mchatJmAdapter);
        //注册极光接收的广播
        JMessageClient.registerEventReceiver(this);
        //发送消息
        tv_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送文本消息
                Message msg=JMessageClient.createSingleTextMessage("RC10565",edv_msg.getText().toString());
                JMessageClient.sendMessage(msg);
                //将消息加进list
                mchatJmAdapter.addMsgToList(msg);
                setToBottom();
                edv_msg.setText("");
            }
        });

        RxView.clicks(img_input_way)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                       if (flag==0){
                           img_input_way.setBackgroundResource(R.drawable.jmui_voice);
                           edv_msg.setVisibility(View.VISIBLE);
                           btn_press_voice.setVisibility(View.GONE);
                           flag=1;
                           if (mimm!=null){
                               mimm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                           }
                       }
                       else if (flag==1){
                           img_input_way.setBackgroundResource(R.drawable.jmui_keyboard);
                           edv_msg.setVisibility(View.GONE);
                           btn_press_voice.setVisibility(View.VISIBLE);
                           flag=0;
                           if (mimm!=null){
                               mimm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                           }
                       }

                    }
                });
        RxView.longClicks(btn_press_voice)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        btn_press_voice.setText(R.string.pressed_finish);
                        Toast.makeText(getBaseContext(),"长按了",Toast.LENGTH_SHORT).show();
                    }
                });
        //获取传递过来的消息数据
        Intent intent=getIntent();
        nickname=intent.getStringExtra("nickname");
        username=intent.getStringExtra("targetId");
        targetkey=intent.getStringExtra("targetAppKey");
        if (!TextUtils.isEmpty(targetkey)){
            mconv=JMessageClient.getSingleConversation(username,targetkey);
            if (mconv!=null){
                UserInfo userInfo= (UserInfo) mconv.getTargetInfo();
                jmui_title.setText(userInfo.getUserName());
                mlist=mconv.getAllMessage();
                for (int i=0;i<mlist.size();i++){
                    Message msg=mlist.get(i);
                    mchatJmAdapter.addMsgToList(msg);
                    mchatJmAdapter.notifyDataSetChanged();
                }
            }
        }
        //滑动到底部
        setToBottom();

    }
    public void setToBottom(){
        chatrecycview.clearFocus();
        chatrecycview.post(new Runnable() {
            @Override
            public void run() {
                chatrecycview.scrollToPosition(mchatJmAdapter.getItemCount()-1);
            }
        });

    }
    //主线程处理消息接收
    public void onEventMainThread(MessageEvent event){
        Message msg=event.getMessage();
        if (msg!=null){
            switch (msg.getContentType()){
                case text:
                    //处理接收到的文字消息
                    mchatJmAdapter.addMsgToList(msg);
                    setToBottom();
                    mchatJmAdapter.notifyDataSetChanged();
                    Log.i("info", "onEventMainThread: "+mlist.size());
                    break;
                case voice:
                    mchatJmAdapter.addMsgToList(msg);
                    setToBottom();
                    mchatJmAdapter.notifyDataSetChanged();
                    break;
                case image:
                    mchatJmAdapter.addMsgToList(msg);
                    setToBottom();
                    mchatJmAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }
}
