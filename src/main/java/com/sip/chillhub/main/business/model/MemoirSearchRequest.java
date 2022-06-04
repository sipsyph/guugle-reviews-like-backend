package com.sip.chillhub.main.business.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class MemoirSearchRequest {
	
	private Long id;
	private String searchString;
	private Long categoryId;
	private Long placeId;
	private List<String> orderByAndSortBy;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<String> getOrderByAndSortBy() {
		return orderByAndSortBy;
	}

	public void setOrderByAndSortBy(List<String> orderBy) {
		this.orderByAndSortBy = orderBy;
	}
	
	public boolean isEmpty() {
		if(categoryId==null&&id==null&&searchString==null&&
				placeId==null) {
			return true;
		}
		return false;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	
}
