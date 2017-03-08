package com.vincent.hss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vincent.hss.R;
import com.vincent.hss.bean.Room;
import com.vincent.hss.view.RoomListItemOnClickListener;

import java.util.List;

/**
 * description ：房间适配器
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 19:24
 *
 * @version 1.0
 */

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomListViewHolder> {

    private Context mContext;
    private List<Room> data ;
    private RoomListItemOnClickListener itemOnClickListener;

    public RoomListAdapter(Context context){
        this.mContext = context;
    }

    public void setItemOnClickListener(RoomListItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public void setData(List<Room> data) {
        this.data = data;
    }

    @Override
    public RoomListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null ){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_room,null);
        return new RoomListViewHolder(view,itemOnClickListener);
    }

    @Override
    public void onBindViewHolder(RoomListViewHolder holder, int position) {
        Room room = data.get(position);
        holder.tvRoomName.setText(room.getRoomName());
        Glide.with(mContext).load(room.getRomImg()).into(holder.lvRoomImg);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RoomListViewHolder extends RecyclerView.ViewHolder{

        private ImageView lvRoomImg;
        private TextView tvRoomName;

        public RoomListViewHolder(View itemView, final RoomListItemOnClickListener listItemOnClickListener) {
            super(itemView);
            lvRoomImg = (ImageView)itemView.findViewById(R.id.room_item_img);
            tvRoomName = (TextView)itemView.findViewById(R.id.room_item_room_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemOnClickListener.onClick(v,getLayoutPosition());
                }
            });
        }
    }
}
