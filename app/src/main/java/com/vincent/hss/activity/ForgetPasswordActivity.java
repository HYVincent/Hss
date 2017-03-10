package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.presenter.controller.ForgetPassworcController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/5 23:25
 *
 * @version 1.0
 */

public class ForgetPasswordActivity extends BaseActivity implements ForgetPassworcController.IView{

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.common_et_phone)
    EditText commonEtPhone;
    @BindView(R.id.common_tv_get_authcode)
    TextView commonTvGetAuthcode;
    @BindView(R.id.common_et_authcode)
    EditText commonEtAuthcode;
    @BindView(R.id.common_et_password)
    EditText commonEtPassword;
    @BindView(R.id.common_iv_show_password)
    ImageView commonIvShowPassword;
    @BindView(R.id.forget_password_btn_action)
    Button forgetPasswordBtnAction;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_reseda));
        ButterKnife.bind(this);
        commonTvTitle.setText("重置密码");
        commonIvShowPassword.setVisibility(View.GONE);
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, ForgetPasswordActivity.class));
    }


    @OnClick({R.id.common_rl_return, R.id.common_tv_get_authcode, R.id.forget_password_btn_action})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.common_tv_get_authcode:
                showMsg(0,"验证码功能暂未开放");
                break;
            case R.id.forget_password_btn_action:

                break;
        }
    }

    @Override
    public void showDialog() {
        super.showDialog();
    }

    @Override
    public void closeDialog() {
        super.closeDialog();
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code,msg);
    }

    @Override
    public void resetPasswordSuccess() {
        LoginActivity.actionStart(ForgetPasswordActivity.this,false);
        overridePendingTransition(R.anim.activity_start_join,R.anim.activity_close_exit);
        finish();
    }
}
