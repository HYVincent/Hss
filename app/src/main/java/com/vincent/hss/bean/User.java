package com.vincent.hss.bean;

import java.io.Serializable;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/5 22:40
 *
 * @version 1.0
 */

public class User implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6375697395831845246L;

    /**
     * 用户id
     */
    private  String user_id;

    /**
     * 用户手机号码
     */
    private  String phone;



    /**
     * 用户名
     */
    private String nickname;

    /**
     * 用户头像地址
     */
    private String head;

    /**
     * 性别
     */
    private  String sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 生活状态(发表的说说)
     */
    private String live_status;

    /**
     * 注册时间
     */
    private String createTime;

    /**
     * 退出登录时间
     */
    private String logoutTime;

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLive_status() {
        return live_status;
    }

    public void setLive_status(String live_status) {
        this.live_status = live_status;
    }
}
