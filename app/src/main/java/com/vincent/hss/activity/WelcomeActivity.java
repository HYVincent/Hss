package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.config.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/5 23:35
 *
 * @version 1.0
 */

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this, 0);
//        welcomeLvImg.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.icon_speed_start));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if(BaseApplication.getShared().getBoolean(Config.IS_LOGIN)){
                        HomeActivity.actionStart(WelcomeActivity.this);
                    }else{
                        LoginActivity.actionStart(WelcomeActivity.this);
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void actionStart(Context context){
        Intent intent = new Intent(context,WelcomeActivity.class);
        context.startActivity(intent);
    }
}
