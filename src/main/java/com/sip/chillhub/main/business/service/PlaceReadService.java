package com.sip.chillhub.main.business.service;

import java.util.List;

import com.sip.chillhub.main.business.model.PlaceSearchRequest;

public interface PlaceReadService {

	List<Long> findAllBySearchRequest(PlaceSearchRequest request);

}
