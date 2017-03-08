package com.vincent.hss.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;

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

public class SettingActivity extends BaseActivity {

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;
    @BindView(R.id.setting_clv_head)
    CircleImageView settingClvHead;
    @BindView(R.id.setting_tv_nickname)
    TextView settingTvNickname;
    @BindView(R.id.setting_tv_phone)
    TextView settingTvPhone;
    @BindView(R.id.setting_ll_msg)
    LinearLayout settingLlMsg;
    @BindView(R.id.setting_ll_device_manager)
    LinearLayout settingLlDeviceManager;
    @BindView(R.id.setting_ll_change_password)
    LinearLayout settingLlChangePassword;
    @BindView(R.id.setting_ll_common)
    LinearLayout settingLlCommon;
    @BindView(R.id.setting_ll_feedback)
    LinearLayout settingLlFeedback;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setupWindowAnimations();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_reseda));
        commonTvTitle2.setText("设置");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupWindowAnimations() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setExitTransition(transition);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @OnClick()
    public void onClick() {
        finish();
    }

    @OnClick({R.id.common_rl_return_2,R.id.setting_ll_msg, R.id.setting_ll_device_manager,
            R.id.setting_ll_change_password, R.id.setting_ll_common, R.id.setting_ll_feedback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.setting_ll_msg:
                SystemMsgActivity.actionStart(SettingActivity.this);
                overridePendingTransition(R.anim.activity_start_join,R.anim.activity_close_exit);
                break;
            case R.id.setting_ll_device_manager:
                DeviceManagerActivity.actionStart(SettingActivity.this);
                break;
            case R.id.setting_ll_change_password:
                ChangeLoginPasswordActivity.actionStart(SettingActivity.this);
                break;
            case R.id.setting_ll_common:
                CommonActivity.actionStart(SettingActivity.this);
                break;
            case R.id.setting_ll_feedback:
                FeedbackActivity.actionStart(SettingActivity.this);
                break;
        }
    }
}
