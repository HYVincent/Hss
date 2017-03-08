package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.utils.FileUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 2:55
 *
 * @version 1.0
 */

public class AgreementActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.agreement_tv_content)
    TextView agreementTvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_reseda));
        commonTvTitle.setText("用户协议");
        initData();
    }

    private void initData() {
        String content = FileUtils.readAssetsTextReturnStr(AgreementActivity.this, "agreement");
        agreementTvContent.setText(content);
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AgreementActivity.class));
    }

    @OnClick(R.id.common_rl_return)
    public void onClick() {
        finish();
    }
}
