package com.vincent.hss.servoce;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.vincent.hss.netty.Config;
import com.vincent.hss.netty.PushClient;
import com.vise.log.ViseLog;


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
//            PushClient.create();//初始化、在MyApplication中
                PushClient.start();//开始连接服务器
            }catch (Exception e){
                e.printStackTrace();
                ViseLog.d(NettyPushService.class.getSimpleName()+"NettyClient Start Error");
            }
            if(PushClient.isOpen()){
                ViseLog.d("Netty Push --连接成功");
            }else {
                ViseLog.d("Netty Push --连接失败");
            }
//        PushClient.close();//关闭通道
//        PushClient.isOpen();//判断是否连接成功
        }else {
            ViseLog.e(NettyPushService.class.getSimpleName(),"PushClient is close");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Config.openNetty&& PushClient.getBootstrap()!=null){
            PushClient.close();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_STICKY;
    }
}
