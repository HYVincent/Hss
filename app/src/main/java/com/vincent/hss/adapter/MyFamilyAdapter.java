package com.vincent.hss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vincent.hss.R;
import com.vincent.hss.bean.Family;
import com.vincent.hss.view.CommonOnClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * description ：
 * project name：MyAppProject
 * author : Vincent
 * creation date: 2017/3/1 13:56
 *
 * @version 1.0
 */

public class MyFamilyAdapter extends RecyclerView.Adapter<MyFamilyAdapter.MyFamilyListViewHolder> {

    private CommonOnClickListener listener;
    private List<Family> listData;
    private Context mContext;

    public MyFamilyAdapter(Context context){
        this.mContext = context;
    }

    public void setListData(List<Family> data){
        this.listData = data;
        notifyDataSetChanged();
    }

    public void setOnClick(CommonOnClickListener listener){
        this.listener = listener;
    }

    @Override
    public MyFamilyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_family,null);
        return new MyFamilyListViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(MyFamilyListViewHolder holder, int position) {
        Family family = listData.get(position);
        Glide.with(mContext).load("http://182.254.232.121:8080/Image/family_head.jpg").into(holder.clvHead);
        holder.nickname.setText(family.getRemark());
        holder.liveStatus.setText(family.getRemark());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyFamilyListViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView clvHead;

        private TextView nickname,liveStatus;

        public MyFamilyListViewHolder(View itemView, final CommonOnClickListener listener) {
            super(itemView);
            nickname = (TextView)itemView.findViewById(R.id.item_family_nickname);
            liveStatus = (TextView)itemView.findViewById(R.id.item_family_status);
            clvHead = (CircleImageView)itemView.findViewById(R.id.item_family_head);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getLayoutPosition());
                }
            });
        }
    }
}
