package com.vincent.hss.presenter;

import com.alibaba.fastjson.JSON;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.bean.Result;
import com.vincent.hss.bean.User;
import com.vincent.hss.config.Config;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.SettingController;
import com.vise.log.ViseLog;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/21 12:47
 *
 * @version 1.0
 */

public class SettingPresenter implements SettingController.IPresenter {

    private SettingController.IView view;

    public SettingPresenter(SettingController.IView view) {
        this.view = view;
    }

    @Override
    public void getUserHeadUrl(String phone) {
        Call<Result> call = RetrofitUtils.getApiService().getUserInfo(phone);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        User user = JSON.parseObject(JSON.toJSONString(result.getData()),User.class);
                        ViseLog.d("用户头像地址："+user.getHead());
                        BaseApplication.getShared().putString(Config.USER_HEAD_IMG,user.getHead());
                        view.refreshHean(user.getHead());
                    }else {
                        view.msg(0,result.getMsg());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                ViseLog.e(t.getMessage());
            }
        });
    }

    @Override
    public void uploadUserHead(String phone,String filePath) {
        view.showDialog();
        File file = new File(filePath);

        // 创建 RequestBody，用于封装构建RequestBody
        final RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        // 添加描述 我不需要描述
        String descriptionString = "hello, 这是文件描述";
        /*RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);*/

        Call<Result> call = RetrofitUtils.getApiService().uploadUserHead(phone,body);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                view.closeDialog();
//                ViseLog.d("上传结果返回："+JSON.toJSONString(response.body().getData()));
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        //上传成功，更新头像
                        User user = JSON.parseObject(JSON.toJSONString(result.getData()),User.class);
                        BaseApplication.getShared().putString(Config.USER_HEAD_IMG,user.getHead());
                        view.refreshHean(user.getHead());
                        view.msg(1,"头像上传成功");
                    }else{
                        view.msg(0,result.getMsg());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    view.msg(0,"异常了");
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                view.msg(0,"请求错误");
                ViseLog.e(t);
                view.closeDialog();
            }
        });
    }
}
