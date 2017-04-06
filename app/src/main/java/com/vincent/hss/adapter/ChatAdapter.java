package com.vincent.hss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vincent.hss.R;
import com.vincent.hss.config.Config;
import com.vincent.lwx.netty.msg.ChatMsg;
import com.vise.log.ViseLog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 18:14
 *
 * @version 1.0
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    //发送出去的消息
    public static final int MSG_TYPE_SEND = 1;
    //接收到的消息
    public static final int MSG_TYPE_ACCEPT = 0;

    private List<ChatMsg> chatDatas;

    public void setChatDatas(List<ChatMsg> chatDatas) {
        this.chatDatas = chatDatas;
        notifyDataSetChanged(); //全局更新
//        notifyItemChanged(); 局部更新
    }

    public ChatAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case MSG_TYPE_SEND:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_chatmsg_send,null);
                holder = new ChatSendViewHolder(view);
                break;
            case MSG_TYPE_ACCEPT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_chatmsg_accept,null);
                holder = new ChatAcceptViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        ChatMsg chatMsg = chatDatas.get(position);
        switch (type){
            case MSG_TYPE_SEND:
                setSendData((ChatSendViewHolder)holder,chatMsg,position);
                break;
            case MSG_TYPE_ACCEPT:
                setAcceptData((ChatAcceptViewHolder)holder,chatMsg,position);
                break;
        }
    }
    /**
     * 接收到的数据
     * @param holder
     * @param chatMsg
     * @param position
     */
    private void setAcceptData(ChatAcceptViewHolder holder, ChatMsg chatMsg, int position) {
        Glide.with(mContext).load(Config.QQ_SHARE_LOGO).into(holder.clvHead);
        holder.tvChatContent.setText(chatMsg.getChatContent());
    }

    /**
     * 设置数据 发出去的
     * @param holder
     * @param chatMsg
     * @param position
     */
    private void setSendData(ChatSendViewHolder holder, ChatMsg chatMsg, int position) {
        Glide.with(mContext).load(Config.QQ_SHARE_LOGO).into(holder.clvHead);
        holder.tvChatContent.setText(chatMsg.getChatContent());
    }


    @Override
    public int getItemViewType(int position) {
        ChatMsg chatMsg = chatDatas.get(position);
        if(chatMsg.getMsgType().equals("1")){//自己发的
            return MSG_TYPE_SEND;
        }else {//别人发给我的
            return MSG_TYPE_ACCEPT;
        }
    }


    @Override
    public int getItemCount() {
        return chatDatas.size();
    }

    class ChatSendViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView clvHead;
        private TextView tvChatContent;

        public ChatSendViewHolder(View itemView) {
            super(itemView);
            clvHead = (CircleImageView)itemView.findViewById(R.id.chat_send_clv_head);
            tvChatContent = (TextView)itemView.findViewById(R.id.chat_send_content);
        }
    }

    class ChatAcceptViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView clvHead;
        private TextView tvChatContent;

        public ChatAcceptViewHolder(View itemView) {
            super(itemView);
            clvHead = (CircleImageView)itemView.findViewById(R.id.chat_accept_clv_head);
            tvChatContent = (TextView)itemView.findViewById(R.id.chat_accept_content);
        }
    }

}
