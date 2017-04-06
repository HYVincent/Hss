package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.view.SimpleLineChart;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.slc_data)
    SimpleLineChart slcData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_statistics);
        ButterKnife.bind(this);
        commonTvTitle.setText("家庭用电分析");
//        Glide.with(this).load(R.drawable.icon_power_statistics).into(iv);
        String[] xItem = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
                "25", "26", "27", "28", "29", "30", "31"};
        String[] yItem = {"80℃", "70℃", "60℃", "50℃", "40℃"};
        slcData.setXItem(xItem);
        slcData.setYItem(yItem);
        HashMap<Integer, Integer> pointMap = new HashMap();
        for (int i = 0; i < xItem.length; i++) {
            pointMap.put(i, (int) (Math.random() * 5));
        }
        slcData.setData(pointMap);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PowerStatisticsActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.common_rl_return)
    public void onViewClicked() {
        finish();
    }
}
