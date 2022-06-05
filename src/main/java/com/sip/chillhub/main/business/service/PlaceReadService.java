package com.sip.chillhub.main.business.service;

import java.util.List;

import com.sip.chillhub.main.business.model.PlaceSearchRequest;

public interface PlaceReadService {

	<T> List<T> findAllBySearchRequest(PlaceSearchRequest request);

}
