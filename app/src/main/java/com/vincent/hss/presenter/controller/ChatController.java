package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.lwx.netty.msg.ChatMsg;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 19:09
 *
 * @version 1.0
 */

public interface ChatController {

    interface IView extends BaseController.IView{

        /**
         * 消息发送成功
         */
        void refreshView(ChatMsg data);
    }

    interface IPresenter{

        /**
         * 内容
         * @param phone
         * @param ask_phone
         * @param chatContent
         */
        void sendChatMsg(String phone,String ask_phone,String chatContent);

    }


}
