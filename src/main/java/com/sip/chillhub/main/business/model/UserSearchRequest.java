package com.sip.chillhub.main.business.model;

import java.util.Date;
import java.util.List;

public class UserSearchRequest {
	
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
	public boolean isObj() {
		return obj;
	}
	public void setObj(boolean obj) {
		this.obj = obj;
	}
	public List<String> getOrderByAndSortBy() {
		return orderByAndSortBy;
	}
	public void setOrderByAndSortBy(List<String> orderByAndSortBy) {
		this.orderByAndSortBy = orderByAndSortBy;
	}
	private Long id;
	private String name;
	private List<String> orderByAndSortBy;
	private boolean obj;
	
	public boolean isEmpty() {
		if(id==null&&name==null) {
			return true;
		}
		return false;
	}

}
