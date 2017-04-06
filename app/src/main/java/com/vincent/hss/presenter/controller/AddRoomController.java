package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;
import com.vincent.hss.bean.Room;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/15 11:30
 *
 * @version 1.0
 */

public interface AddRoomController {

    interface IView extends BaseController.IView{

        /**
         * 添加成功
         */
        void addSuccess(Room room);

    }

    interface IPresenter{

        /**
         * 添加房间信息
         * @param room
         */
        void addRoom(Room room);

        /**
         * 多文件上传
         * @param room
         */
        void addRooms(Room room);

    }

}
