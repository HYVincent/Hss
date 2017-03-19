package com.vincent.hss.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.vincent.hss.bean.Result;
import com.vincent.hss.bean.User;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.SearchFamilyController;
import com.vise.log.ViseLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 0:37
 *
 * @version 1.0
 */

public class SearchFamilyPresenter implements SearchFamilyController.IPresenter {

    private SearchFamilyController.IView view;

    public SearchFamilyPresenter(SearchFamilyController.IView view) {
        this.view = view;
    }

    @Override
    public void search(String phone) {
        Call<Result> call = RetrofitUtils.getApiService().searchLikeFamily(phone);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        List<User> data = JSONArray.parseArray(JSON.toJSONString(result.getData()),User.class);
                        if(data!=null){
                            view.refreshView(data);
                        }else {
                            view.refreshView(null);
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
            }
        });
    }
}
