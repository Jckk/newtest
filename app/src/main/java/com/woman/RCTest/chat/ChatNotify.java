package com.woman.RCTest.chat;

import android.content.Context;
import android.content.Intent;

import com.woman.RCTest.DemoRecyclerApplication;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class ChatNotify {
    private Context mcontext;
    public ChatNotify(Context context){
        mcontext=context;
        JMessageClient.registerEventReceiver(this);
    }
    /**
     * 收到消息处理
     * @param notificationClickEvent 通知点击事件
     */
    public void onEvent(NotificationClickEvent notificationClickEvent) {
        if (null == notificationClickEvent) {
            return;
        }
        Message msg = notificationClickEvent.getMessage();
        TextContent txt= (TextContent) msg.getContent();
        String txtmsg=txt.getText();
        if (msg != null) {
            String targetId = msg.getTargetID();
            String appKey = msg.getFromAppKey();
            ConversationType type = msg.getTargetType();// 	getTargetType()  获取消息发送对象的类型。单聊(Single)或者群聊(Group
            Conversation conv;
            Intent notificationIntent = new Intent(mcontext, ChatListActivity.class);
            //单聊
            if (type == ConversationType.single) {
                conv = JMessageClient.getSingleConversation(targetId, appKey);
                notificationIntent.putExtra(DemoRecyclerApplication.TARGET_ID, targetId);
                notificationIntent.putExtra(DemoRecyclerApplication.TARGET_APP_KEY, appKey);
            }
            //群聊
            else {
                conv = JMessageClient.getGroupConversation(Long.parseLong(targetId));
                notificationIntent.putExtra(DemoRecyclerApplication.GROUP_ID, Long.parseLong(targetId));
            }
            notificationIntent.putExtra(DemoRecyclerApplication.CONV_TITLE, conv.getTitle());
            conv.resetUnreadCount();
            //notificationIntent.putExtra("msg",msg);//把消息传过去;
            notificationIntent.putExtra("msgno",txtmsg);
            notificationIntent.putExtra("fromGroup", false);
         notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK//一定要这个flag才能正确跳转
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mcontext.startActivity(notificationIntent);
        }
    }
}
