package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/14 18:41
 *
 * @version 1.0
 */

public class FamilyChatActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_rl_title)
    RelativeLayout commonRlTitle;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_chat);
        ButterKnife.bind(this);
        commonTvTitle.setText("家人互通");
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,FamilyChatActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.common_rl_return)
    public void onClick() {
        finish();
    }
}
