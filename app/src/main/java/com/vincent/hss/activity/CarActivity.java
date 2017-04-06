package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 10:23
 *
 * @version 1.0
 */

public class CarActivity extends BaseActivity {

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.car_top_banner)
    Banner carTopBanner;
    @BindView(R.id.car_info)
    TextView carInfo;
    @BindView(R.id.car_error_code)
    TextView carErrorCode;
    @BindView(R.id.car_service_example)
    TextView carServiceExample;
    @BindView(R.id.car_weixin)
    TextView carWeixin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        commonTvTitle2.setText("汽车服务平台");
        carouselImg();
    }

    private void carouselImg() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.common_icon_car);
        images.add(R.drawable.common_icon_car);
        images.add(R.drawable.common_icon_car);
        //设置图片加载器
        carTopBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        carTopBanner.setImages(images).start();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CarActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.car_info, R.id.car_error_code, R.id.car_service_example, R.id.car_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.car_info:
                CarInfoActivity.actionStart(CarActivity.this);
                break;
            case R.id.car_error_code:
                showMsg(0,"正在开发中");
                break;
            case R.id.car_service_example:
                showMsg(0,"正在开发中");
                break;
            case R.id.car_weixin:
                showMsg(0,"正在开发中");
                break;
        }
    }
}
