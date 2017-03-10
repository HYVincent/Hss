package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.hss.bean.Room;
import com.vincent.hss.bean.dao.RoomDao;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/9 22:46
 *
 * @version 1.0
 */

public interface FamilyController {

    interface IView extends BaseController.IView{

        /**
         * 刷新房间数据
         * @param data
         */
        void refreshRoom(List<Room> data);
    }

    interface IPresenter{

        /**
         * 查询房间 从数据库去查
         * @param dao
         */
        void queryRoom(RoomDao dao);
    }

}
