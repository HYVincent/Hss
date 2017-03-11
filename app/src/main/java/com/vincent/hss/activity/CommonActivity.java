package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.sinping.iosdialog.animation.BaseAnimatorSet;
import com.sinping.iosdialog.animation.BounceEnter.BounceTopEnter;
import com.sinping.iosdialog.animation.SlideExit.SlideBottomExit;
import com.sinping.iosdialog.dialog.listener.OnBtnClickL;
import com.sinping.iosdialog.dialog.widget.NormalDialog;
import com.vincent.hss.BuildConfig;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.config.Config;
import com.vincent.hss.servoce.HssService;
import com.vincent.hss.servoce.NettyPushService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 19:34
 *
 * @version 1.0
 */

public class CommonActivity extends BaseActivity {

    @BindView(R.id.common_rl_return)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.common_ll_shared)
    LinearLayout commonLlShared;
    @BindView(R.id.common_ll_logout)
    LinearLayout commonLlLogout;
    @BindView(R.id.common_tv_current)
    TextView commonTvCurrent;
    @BindView(R.id.common_ll_version)
    RelativeLayout commonLlVersion;
    @BindView(R.id.common_ll_check_update)
    RelativeLayout commonLlCheckUpdate;
    @BindView(R.id.common_ll_about_us)
    RelativeLayout commonLlAboutUs;

    private BaseAnimatorSet bas_in;
    private BaseAnimatorSet bas_out;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);
        commonTvTitle.setText("通用");
        commonTvCurrent.setText(BuildConfig.VERSION_NAME);

        bas_in = new BounceTopEnter();
        bas_out = new SlideBottomExit();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CommonActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.common_ll_about_us,R.id.common_ll_check_update, R.id.common_ll_version,
            R.id.common_rl_return, R.id.common_ll_shared, R.id.common_ll_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.common_ll_version:

                break;
            case R.id.common_ll_shared:

                break;
            case R.id.common_ll_logout:
                logout();
                break;
            case R.id.common_ll_check_update:
                showMsg(1, "当前已是最新本");
                break;
            case R.id.common_ll_about_us:
                AboutUsActivity.actionStart(CommonActivity.this);
                break;
        }
    }

    private void logout() {
        final NormalDialog dialog = new NormalDialog(CommonActivity.this);
        dialog.content("你要注销账号吗？")//
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)//
                .btnText("我还要看看", "确定")//
                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#383838"))//
                .btnTextSize(16f, 16f)//
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        stopService(new Intent(CommonActivity.this, HssService.class));
                        stopService(new Intent(CommonActivity.this,NettyPushService.class));
                        BaseApplication.getShared().putBoolean(Config.IS_LOGIN, false);
                        LoginActivity.actionStart(CommonActivity.this, true);
                        dialog.superDismiss();
                        finish();
                    }
                });
    }
}
