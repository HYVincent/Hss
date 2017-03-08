package com.vincent.hss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.bean.Feedback;
import com.vincent.hss.bean.SystemMsg;
import com.vincent.hss.view.CommonOnClickListener;
import com.vincent.hss.view.FeedbackHistoryItemListener;

import java.util.List;

/**
 * description ：
 * project name：MyAppProject
 * author : Vincent
 * creation date: 2017/3/1 13:56
 *
 * @version 1.0
 */

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.FeedbackHostoryViewHolder> {

    private CommonOnClickListener listener;
    private List<SystemMsg> listData;
    private Context mContext;

    public MsgListAdapter(Context context){
        this.mContext = context;
    }

    public void setListData(List<SystemMsg> data){
        this.listData = data;
        notifyDataSetChanged();
    }

    public void setOnClick(CommonOnClickListener listener){
        this.listener = listener;
    }

    @Override
    public FeedbackHostoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_msg,null);
        return new FeedbackHostoryViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(FeedbackHostoryViewHolder holder, int position) {
        SystemMsg msg = listData.get(position);
        holder.msgTitle.setText(msg.getMsgTitle());
        holder.msgContent.setText(msg.getMsgContent());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class FeedbackHostoryViewHolder extends RecyclerView.ViewHolder{

        private TextView msgTitle,msgContent;

        public FeedbackHostoryViewHolder(View itemView, final CommonOnClickListener listener) {
            super(itemView);
            msgTitle = (TextView)itemView.findViewById(R.id.item_msg_title);
            msgContent = (TextView)itemView.findViewById(R.id.item_msg_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getLayoutPosition());
                }
            });
        }
    }
}
