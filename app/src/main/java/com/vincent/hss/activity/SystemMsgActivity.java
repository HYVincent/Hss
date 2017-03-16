package com.vincent.hss.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.adapter.MsgListAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.bean.SystemMsg;
import com.vincent.hss.presenter.SystemMsgPresenter;
import com.vincent.hss.presenter.controller.SystemMsgController;
import com.vincent.hss.view.CommonOnClickListener;
import com.vincent.hss.view.WindowUtils;
import com.vincent.hss.view.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 17:49
 *
 * @version 1.0
 */

public class SystemMsgActivity extends BaseActivity implements SystemMsgController.IView,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.common_rl_title)
    RelativeLayout commonRlTitle;
    @BindView(R.id.common_iv_no_content)
    ImageView commonIvNoContent;
    @BindView(R.id.common_tv_no_content)
    TextView commonTvNoContent;
    @BindView(R.id.rl_no_content)
    RelativeLayout rlNoContent;
    @BindView(R.id.rlv_msg_content)
    RecyclerView rlvMsgContent;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.view_root)
    LinearLayout llRoot;
    private SystemMsgPresenter presenter =null;
    private MsgListAdapter adapter;
    private List<SystemMsg> listData;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ButterKnife.bind(this);
        activity =this;
        commonTvTitle.setText("系统消息");
        commonTvNoContent.setText("没有系统消息");
        presenter = new SystemMsgPresenter(this);
        adapter = new MsgListAdapter(this);
        srlRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        srlRefresh.setOnRefreshListener(this);
        adapter.setOnClick(new CommonOnClickListener() {
            @Override
            public void onClick(View view, int position) {
//                showMsg(0,"啥也没有");
                SystemMsg systemMsg = listData.get(position);
                WindowUtils.showAlertDialog(activity,systemMsg.getMsgTitle(),systemMsg.getMsgContent(),"取消","确定");
            }
        });
        initData();
    }


    private void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvMsgContent.setLayoutManager(layoutManager);
        rlvMsgContent.addItemDecoration(new SpaceItemDecoration(10));
        requestData();
    }

    private void requestData(){
        //模拟数据
        if(listData!=null){
            listData.clear();
            adapter.notifyDataSetChanged();
            rlvMsgContent.setVisibility(View.GONE);
            rlNoContent.setVisibility(View.VISIBLE);
            commonTvNoContent.setText("正在请求数据");
        }
        presenter.getMsg();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SystemMsgActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showDialog() {
       srlRefresh.setRefreshing(true);
    }

    @Override
    public void closeDialog() {
        srlRefresh.setRefreshing(false);
    }

    @OnClick(R.id.common_rl_return)
    public void onClick() {
        finish();
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }

    @Override
    public void refreshMsg(List<SystemMsg> data) {
        if(data != null&&data.size()>0){
            listData = data;
            adapter.setListData(data);
            rlvMsgContent.setAdapter(adapter);
            rlvMsgContent.setVisibility(View.VISIBLE);
            rlNoContent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        requestData();
    }
}
