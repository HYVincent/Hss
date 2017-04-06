package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.lwx.netty.msg.SystemMsg;

import java.util.List;


/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 18:26
 *
 * @version 1.0
 */

public interface SystemMsgController {

    interface IView extends BaseController.IView{

        /**
         * 刷新消息
         * @param data
         */
        void refreshMsg(List<SystemMsg> data);
    }

    interface IPersenter{

        /**
         * 获取系统消息
         */
        void getMsg(String phone);
    }

}
