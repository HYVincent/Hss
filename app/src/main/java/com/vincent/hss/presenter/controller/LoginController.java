package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 0:22
 *
 * @version 1.0
 */

public interface LoginController {

    interface IView extends BaseController.IView{

        /**
         * 登陆成功
         */
        void loginSuccess();
    }

    interface IPresenter{

        /**
         * 登录实现
         * @param phone
         * @param password
         */
        void login(String phone,String password);
    }

}
