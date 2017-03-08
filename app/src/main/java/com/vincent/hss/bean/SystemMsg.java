package com.vincent.hss.bean;

import java.io.Serializable;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 18:01
 *
 * @version 1.0
 */

public class SystemMsg implements Serializable {

    private String msgTitle;
    private String msgContent;

    public SystemMsg(){
        super();
    }

    public SystemMsg(String msgTitle, String msgContent) {
        this.msgTitle = msgTitle;
        this.msgContent = msgContent;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
