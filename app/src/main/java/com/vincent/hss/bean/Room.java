package com.vincent.hss.bean;


import org.litepal.crud.DataSupport;

/**
 * description ：http://www.jianshu.com/p/853401a7d02b
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 19:25
 *
 * @version 1.0
 */

public class Room extends DataSupport{

    private int id;
    private String phone;
    private String roomType;
    private String roomName;
    private String roomImg;//图标
    private String roomBigImg;//大图 实际上是一个List<String>类型，到时候拿出来转换吧，我艹

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomImg() {
        return roomImg;
    }

    public void setRoomImg(String roomImg) {
        this.roomImg = roomImg;
    }

    public String getRoomBigImg() {
        return roomBigImg;
    }

    public void setRoomBigImg(String roomBigImg) {
        this.roomBigImg = roomBigImg;
    }
}
