package com.vincent.hss.netty;


import com.vise.log.ViseLog;

/**
 * netty推送客户端
 *
 * @author 徐飞
 * @version 2016/02/25 15:15
 */
public class PushClient {

    private static NettyClientBootstrap bootstrap;

    public static NettyClientBootstrap getBootstrap() {
        return bootstrap;
    }

    public static void setBootstrap(NettyClientBootstrap bootstrap) {
        PushClient.bootstrap = bootstrap;
    }

    public static void create() throws Exception{
        NettyClientBootstrap bootstrap=new NettyClientBootstrap();
        if(bootstrap!=null){
            PushClient.setBootstrap(bootstrap);
        }else {
            ViseLog.e(PushClient.class.getSimpleName(),"PushClicnt初始化异常");
        }
    }

    public static void start(){
        NettyClientBootstrap bootstrap = PushClient.getBootstrap();
        try{
            bootstrap.startNetty();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        PushClient.setBootstrap(bootstrap);
    }

    /**
     * 调用此方法关闭通道后会自动重连，如果想真正实现关闭，
     * 需要修改handler中重写的断线处理方法。
     * @see NettyClientHandler
     */
    public static void close(){
        try {
            NettyClientBootstrap bootstrap = PushClient.getBootstrap();
            if(bootstrap!=null){
                bootstrap.closeChannel();
                PushClient.setBootstrap(bootstrap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * @return 返回通道连接状态
     */
    public static boolean isOpen(){
        NettyClientBootstrap bootstrap = PushClient.getBootstrap();
        if(bootstrap!=null){
            return bootstrap.isOpen();
        }else {
            return false;
        }
    }
}
