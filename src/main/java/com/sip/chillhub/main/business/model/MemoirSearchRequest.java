package com.sip.chillhub.main.business.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class MemoirSearchRequest extends BaseSearchRequest {

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

	private List<Long> placeId;
	private List<Long> usrId;
	private List<Integer> categoryType;
	private List<Integer> descType;
	private List<Integer> peopleTrafficType;

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
