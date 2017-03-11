package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.presenter.ChangeLoginPasswordPresenter;
import com.vincent.hss.presenter.controller.ChangeLoginPasswordController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 13:36
 *
 * @version 1.0
 */

public class ChangeLoginPasswordActivity extends BaseActivity implements ChangeLoginPasswordController.IView {

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn2;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle2;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;
    @BindView(R.id.password_iv_old_password)
    ImageView passwordIvOldPassword;
    @BindView(R.id.password_iv_new_password)
    ImageView passwordIvNewPassword;
    @BindView(R.id.password_et_new_password)
    EditText passwordEtNewPassword;
    @BindView(R.id.password_iv_new_password_ok)
    ImageView passwordIvNewPasswordOk;
    @BindView(R.id.password_et_new_password_ok)
    EditText passwordEtNewPasswordOk;
    @BindView(R.id.password_et_old_password)
    EditText passwordEtOldPassword;

    private ChangeLoginPasswordPresenter passwordPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        commonTvTitle2.setText("修改登录密码");
        commonTitleRight.setText("提交");
        commonTitleRight.setVisibility(View.VISIBLE);
        passwordPresenter = new ChangeLoginPasswordPresenter(this);
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code, msg);
    }

    @Override
    public void changePasswordSuccess() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void oldPassowrdError() {
        showMsg(0,"当前密码错误,请修改后重试");
        passwordIvOldPassword.setImageDrawable(getDrawable(R.drawable.common_icon_old_password_error));
        passwordEtOldPassword.setTextColor(ContextCompat.getColor(this,R.color.color_red));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void newPasswordDiff() {
        showMsg(0,"两次密码不一致");
        passwordIvNewPasswordOk.setImageDrawable(getDrawable(R.drawable.common_icon_old_password_error));
        passwordEtNewPasswordOk.setTextColor(ContextCompat.getColor(this,R.color.color_red));
    }

    @OnClick({R.id.common_rl_return_2, R.id.common_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.common_title_right:
                passwordPresenter.changePassword(BaseApplication.user.getPhone(),passwordEtOldPassword.getText().toString().trim(),
                    passwordEtNewPassword.getText().toString().trim(),passwordEtNewPasswordOk.getText().toString().trim());
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

    public static void actionStart(Context context){
        Intent intent = new Intent(context,ChangeLoginPasswordActivity.class);
        context.startActivity(intent);
    }
}
