package com.vincent.hss.netty;

import com.vincent.hss.base.BaseApplication;
import com.vincent.lwx.netty.msg.Constants;
import com.vincent.lwx.netty.msg.LoginMsg;
import com.vincent.lwx.netty.msg.MsgType;
import com.vincent.lwx.netty.msg.PushMsg;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author 徐飞
 * @version 2016/02/25 14:11
 */
public class NettyClientBootstrap {
    protected static final int port = Config.SERVICE_PORT;//服务器端口
    protected static final String host =Config.SERVICE_IP_MY ;//服务器地址
    public static SocketChannel socketChannel;//客户端通道
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public void startNetty() throws InterruptedException {
        if (socketChannel!=null && socketChannel.isOpen()) {
            System.out.println("已经连接");
        }else {
            Constants.setPhoneNum(BaseApplication.user.getPhone());// TODO: 2016/2/23
            System.out.println("长链接开始");
            if (start()) {
                LoginMsg loginMsg = new LoginMsg();// TODO: 2016/2/23
                loginMsg.setPassword("");
                loginMsg.setUserName(BaseApplication.user.getNickname());
                loginMsg.setPhoneNum(BaseApplication.user.getPhone());
                socketChannel.writeAndFlush(loginMsg);
                System.out.println("长链接成功");
            }else {
                System.out.println("长链接失败...");
                start();
            }
        }
    }

    protected Boolean start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(host, port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));//10表示多久往服务器写入心跳消息 20表示20秒读取一次服务器消息
                socketChannel.pipeline().addLast(new ObjectEncoder());
                socketChannel.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                socketChannel.pipeline().addLast(new NettyClientHandler());
            }
        });
        ChannelFuture future = null;
        try {
            //连接Netty服务器
            future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                System.out.println("connect server  成功---------");
                return true;
            } else {
                System.out.println("connect server  失败---------");
                startNetty();
                return false;
            }
        } catch (Exception e) {
            System.out.println("无法连接----------------");
            return false;
        }
    }

    public void closeChannel() {
        if (socketChannel != null) {
            socketChannel.close();
        }
    }

    public boolean isOpen() {
        if (socketChannel != null) {
            System.out.println(socketChannel.isOpen());
            return socketChannel.isOpen();
        }
        return false;
    }

    /**
     * 发送消息
     * @param pushMsg
     */
    public static void sendMsg(PushMsg pushMsg){
        if(socketChannel!=null){
            socketChannel.writeAndFlush(pushMsg);
        }else {
            throw new NullPointerException("socketChannel is null ,can't send msg");
        }
    }
}