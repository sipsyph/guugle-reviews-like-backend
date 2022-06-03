package com.sip.chillhub.main.business.model;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class PlaceSearchRequest {
	
	private Long id;
	private String searchString;
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

	public List<String> getOrderByAndSortBy() {
		return orderByAndSortBy;
	}

	public void setOrderByAndSortBy(List<String> orderBy) {
		this.orderByAndSortBy = orderBy;
	}
	
	public boolean isEmpty() {
		if(id==null&&searchString==null) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return getId()+" "+getSearchString()+" "+getOrderByAndSortBy().toString();
	}
	
}
