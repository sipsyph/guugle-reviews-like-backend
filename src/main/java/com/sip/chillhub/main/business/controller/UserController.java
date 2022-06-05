package com.sip.chillhub.main.business.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sip.chillhub.main.business.model.User;
import com.sip.chillhub.main.business.model.UserSearchRequest;
import com.sip.chillhub.main.business.service.UserReadService;
import com.sip.chillhub.main.business.service.UserWriteService;

@RestController
@RequestMapping("/user")
public class UserController {
	
    @Autowired
    private UserReadService userReadService;
    
    @Autowired
    private UserWriteService userWriteService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String findAll(@RequestBody UserSearchRequest request) {
        return new Gson().toJson(userReadService.findAllBySearchRequest(request));
    }
    
    @PostMapping
    @Transactional
    public String create(@RequestBody User user) {
        return userWriteService.createUser(user);
    }
}
