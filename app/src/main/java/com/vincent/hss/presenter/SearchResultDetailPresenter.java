package com.vincent.hss.presenter;

import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.SearchResultDetailController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 8:13
 *
 * @version 1.0
 */

public class SearchResultDetailPresenter implements SearchResultDetailController.IPresenter {

    private SearchResultDetailController.IView view;

    public SearchResultDetailPresenter(SearchResultDetailController.IView view) {
        this.view = view;
    }

    @Override
    public void sendAddFamilyRequest(String phone, String ask_phone, String msgConent) {
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().sendAddFamilyRequest(phone,ask_phone,msgConent);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                view.closeDialog();
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        view.msg(1,"请求已发送");
                        view.sendSuccess();
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

    @Override
    public void hasFamily(String phone, String familyPhone) {
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().hasFamily(phone,familyPhone);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                view.closeDialog();
                Result result = response.body();
                if(result.getStatus().equals("1")){
                    ViseLog.d("已添加");
                    view.hasFamily(true);
                }else {
                    ViseLog.d("还没有添加");
                    view.hasFamily(false);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                view.closeDialog();
                ViseLog.e(t);
            }
        });
    }
}
