package com.vincent.hss.presenter;

import com.alibaba.fastjson.JSON;
import com.vincent.hss.bean.App;
import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.CommonController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/16 16:43
 *
 * @version 1.0
 */

public class CommonPresenter implements CommonController.IPresenter {

    private CommonController.IView view;

    public CommonPresenter(CommonController.IView view) {
        this.view = view;
    }

    @Override
    public void checkNewestVersion(String current_version) {
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().checkUpdate(current_version);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    view.closeDialog();
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        if(result.getMsg().equals("当前已是最新版本")){
                            view.msg(1,"当前已是最新版本");
                        }else {
                            App app = JSON.parseObject(JSON.toJSONString(result.getData()),App.class);
                            view.hasNewVersion(app);
                        }
                    }else {
                        view.msg(0,result.getMsg());
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
}
