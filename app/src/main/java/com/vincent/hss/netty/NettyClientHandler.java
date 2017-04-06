package com.vincent.hss.netty;


import android.content.Intent;


import com.alibaba.fastjson.JSON;
import com.vincent.hss.base.BaseApplication;
import com.vincent.hss.config.*;
import com.vincent.hss.config.Config;
import com.vincent.hss.service.NettyPushService;
import com.vincent.hss.utils.EventUtil;
import com.vincent.hss.utils.NotificationUtil;
import com.vincent.lwx.netty.msg.AskMessage;
import com.vincent.lwx.netty.msg.BaseMsg;
import com.vincent.lwx.netty.msg.ChatMsg;
import com.vincent.lwx.netty.msg.LoginMsg;
import com.vincent.lwx.netty.msg.MsgType;
import com.vincent.lwx.netty.msg.PingMsg;
import com.vincent.lwx.netty.msg.PushMsg;
import com.vincent.lwx.netty.msg.SystemMsg;
import com.vise.log.ViseLog;

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
    public static final int MIN_CLICK_DELAY_TIME = 1000 * 3;
    private long lastClickTime = 0;
    //设置心跳时间   结束

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
                        ctx.writeAndFlush(pingMsg);
                        System.out.println("send ping to server----------");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    //这里是断线要进行的操作
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("重连了。---------");
        NettyClientBootstrap bootstrap = PushClient.getBootstrap();
        bootstrap.startNetty();
    }

    //这里是出现异常的话要进行的操作
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        try {
            super.exceptionCaught(ctx, cause);
            System.out.println("出现异常了。。。。。。。。。。。。。");
            cause.printStackTrace();
            BaseApplication.getApplication().stopService(new Intent(BaseApplication.getApplication(), NettyPushService.class));
        }catch (Exception e){
            e.printStackTrace();
            ViseLog.e("Netty异常了，停止NettyService");
        }
    }

    //这里是接受服务端发送过来的消息
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
        if(baseMsg == null){
            ViseLog.d("baseMsg is null");
            return;
        }
        switch (baseMsg.getType()) {
            case LOGIN:
                //向服务器发起登录
                LoginMsg loginMsg = new LoginMsg();
                loginMsg.setType(MsgType.LOGIN);
                loginMsg.setPhoneNum(BaseApplication.user.getPhone());
                loginMsg.setUserName(BaseApplication.user.getNickname());
                channelHandlerContext.writeAndFlush(loginMsg);
                break;
            case PING:
                System.out.println("receive ping from server----------");
                break;
            case PUSH:
                PushMsg pushMsg = (PushMsg) baseMsg;
                if(pushMsg!=null){
                    ViseLog.d("pushMsg:"+pushMsg.getPhoneNum()+" "+pushMsg.getContent());
                    EventUtil.post(pushMsg);
                }else {
                    ViseLog.d("推送消息是空的");
                }
                break;
            case CHAT://表示为聊天
                ChatMsg chatMsg = (ChatMsg)baseMsg;
                System.out.println(System.currentTimeMillis());
                /*if(chatMsg!=null){
                    EventUtil.post(chatMsg);
                    if(BaseApplication.getShared().getString(Config.MSG.CHATING,"").equals(chatMsg.getPhoneNum())){
                        System.out.println("不发送通知...");
                    }else {
                        System.out.println("发送通知...");
                        NotificationUtil.sendNotificationChatMsg(BaseApplication.getApplication(),chatMsg.getAsk_phone(), JSON.toJSONString(chatMsg));
                    }
                }*/
                break;
            case ASK:
                AskMessage askMessage = (AskMessage)baseMsg;
                if(askMessage == null){
                    return;
                }
                ViseLog.d("收到ask类型消息：fromPhone "+askMessage.getFromPhone()+" content-->"+askMessage.getMsgContent());
                EventUtil.post(askMessage);//把这个消息发送出去
                break;
            case SYSTEM_MSG:
                //系统通知
                SystemMsg systemMsg = (SystemMsg)baseMsg;
                if(systemMsg == null){
                    return;
                }
                ViseLog.d("收到SYSTEM_MSG类型消息：title-->"+systemMsg.getMsgTitle()+" msvContent-->"+systemMsg.getMsgContent());
                EventUtil.post(systemMsg);
                System.out.println("----------------");
                break;
            default:
                System.out.println("default..");
                break;
        }
        ReferenceCountUtil.release(baseMsg);
    }
}
