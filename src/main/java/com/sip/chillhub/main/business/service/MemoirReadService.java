package com.sip.chillhub.main.business.service;

import java.util.List;

import com.sip.chillhub.main.business.model.MemoirSearchRequest;

public interface MemoirReadService {

	<T> List<T> findAllBySearchRequest(MemoirSearchRequest request);

}
