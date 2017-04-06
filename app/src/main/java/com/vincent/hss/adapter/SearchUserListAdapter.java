package com.vincent.hss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vincent.hss.R;
import com.vincent.hss.bean.User;
import com.vincent.hss.view.CommonOnClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 7:11
 *
 * @version 1.0
 */

public class SearchUserListAdapter extends RecyclerView.Adapter<SearchUserListAdapter.SearchUserListViewHolder>{

    private CommonOnClickListener listener;
    private List<User> listData;
    private Context mContext;

    public SearchUserListAdapter(Context context){
        this.mContext = context;
    }

    public void setListData(List<User> data){
        this.listData = data;
        notifyDataSetChanged();
    }

    public void setOnClick(CommonOnClickListener listener){
        this.listener = listener;
    }

    @Override
    public SearchUserListAdapter.SearchUserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_user_layout,null);
        return new SearchUserListAdapter.SearchUserListViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(SearchUserListAdapter.SearchUserListViewHolder holder, int position) {
        User u = listData.get(position);
        Glide.with(mContext).load("http://182.254.232.121:8080/Image/family_head.jpg").into(holder.cirHead);
        holder.tvNickName.setText(u.getNickname());
        holder.tvLineStatus.setText(u.getLive_status());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class SearchUserListViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNickName,tvLineStatus;
        private CircleImageView cirHead;

        public SearchUserListViewHolder(View itemView, final CommonOnClickListener listener) {
            super(itemView);
            ;
            tvLineStatus = (TextView)itemView.findViewById(R.id.item_search_user_live_status);
            tvNickName = (TextView)itemView.findViewById(R.id.item_search_user_nickname);
            cirHead = (CircleImageView)itemView.findViewById(R.id.item_search_user_head);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getLayoutPosition());
                }
            });
        }
    }

}
