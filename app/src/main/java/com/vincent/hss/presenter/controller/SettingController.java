package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/21 12:45
 *
 * @version 1.0
 */

public interface SettingController {

    interface IView extends BaseController.IView{

        /**
         * 刷新头像
         * @param headUrl
         */
        void refreshHean(String headUrl);

    }


    interface IPresenter{

        /**
         * 获取用户头像Url
         * @param phone
         */
        void getUserHeadUrl(String phone);

        /**
         * 上传用户头像
         * @param filePath
         */
        void uploadUserHead(String phone,String filePath);

    }
}
