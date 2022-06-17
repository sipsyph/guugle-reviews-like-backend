package com.sip.chillhub.main.business.service;

import java.util.List;

import com.sip.chillhub.main.business.model.CommentSearchRequest;

public interface CommentReadService {

	<T> List<T> findAllBySearchRequest(CommentSearchRequest request);

}
