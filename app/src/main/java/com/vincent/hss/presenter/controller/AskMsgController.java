package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.lwx.netty.msg.AskMessage;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/17 20:39
 *
 * @version 1.0
 */

public interface AskMsgController {

    interface AskMsgView extends BaseController.IView{

        /**
         * 验证消息列表
         * @param data
         */
        void refreshView(List<AskMessage> data);


    }

    interface AskMsgPresenter{

        /**
         * 获取验证消息列表
         */
        void getAskMsg();

    }
}
