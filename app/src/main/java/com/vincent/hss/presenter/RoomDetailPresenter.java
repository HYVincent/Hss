package com.vincent.hss.presenter;

import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.RoomDetailController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/15 12:09
 *
 * @version 1.0
 */

public class RoomDetailPresenter implements RoomDetailController.IPresenter {

    private RoomDetailController.IView view;

    public RoomDetailPresenter(RoomDetailController.IView view) {
        this.view = view;
    }

    @Override
    public void deleteRoom(String phone, String roomName) {
//        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().deleteRoomItem(phone,roomName);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
//                view.closeDialog();
                Result result = response.body();
                if(result.getStatus().equals("1")){
                    view.msg(1,"已删除");
                    view.deleteRoomSuccess();
                }else {
                    view.msg(0,result.getMsg());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
//                view.closeDialog();
                view.msg(0,"请求错误");
            }
        });
    }
}
