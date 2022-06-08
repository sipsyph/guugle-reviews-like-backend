package com.sip.chillhub.main.business.model;

import java.util.Date;
import java.util.List;

public class UserSearchRequest extends BaseSearchRequest {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isEmpty() {
		if(
				isEmptyBase()&&
				name==null) {
			return true;
		}
		return false;
	}
	
}
