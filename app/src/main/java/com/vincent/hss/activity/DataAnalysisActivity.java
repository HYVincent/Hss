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
import lecho.lib.hellocharts.view.LineChartView;

/**
 * description ：数据分析
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 10:24
 *
 * @version 1.0
 */

public class DataAnalysisActivity extends BaseActivity {

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.data_chart)
    LineChartView dataChart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);
        ButterKnife.bind(this);
        commonTvTitle2.setText("数据分析");
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DataAnalysisActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.common_rl_return_2)
    public void onClick() {
        finish();
    }
}
