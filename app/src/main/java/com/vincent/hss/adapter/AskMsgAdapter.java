package com.vincent.hss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.view.CommonOnClickListener;
import com.vincent.lwx.netty.msg.AskMessage;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/17 20:22
 *
 * @version 1.0
 */

public class AskMsgAdapter extends RecyclerView.Adapter<AskMsgAdapter.AskMsgViewHolder> {

    private Context mContent;
    private List<AskMessage> listData;
    private CommonOnClickListener clickListener;


    public AskMsgAdapter(Context mContent) {
        this.mContent = mContent;
    }

    public void setListData(List<AskMessage> listData) {
        this.listData = listData;
    }

    public void setClickListener(CommonOnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public AskMsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContent == null){
            mContent = parent.getContext();
        }
        View view = LayoutInflater.from(mContent).inflate(R.layout.item_ask_layout,null);
        return new AskMsgViewHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(AskMsgViewHolder holder, int position) {
        AskMessage askMessage = listData.get(position);
        holder.tvFromPhone.setText(askMessage.getFromPhone());
        holder.tvMsgContent.setText(askMessage.getMsgContent());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class AskMsgViewHolder extends RecyclerView.ViewHolder{

        private TextView tvFromPhone,tvMsgContent;

        public AskMsgViewHolder(View itemView, final CommonOnClickListener listener) {
            super(itemView);
            tvFromPhone = (TextView)itemView.findViewById(R.id.tv_from_phone);
            tvMsgContent = (TextView)itemView.findViewById(R.id.tv_msgcontent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getLayoutPosition());
                }
            });
        }
    }
}
