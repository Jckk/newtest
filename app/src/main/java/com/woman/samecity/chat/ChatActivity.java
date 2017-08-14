package com.woman.samecity.chat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.woman.samecity.R;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tv_chat= (TextView) findViewById(R.id.tv_chat_msg);
        tv_send_msg= (TextView) findViewById(R.id.editText_send);
        jmui_title= (TextView) findViewById(R.id.jmui_title);
        edv_msg= (EditText) findViewById(R.id.edv_msg);
        chatrecycview= (RecyclerView) findViewById(R.id.rc_chat);
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
                Message msg=JMessageClient.createSingleTextMessage("RC10584",edv_msg.getText().toString());
                JMessageClient.sendMessage(msg);
                //将消息加进list
                mchatJmAdapter.addMsgToList(msg);
                edv_msg.setText("");
            }
        });
    }
    //主线程处理消息接收
    public void onEventMainThread(MessageEvent event){
        Message msg=event.getMessage();
        TextContent txt= (TextContent) msg.getContent();
        String txtmsg=txt.getText();
        if (msg!=null){
            switch (msg.getContentType()){
                case text:
                    //处理接收到的文字消息
                    mchatJmAdapter.addMsgToList(msg);
                    mchatJmAdapter.notifyDataSetChanged();
                    Log.i("info", "onEventMainThread: "+mlist.size());
                    break;
            }
        }
    }
    //主线程处理通知栏消息
    public void onEventMainThread(NotificationClickEvent notificationClickEvent){
        Message notimsg=notificationClickEvent.getMessage();
        if (notimsg!=null){
            switch (notimsg.getContentType()){
                case text:
                    //处理接收到的文字消息
                    mchatJmAdapter.addMsgToList(notimsg);
                    mchatJmAdapter.notifyDataSetChanged();
                    Log.i("info", "onEventMainThreadnoti: "+mlist.size());
                    break;
            }
        }
    }
    //离线消息
    public void onEventMainThread(OfflineMessageEvent offlineMessageEvent){
        Conversation notimsg=offlineMessageEvent.getConversation();
        Message nomsg= (Message) notimsg.getAllMessage();
        if (notimsg!=null){
            switch (notimsg.getLatestType()){
                case text:
                    //处理接收到的文字消息
                    mchatJmAdapter.addMsgToList(nomsg);
                    mchatJmAdapter.notifyDataSetChanged();
                    Log.i("info", "onEventMainThreadoffline: "+mlist.size());
                    break;
            }
        }
    }







    //子线程处理通知栏消息
   public void onEvent(NotificationClickEvent notificationClickEvent){
      /*  Message notifymsg=notificationClickEvent.getMessage();
       MessageContent content=notifymsg.getContent();
        if (notifymsg!=null){
            switch (notifymsg.getContentType()){
                case text:
                    //处理接收到的文字消息
                    mchatJmAdapter.addMsgToList(notifymsg);
                    mchatJmAdapter.notifyDataSetChanged();
                    Log.i("info", "onEventThreadnotifymsg: "+mlist.size());
                    break;
            }
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }
}
