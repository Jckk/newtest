package com.woman.RCTest.chat;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woman.RCTest.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>implements View.OnClickListener,View.OnLongClickListener{

    private Activity activity;
    private List<Message> mMsgList = new ArrayList<Message>();//所有消息列表
    private List<Conversation>mconv=new ArrayList<Conversation>();
    //文本
    private final int TYPE_SEND_TXT = 0;
    private final int TYPE_RECEIVE_TXT = 1;
    // 图片
    private final int TYPE_SEND_IMAGE = 2;
    private final int TYPE_RECEIVER_IMAGE = 3;
    private onItemClickListener onItemClickListener=null;

    public  ChatListAdapter(List<Conversation>list,Activity activity){
        super();
        this.mconv=list;
        this.activity=activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.jmui_chat_list_item,parent,false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Conversation mconvsa=mconv.get(position);
        Message newmsg=mconvsa.getLatestMessage();
        String title=mconvsa.getTitle();
        String unreadmsg= String.valueOf(mconvsa.getUnReadMsgCnt());
        holder.itemView.setTag(position);
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String curdate=formatter.format(curDate);
        switch (newmsg.getContentType()){
            case text:
                TextContent txt= (TextContent) newmsg.getContent();
                String txtmsg=txt.getText();
                ( (ChatListViewHolder)holder).tv_chat_content.setText(txtmsg);
                ( (ChatListViewHolder)holder).tv_chat_titie.setText(title);
                ( (ChatListViewHolder)holder).tv_chat_unread.setText(unreadmsg);
                ( (ChatListViewHolder)holder).tv_chat_time.setText(curdate);
                break;
            case image:
                break;
            case voice:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        if (onItemClickListener!=null){
            onItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }
    @Override
    public boolean onLongClick(View v) {
        if (onItemClickListener!=null){
            onItemClickListener.onItemLongClick(v, (Integer) v.getTag());
        }
        return true;
    }

    public static interface onItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        this.onItemClickListener=listener;
    }
    @Override
    public int getItemCount() {
        return mconv.size();
    }
    class ChatListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_chat_titie,tv_chat_content,tv_chat_time,tv_chat_unread;
        public ChatListViewHolder(View itemView) {
            super(itemView);
            tv_chat_titie= (TextView) itemView.findViewById(R.id.tv_chat_title);
            tv_chat_content= (TextView) itemView.findViewById(R.id.tv_chat_content);
            tv_chat_time= (TextView) itemView.findViewById(R.id.tv_chat_time);
            tv_chat_unread= (TextView) itemView.findViewById(R.id.tv_chat_unread);
        }
    }
    //将会话加入列表
    public void addMsgToList(Conversation conv){
        mconv.add(conv);
        notifyDataSetChanged();
    }
}
