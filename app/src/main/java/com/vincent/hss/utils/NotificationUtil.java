package com.vincent.hss.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.vincent.hss.R;
import com.vincent.hss.activity.NettyMsgDetailActivity;
import com.vise.log.ViseLog;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * @name Supplier
 * @class name：com.shangyi.utils
 * @class describe
 * @anthor Vincent QQ:1032006226
 * @time 2016/11/8 15:40
 * @change
 * @chang time
 * @class describe
 */
public class NotificationUtil {


    /**
     * 发送通知提示
     * @param context 上下文
     * @param imgId 通知栏显示的图标id
     * @param title 通知title
     * @param msg 消息
     * @param activity 点击通知需要跳转的Activity，Activity需要完整路径，包含包名
     */
    public static void sendNotification(Context context, String activity, int imgId, String title, String msg) {
        try {
            NotificationManager  mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            Intent resultIntent = new Intent(context, SystemUtilts.getReflectInstance(context,activity).getClass());
            resultIntent.putExtra("title",title);
            resultIntent.putExtra("msg",msg);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(imgId)
                    .setTicker("您有新的消息")
                    .setContentTitle(title)//通知的标题
                    .setContentText(msg)//显示在界面上的内容
                    .setContentIntent(pendingIntent);
            Notification mNotification = mBuilder.build();
            mNotification.iconLevel=imgId;
            //在通知栏上点击此通知后自动清除此通知
            mNotification.flags = Notification.FLAG_ONGOING_EVENT;//FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL  点击和清理可以去调
            mNotification.flags = Notification.COLOR_DEFAULT;
            mNotification.defaults = Notification.DEFAULT_SOUND;//声音效果，不震动
            mNotification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
            //设置发出消息的内容
            mNotification.tickerText = "您有新的消息";//通知产生的时候发出的内容
            //设置发出通知的时间
            mNotification.when = System.currentTimeMillis();
            mNotificationManager.notify((int) System.currentTimeMillis(),mNotification);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 显示通知
     * @param context
     * @param activity
     * @param imgId
     * @param title
     * @param msg
     * @param content
     */
    public static void sendNotification2(Context context, String activity, int imgId, String title, String msg,String content) {
        try {
            int notifyId = (int) System.currentTimeMillis();
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            Intent resultIntent = new Intent(context, SystemUtilts.getReflectInstance(context,activity).getClass());
            mBuilder.setAutoCancel(false);//设置消息点击之后取消
            resultIntent.putExtra("title",title);
            resultIntent.putExtra("msg",msg);
            resultIntent.putExtra("content",content);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(imgId)
                    .setTicker("您有新的消息")
                    .setContentTitle(title)//通知的标题
                    .setContentText(msg)//显示在界面上的内容
                    .setContentIntent(pendingIntent);
            Notification mNotification = mBuilder.build();
            mNotification.iconLevel=imgId;
            mNotification.flags = Notification.FLAG_AUTO_CANCEL;//设置点击之后消失
            mNotification.flags = Notification.COLOR_DEFAULT;
            mNotification.defaults = Notification.DEFAULT_SOUND;//声音效果，不震动
            mNotification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
            //设置发出消息的内容
            mNotification.tickerText = "您有新的消息";//通知产生的时候发出的内容
            //设置发出通知的时间
            mNotification.when = System.currentTimeMillis();
//        startForeground(notifyId, mNotification);//把该service创建为前台service
            mNotificationManager.notify(notifyId,mNotification);
            mNotificationManager.cancel(notifyId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 发送通知，点击跳转到相关的Activity，并传递值 点击通知可取消
     * @param context
     * @param c
     * @param content
     */
    public  static final void sendNotification(Context context,Class c,String content){
        // Notification.FLAG_ONGOING_EVENT --设置常驻 Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
//		notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
        NotificationManager mNotificationManager= (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("智能家车系统")
                .setContentText("您有新的消息")
                .setContentIntent(getDefalutIntent(context,Notification.FLAG_AUTO_CANCEL))
//				.setNumber(number)//显示数量
                .setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.app_logo);
        //点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent(context, c);
        resultIntent.putExtra("content",content);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    public static PendingIntent getDefalutIntent(Context context,int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(), flags);
        return pendingIntent;
    }
}
