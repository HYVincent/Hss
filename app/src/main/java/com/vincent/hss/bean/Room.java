package com.vincent.hss.bean;

import java.io.Serializable;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 19:25
 *
 * @version 1.0
 */

public class Room implements Serializable {

    private String roomName;
    private int romImg;//图标
    private int romBigImg;//大图

    public Room(String roomName, int romImg, int romBigImg) {
        this.roomName = roomName;
        this.romImg = romImg;
        this.romBigImg = romBigImg;
    }

    public int getRomBigImg() {
        return romBigImg;
    }

    public void setRomBigImg(int romBigImg) {
        this.romBigImg = romBigImg;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRomImg() {
        return romImg;
    }

    public void setRomImg(int romImg) {
        this.romImg = romImg;
    }
}
