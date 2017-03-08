package com.vincent.hss.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.User;
import com.vincent.hss.config.Config;
import com.vincent.hss.config.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.LoginController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 0:22
 *
 * @version 1.0
 */

public class LoginPresenter implements LoginController.IPresenter {
    private LoginController.IView view;

    public LoginPresenter(LoginController.IView view) {
        this.view = view;
    }

    @Override
    public void login(String phone, String password) {
        if(TextUtils.isEmpty(phone)){
            view.msg(0,"手机号码不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            view.msg(0,"密码不能为空");
            return;
        }
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().login(phone,password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if(result.getStatus().equals("1")){
                    BaseApplication.user = JSONObject.parseObject(JSON.toJSONString(result.getData()),User.class);
                    ViseLog.d("user:"+BaseApplication.user);
                    BaseApplication.getShared().putString(Config.USER,JSON.toJSONString(BaseApplication.user));
                    view.msg(1,"登录成功");
                    BaseApplication.getShared().putBoolean(Config.IS_LOGIN,true);
                    view.loginSuccess();
                }else {
                    view.msg(0,result.getMsg());
                }
                view.closeDialog();
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
