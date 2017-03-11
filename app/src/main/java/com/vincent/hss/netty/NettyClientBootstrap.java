package com.vincent.hss.netty;

import android.text.TextUtils;

import com.vincent.hss.base.BaseApplication;
import com.vincent.lwx.netty.msg.Constants;
import com.vincent.lwx.netty.msg.LoginMsg;
import com.vincent.lwx.netty.msg.MsgType;
import com.vise.log.ViseLog;

import java.net.InetSocketAddress;
import java.util.Date;

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

/**
 * @author 徐飞
 * @version 2016/02/25 14:11
 */
public class NettyClientBootstrap {
    private int port = Config.SERVICE_PORT;
    private String host = Config.SERVICE_IP ;
    public SocketChannel socketChannel;
    private ChannelFuture future = null;
    private String account;
    private int a=1;
    private int b=1;

    public void startNetty() throws InterruptedException {

        if (socketChannel!=null && socketChannel.isOpen()) {
            ViseLog.e(NettyClientBootstrap.class.getSimpleName()+"已经连接");
        }else {
            Constants.setAccount(BaseApplication.user.getPhone());
            new Thread(new Runnable() {//在线程中执行，不然会无响应..严重卡机...
                @Override
                public void run() {
                    ViseLog.e(NettyClientBootstrap.class.getSimpleName()+"长链接开始");
                    try {
                        if (start()) {
                            if(TextUtils.isEmpty(account)){
                                //如果账号为空，就不要登陆，说明当前账号没有登录
                                return;
                            }
                            LoginMsg loginMsg = new LoginMsg();
                            // TODO: 2016/2/23
                            String deviceToken= "";
                            ViseLog.d("deviceToken="+ deviceToken);
                            loginMsg.setType(MsgType.LOGIN);
                            loginMsg.setKey(deviceToken);
                            loginMsg.setAccount(BaseApplication.user.getPhone());
                            ViseLog.d("providerAccout="+BaseApplication.user.getPhone());
                            System.out.println("开始登录NettyService");
                            socketChannel.writeAndFlush(loginMsg);
                            System.out.println("NettyClientBootstrap-->登录服务器成功，这是第"+(a++)+"次登陆"+",登录时间："+new Date(System.currentTimeMillis()));
                        }else {
                            System.out.println("NettyClientBootstrap-->登陆失败，这是第"+(b++)+"次尝试登陆");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        System.out.println("NettyClientBootstrap-->登录失败，正在等待3s后重新尝试登录...");
                                        Thread.sleep(3*1000);
                                        startNetty();//失败了就继续连接
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("NettyClientBootstrap-->登录异常..");
//                        MyApplication.getInstance().stopService(new Intent(MyApplication.getInstance(), NettyPushService.class));
                    }
                }
            }).start();
        }
    }



    private Boolean start() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(host, port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new IdleStateHandler(10, 10, 0));//10表示多久往服务器写入心跳消息 20表示20秒读取一次服务器消息
                socketChannel.pipeline().addLast(new ObjectEncoder());
                socketChannel.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                socketChannel.pipeline().addLast(new NettyClientHandler());
            }
        });

        try {
            future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                System.out.println("NettyClientBootstrap-->连接服务器成功"+new Date().toString());
                return true;
            } else {
                System.out.println("NettyClientBootstrap-->服务器连接失败");
                startNetty();
                return false;
            }
        } catch (Exception e) {
            System.out.println("NettyClientBootstrap-->发生异常了，异常信息："+e.toString());
            e.printStackTrace();
            return false;
        }
    }

    public void closeChannel() {
        try {
            if (socketChannel != null) {
                socketChannel.close();
                if(future.channel().close().isSuccess()){//断开和NettyService的连接
                    System.out.println("NettyClientBootstrap-->closeChannel()方法调用，Channel成功关闭...");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("NettyClientBootstrap-->closeChannel()方法调用，发生异常了");
        }
    }

    /**
     * 返回的通道是否打开
     * @return
     */
    public boolean isOpen() {
        if (socketChannel != null) {
            System.out.println(socketChannel.isOpen());
            return socketChannel.isOpen();
        }
        return false;
    }
}