package com.vincent.hss.bean;

import java.io.Serializable;


/**   
* @Title: Family.java 
* @Package com.vincent.lwx.bean 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Vincent   
* @date 2017年3月17日 下午11:23:00 
* @version V1.0   
*/
public class Family implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 	create table family_list(
		phone varchar(11) not null,
		familyPhone varchar(11) not null,
		remark varchar(20) not null,
		time varchar(20));
	 */
	
	private int id;
	private String phone;
	private String familyPhone;
	private String remark;//备注名称
	private String time;

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

	public String getFamilyPhone() {
		return familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}


