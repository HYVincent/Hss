package com.vincent.hss.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.vincent.hss.R;
import com.vincent.hss.base.BaseActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.config.Config;
import com.vincent.hss.presenter.FeedbackPresenter;
import com.vincent.hss.presenter.controller.FeedbackController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 0:58
 *
 * @version 1.0
 */

public class FeedbackActivity extends BaseActivity implements FeedbackController.IView {

    @BindView(R.id.common_rl_return_2)
    RelativeLayout commonRlReturn;
    @BindView(R.id.common_tv_title_2)
    TextView commonTvTitle;
    @BindView(R.id.feedback_et_title)
    EditText feedbackEtTitle;
    @BindView(R.id.feedback_et_content)
    EditText feedbackEtContent;
    @BindView(R.id.feedback_btn_submit)
    Button feedbackBtnSubmit;
    @BindView(R.id.common_title_right)
    TextView commonTitleRight;
    private FeedbackPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        commonTvTitle.setText("意见反馈");
        commonTitleRight.setText("反馈记录");
        commonTitleRight.setVisibility(View.VISIBLE);
        presenter = new FeedbackPresenter(this);
        if(!TextUtils.isEmpty(BaseApplication.getShared().getString(Config.FEEDBACK_TITLE,""))){
            feedbackEtTitle.setText(BaseApplication.getShared().getString(Config.FEEDBACK_TITLE,""));
            feedbackEtContent.setText(BaseApplication.getShared().getString(Config.FEEDBACK_CONTENT,""));
        }
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.common_title_right,R.id.common_rl_return_2, R.id.feedback_btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_rl_return_2:
                finish();
                break;
            case R.id.feedback_btn_submit:
                presenter.feedback(feedbackEtTitle.getText().toString().trim(), feedbackEtContent.getText().toString().trim());
                break;
            case R.id.common_title_right:
                FeedbackHistoryActivity.actionStart(FeedbackActivity.this);
                overridePendingTransition(R.anim.activity_start_join,R.anim.activity_close_exit);
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
    public void feedbackSuccess() {
        SettingActivity.actionStart(FeedbackActivity.this);
        overridePendingTransition(R.anim.activity_start_join, R.anim.activity_close_exit);
        finish();
    }

    @Override
    public void msg(int code, String msg) {
        showMsg(code, msg);
    }

}
