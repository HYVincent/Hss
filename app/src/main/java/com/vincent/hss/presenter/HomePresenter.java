package com.vincent.hss.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.vincent.hss.bean.Weather;
import com.vincent.hss.config.Config;
import com.vincent.hss.network.RetrofitUtils;
import com.vincent.hss.network.service.ApiService;
import com.vincent.hss.presenter.controller.HomeController;
import com.vise.log.ViseLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                    ViseLog.d("weather-->"+response.body());
                    Weather weather = response.body();
                    if(weather.getReason().equals("successed!")){
                        String weatherStr = weather.getResult().getData().getRealtime().getWeather().getInfo();
                        String temperature = weather.getResult().getData().getRealtime().getWeather().getTemperature();
                        String humidity = weather.getResult().getData().getRealtime().getWeather().getHumidity();
                        String updateTime = weather.getResult().getData().getRealtime().getTime();
                        view.setWeather(weatherStr,temperature,humidity,updateTime);
                        isWeather = false;
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
