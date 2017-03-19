package com.vincent.hss.presenter.controller;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.vincent.hss.base.BaseController;
import com.vincent.hss.bean.App;

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

        /**
         * 有新版本
         * @param app
         */
        void hasNewVersion(App app);

    }

    interface IPresenter{
        /**
         * 定位
         * @param aMapLocationClient
         */
        void getCurrentLocation(AMapLocationClient aMapLocationClient);

        /**
         * 检查升级
         * @param current_version
         */
        void checkUpdate(String current_version);

    }

}
