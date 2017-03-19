package com.vincent.hss.bean;


/**   
* @Title: App.java 
* @Package com.vincent.lwx.bean 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Vincent   
* @date 2017年3月16日 下午12:30:44 
* @version V1.0   
*/

public class App {
	
	/**
	 * 	create table app(
		id int(10) not null primary key auto_increment,
		version varchar(20) not null,
		updateDesc  varchar(100) not null,
		downUrl varchar(100) not null,
		releaseDate varchar(10) not null
		) engine = InnoDB default charset = utf8;
	 */
	
	private int id;
	private String version;//版本号
	private String updateDesc;//升级描述
	private String downUrl;//下载地址
	private String releaseDate;//升级日期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
}


