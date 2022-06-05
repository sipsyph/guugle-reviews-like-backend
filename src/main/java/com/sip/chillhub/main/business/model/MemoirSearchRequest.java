package com.sip.chillhub.main.business.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class MemoirSearchRequest {

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

	public List<Long> getPlaceId() {
		return placeId;
	}

	public void setPlaceId(List<Long> placeId) {
		this.placeId = placeId;
	}

	public List<Long> getUsrId() {
		return usrId;
	}

	public void setUsrId(List<Long> usrId) {
		this.usrId = usrId;
	}

	public List<String> getOrderByAndSortBy() {
		return orderByAndSortBy;
	}

	public void setOrderByAndSortBy(List<String> orderByAndSortBy) {
		this.orderByAndSortBy = orderByAndSortBy;
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

	public boolean isObj() {
		return obj;
	}

	public void setObj(boolean obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "MemoirSearchRequest [id=" + id + ", searchString=" + searchString + ", placeId=" + placeId + ", usrId="
				+ usrId + ", orderByAndSortBy=" + orderByAndSortBy + ", categoryType=" + categoryType + ", descType="
				+ descType + ", peopleTrafficType=" + peopleTrafficType + ", obj=" + obj + "]";
	}

	private List<Long> id;
	private String searchString;
	private List<Long> placeId;
	private List<Long> usrId;
	private List<String> orderByAndSortBy;
	private List<Integer> categoryType;
	private List<Integer> descType;
	private List<Integer> peopleTrafficType;
	private boolean obj;

	public boolean isEmpty() {
		if((id==null||id.isEmpty())&&
				(placeId==null||placeId.isEmpty())&&
				(usrId==null||usrId.isEmpty())&&
				(categoryType==null||categoryType.isEmpty())&&
				(descType==null||descType.isEmpty())&&
				(peopleTrafficType==null||peopleTrafficType.isEmpty())&&
				searchString==null) {
			return true;
		}
		return false;
	}

	
}
