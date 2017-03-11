package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.presenter.RegisterPresenter;
import com.vincent.hss.presenter.controller.RegisterController;

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

public class RegisterActivity extends BaseActivity implements RegisterController.IView {


    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.register_et_phone)
    EditText registerEtPhone;
    @BindView(R.id.register_tv_get_authcode)
    TextView registerTvGetAuthcode;
    @BindView(R.id.register_et_authcode)
    EditText registerEtAuthcode;
    @BindView(R.id.register_et_password)
    EditText registerEtPassword;
    @BindView(R.id.register_iv_show_password)
    ImageView registerIvShowPassword;
    @BindView(R.id.register_btn_action)
    Button registerBtnAction;
    @BindView(R.id.register_cb_agreement)
    CheckBox registerCbAgreement;
    @BindView(R.id.register_tv_agreement)
    TextView registerTvAgreement;

    private RegisterPresenter presenter;
    private boolean isShow = false;//密码是否明文

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter = new RegisterPresenter(this);
        commonTvTitle.setText("注册");
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
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
        showMsg(code, msg);
    }

    @Override
    public void registerSuccess() {
        LoginActivity.actionStart(RegisterActivity.this,false);
        overridePendingTransition(R.anim.activity_start_join, R.anim.activity_close_exit);
        finish();
    }

    @OnClick({R.id.common_rl_return, R.id.register_tv_get_authcode, R.id.register_iv_show_password, R.id.register_btn_action,R.id.register_tv_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.register_tv_get_authcode:
                showMsg(0,"暂未开放验证码");
                break;
            case R.id.register_iv_show_password:
                if(!isShow){
                    registerEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//明文
                    Glide.with(RegisterActivity.this).load(R.drawable.register_password_blue).into(registerIvShowPassword);
                    isShow = true;
                }else {
                    registerEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Glide.with(RegisterActivity.this).load(R.drawable.register_password_gray).into(registerIvShowPassword);
                    isShow = false;
                }
                break;
            case R.id.register_btn_action:
                if(registerCbAgreement.isEnabled()){
                    presenter.register(registerEtPhone.getText().toString().trim(),registerEtPassword.getText().toString().trim());
                }else {
                    showMsg(0,"请查看并勾选用户协议");
                }
                break;
            case R.id.register_tv_agreement:
                AgreementActivity.actionStart(RegisterActivity.this);
                break;
        }
    }
}
