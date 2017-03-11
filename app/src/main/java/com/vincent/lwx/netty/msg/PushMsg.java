package com.vincent.lwx.netty.msg;

/**
 * 服务器消息类
 *
 * @author
 * @version 2016/02/24 19:40
 */
public class PushMsg extends BaseMsg {
    private String account;
    private String content;
    private Integer status;

    public PushMsg() {
        super();
        setType(MsgType.PUSH);
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
