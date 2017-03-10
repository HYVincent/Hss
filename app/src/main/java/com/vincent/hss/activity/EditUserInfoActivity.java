package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/9 18:14
 *
 * @version 1.0
 */

public class EditUserInfoActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.user_info_et_user_nickname)
    TextView userInfoEtUserNickname;
    @BindView(R.id.user_info_et_user_sex)
    TextView userInfoEtUserSex;
    @BindView(R.id.user_info_et_user_birthday)
    TextView userInfoEtUserBirthday;
    @BindView(R.id.user_info_et_user_live_status)
    TextView userInfoEtUserLiveStatus;
    @BindView(R.id.user_info_submit_alter)
    Button userInfoSubmitAlter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_user_info);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.color_reseda));
        commonTvTitle.setText("编辑个人资料");
        userInfoEtUserBirthday.setText(BaseApplication.user.getBirthday());
        userInfoEtUserLiveStatus.setText(BaseApplication.user.getLive_status());
        userInfoEtUserNickname.setText(BaseApplication.user.getNickname());
        userInfoEtUserSex.setText(BaseApplication.user.getSex());
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.common_rl_return, R.id.user_info_submit_alter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.user_info_submit_alter:
                //TODO 请求网络 修改资料
                showMsg(0,"尚未开发,请等待..");
                break;
        }
    }
}
