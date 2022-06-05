package com.sip.chillhub.main.business.service;

import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.User;

public interface UserWriteService {

	Long createUser(User user);
	
}
