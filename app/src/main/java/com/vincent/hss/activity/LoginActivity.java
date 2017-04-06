package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.presenter.LoginPresenter;
import com.vincent.hss.presenter.controller.LoginController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * description ：用户登录
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/5 23:25
 *
 * @version 1.0
 */

public class LoginActivity extends BaseActivity implements LoginController.IView {

    @BindView(R.id.login_tie_phone)
    TextInputEditText loginTiePhone;
    @BindView(R.id.login_tie_password)
    TextInputEditText loginTiePassword;
    @BindView(R.id.login_btn_action)
    Button loginBtnAction;
    @BindView(R.id.login_text_forget_password)
    TextView loginTextForgetPassword;
    @BindView(R.id.login_text_register)
    TextView loginTextRegister;
    @BindView(R.id.menu_user_head_clv)
    CircleImageView menuUserHeadClv;
    @BindView(R.id.tll_phone)
    TextInputLayout tllPhone;

    private LoginPresenter presenter;
    private long mExitTime = 0;

    /**
     * 初始化布局空间，onCrete方法，每个Activity第一个初始化的方法
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        Intent intent = getIntent();
        boolean isFinish = intent.getBooleanExtra("isFinish",false);
        if(isFinish){
            finshOther(this);
        }
    }

    @OnClick({R.id.login_btn_action, R.id.login_text_forget_password, R.id.login_text_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_action:
                presenter.login(loginTiePhone.getText().toString().trim(),loginTiePassword.getText().toString().trim());
                break;
            case R.id.login_text_forget_password:
                ForgetPasswordActivity.actionStart(LoginActivity.this);
                break;
            case R.id.login_text_register:
                RegisterActivity.actionStart(LoginActivity.this);
                break;
        }
    }

    public static void actionStart(Context context,boolean isFinish) {
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra("isFinish",isFinish);
        context.startActivity(intent);
    }

    @Override
    public void msg(int code, String msg) {
        super.showMsg(code, msg);
    }

    /**
     * 登录成功的处理
     */
    @Override
    public void loginSuccess() {
        HomeActivity.actionStart(LoginActivity.this);
        overridePendingTransition(R.anim.activity_start_join, R.anim.activity_close_exit);
        finish();
    }


    @Override
    public void onBackPressed() {
//        _exit();
        super.onBackPressed();
    }

    /**
     * 退出
     */
    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            showMsg(0, "再按一次退出APP");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
