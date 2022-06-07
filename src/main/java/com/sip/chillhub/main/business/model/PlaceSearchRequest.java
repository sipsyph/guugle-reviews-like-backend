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


	public List<Integer> getCategoryType() {
		return categoryType;
	}


	public void setCategoryType(List<Integer> categoryType) {
		this.categoryType = categoryType;
	}


	public List<Integer> getDescType() {
		return descType;
	}


	public void setDescType(List<Integer> descType) {
		this.descType = descType;
	}


	public List<Integer> getPeopleTrafficType() {
		return peopleTrafficType;
	}


	public void setPeopleTrafficType(List<Integer> peopleTrafficType) {
		this.peopleTrafficType = peopleTrafficType;
	}


	public Double getCoordinatesX() {
		return coordinatesX;
	}


	public void setCoordinatesX(Double coordinatesX) {
		this.coordinatesX = coordinatesX;
	}


	public Double getCoordinatesY() {
		return coordinatesY;
	}


	public void setCoordinatesY(Double coordinatesY) {
		this.coordinatesY = coordinatesY;
	}

	public String getSortByUpvotes() {
		return sortByUpvotes;
	}


	public void setSortByUpvotes(String sortByUpvotes) {
		this.sortByUpvotes = sortByUpvotes;
	}

	public List<String> getOrderByAndSortBy() {
		return orderByAndSortBy;
	}


	public void setOrderByAndSortBy(List<String> orderByAndSortBy) {
		this.orderByAndSortBy = orderByAndSortBy;
	}


	public boolean isObj() {
		return obj;
	}


	public void setObj(boolean obj) {
		this.obj = obj;
	}
	
	private List<Long> id;
	private String searchString;
	private List<Integer> categoryType;
	private List<Integer> descType;
	private List<Integer> peopleTrafficType;
	private Double coordinatesX;
	private Double coordinatesY;
	private String sortByUpvotes;
	private List<String> orderByAndSortBy;
	private boolean obj;

	
	public boolean isEmpty() {
		if(
				(id==null||id.isEmpty())&&
				searchString==null&&
				coordinatesX==null&&
				coordinatesY==null) {
			return true;
		}
		return false;
	}
	
	public boolean isMemoirAttributesEmpty() {
		if(
				(categoryType==null||categoryType.isEmpty())&&
				(descType==null||descType.isEmpty())&&
				(peopleTrafficType==null||peopleTrafficType.isEmpty())&&
				sortByUpvotes==null) {
			return true;
		}
		return false;
	}
	
	public boolean isMemoirAttributesEmptyExceptUpvotesSort() {
		if(
				(categoryType==null||categoryType.isEmpty())&&
				(descType==null||descType.isEmpty())&&
				(peopleTrafficType==null||peopleTrafficType.isEmpty())&&
				sortByUpvotes!=null) {
			return true;
		}
		return false;
	}





	
}
