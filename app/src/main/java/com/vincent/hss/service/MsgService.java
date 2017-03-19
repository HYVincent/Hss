package com.vincent.hss.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.vincent.hss.activity.AskMsgDetailActivity;
import com.vincent.hss.utils.EventUtil;
import com.vincent.hss.utils.NotificationUtil;
import com.vincent.lwx.netty.msg.AskMessage;
import com.vincent.lwx.netty.msg.ChatMsg;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * description ：用来处理各种消息
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/17 19:45
 *
 * @version 1.0
 */

public class MsgService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventUtil.register(this);
    }

    //处理服务器的ask消息
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void askMsg(final AskMessage askMessage){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            public void run(){
                NotificationUtil.sendNotificationAskMsg(MsgService.this, AskMsgDetailActivity.class,askMessage.getFromPhone(),askMessage.getMsgContent());
            }
        });
    }

  /*  *//**
     * 处理聊天消息
     * @param chatMsg
     *//*
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void chatMsg(final ChatMsg chatMsg){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            public void run(){
                NotificationUtil.sendNotificationChatMsg(MsgService.this,chatMsg.getAsk_phone(),JSON.toJSONString(chatMsg));
            }
        });
    }*/


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }
}
