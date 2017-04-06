package com.vincent.hss.presenter;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.vincent.hss.bean.App;
import com.vincent.hss.bean.Result;
import com.vincent.hss.bean.Weather;
import com.vincent.hss.config.Config;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.presenter.controller.HomeController;
import com.vise.log.ViseLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 21:51
 *
 * @version 1.0
 */

public class HomePresenter implements HomeController.IPresenter {

    private HomeController.IView view;
    private boolean isWeather = true;//天气获取一次就好了

    public HomePresenter(HomeController.IView view) {
        this.view = view;
    }

    @Override
    public void getCurrentLocation(final AMapLocationClient aMapLocationClient) {
        if(aMapLocationClient == null){
            return;
        }
        /*定位回调监听器*/
        AMapLocationListener aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation != null){
                    if(aMapLocation.getErrorCode() == 0){
                        //解析定位结果
                        ViseLog.d("定位结果"+aMapLocation);
                        view.location(aMapLocation.getCity());
                        if(!TextUtils.isEmpty(aMapLocation.getCity())){
                            if(isWeather){
                                view.setWeather("正在获取天气..","","","");
                                getCityWeather(aMapLocation.getCity());
                            }
                        }
                    }
                }
            }

        };
        //设置定位回调坚挺
        aMapLocationClient.setLocationListener(aMapLocationListener);
        //启动定位
        aMapLocationClient.startLocation();
    }

    @Override
    public void checkUpdate(String current_version) {
        view.showDialog();
        Call<Result> call = RetrofitUtils.getApiService().checkUpdate(current_version);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                view.closeDialog();
                Result result = response.body();
                if(result.getStatus().equals("1")){
                    if(result.getMsg().equals("当前已是最新版本")){
//                        view.msg(1,"当前已是最新版本");
                    }else {
                        App app = JSON.parseObject(JSON.toJSONString(result.getData()),App.class);
                        view.hasNewVersion(app);
                    }
                }else {
                    view.msg(0,result.getMsg());
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
    public void uploadVersionInfo(String phone, String version, String phoneModel, String android_version) {
        Call<Result> call = RetrofitUtils.getApiService().commitVersion(phone,version,phoneModel,android_version);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                try {
                    Result result = response.body();
                    if(result.getStatus().equals("1")){
                        ViseLog.d("已上传");
                    }else{
                        ViseLog.d("上传失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                ViseLog.d("请求失败");
                ViseLog.e(t);
            }
        });
    }

    /**
     * 获取当前城市天气
     * ?key=cxb4vnpbr2autg9z&language=zh-Hans&unit=c&location=重庆
     */
    private void getCityWeather(final String city) {
        ViseLog.e("开始获取天气..");
        Call<Weather> call = RetrofitUtils.getApiService(Config.JUHE_WEATHER_API).getWeather(city,Config.JUHE_APP_KEY);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                try {
                    Weather weather = response.body();
                    ViseLog.d("weather-->"+response.body().getReason());
                    if(weather.getReason().equals("查询成功")){
                        String weatherStr = weather.getResult().getData().getRealtime().getWeather().getInfo();
                        String temperature = weather.getResult().getData().getRealtime().getWeather().getTemperature();
                        String humidity = weather.getResult().getData().getRealtime().getWeather().getHumidity();
                        String updateTime = weather.getResult().getData().getRealtime().getTime();
                        view.setWeather(weatherStr,temperature,humidity,updateTime);
                        ViseLog.d("刷新天气。。");
                        isWeather = false;
                    }else {
                        ViseLog.d("获取失败");
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    view.setWeather("天气获取失败,点击重试","","","");
//                    getCityWeather(city);
                }
            }
            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
//                view.msg(0,t.getMessage());
                ViseLog.e(t);
            }
        });
    }
}
