package com.sip.chillhub.main.business.model;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class Memoir {
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Long getUsrId() {
		return usrId;
	}
	public void setUsrId(Long usrId) {
		this.usrId = usrId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Integer getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}
	public Integer[] getDescType() {
		return descType;
	}
	public void setDescType(Integer[] descType) {
		this.descType = descType;
	}
	public Integer getPeopleTrafficType() {
		return peopleTrafficType;
	}
	public void setPeopleTrafficType(Integer peopleTrafficType) {
		this.peopleTrafficType = peopleTrafficType;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getUps() {
		return ups;
	}
	public void setUps(Integer ups) {
		this.ups = ups;
	}
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	public Memoir(Long id, Long placeId, Long usrId, String name, String body, Integer categoryType, Integer[] descType,
			Integer peopleTrafficType, Date createdDate, Integer ups, boolean del) {
		this.id = id;
		this.placeId = placeId;
		this.usrId = usrId;
		this.name = name;
		this.body = body;
		this.categoryType = categoryType;
		this.descType = descType;
		this.peopleTrafficType = peopleTrafficType;
		this.createdDate = createdDate;
		this.ups = ups;
		this.del = del;
	}
	public Memoir() {
		
	}
	@Override
	public String toString() {
		return "Memoir [id=" + id + ", placeId=" + placeId + ", usrId=" + usrId + ", name=" + name + ", body=" + body
				+ ", categoryType=" + categoryType + ", descType=" + Arrays.toString(descType) + ", peopleTrafficType="
				+ peopleTrafficType + ", createdDate=" + createdDate + ", ups=" + ups + ", del=" + del + "]";
	}
	private Long id;
	private Long placeId;
	private Long usrId;
	private String name;
	private String body;
	private Integer categoryType;
	private Integer[] descType;
	private Integer peopleTrafficType;
	private Date createdDate;
	private Integer ups;
	private boolean del;

}
