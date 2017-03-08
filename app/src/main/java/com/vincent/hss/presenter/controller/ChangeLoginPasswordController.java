package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 13:51
 *
 * @version 1.0
 */

public interface ChangeLoginPasswordController {

    interface IView extends BaseController.IView{

        /**
         * 密码修改成功
         */
        void changePasswordSuccess();

        /**
         * 旧密码错误
         */
        void oldPassowrdError();

        /**
         * 两次密码不一致
         */
        void newPasswordDiff();
    }

    interface IPresenter{

        /**
         * 修改用户密码
         * @param phone
         * @param oldPassword
         * @param newPassword
         * @param okNewPassword 确认新密码
         */
        void changePassword(String phone,String oldPassword,String newPassword,String okNewPassword);
    }

}
