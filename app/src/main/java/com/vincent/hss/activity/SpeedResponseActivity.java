package com.vincent.hss.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.base.BaseActivity;


/**
 * description ：
 * project name：MyAppProject
 * author : Vincent
 * creation date: 2017/2/21 7:58
 *
 * @version 1.0
 */

public class SpeedResponseActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTranslucent(this, 0);
        WelcomeActivity.actionStart(SpeedResponseActivity.this);
        finish();
    }
}
