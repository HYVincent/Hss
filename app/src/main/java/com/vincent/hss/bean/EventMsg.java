package com.vincent.hss.bean;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/13 23:56
 *
 * @version 1.0
 */

public class EventMsg {

    private String msg;

    private String tab;

    public EventMsg(String msg, String tab) {
        this.msg = msg;
        this.tab = tab;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
}
