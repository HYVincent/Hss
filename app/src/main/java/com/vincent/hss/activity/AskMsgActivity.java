package com.vincent.hss.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.adapter.AskMsgAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.view.SpaceItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/17 19:49
 *
 * @version 1.0
 */

public class AskMsgActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.rlv_ask_msg)
    RecyclerView rlvAskMsg;

    private AskMsgAdapter adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_msg);
        ButterKnife.bind(this);
        commonTvTitle.setText("验证消息列表");
        adapter = new AskMsgAdapter(this);
        initRecycleView();
    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvAskMsg.setLayoutManager(layoutManager);
        rlvAskMsg.addItemDecoration(new SpaceItemDecoration(10));
    }

    @OnClick(R.id.common_rl_return)
    public void onClick() {
        finish();
    }
}
