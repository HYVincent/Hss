package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.rl_3)
    RelativeLayout rl3;
    @BindView(R.id.rl_4)
    RelativeLayout rl4;
    @BindView(R.id.rl_5)
    RelativeLayout rl5;
    @BindView(R.id.rl_6)
    RelativeLayout rl6;
    @BindView(R.id.rl_7)
    RelativeLayout rl7;
    @BindView(R.id.rl_8)
    RelativeLayout rl8;
    @BindView(R.id.car_top_banner)
    Banner carTopBanner;
    @BindView(R.id.clv_3)
    CircleImageView clv3;
    @BindView(R.id.clv_4)
    CircleImageView clv4;
    @BindView(R.id.clv_5)
    CircleImageView clv5;
    @BindView(R.id.clv_6)
    CircleImageView clv6;
    @BindView(R.id.clv_7)
    CircleImageView clv7;
    @BindView(R.id.clv_8)
    CircleImageView clv8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_reseda));
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

    @OnClick({R.id.common_rl_return_2, R.id.rl_1, R.id.rl_2, R.id.rl_3, R.id.rl_4, R.id.rl_5, R.id.rl_6, R.id.rl_7, R.id.rl_8})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.rl_1:
                showMsg(0, "我也不知道写啥");
                break;
            case R.id.rl_2:
                showMsg(0, "我也不知道写啥");
                break;
            case R.id.rl_3:
                showMsg(0, "我也不知道写啥");
                break;
            case R.id.rl_4:
                showMsg(0, "我也不知道写啥");
                break;
            case R.id.rl_5:
                showMsg(0, "我也不知道写啥");
                break;
            case R.id.rl_6:
                showMsg(0, "我也不知道写啥");
                break;
            case R.id.rl_7:
                showMsg(0, "我也不知道写啥");
                break;
            case R.id.rl_8:
                showMsg(0, "我也不知道写啥");
                break;
        }
    }
}
