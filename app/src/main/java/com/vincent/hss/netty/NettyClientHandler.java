package com.vincent.hss.netty;


import android.content.Intent;

import com.vincent.hss.R;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.servoce.NettyPushService;
import com.vincent.hss.utils.SystemUtilts;
import com.vincent.lwx.netty.msg.BaseMsg;
import com.vincent.lwx.netty.msg.LoginMsg;
import com.vincent.lwx.netty.msg.MsgType;
import com.vincent.lwx.netty.msg.PingMsg;
import com.vincent.lwx.netty.msg.PushMsg;
import com.vise.log.ViseLog;

import java.io.IOException;
import java.net.SocketException;
import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * @author 徐飞
 * @version 2016/02/25 14:11
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg> {
    //设置心跳时间  开始
    public static final int MIN_CLICK_DELAY_TIME = 3 * 1000;
    private long lastClickTime = 0;
    //设置心跳时间   结束
    private boolean isNettyLogin = false;
    private int count = 1;


    //利用写空闲发送心跳检测消息
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                        lastClickTime = System.currentTimeMillis();
                        PingMsg pingMsg = new PingMsg();
//                        pingMsg.setKey("当前手机型号：" + SystemUtilts.getPhoneManufacturer() + "," + SystemUtilts.getPhoneMdel());
//                        pingMsg.setKey(App.getShared().getString("deviceToken"));
                        pingMsg.setKey("");
                        ctx.writeAndFlush(pingMsg);
                        System.out.println("NettyClientHandler-->client(发送ping)-->service");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    //这里是断线要进行的操作
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ViseLog.e("channelInactive(ChannelHandlerContext ctx)函数调用,关闭NettyPushService");
        BaseApplication.getApplication().stopService(new Intent( BaseApplication.getApplication(), NettyPushService.class));
    }

    //这里是出现异常的话要进行的操作
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        cause.printStackTrace();
        System.out.println("cause.getClass()==  " + cause.getClass());
        System.out.println("cause.getClass().getName()===  "+cause.getClass().getName());
        if (cause instanceof IOException) {
            BaseApplication.getApplication().stopService(new Intent( BaseApplication.getApplication(), NettyPushService.class));
        } else if (cause instanceof SocketException) {
            BaseApplication.getApplication().stopService(new Intent( BaseApplication.getApplication(), NettyPushService.class));
        } else {
            Intent intent = new Intent();
            intent.setAction("QiangZhiExit");
            intent.putExtra("error_message", "登录状态异常，请重新登录");
            BaseApplication.getApplication().sendBroadcast(intent);
            BaseApplication.getApplication().stopService(new Intent(BaseApplication.getApplication(), NettyPushService.class));
        }
    }

    //这里是接受服务端发送过来的消息
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
        switch (baseMsg.getType()) {
            case LOGIN:
                while (!isNettyLogin) {
                    //向服务器发起登录
                    ViseLog.d("正在登录..Netty踢人系统..");
                    LoginMsg loginMsg = new LoginMsg();
                    //这里不采用供应商或者学生账号来实现推送了，采用手机设备识别码IME来实现消息的推送
                    String imei = SystemUtilts.getIMEINumber(BaseApplication.getApplication());
                    System.out.println("NettyClientHandler-->imei=" + imei);
                    loginMsg.setType(MsgType.LOGIN);
                    loginMsg.setAccount(BaseApplication.user.getPhone());
                    ViseLog.d("providerAccout=" +BaseApplication.user.getPhone());
                    loginMsg.setKey(imei);
                    channelHandlerContext.writeAndFlush(loginMsg);
                }
                LoginMsg loginMsg = (LoginMsg) baseMsg;
                ViseLog.e("login message-->" + loginMsg.getStatus());
                if (loginMsg.getStatus() == 0) {
                    Intent intent = new Intent();
                    intent.setAction("QiangZhiExit");
                    intent.putExtra("error_message", "登录状态异常，请重新登录");
                    BaseApplication.getApplication().sendBroadcast(intent);
                    BaseApplication.getApplication().stopService(new Intent(BaseApplication.getApplication(), NettyPushService.class));
                }
                break;
            case PING:
                System.out.println("NettyClientHandler-->service(ping)-->client,收到服务器发过来的ping消息，这是第" + (count++) + "次 " + new Date().toString());
                break;
            case PUSH://接收到服务器Push到的消息
                PushMsg pushMsg = (PushMsg) baseMsg;
                if (pushMsg.getStatus() == null) {
                    return;
                }
                ViseLog.d("pushMsg-->"+pushMsg);
                if (pushMsg.getStatus() < 0) {//强制用户退出登录
                    System.out.println("NettyClientHandler-->有人登录了你的账号，你被迫强制退出...");
                    BaseApplication.getShared().putString("providerAccout","");
                    Intent intent = new Intent();
                    intent.setAction("QiangZhiExit");
                    intent.putExtra("error_message", BaseApplication.getApplication().getString(R.string.exit_hint));
                    BaseApplication.getApplication().sendBroadcast(intent);
                    PushClient.close();
                    BaseApplication.getApplication().stopService(new Intent(BaseApplication.getApplication(), NettyPushService.class));
                } else if (pushMsg.getStatus() == 1) {//登录结果反馈
                    ViseLog.e("NettyClientHandler-->登录结果反馈-->Success  " + new Date(System.currentTimeMillis()));
                    isNettyLogin = true;
                    ViseLog.d("已经登录成功...");
                    Intent intent = new Intent();
                    intent.setAction("NettyConnectSuccess");
                    BaseApplication.getApplication().sendBroadcast(intent);
                } else if (pushMsg.getStatus() == 0) {
                    Intent intent = new Intent();
                    intent.setAction("QiangZhiExit");
                    BaseApplication.getApplication().sendBroadcast(intent);
                    BaseApplication.getApplication().stopService(new Intent(BaseApplication.getApplication(), NettyPushService.class));
                } else {//普通消息
                    ViseLog.e("NettyClientHandler-->普通消息");
//                    NotificationUtil.sendNotification(BaseApplication.getApplication()., "com.shangyi.supplier.ui.activity.LoadUriActivity", R.mipmap.et_app_icon, "title", pushMsg.getContent());
                }
                System.out.println("NettyClientHandler-->title=" + pushMsg.getAccount() + ",Content=" + pushMsg.getContent());
                break;
            default:
                ViseLog.e("NettyClientHandler-->default..");
                break;
        }
        ReferenceCountUtil.release(baseMsg);
    }

}