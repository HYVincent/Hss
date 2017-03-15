package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/14 18:35
 *
 * @version 1.0
 */

public class FamilyDetailActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.videoview)
    VideoView videoview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_detail);
        ButterKnife.bind(this);
        commonTvTitle.setText("家中情景");

    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,FamilyDetailActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.common_rl_return)
    public void onClick() {
        finish();
    }
}
