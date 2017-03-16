package com.vincent.hss.presenter;

import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/16 8:59
 *
 * @version 1.0
 */

public interface EditUserInfoController {

    interface IView extends BaseController.IView{

        /**
         * 修改用户信息成功
         */
        void alterUserInfoSuccess();

    }

    interface IPresenter{

        /**
         * 提交修改用户信息
         * @param nickname
         * @param sex
         * @param birthday
         * @param status
         */
        void submitAlterUserInfo(String phone,String nickname,String sex,String birthday,String status);
    }

}
