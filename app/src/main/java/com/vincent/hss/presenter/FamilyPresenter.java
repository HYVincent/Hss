package com.vincent.hss.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.Result;
import com.vincent.hss.bean.Room;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.FamilyController;
import com.vise.log.ViseLog;

import org.litepal.crud.DataSupport;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/9 22:48
 *
 * @version 1.0
 */

public class FamilyPresenter implements FamilyController.IPresenter{

    private FamilyController.IView view;

    public FamilyPresenter(FamilyController.IView view) {
        this.view = view;
    }

    @Override
    public void queryRoom() {
        List<Room> list = DataSupport.findAll(Room.class);
        if(list.size()>0){
            ViseLog.d("本地有数据有数据，刷新");
            view.refreshRoom(list);
        }else {
            ViseLog.d("没有数据,查询服务器");
            queryServiceRoom(BaseApplication.user.getPhone());
        }
    }

    @Override
    public void queryServiceRoom(String phone) {
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().getAllRoom(phone);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    view.closeDialog();
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        String json = JSON.toJSONString(result.getData());
                        ViseLog.e("服务器有数据："+json);
                        List<Room> listData = JSONArray.parseArray(json,Room.class);
                        for(int i=0;i<listData.size();i++){
                            listData.get(i).save();
                        }
                        view.refreshRoom(listData);
                    }else {
                        view.msg(0,result.getMsg());
                        view.refreshRoom(null);
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
