package com.vincent.hss.presenter;

import android.text.TextUtils;

import com.vincent.hss.config.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.ChangeLoginPasswordController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 13:58
 *
 * @version 1.0
 */

public class ChangeLoginPasswordPresenter implements ChangeLoginPasswordController.IPresenter {

    private ChangeLoginPasswordController.IView view;

    public ChangeLoginPasswordPresenter(ChangeLoginPasswordController.IView view) {
        this.view = view;
    }

    @Override
    public void changePassword(String phone, String oldPassword, String newPassword, String okNewPassword) {
        if(TextUtils.isEmpty(oldPassword)||TextUtils.isEmpty(newPassword)||TextUtils.isEmpty(okNewPassword)){
            view.msg(0,"参数不完整，检查当前密码和新密码是否填写");
            return;
        }
        if(!TextUtils.equals(newPassword,okNewPassword)){
            view.newPasswordDiff();
            return;
        }
        //开始请求服务器
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().alterUserPassword(phone,oldPassword,newPassword);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                view.closeDialog();
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        view.msg(1,"密码已修改");
                        view.changePasswordSuccess();
                    }else if(TextUtils.equals("旧密码错误，请重试",result.getMsg())){
                        view.oldPassowrdError();
                    }else {
                        view.msg(0,result.getMsg());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    view.msg(0,"发生异常了");
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                ViseLog.e(t);
                view.msg(0,"请求错误，请稍后再试");
                view.closeDialog();
            }
        });
    }
}
