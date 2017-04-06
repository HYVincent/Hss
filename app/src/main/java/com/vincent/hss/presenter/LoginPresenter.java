package com.vincent.hss.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.User;
import com.vincent.hss.config.Config;
import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.LoginController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：登录逻辑实现
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 0:22
 *
 * @version 1.0
 */

public class LoginPresenter implements LoginController.IPresenter {
    private LoginController.IView view;

    /*有参数的构造方法*/
    public LoginPresenter(LoginController.IView view) {
        this.view = view;
    }

    @Override
    public void login(String phone, String password) {
        /*判断用户信息的完整性*/
        if(TextUtils.isEmpty(phone)){
            view.msg(0,"手机号码不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            view.msg(0,"密码不能为空");
            return;
        }
        /*显示正在登录的动画效果*/
        view.showDialog();
        /*初始化ApiService对象*/
        Call<Result> call = RetrofitUtils.getApiService().login(phone,password);
        /*异步请求网络*/
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    /*封装返回结果*/
                    Result result = response.body();
                    /*判断返回结果状态*/
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
                }catch (Exception e){/*出现异常*/
                    e.printStackTrace();
                    view.msg(0,"服务器无返回，空指针了");
                }
            }

            /*请求发送失败，可能原因有：1、服务器未启动 2、手机无网络*/
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                ViseLog.e(t);
                view.msg(0,"请求失败");
                view.closeDialog();
            }
        });
    }
}
