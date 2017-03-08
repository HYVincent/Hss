package com.vincent.hss.presenter;

import com.vincent.hss.bean.SystemMsg;
import com.vincent.hss.presenter.controller.SystemMsgController;

import java.util.ArrayList;
import java.util.List;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 18:44
 *
 * @version 1.0
 */

public class SystemMsgPresenter implements SystemMsgController.IPersenter {

    private SystemMsgController.IView view;

    public SystemMsgPresenter(SystemMsgController.IView view) {
        this.view = view;
    }

    @Override
    public void getMsg() {
        view.showDialog();
        //模拟数据...
        List<SystemMsg> data = new ArrayList<>();
        SystemMsg msg1 = new SystemMsg("系统通知","有新版本啦，大版本升级有惊喜");
        SystemMsg msg2 = new SystemMsg("商品打折啦","祝广大妇女节日快乐，上架有大活动，惊喜多多，赶紧去看看吧。。");
        SystemMsg msg3 = new SystemMsg("系统通知","有新版本啦，大版本升级有惊喜");
        SystemMsg msg4 = new SystemMsg("系统通知","有新版本啦，大版本升级有惊喜");
        SystemMsg msg5 = new SystemMsg("系统通知","有新版本啦，大版本升级有惊喜");
        data.add(msg1);
        data.add(msg2);
        data.add(msg3);
        data.add(msg4);
        data.add(msg5);
        view.refreshMsg(data);
        view.closeDialog();
    }
}
