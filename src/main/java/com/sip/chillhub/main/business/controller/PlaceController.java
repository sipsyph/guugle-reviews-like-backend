package com.sip.chillhub.main.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sip.chillhub.main.business.model.PlaceSearchRequest;
import com.sip.chillhub.main.business.repository.PlaceRepository;
import com.sip.chillhub.main.business.service.PlaceReadService;

@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceReadService placeReadService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String findAll(@RequestBody PlaceSearchRequest request) {
        return new Gson().toJson(placeReadService.findAllBySearchRequest(request));
    }
 
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id) {
    	return "";
    }
    
}
