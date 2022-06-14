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
import com.sip.chillhub.main.business.model.MemoirSearchRequest;
import com.sip.chillhub.main.business.model.User;
import com.sip.chillhub.main.business.repository.MemoirRepository;
import com.sip.chillhub.main.business.service.MemoirReadService;
import com.sip.chillhub.main.business.service.MemoirWriteService;

@RestController
@RequestMapping("/memoir")
public class MemoirController {

    @Autowired
    private MemoirReadService memoirReadService;
    
    @Autowired
    private MemoirWriteService memoirWriteService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String findAll(@RequestBody MemoirSearchRequest request) {
        return new Gson().toJson(memoirReadService.findAllBySearchRequest(request));
    }
 
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id) {
    	return "";
    }
    
    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody Memoir memoir) {
    	
    	final Long memoirId = memoirWriteService.createMemoir(memoir);
    	if(memoirId!=null) {
    		return new ResponseEntity(HttpStatus.OK);
    	}else {
    		return new ResponseEntity(HttpStatus.BAD_REQUEST);
    	}
    }
    
}
