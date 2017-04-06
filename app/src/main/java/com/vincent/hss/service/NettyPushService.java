package com.vincent.hss.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.vincent.hss.R;
import com.vincent.hss.activity.NettyMsgDetailActivity;
import com.vincent.hss.netty.Config;
import com.vincent.hss.netty.PushClient;
import com.vincent.hss.utils.EventUtil;
import com.vincent.hss.utils.NotificationUtil;
import com.vincent.lwx.netty.msg.PushMsg;
import com.vise.log.ViseLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @name Supplier
 * @class name：com.shangyi.service
 * @class describe
 * @anthor Vincent QQ:1032006226
 * @time 2016/11/1 10:55
 * @change
 * @chang time
 * @class describe
 */
public class NettyPushService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Config.openNetty){
            ViseLog.e(NettyPushService.class.getSimpleName(),"开始连接服务器");
            try {
                //开始连接服务器
                PushClient.start();
            }catch (Exception e){
                e.printStackTrace();
                ViseLog.d(NettyPushService.class.getSimpleName()+"NettyClient Start Error");
            }
            if(PushClient.isOpen()){
                ViseLog.d("Netty Push --连接成功");
                NotificationUtil.sendNotification(this,"com.vincent.hss.activity.HomeActivity", R.drawable.app_logo,"智能家车系统","已连接");
            }else {
                ViseLog.d("Netty Push --连接失败");
            }
            EventUtil.register(this);
        }else {
            ViseLog.e(NettyPushService.class.getSimpleName(),"PushClient is close");
        }

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void fromNettyServiceMsg(final PushMsg pushMsg){
        //处理消息 测试通过
        ViseLog.d("收到服务器消息:"+pushMsg.getPhoneNum()+" "+pushMsg.getContent());
       Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            public void run(){
                NotificationUtil.sendNotification(NettyPushService.this, NettyMsgDetailActivity.class,pushMsg.getContent());
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Config.openNetty&& PushClient.getBootstrap()!=null){
            PushClient.close();
            EventUtil.unregister(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
