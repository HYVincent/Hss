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
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/14 18:08
 *
 * @version 1.0
 */

public class CarHomeInternetActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.common_rl_title)
    RelativeLayout commonRlTitle;
    @BindView(R.id.car_home_detail)
    LinearLayout carHomeDetail;
    @BindView(R.id.car_home_road)
    LinearLayout carHomeRoad;
    @BindView(R.id.car_home_contact)
    LinearLayout carHomeContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_home_internet);
        ButterKnife.bind(this);
        commonTvTitle.setText("家车互联");
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CarHomeInternetActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.common_rl_return, R.id.car_home_detail, R.id.car_home_road, R.id.car_home_contact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.car_home_detail:
                FamilyDetailActivity.actionStart(CarHomeInternetActivity.this);
                break;
            case R.id.car_home_road:
                FamilyAndCarConnectionActivity.actionStart(CarHomeInternetActivity.this);
                break;
            case R.id.car_home_contact:
                FamilyChatActivity.actionStart(CarHomeInternetActivity.this);
                break;
        }
    }
}
