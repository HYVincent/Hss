package com.vincent.hss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.view.CommonOnClickListener;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/10 11:11
 *
 * @version 1.0
 */

public class CommonSingleTextAdapter extends RecyclerView.Adapter<CommonSingleTextAdapter.RoomClassListViewHolder> {
    private Context context;
    private List<String> data;
    private CommonOnClickListener clickListener;

    public CommonSingleTextAdapter(Context context) {
        this.context = context;
    }

    public void setClickListener(CommonOnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RoomClassListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context == null){
            parent.getContext();
        }
        return new RoomClassListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_popupwindow_layout,parent,false),clickListener);
    }

    @Override
    public void onBindViewHolder(RoomClassListViewHolder holder, int position) {
        String text = data.get(position);
        holder.itemContent.setText(text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RoomClassListViewHolder extends RecyclerView.ViewHolder{

        private TextView itemContent;

        public RoomClassListViewHolder(View itemView, final CommonOnClickListener listener) {
            super(itemView);
            itemContent = (TextView)itemView.findViewById(R.id.item_popupwindow_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getLayoutPosition());
                }
            });
        }
    }
}
