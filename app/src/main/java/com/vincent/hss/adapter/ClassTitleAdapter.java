package com.vincent.hss.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.bean.ClassBean;
import com.vincent.hss.view.CommonOnClickListener;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 19:59
 *
 * @version 1.0
 */

public class ClassTitleAdapter extends RecyclerView.Adapter<ClassTitleAdapter.ClassTitleViewHolder> {

    private CommonOnClickListener listener;
    private List<ClassBean> data;
    private Context context;

    public  ClassTitleAdapter(Context context) {
        this.context = context;
    }

    public void setOnClick(CommonOnClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<ClassBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ClassTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_title,null);
        return new ClassTitleViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(ClassTitleViewHolder holder, int position) {
        ClassBean bean = data.get(position);
        holder.tvTitle.setText(bean.getTitle());
        holder.tvLine.setBackgroundColor(ContextCompat.getColor(context,bean.getColor()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ClassTitleViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle,tvLine;

        public ClassTitleViewHolder(View itemView, final CommonOnClickListener listener) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.item_class_text);
            tvLine = (TextView)itemView.findViewById(R.id.item_class_line);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getLayoutPosition());
                }
            });
        }
    }
}
