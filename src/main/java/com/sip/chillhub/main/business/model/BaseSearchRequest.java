package com.sip.chillhub.main.business.model;

import java.util.List;

public class BaseSearchRequest {
	public boolean isObj() {
		return obj;
	}
	public void setObj(boolean obj) {
		this.obj = obj;
	}
	public List<Long> getId() {
		return id;
	}
	public void setId(List<Long> id) {
		this.id = id;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public List<String> getOrderByAndSortBy() {
		return orderByAndSortBy;
	}
	public void setOrderByAndSortBy(List<String> orderByAndSortBy) {
		this.orderByAndSortBy = orderByAndSortBy;
	}
	protected boolean obj;
	protected List<Long> id;
	protected String searchString;
	protected List<String> orderByAndSortBy;
	
	public boolean isEmptyBase() {
		if(
				(id==null||id.isEmpty())&&
				searchString==null) {
			return true;
		}
		return false;
	}
}
