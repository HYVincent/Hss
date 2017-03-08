package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 21:30
 *
 * @version 1.0
 */

public class CarRL1Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rl_1);
    }

    public static void actionStart(Context context,String title,String url){
        Intent intent = new Intent(context,CarRL1Activity.class);
        context.startActivity(intent);
    }

}
