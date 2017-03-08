package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/6 9:43
 *
 * @version 1.0
 */

public interface ForgetPassworcController {

    interface IView extends BaseController.IView{

        /**
         * 重置密码成功
         */
        void resetPasswordSuccess();
    }

    interface IPresenter{

        /**
         * 重置密码
         * @param phone
         * @param password
         */
        void resetPassword(String phone,String password);

    }

}
