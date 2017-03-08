package com.vincent.hss.bean;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/8 19:56
 *
 * @version 1.0
 */

public class ClassBean {

    private String title;
    private int color;

    public ClassBean(String title, int color) {
        this.title = title;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
