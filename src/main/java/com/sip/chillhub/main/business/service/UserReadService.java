package com.sip.chillhub.main.business.service;

import java.util.List;

import com.sip.chillhub.main.business.model.UserSearchRequest;

public interface UserReadService {

	public <T> List<T> findAllBySearchRequest(UserSearchRequest request);
	
}
