package com.vincent.hss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.User;
import com.vincent.hss.presenter.AskMsgDetailPresenter;
import com.vincent.hss.presenter.controller.AskMsgDetailController;
import com.vise.log.ViseLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/17 20:56
 *
 * @version 1.0
 */

public class AskMsgDetailActivity extends BaseActivity implements AskMsgDetailController.IView{

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
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
    @BindView(R.id.tv_repulse)
    TextView tvRepulse;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private String fromPhone;
    private AskMsgDetailPresenter presenter;
    private String msgContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_msg_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        fromPhone = intent.getStringExtra("fromPhone");
        commonTvTitle.setText("来自"+fromPhone+"的添加请求");
        msgContent = intent.getStringExtra("content");
        tvContent.setText(msgContent);
        presenter = new AskMsgDetailPresenter(this);
        presenter.getAskInfo(fromPhone);
    }

    @OnClick({R.id.common_rl_return, R.id.tv_repulse, R.id.tv_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.tv_repulse:
                finish();
                break;
            case R.id.tv_agreement:
                //请求添加好友
                ViseLog.d("familyPhone--->"+fromPhone);
                presenter.agreeAddToFamilyList(BaseApplication.user.getPhone(),fromPhone,msgContent);
                break;
        }
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }

    @Override
    public void refreshAskInfo(User user) {
        if(user!=null){
            userInfoEtUserBirthday.setText(user.getBirthday());
            userInfoEtUserLiveStatus.setText(user.getLive_status());
            userInfoEtUserNickname.setText(user.getNickname());
            userInfoEtUserSex.setText(user.getSex());
            userInfoEtUserPhone.setText(user.getPhone());
        }else {
            userInfoEtUserBirthday.setText("获取失败");
            userInfoEtUserLiveStatus.setText("获取失败");
            userInfoEtUserNickname.setText("获取失败");
            userInfoEtUserSex.setText("获取失败");
            userInfoEtUserPhone.setText("获取失败");
        }
    }

    @Override
    public void agreeAdd() {
        finish();
    }
}
