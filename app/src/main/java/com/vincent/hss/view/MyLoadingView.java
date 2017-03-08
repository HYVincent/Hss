package com.vincent.hss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vincent.hss.R;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 0:25
 *
 * @version 1.0
 */

public class MyLoadingView extends AlertDialog {

    private Context mContext;
    private View view;
    private TextView tvText;

    public MyLoadingView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading_view,null);
        tvText = (TextView)view.findViewById(R.id.tv_loading);
    }

    public void setTvText(String msg){
        if(tvText !=null ){
            tvText.setText(msg);
        }
    }

    protected MyLoadingView(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected MyLoadingView(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
