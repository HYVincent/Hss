package com.vincent.hss.servoce;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

import com.vincent.hss.R;
import com.vincent.hss.utils.NotificationUtil;

public class HssService extends Service {
    public HssService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }

    private void createNotification() {
        NotificationUtil.sendNotification(this,"com.vincent.hss.activity.HomeActivity", R.drawable.app_logo,"智能家车系统","已连接");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
