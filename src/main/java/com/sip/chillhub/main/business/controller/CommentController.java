package com.sip.chillhub.main.business.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sip.chillhub.main.business.model.Comment;
import com.sip.chillhub.main.business.model.CommentSearchRequest;
import com.sip.chillhub.main.business.service.CommentReadService;
import com.sip.chillhub.main.business.service.CommentWriteService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	

    @Autowired
    private CommentReadService commentReadService;
    
    @Autowired
    private CommentWriteService commentWriteService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String findAll(@RequestBody CommentSearchRequest request) {
        return new Gson().toJson(commentReadService.findAllBySearchRequest(request));
    }
    
    @PostMapping
    @Transactional
    public Long create(@RequestBody Comment comment) {
    	final Long commentId = commentWriteService.createComment(comment);
    	return commentId;
    }

}
