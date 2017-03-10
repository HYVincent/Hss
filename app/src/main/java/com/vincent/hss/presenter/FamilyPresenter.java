package com.vincent.hss.presenter;

import com.vincent.hss.bean.Room;
//import com.vincent.hss.bean.dao.RoomDao;
import com.vincent.hss.bean.dao.RoomDao;
import com.vincent.hss.presenter.controller.FamilyController;
import com.vise.log.ViseLog;

import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/9 22:48
 *
 * @version 1.0
 */

public class FamilyPresenter implements FamilyController.IPresenter{
    private FamilyController.IView view;

    public FamilyPresenter(FamilyController.IView view) {
        this.view = view;
    }

    @Override
    public void queryRoom(RoomDao dao) {
        List<Room> data = dao.loadAll();
        ViseLog.d("data:"+data);
        if(data.size()>0){
            ViseLog.d("有数据");
        }else {
            ViseLog.d("没有数据");
        }
        view.refreshRoom(data);
    }
}
