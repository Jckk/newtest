package com.woman.RCTest.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.woman.RCTest.DemoRecyclerApplication;
import com.woman.RCTest.R;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class ChatListActivity extends Activity {
    private TextView tv_ignore_read;
    private RecyclerView rc_chat_list;
    private ChatListAdapter mchatListAdapter;

    private List<Conversation>mconv=new ArrayList<Conversation>(){};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        tv_ignore_read= (TextView) findViewById(R.id.ignore_unread);
        rc_chat_list= (RecyclerView) findViewById(R.id.chat_list_recycview);
        mchatListAdapter=new ChatListAdapter(mconv,ChatListActivity.this);
        rc_chat_list.setLayoutManager(new LinearLayoutManager(this));
        rc_chat_list.setAdapter(mchatListAdapter);
        //注册接收
        JMessageClient.registerEventReceiver(this);
        //初始化接收
        try {
            mconv= JMessageClient.getConversationList();
            for (int i=0;i<mconv.size();i++){
                Conversation conversation=mconv.get(i);
                mchatListAdapter.addMsgToList(mconv.get(i));
                mchatListAdapter.notifyDataSetChanged();
            }
        }
        catch(NullPointerException e){
        }
        //item的监听
        mchatListAdapter.setOnItemClickListener(new ChatListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Intent intent=new Intent();
                Conversation con=mconv.get(position);
                if (con!=null){//获取会话的信息
                    String targetusername=((UserInfo)con.getTargetInfo()).getUserName();
                    intent.putExtra(DemoRecyclerApplication.TARGET_ID,targetusername);
                    intent.putExtra(DemoRecyclerApplication.TARGET_APP_KEY,con.getTargetAppKey());
                    intent.putExtra("nickname",((UserInfo)con.getTargetInfo()).getNickname());
                    intent.setClass(ChatListActivity.this,ChatActivity.class);
                    startActivity(intent);
                }
                con.resetUnreadCount();
                mchatListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ChatListActivity.this,"长按了",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //主线程处理消息接收
    public void onEventMainThread(MessageEvent event){
        Message msg=event.getMessage();
        if (msg!=null){
            mchatListAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

}
