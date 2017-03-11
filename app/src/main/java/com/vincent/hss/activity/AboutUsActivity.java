package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
 * creation date: 2017/3/10 0:30
 *
 * @version 1.0
 */

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.about_us_tv_content)
    TextView aboutUsTvContent;
    @BindView(R.id.common_rl_title)
    RelativeLayout commonRlTitle;
    @BindView(R.id.about_us_img)
    Banner aboutUsImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        commonTvTitle.setText("关于我们");
        initBanner();
    }

    private void initBanner() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.family_icon_couples_room);
        images.add(R.drawable.family_icon_couples_room);
        images.add(R.drawable.family_icon_couples_room);
        //设置图片加载器
        aboutUsImg.setImageLoader(new GlideImageLoader());
        //设置图片集合
        aboutUsImg.setImages(images).start();
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        aboutUsImg.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        aboutUsImg.stopAutoPlay();
    }

    @OnClick(R.id.common_rl_return)
    public void onClick() {
        finish();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

}
