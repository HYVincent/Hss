package com.vincent.hss.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vincent.hss.servoce.HssService;
import com.vise.log.ViseLog;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/11 14:44
 *
 * @version 1.0
 */

public class HssBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent sayHelloIntent=new Intent(context,HssService.class);
            context.startService(sayHelloIntent);
            ViseLog.d("data","收到开机广播了，启动JulieService");
        }
    }
}
