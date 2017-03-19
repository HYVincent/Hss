package com.vincent.hss.presenter;

import com.alibaba.fastjson.JSON;
import com.vincent.hss.bean.Result;
import com.vincent.hss.bean.Room;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.AddRoomController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/15 11:32
 *
 * @version 1.0
 */

public class AddRoomPresenter implements AddRoomController.IPresenter {
    private AddRoomController.IView view;

    public AddRoomPresenter(AddRoomController.IView view) {
        this.view = view;
    }

    @Override
    public void addRoom(Room room) {
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().addRoom(
                room.getPhone(),
                room.getRoomType(),
                room.getRoomName(),
                String.valueOf(room.getRoomImg()),
                room.getRoomBigImg());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    view.closeDialog();
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        view.msg(1,"已添加");
                        Room r = JSON.parseObject(JSON.toJSONString(result.getData()),Room.class);
                        view.addSuccess(r);
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
                view.closeDialog();
                view.msg(0,"请求错误");
            }
        });
    }
}
