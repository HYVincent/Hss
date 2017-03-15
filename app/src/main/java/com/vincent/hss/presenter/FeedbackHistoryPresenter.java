package com.vincent.hss.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.vincent.hss.bean.Feedback;
import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.FeedbackHistoryController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 12:17
 *
 * @version 1.0
 */

public class FeedbackHistoryPresenter implements FeedbackHistoryController.IPresenter {

    private FeedbackHistoryController.IView view;

    public FeedbackHistoryPresenter(FeedbackHistoryController.IView view) {
        this.view = view;
    }

    @Override
    public void getFeedbackHistory(String phone) {
        if(TextUtils.isEmpty(phone)){
            view.msg(0,"用户登录信息失效，请重新登录");
            return;
        }
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().getFeedHistory(phone);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                view.closeDialog();
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        view.showFeedbackHistory(JSONArray.parseArray(JSON.toJSONString(result.getData()), Feedback.class));
                        view.msg(1,"刷新成功");
                    }else {
                        view.msg(0,result.getMsg());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    view.msg(0,"发生异常了...");
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                ViseLog.e(t);
                view.msg(0,"Request is error ...");
                view.requestError();
                view.closeDialog();
            }
        });
    }
}
