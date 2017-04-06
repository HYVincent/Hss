package com.vincent.hss.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.vincent.hss.bean.Result;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.SystemMsgController;
import com.vincent.lwx.netty.msg.SystemMsg;
import com.vise.log.ViseLog;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 18:44
 *
 * @version 1.0
 */

public class SystemMsgPresenter implements SystemMsgController.IPersenter {

    private SystemMsgController.IView view;

    public SystemMsgPresenter(SystemMsgController.IView view) {
        this.view = view;
    }

    @Override
    public void getMsg(String phone) {
        view.showDialog();
        //TODO 从服务器获取数据
       /* List<SystemMsg> list = DataSupport.findAll(SystemMsg.class);
        if(list!=null&list.size()!=0){
            ViseLog.d("list.size-->"+list.size());
            view.refreshMsg(list);
        }else {*/
            Call<Result> call = RetrofitUtils.getApiService().getAllSystemMsg(phone);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        List<SystemMsg> list = JSONArray.parseArray(JSON.toJSONString(result.getData()),SystemMsg.class);
                        DataSupport.saveAll(list);
                        view.refreshMsg(list);
                    }else {
                        view.msg(0,result.getMsg());
                    }
                }
                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    ViseLog.d(t);
                }
            });
//        }
        view.closeDialog();
    }
}
