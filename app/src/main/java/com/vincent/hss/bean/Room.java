package com.vincent.hss.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

/**
 * description ：http://www.jianshu.com/p/853401a7d02b
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/7 19:25
 *
 * @version 1.0
 */

@Entity
public class Room {

    @Id(autoincrement = true)
    private long id;
    private String roomName;
    private int romImg;//图标
    private String roomBigImg;//大图 实际上是一个List<String>类型，到时候拿出来转换吧，我艹
    @Generated(hash = 899069452)
    public Room(long id, String roomName, int romImg, String roomBigImg) {
        this.id = id;
        this.roomName = roomName;
        this.romImg = romImg;
        this.roomBigImg = roomBigImg;
    }
    @Generated(hash = 703125385)
    public Room() {
    }


    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getRoomName() {
        return this.roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public int getRomImg() {
        return this.romImg;
    }
    public void setRomImg(int romImg) {
        this.romImg = romImg;
    }
    public String getRoomBigImg() {
        return this.roomBigImg;
    }
    public void setRoomBigImg(String roomBigImg) {
        this.roomBigImg = roomBigImg;
    }
  
}
