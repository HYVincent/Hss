package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.hss.bean.App;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/16 16:40
 *
 * @version 1.0
 */

public interface CommonController {

    interface IView extends BaseController.IView{

        /**
         * 有新版本
         * @param app
         */
        void hasNewVersion(App app);

    }


    interface IPresenter{

        /**
         * 检查当前版本
         * @param current_version
         */
        void checkNewestVersion(String current_version);
    }
}
