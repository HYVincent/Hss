package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 8:04
 *
 * @version 1.0
 */

public interface SearchResultDetailController {

    interface IView extends BaseController.IView{

        /**
         * 请求发送成功
         */
        void sendSuccess();

    }

    interface IPresenter{
        /**
         * @param phone 我的手机号码
         * @param ask_phone 对方的手机号码
         * @param msgConent 给对方带话
         */
        void sendAddFamilyRequest(String phone,String ask_phone,String msgConent);
    }

}
