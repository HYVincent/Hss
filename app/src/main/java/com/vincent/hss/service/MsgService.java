package com.vincent.hss.service;

import android.app.Service;
import android.content.Intent;
import android.icu.util.Measure;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.vincent.hss.activity.AskMsgDetailActivity;
import com.vincent.hss.activity.SystemMsgActivity;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.config.Config;
import com.vincent.hss.utils.EventUtil;
import com.vincent.hss.utils.NotificationUtil;
import com.vincent.lwx.netty.msg.AskMessage;
import com.vincent.lwx.netty.msg.ChatMsg;
import com.vincent.lwx.netty.msg.SystemMsg;
import com.vise.log.ViseLog;

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

    private static final int MSG_CHAT = 0;
    private static final int MSG_ASK = 1;
    private static final int SYSTEM_MSG = 2;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1){
                    case MSG_CHAT:
                    ChatMsg chatMsg = (ChatMsg) msg.obj;
                    //是否正在和当前消息来源的人在聊天
                    ViseLog.d("收到聊天消息--->"+chatMsg.getChatContent());
                    if(BaseApplication.getShared().getString(Config.MSG.CHATING,"").equals(chatMsg.getPhoneNum())){
                        System.out.println("uuuuuu");
                    }else {
                        NotificationUtil.sendNotificationChatMsg(MsgService.this,chatMsg.getAsk_phone(),JSON.toJSONString(chatMsg));
                    }
                    break;
                case MSG_ASK:
                    AskMessage askMessage = (AskMessage) msg.obj;
                    NotificationUtil.sendNotificationAskMsg(MsgService.this, AskMsgDetailActivity.class,askMessage.getFromPhone(),askMessage.getMsgContent());
                    break;
                case SYSTEM_MSG:
                    SystemMsg systemMsg = (SystemMsg) msg.obj;
                    //同步到数据库
                    systemMsg.save();
                    ViseLog.d("收到系统消息："+systemMsg.getMsgTitle()+" Content:"+systemMsg.getMsgContent());
                    //把消息存到数据库
                    NotificationUtil.sendSystemMsg(MsgService.this, SystemMsgActivity.class,systemMsg.getMsgTitle(),systemMsg.getMsgContent());
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        EventUtil.register(this);
    }

    //处理服务器的ask消息
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void askMsg(final AskMessage askMessage){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.arg1 = MSG_ASK;
                message.obj = askMessage;
                handler.sendMessage(message);
            }
        }).start();
    }

  /*  @Subscribe(threadMode = ThreadMode.POSTING)
    public void chatMsg(final ChatMsg chatMsg){
       new Thread(new Runnable() {
           @Override
           public void run() {
               Message message = Message.obtain();
               message.obj = chatMsg;
               message.arg1 = MSG_CHAT;
               handler.sendMessage(message);
           }
       }).start();
    }
*/
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handlerSystemMsg(final SystemMsg systemMsg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.obj = systemMsg;
                message.arg1 = SYSTEM_MSG;
                handler.sendMessage(message);
            }
        }).start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }
}
