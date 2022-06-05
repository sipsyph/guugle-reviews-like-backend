package com.sip.chillhub.main.business.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class User {
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getUsrType() {
		return usrType;
	}
	public void setUsrType(Integer usrType) {
		this.usrType = usrType;
	}
	public boolean isPremium() {
		return isPremium;
	}
	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}
	public Integer getCoins() {
		return coins;
	}
	public void setCoins(Integer coins) {
		this.coins = coins;
	}
	public String getAvatarImg() {
		return avatarImg;
	}
	public void setAvatarImg(String avatarImg) {
		this.avatarImg = avatarImg;
	}
	public Integer getNameStyle() {
		return nameStyle;
	}
	public void setNameStyle(Integer nameStyle) {
		this.nameStyle = nameStyle;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", pass=" + pass + ", desc=" + desc + ", usrType=" + usrType
				+ ", isPremium=" + isPremium + ", coins=" + coins + ", avatarImg=" + avatarImg + ", nameStyle="
				+ nameStyle + ", lastLoginDate=" + lastLoginDate + ", del=" + del + "]";
	}
	public User(Long id, String name, String pass, String desc, Integer usrType, boolean isPremium, Integer coins,
			String avatarImg, Integer nameStyle, Date lastLoginDate, boolean del) {
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.desc = desc;
		this.usrType = usrType;
		this.isPremium = isPremium;
		this.coins = coins;
		this.avatarImg = avatarImg;
		this.nameStyle = nameStyle;
		this.lastLoginDate = lastLoginDate;
		this.del = del;
	}
	public User() {
		
	}
	private Long id;
	private String name;
	private String pass;
	private String desc;
	private Integer usrType;
	private boolean isPremium;
	private Integer coins;
	private String avatarImg;
	private Integer nameStyle;
	private Date lastLoginDate;
	private boolean del;

}
