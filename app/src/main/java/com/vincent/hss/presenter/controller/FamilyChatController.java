package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.hss.bean.Family;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/17 22:17
 *
 * @version 1.0
 */

public interface FamilyChatController  {

    interface IView extends BaseController.IView{

        /**
         * 家人列表
         * @param familyList
         */
        void refresh(List<Family> familyList);

    }

    interface IPresenter{

        /**
         * 根据账号获取
         * @param phone
         */
        void getFamilyList(String phone);
    }

}
