package com.vincent.hss.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.config.Config;

import java.util.ArrayList;

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

    private static final int IMAGE_PICKER = 1111;
    @BindView(R.id.setting_ll_user_info)
    LinearLayout settingLlUserInfo;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setupWindowAnimations();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_reseda));
        commonTvTitle2.setText("设置");
        settingTvNickname.setText(BaseApplication.user.getNickname());
        settingTvPhone.setText(BaseApplication.user.getPhone());
        String user_head_img = BaseApplication.getShared().getString(Config.USER_HEAD_IMG,"");
        if(!TextUtils.isEmpty(user_head_img)){
            Glide.with(this).load(user_head_img).into(settingClvHead);
        }

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


    @OnClick({R.id.setting_clv_head, R.id.common_rl_return_2, R.id.setting_ll_msg, R.id.setting_ll_device_manager,
            R.id.setting_ll_change_password, R.id.setting_ll_common, R.id.setting_ll_feedback,R.id.setting_ll_user_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_clv_head:
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.setting_ll_msg:
                SystemMsgActivity.actionStart(SettingActivity.this);
                overridePendingTransition(R.anim.activity_start_join, R.anim.activity_close_exit);
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
            case R.id.setting_ll_user_info:
                UserInfoActivity.actionStart(SettingActivity.this);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                /*MyAdapter adapter = new MyAdapter(images);
                gridView.setAdapter(adapter);*/
                BaseApplication.getShared().putString(Config.USER_HEAD_IMG,images.get(0).path);
                Glide.with(this).load(images.get(0).path).into(settingClvHead);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.setting_ll_user_info)
    public void onClick() {
    }
}
