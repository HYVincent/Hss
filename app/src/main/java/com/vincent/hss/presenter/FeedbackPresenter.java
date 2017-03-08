package com.vincent.hss.presenter;

import android.speech.tts.Voice;
import android.text.TextUtils;
import android.view.View;

import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.config.Config;
import com.vincent.hss.config.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.FeedbackController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 1:13
 *
 * @version 1.0
 */

public class FeedbackPresenter implements FeedbackController.IPresenter {

    private FeedbackController.IView view;

    public FeedbackPresenter(FeedbackController.IView view) {
        this.view = view;
    }

    @Override
    public void feedback(final String title, final String content) {
        if(TextUtils.isEmpty(title)){
            view.msg(0,"主题不能为空");
            return;
        }
        if(TextUtils.isEmpty(content))
        {
            view.msg(0,"内容不能为空");
            return;
        }
        view.showDialog();
        ViseLog.d("");
        //提交
        Call<Result> call = RetrofitUtils.getApiService(Config.SERVICE_API_ADDRESS).submitFeedback(BaseApplication.user.getPhone(),title,content);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                view.closeDialog();
                try {
                    Result result = response.body();
                    ViseLog.d("result-->"+result);
                    if(result.getStatus().equals("1")){
                        view.msg(1,"反馈已提交");
                        BaseApplication.getShared().putString(Config.FEEDBACK_TITLE,"");
                        BaseApplication.getShared().putString(Config.FEEDBACK_CONTENT,"");
                        view.feedbackSuccess();
                    }else {
                        view.msg(0,result.getMsg());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    ViseLog.e(e);
                    view.msg(0,"结果解析异常了..");
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                BaseApplication.getShared().putString(Config.FEEDBACK_TITLE,title);
                BaseApplication.getShared().putString(Config.FEEDBACK_CONTENT,content);
                ViseLog.e(t);
                view.msg(0,"请求错误，请稍后再试");
                view.closeDialog();
            }
        });
    }
}
