package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.adapter.FeedbackHostotyListAdapter;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.Feedback;
import com.vincent.hss.presenter.FeedbackHistoryPresenter;
import com.vincent.hss.presenter.controller.FeedbackHistoryController;
import com.vincent.hss.view.FeedbackHistoryItemListener;
import com.vincent.hss.view.PopupwindowUtils;
import com.vincent.hss.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 12:06
 *
 * @version 1.0
 */

public class FeedbackHistoryActivity extends BaseActivity implements FeedbackHistoryController.IView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.rlv_feed_history_list)
    RecyclerView rlvFeedHistoryList;
    @BindView(R.id.common_iv_no_content)
    ImageView commonIvNoContent;
    @BindView(R.id.common_tv_no_content)
    TextView commonTvNoContent;
    @BindView(R.id.rl_no_content)
    RelativeLayout rlNoContent;
    @BindView(R.id.srf_refresh)
    SwipeRefreshLayout srfRefresh;
    private FeedbackHistoryPresenter presenter;
    private FeedbackHostotyListAdapter adapter;
    private List<Feedback> listData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_history);
        ButterKnife.bind(this);
        srfRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        srfRefresh.setOnRefreshListener(this);
        commonTvTitle.setText("反馈记录");
        presenter = new FeedbackHistoryPresenter(this);
        initData();
    }

    private void initData() {
        adapter = new FeedbackHostotyListAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvFeedHistoryList.setLayoutManager(layoutManager);
        rlvFeedHistoryList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                srfRefresh.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        rlvFeedHistoryList.addItemDecoration(new SpaceItemDecoration(10));
        adapter.setOnClick(new FeedbackHistoryItemListener() {
            @Override
            public void onClick(View view, int postion) {
                Feedback feedback = listData.get(postion);
                PopupwindowUtils.showAlertDialog(FeedbackHistoryActivity.this,feedback.getTitle(),feedback.getContent(),"取消","关闭");
            }
        });
        fromServiceGetData();
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code, msg);
    }

    @Override
    public void showDialog() {
        srfRefresh.setRefreshing(true);
    }

    @Override
    public void closeDialog() {
        srfRefresh.setRefreshing(false);
    }

    @OnClick(R.id.common_rl_return)
    public void onClick() {
        finish();
    }

    private void fromServiceGetData() {
        commonTvNoContent.setText("正在获取数据...");
        presenter.getFeedbackHistory(BaseApplication.user.getPhone());
    }

    @Override
    public void showFeedbackHistory(List<Feedback> data) {
        if (data.size() > 0 && data != null) {
            listData = data;
            adapter.setListData(data);
            rlvFeedHistoryList.setAdapter(adapter);
            rlvFeedHistoryList.setVisibility(View.VISIBLE);
            rlNoContent.setVisibility(View.GONE);
        }else {
            showMsg(0,"没有提交过反馈");
            commonTvNoContent.setText("没有反馈记录");
        }
    }

    @Override
    public void requestError() {
        commonTvNoContent.setText("请求错误，服务器可能正在维护中...");
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FeedbackHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (listData.size() > 0 && listData != null) {
            //说明服务器有数据，刷新一下
            listData.clear();
            adapter.notifyDataSetChanged();
            rlNoContent.setVisibility(View.VISIBLE);
            rlvFeedHistoryList.setVisibility(View.GONE);
            fromServiceGetData();
        } else {
            fromServiceGetData();
        }
    }
}
