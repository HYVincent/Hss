package com.vincent.hss.presenter;

import android.text.TextUtils;

import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.ForgetPassworcController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 10:06
 *
 * @version 1.0
 */

public class ForgetPasswordPresenter implements ForgetPassworcController.IPresenter {

    private ForgetPassworcController.IView view;

    public ForgetPasswordPresenter(ForgetPassworcController.IView view) {
        this.view = view;
    }

    @Override
    public void resetPassword(String phone, String password) {
        if(TextUtils.isEmpty(phone)){
            view.msg(0,"手机号码不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            view.msg(0,"密码不能为空");
            return;
        }
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().resetPassword(phone,password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        view.msg(1,"密码已重置");
                        view.resetPasswordSuccess();
                    }else {
                        view.msg(0,result.getMsg());
                    }
                    view.closeDialog();
                }catch (Exception e){
                    e.printStackTrace();
                    view.msg(0,"服务器无返回，空指针了");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                ViseLog.e(t);
                view.msg(0,"请求失败");
                view.closeDialog();
            }
        });
    }
}
