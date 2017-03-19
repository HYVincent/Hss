package com.vincent.hss.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.vincent.hss.bean.Family;
import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.FamilyChatController;
import com.vise.log.ViseLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/18 0:49
 *
 * @version 1.0
 */

public class FamilyChatPresenter implements FamilyChatController.IPresenter {

    private FamilyChatController.IView view;

    public FamilyChatPresenter(FamilyChatController.IView view) {
        this.view = view;
    }

    @Override
    public void getFamilyList(String phone) {
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().getFamilyList(phone);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    view.closeDialog();
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        List<Family> families = JSONArray.parseArray(JSON.toJSONString(result.getData()),Family.class);
                        if(families!=null&families.size()>0){
                            view.refresh(families);
                        }else {
                            view.refresh(null);
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
                view.closeDialog();
                ViseLog.d(t.getMessage());
            }
        });
    }
}
