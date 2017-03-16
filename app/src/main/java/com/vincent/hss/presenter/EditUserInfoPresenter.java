package com.vincent.hss.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.Result;
import com.vincent.hss.bean.User;
import com.vincent.hss.config.Config;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.utils.EventUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/16 9:01
 *
 * @version 1.0
 */

public class EditUserInfoPresenter implements EditUserInfoController.IPresenter {

    private EditUserInfoController.IView view;

    public EditUserInfoPresenter(EditUserInfoController.IView view) {
        this.view = view;
    }

    @Override
    public void submitAlterUserInfo(String phone,String nickname, String sex, String birthday, String status) {
        if(TextUtils.isEmpty(nickname)){
            view.msg(0,"昵称不能为空");
            return;
        }
        if(TextUtils.isEmpty(status)){
            view.msg(0,"最近还好吗，不说点什么吗");
            return;
        }
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().updateUserInfo(phone,sex,birthday,nickname,status);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                view.closeDialog();
                Result result = response.body();
                if(result.getStatus().equals("1")){
                    String userJson = JSON.toJSONString(result.getData());
                    BaseApplication.user = JSON.parseObject(userJson, User.class);
                    BaseApplication.getShared().putString(Config.USER,userJson);
                    view.alterUserInfoSuccess();
                }else {
                    view.msg(0,result.getMsg());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                view.msg(0,"请求失败");
                view.closeDialog();
            }
        });
    }
}
