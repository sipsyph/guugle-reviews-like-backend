package com.sip.chillhub.main.business.model;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class PlaceSearchRequest extends BaseSearchRequest {

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
	
	private List<Integer> categoryType;
	private List<Integer> descType;
	private List<Integer> peopleTrafficType;
	private Double coordinatesX;
	private Double coordinatesY;
	private String sortByUpvotes;

	
	public boolean isEmpty() {
		if(
				isEmptyBase()&&
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
