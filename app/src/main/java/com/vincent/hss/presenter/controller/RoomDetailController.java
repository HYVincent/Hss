package com.vincent.hss.presenter.controller;

import com.vincent.hss.base.BaseController;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/15 12:07
 *
 * @version 1.0
 */

public interface RoomDetailController {

    interface IView extends BaseController.IView{


        /**
         * 删除成功
         */
        void deleteRoomSuccess();

    }

    interface IPresenter{

        /**
         * 删除房间
         * @param phone
         * @param roomName
         */
        void deleteRoom(String phone,String roomName);

    }

}
