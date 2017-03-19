package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.hss.bean.User;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/19 0:35
 *
 * @version 1.0
 */

public interface SearchFamilyController {

    interface IView extends BaseController.IView{

        /**
         * 刷新页面
         * @param list
         */
        void refreshView(List<User> list);

    }

    interface IPresenter{

        /**
         * 搜索数据
         * @param phone
         */
        void search(String phone);

    }

}
