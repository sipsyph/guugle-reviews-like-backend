package com.sip.chillhub.main.business.model;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class PlaceSearchRequest {
	
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


	@Override
	public String toString() {
		return "PlaceSearchRequest [id=" + id + ", searchString=" + searchString + ", orderByAndSortBy="
				+ orderByAndSortBy + "]";
	}


	private List<Long> id;
	private String searchString;
	private List<String> orderByAndSortBy;

	
	public boolean isEmpty() {
		if((id==null||id.isEmpty())&&searchString==null) {
			return true;
		}
		return false;
	}
	
}
