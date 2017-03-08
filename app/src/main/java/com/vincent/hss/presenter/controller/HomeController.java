package com.vincent.hss.presenter.controller;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 21:50
 *
 * @version 1.0
 */

public interface HomeController {

    interface IView extends BaseController.IView{

        /**
         * 定位到当前的详细地址
         * @param city
         */
        void location(String city);

        /**
         * 天气信息
         * @param weather 天气
         * @param temperature 温度
         * @param humidity 湿度
         * @param updateTime 发布时间
         */
        void setWeather(String weather,String temperature,String humidity,String updateTime);

    }

    interface IPresenter{
        void getCurrentLocation(AMapLocationClient aMapLocationClient);
    }

}
