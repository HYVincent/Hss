package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：显示用户信息
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/9 17:12
 *
 * @version 1.0
 */

public class UserInfoActivity extends BaseActivity {


    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;
    @BindView(R.id.user_info_et_user_id)
    TextView userInfoEtUserId;
    @BindView(R.id.user_info_et_user_phone)
    TextView userInfoEtUserPhone;
    @BindView(R.id.user_info_et_user_nickname)
    TextView userInfoEtUserNickname;
    @BindView(R.id.user_info_et_user_sex)
    TextView userInfoEtUserSex;
    @BindView(R.id.user_info_et_user_birthday)
    TextView userInfoEtUserBirthday;
    @BindView(R.id.user_info_et_user_live_status)
    TextView userInfoEtUserLiveStatus;
    @BindView(R.id.user_info_et_user_create_time)
    TextView userInfoEtUserCreateTime;
    @BindView(R.id.rl_common_title_1)
    RelativeLayout rlCommonTitle1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        commonTvTitle2.setText(BaseApplication.user.getNickname());
        commonTitleRight.setText("编辑");
        commonTitleRight.setVisibility(View.VISIBLE);
        userInfoEtUserId.setText(BaseApplication.user.getUser_id());
        userInfoEtUserNickname.setText(BaseApplication.user.getNickname());
        userInfoEtUserPhone.setText(BaseApplication.user.getPhone());
        userInfoEtUserSex.setText(BaseApplication.user.getSex());
        userInfoEtUserLiveStatus.setText(BaseApplication.user.getLive_status());
        userInfoEtUserCreateTime.setText(BaseApplication.user.getCreateTime());
    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.common_rl_return_2, R.id.common_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.common_title_right:
                EditUserInfoActivity.actionStart(UserInfoActivity.this);
                break;
        }
    }
}
