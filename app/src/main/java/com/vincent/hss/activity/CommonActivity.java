package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinping.iosdialog.animation.BaseAnimatorSet;
import com.sinping.iosdialog.animation.BounceEnter.BounceTopEnter;
import com.sinping.iosdialog.animation.SlideExit.SlideBottomExit;
import com.sinping.iosdialog.dialog.listener.OnBtnClickL;
import com.sinping.iosdialog.dialog.widget.NormalDialog;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.vincent.hss.BuildConfig;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.Result;
import com.vincent.hss.config.Config;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.servoce.HssService;
import com.vincent.hss.servoce.NettyPushService;
import com.vise.log.ViseLog;


import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.common_ll_test_push)
    LinearLayout commonLlTestPush;

    private BaseAnimatorSet bas_in;
    private BaseAnimatorSet bas_out;
    private Tencent mTencent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);
        commonTvTitle.setText("通用");
        commonTvCurrent.setText(BuildConfig.VERSION_NAME);

        mTencent = Tencent.createInstance(Config.QQ_SHARE_APP_ID, this.getApplicationContext());

        bas_in = new BounceTopEnter();
        bas_out = new SlideBottomExit();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CommonActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.common_ll_about_us, R.id.common_ll_check_update, R.id.common_ll_version,
            R.id.common_rl_return, R.id.common_ll_shared, R.id.common_ll_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return:
                finish();
                break;
            case R.id.common_ll_version:
                showMsg(1,BuildConfig.VERSION_NAME);
                break;
            case R.id.common_ll_shared:
                shareToQQ();
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

    private void shareToQzone () {
        //分享类型
        final Bundle params = new Bundle();
//        params.putString(QzoneShare.SHARE_TO_QQ_KEY_TYPE,SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "跳转URL");//必填
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,"ArrayList<ImsgUrl>");
        mTencent.shareToQzone(CommonActivity.this, params, listener);
    }


    /**
     * 分享图文到QQ 好友
     */
    private void shareToQQ() {
            final Bundle params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
//            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
            mTencent.shareToQQ(CommonActivity.this, params, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,listener);
    }

    private BaseUiListener listener = new BaseUiListener();


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            ViseLog.d("onComplete:"+o);
        }
        @Override
        public void onError(UiError e) {
            ViseLog.d("onError:", "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }
        @Override
        public void onCancel() {
            ViseLog.d("onCancel", "");
            showMsg(0,"取消分享");
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
                        stopService(new Intent(CommonActivity.this, NettyPushService.class));
                        BaseApplication.getShared().putBoolean(Config.IS_LOGIN, false);
                        LoginActivity.actionStart(CommonActivity.this, true);
                        dialog.superDismiss();
                        finish();
                    }
                });
    }

    @OnClick(R.id.common_ll_test_push)
    public void onClick() {
        showDialog();
       Call<Result> call = RetrofitUtils.getApiService().testPush(BaseApplication.user.getPhone());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.body().getStatus().equals("1")){
                    showMsg(1,"推送成功");
                }else{
                    showMsg(0,response.body().getMsg());
                }
                closeDialog();
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                showMsg(0,"请求失败");
                closeDialog();
            }
        });
    }
}
