package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 1:11
 *
 * @version 1.0
 */

public interface FeedbackController {

    interface IView extends BaseController.IView{

        /**
         * 反馈成功
         */
        void feedbackSuccess();

    }

    interface IPresenter{

        /**
         * 反馈 网络交互
         * @param title
         * @param content
         */
        void feedback(String title,String content);
    }

}
