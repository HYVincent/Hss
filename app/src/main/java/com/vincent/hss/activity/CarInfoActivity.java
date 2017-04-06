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
 * creation date: 2017/4/3 22:34
 *
 * @version 1.0
 */

public class CarInfoActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.car_rotate_speed)
    TextView carRotateSpeed;
    @BindView(R.id.car_speed)
    TextView carSpeed;
    @BindView(R.id.car_fuel_consumption)
    TextView carFuelConsumption;
    @BindView(R.id.car_air_temperature)
    TextView carAirTemperature;
    @BindView(R.id.car_into_air_consumption)
    TextView carIntoAirConsumption;
    @BindView(R.id.car_coolant_temperature)
    TextView carCoolantTemperature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        ButterKnife.bind(this);
        commonTvTitle.setText("汽车信息");
    }

    /**
     * 跳转到本Activity
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CarInfoActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.common_rl_return)
    public void onViewClicked() {
        finish();
    }
}
