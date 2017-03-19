package com.vincent.hss.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.vincent.hss.R;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/16 20:58
 *
 * @version 1.0
 */

public class ShowUpdateDialog extends Dialog {

    private TextView tvShowUpdateDesc,tvCancelUpdate,tvDownUpdate;
    private String updateDesc;//更新描述
    private CommonOnClickListener listener;

    public void setOnClickListener(CommonOnClickListener listener) {
        this.listener = listener;
    }

    public void setUpdateDesc(String updateDesc){
        this.updateDesc = updateDesc;
    }

    public ShowUpdateDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_show_update);
        tvShowUpdateDesc = (TextView)findViewById(R.id.tv_show_update_desc);
        tvCancelUpdate = (TextView)findViewById(R.id.tv_cancel_down);
        tvDownUpdate = (TextView)findViewById(R.id.tv_down_update);
        tvShowUpdateDesc.setText(updateDesc);
        tvCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvDownUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v,0);
            }
        });
    }
}
