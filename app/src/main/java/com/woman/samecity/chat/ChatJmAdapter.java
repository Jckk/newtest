package com.woman.samecity.chat;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.woman.samecity.R;


import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by like on 2017/8/9 0009.
 */

public class ChatJmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<Message> mMsgList = new ArrayList<Message>();//所有消息列表
    //文本
    private final int TYPE_SEND_TXT = 0;
    private final int TYPE_RECEIVE_TXT = 1;
    // 图片
    private final int TYPE_SEND_IMAGE = 2;
    private final int TYPE_RECEIVER_IMAGE = 3;

    public  ChatJmAdapter(List<Message>list,Activity activity){
        super();
        this.mMsgList=list;
        this.activity=activity;
    }
    //根据不同的条目返回不同的布局类型
    @Override
    public int getItemViewType(int position) {
        Message msg=mMsgList.get(position);
        switch (msg.getContentType()){
            case  text:
                Log.i("info", "getItemViewType: "+msg.getDirect());
                return msg.getDirect()==MessageDirect.send ?TYPE_SEND_TXT:TYPE_RECEIVE_TXT;
            case image:
                return msg.getDirect()==MessageDirect.send ?TYPE_SEND_IMAGE:TYPE_RECEIVER_IMAGE;
            default:
                break;
        }
        return super.getItemViewType(position);
    }
    //根据不同的类型返回不同的holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType==TYPE_SEND_TXT){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.jmui_send_txt,parent,false);
            return new SendTxtViewHolder(view);
        }
        else if (viewType==TYPE_RECEIVE_TXT){
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.jmui_receive_txt,parent,false);
            return new ReceiveTxtViewHolder(view);
        }
        else if (viewType==TYPE_SEND_IMAGE){
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.jmui_send_image,parent,false);
            return new ReceiveTxtViewHolder(view);
        }
        else if (viewType==TYPE_RECEIVER_IMAGE){
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.jmui_receive_image,parent,false);
            return new ReceiveImageViewHolder(view);
        }
        return null;
    }
    //绑定数据的方法
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //发送文字
        if (holder instanceof SendTxtViewHolder){
            TextContent txt= (TextContent) mMsgList.get(position).getContent();
            String txtmsg=txt.getText();
            ((SendTxtViewHolder)holder).tv_send_txt_msg.setText(txtmsg);

            // 两条消息时间离得如果稍长，显示时间
           /* if (position == 0) {
            }
            else {
                    if (DateUtils.isCloseEnough(list.get(position).getMsgTime(), list.get(position - 1).getMsgTime())) {
                    ((SendTxtViewHolder) holder).tv_send_txt_date.setVisibility(View.GONE);
                    }
                    else {
                    ((SendTxtViewHolder) holder).tv_send_txt_date.setText(DateUtils.getTimestampString(new Date(list.get(position).getMsgTime())));
                    ((SendTxtViewHolder) holder).tv_send_txt_date.setVisibility(View.VISIBLE);
                }
            }*/
            //((SendTxtViewHolder)holder).tv_send_txt_msg.setText("1");
        }
        //发送图片
        else if (holder instanceof SendImageViewHolder){
        }
        //接收文字
        else if (holder instanceof ReceiveTxtViewHolder){
            TextContent txt= (TextContent) mMsgList.get(position).getContent();
            String rectxt=txt.getText();
            ((ReceiveTxtViewHolder)holder).tv_rec_txt.setText(rectxt);
        }
        //接收图片
        else if (holder instanceof ReceiveImageViewHolder){
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
    public void addMsgToList(Message msg) {
        mMsgList.add(msg);
        notifyDataSetChanged();
    }
    public void addtxttolist(String s){

    }
    //以下四个内部类为定义的不同的holder

    //发送文本
    class SendTxtViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_send_txt_msg,tv_send_txt_date;
        public SendTxtViewHolder(View view){
            super(view);
            tv_send_txt_msg= (TextView) view.findViewById(R.id.tv_item_send_txt);
            tv_send_txt_date= (TextView) view.findViewById(R.id.tv_send_msg_date);
        }
    }
    //发送图片
    class SendImageViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_send_img_date;
        private ImageView imv_send_img;
        public SendImageViewHolder(View view){
            super(view);
            tv_send_img_date= (TextView) view.findViewById(R.id.tv_send_msg_Image_date);
            imv_send_img= (ImageView) view.findViewById(R.id.iv_send_image);
        }
    }
    //接收文本、
    class ReceiveTxtViewHolder extends RecyclerView.ViewHolder{
        public  TextView tv_rec_txt_date,tv_rec_txt;
        private ImageView img_rec_img_avator;
        public ReceiveTxtViewHolder(View view){
            super(view);
            tv_rec_txt_date= (TextView) view.findViewById(R.id.tv_from_msg_date);
            tv_rec_txt= (TextView) view.findViewById(R.id.tv_item_from_txt);
            img_rec_img_avator= (ImageView) view.findViewById(R.id.from_person_avator);
        }
    }
    //接收图片
    class ReceiveImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_item_from_image;
        private ImageView from_person_avator;

        public ReceiveImageViewHolder(View view) {
            super(view);
            iv_item_from_image = (ImageView) view.findViewById(R.id.iv_from_image);
            from_person_avator = (ImageView) view.findViewById(R.id.from_person_avator);
        }
    }
}


