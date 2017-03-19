package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.hss.bean.User;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/17 21:16
 *
 * @version 1.0
 */

public interface AskMsgDetailController {

    interface IView extends BaseController.IView{

        /**
         * 刷新信息
         * @param user
         */
        void refreshAskInfo(User user);

        /**
         * 同意添加
         */
        void agreeAdd();
    }


    interface IPresenter{

        /**
         * 获取对方信息
         * @param fromPhone
         */
        void getAskInfo(String fromPhone);

        /**
         * 同意添加到家人列表
         * @param phone
         * @param familyPhone
         * @param msgContent
         */
        void agreeAddToFamilyList(String phone,String familyPhone,String msgContent);

    }

}
