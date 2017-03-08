package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 1:43
 *
 * @version 1.0
 */

public interface RegisterController {

    interface IView extends BaseController.IView{

        /**
         * 注册成功
         */
        void registerSuccess();

    }

    interface IPresenter{

        /**
         * 注册
         * @param phone
         * @param password
         */
        void register(String phone,String password);
    }

}
