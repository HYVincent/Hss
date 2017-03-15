package com.vincent.hss.activity;

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
 * creation date: 2017/3/14 12:29
 *
 * @version 1.0
 */

public class NettyMsgDetailActivity extends BaseActivity {


    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.netty_msg_tv)
    TextView nettyMsgTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netty_msg_detail);
        ButterKnife.bind(this);
        commonTvTitle.setText("新消息");
        Intent intent = getIntent();
        nettyMsgTv.setText(intent.getStringExtra("content"));
    }

    @OnClick(R.id.common_rl_return)
    public void onClick() {
//        WelcomeActivity.actionStart(NettyMsgDetailActivity.this);
        finish();
    }
}
