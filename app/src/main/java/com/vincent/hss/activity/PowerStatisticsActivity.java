package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/15 20:24
 *
 * @version 1.0
 */

public class PowerStatisticsActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_statistics);
        ButterKnife.bind(this);
        commonTvTitle.setText("家庭用电分析");


    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PowerStatisticsActivity.class);
        context.startActivity(intent);
    }
}
