package com.vincent.lwx.netty.msg;

/**
 * 消息基本信息
 *
 * @author 徐飞
 * @version 2016/02/24 19:40
 */
public class Constants {
    private static String account;

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        Constants.account = account;
    }
}
