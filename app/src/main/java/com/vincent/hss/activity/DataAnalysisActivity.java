package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.data_power_statistics)
    LinearLayout dataPowerStatistics;
    @BindView(R.id.data_steal_statistics)
    LinearLayout dataStealStatistics;
    @BindView(R.id.data_car_bad_statistics)
    LinearLayout dataCarBadStatistics;
    @BindView(R.id.data_fuel_consumption_statistics)
    LinearLayout dataFuelConsumptionStatistics;


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


    @OnClick({R.id.common_rl_return_2,R.id.data_power_statistics, R.id.data_steal_statistics,
            R.id.data_car_bad_statistics, R.id.data_fuel_consumption_statistics})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.data_power_statistics:
                PowerStatisticsActivity.actionStart(DataAnalysisActivity.this);
                //用电统计
                break;
            case R.id.data_steal_statistics:
                //片区偷盗率
                break;
            case R.id.data_car_bad_statistics:
                //汽车故障率
                break;
            case R.id.data_fuel_consumption_statistics:
                //汽车油耗
                break;
        }
    }
}
