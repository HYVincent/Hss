package com.vincent.lwx.netty.msg;

/**
 * 登录验证消息类型
 *
 * @author 徐飞
 * @version 2016/02/24 19:40
 */
public class LoginMsg extends BaseMsg {
    public LoginMsg(){
        super();
        setType(MsgType.LOGIN);
    }
}
