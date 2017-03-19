package com.vincent.hss.presenter;

import com.alibaba.fastjson.JSON;
import com.vincent.hss.bean.Result;
import com.vincent.hss.bean.User;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.AskMsgDetailController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/17 21:20
 *
 * @version 1.0
 */

public class AskMsgDetailPresenter implements AskMsgDetailController.IPresenter {

    private AskMsgDetailController.IView view;

    public AskMsgDetailPresenter(AskMsgDetailController.IView view) {
        this.view = view;
    }

    @Override
    public void getAskInfo(String fromPhone) {
        view.showDialog();
        ViseLog.d("正在请求"+fromPhone+"的资料");
        Call<Result> call = RetrofitUtils.getApiService().searchFamily(fromPhone);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    view.closeDialog();
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        User user = JSON.parseObject(JSON.toJSONString(result.getData()),User.class);
                        view.refreshAskInfo(user);
                    }else{
                        view.msg(0,result.getMsg());
                        view.refreshAskInfo(null);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    view.msg(0,"服务器无返回，空指针了");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                view.msg(0,"请求错误");
                ViseLog.e(t.getMessage());
                view.closeDialog();
            }
        });
    }

    @Override
    public void agreeAddToFamilyList(String phone, String familyPhone, String msgContent) {
        view.showDialog();
        ViseLog.d("familyPhone-->"+familyPhone);
        Call<Result> call = RetrofitUtils.getApiService().sendMsgAgreeAddToFamilyList(phone,familyPhone,msgContent);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                view.closeDialog();
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        view.msg(1,"已同意");
                        view.agreeAdd();
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
                view.closeDialog();
                view.msg(0,"请求失败");
                ViseLog.e(t.getMessage());
            }
        });
    }
}
