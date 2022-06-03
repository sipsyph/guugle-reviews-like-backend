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
import com.sip.chillhub.main.business.model.MemoirSearchRequest;
import com.sip.chillhub.main.business.repository.MemoirRepository;

@RestController
@RequestMapping("/memoir")
public class MemoirController {

    @Autowired
    private MemoirRepository memoirRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String findAll(@RequestBody MemoirSearchRequest request) {
        return new Gson().toJson(memoirRepository.findBySearchRequestParameters(request));
    }
 
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id) {
    	return "";
    }
    
}
