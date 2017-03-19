package com.vincent.hss.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.User;
import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.RegisterController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 2:48
 *
 * @version 1.0
 */

public class RegisterPresenter implements RegisterController.IPresenter {
    private RegisterController.IView view;

    public RegisterPresenter(RegisterController.IView view) {
        this.view = view;
    }

    @Override
    public void register(String phone, String password) {
        if(TextUtils.isEmpty(phone)){
            view.msg(0,"手机号码不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            view.msg(0,"密码不能为空");
            return;
        }
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().register(phone,password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        BaseApplication.user = JSONObject.parseObject(JSON.toJSONString(result.getData()),User.class);
                        view.msg(1,"注册成功");
                        view.registerSuccess();
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
