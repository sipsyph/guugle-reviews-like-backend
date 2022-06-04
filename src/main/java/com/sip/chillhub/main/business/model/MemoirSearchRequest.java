package com.sip.chillhub.main.business.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class MemoirSearchRequest {
	
	public MemoirSearchRequest() {
		
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
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public List<Long> getPlaceId() {
		return placeId;
	}
	public void setPlaceId(List<Long> placeId) {
		this.placeId = placeId;
	}
	public List<String> getOrderByAndSortBy() {
		return orderByAndSortBy;
	}
	public void setOrderByAndSortBy(List<String> orderByAndSortBy) {
		this.orderByAndSortBy = orderByAndSortBy;
	}
	@Override
	public String toString() {
		return "MemoirSearchRequest [id=" + id + ", searchString=" + searchString + ", categoryId=" + categoryId
				+ ", placeId=" + placeId + ", orderByAndSortBy=" + orderByAndSortBy + "]";
	}
	private List<Long> id;
	private String searchString;
	private Long categoryId;
	private List<Long> placeId;
	private List<String> orderByAndSortBy;

	public boolean isEmpty() {
		if(categoryId==null&&
				(id==null||id.isEmpty())&&
				(placeId==null||placeId.isEmpty())&&
				searchString==null) {
			return true;
		}
		return false;
	}
	
}
