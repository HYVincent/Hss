package com.vincent.hss.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.bean.Feedback;
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

public class FeedbackHostotyListAdapter extends RecyclerView.Adapter<FeedbackHostotyListAdapter.FeedbackHostoryViewHolder> {

    private FeedbackHistoryItemListener listener;
    private List<Feedback> listData;
    private Context mContext;

    public FeedbackHostotyListAdapter(Context context){
        this.mContext = context;
    }

    public void setListData(List<Feedback> data){
        this.listData = data;
        notifyDataSetChanged();
    }

    public void setOnClick(FeedbackHistoryItemListener listener){
        this.listener = listener;
    }

    @Override
    public FeedbackHostoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_feedback_hostory,null);
        return new FeedbackHostoryViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(FeedbackHostoryViewHolder holder, int position) {
        Feedback feedback = listData.get(position);
        holder.itemTitle.setText(feedback.getTitle());
        holder.itemContent.setText(feedback.getContent());
        holder.itemTime.setText(feedback.getCreateTime());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class FeedbackHostoryViewHolder extends RecyclerView.ViewHolder{

        private TextView itemTitle,itemContent,itemTime;
        public FeedbackHostoryViewHolder(View itemView, final FeedbackHistoryItemListener listener) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_feedback_title);
            itemContent = (TextView)itemView.findViewById(R.id.item_feedback_content);
            itemTime = (TextView)itemView.findViewById(R.id.item_feeback_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getLayoutPosition());
                }
            });
        }
    }
}
