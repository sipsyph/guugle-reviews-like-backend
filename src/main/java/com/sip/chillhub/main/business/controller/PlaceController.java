package com.sip.chillhub.main.business.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sip.chillhub.main.business.model.Memoir;
import com.sip.chillhub.main.business.model.Place;
import com.sip.chillhub.main.business.model.PlaceSearchRequest;
import com.sip.chillhub.main.business.repository.PlaceRepository;
import com.sip.chillhub.main.business.service.PlaceReadService;
import com.sip.chillhub.main.business.service.PlaceWriteService;

@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceReadService placeReadService;
    
    @Autowired
    private PlaceWriteService placeWriteService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String findAll(@RequestBody PlaceSearchRequest request) {
        return new Gson().toJson(placeReadService.findAllBySearchRequest(request));
    }
 
    @PostMapping
    @Transactional
    public Long create(@RequestBody Place place) {
    	
    	final Long placeId = placeWriteService.createPlace(place);
    	return placeId;
    }
    
}
