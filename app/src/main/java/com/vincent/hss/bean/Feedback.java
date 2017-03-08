package com.vincent.hss.bean;

import java.io.Serializable;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 12:11
 *
 * @version 1.0
 */

public class Feedback implements Serializable {

    private String title;
    private String content;
    private String createTime;

    public Feedback(){
        super();
    }

    public Feedback(String title, String content, String createTime) {
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
