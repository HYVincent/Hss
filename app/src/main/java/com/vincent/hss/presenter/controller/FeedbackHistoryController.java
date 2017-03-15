package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.hss.bean.Feedback;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 12:15
 *
 * @version 1.0
 */

public interface FeedbackHistoryController {

    interface IView extends BaseController.IView {

        /**
         * 显示
         */
        void showFeedbackHistory(List<Feedback> data);

        /**
         * 请求错误
         */
        void requestError();

    }

    interface IPresenter{

        /**
         * 请求网络数据
         * @param phone
         */
        void getFeedbackHistory(String phone);

    }
}
