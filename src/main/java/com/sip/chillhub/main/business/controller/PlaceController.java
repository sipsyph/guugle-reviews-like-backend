package com.sip.chillhub.main.business.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sip.chillhub.main.business.model.PlaceSearchRequest;
import com.sip.chillhub.main.business.repository.PlaceRepository;

@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceRepository placeRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String findAll(@RequestBody PlaceSearchRequest request) {
        return new Gson().toJson(placeRepository.findBySearchRequestParameters(request));
    }
 
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id) {
    	return "";
    }

//    @PutMapping
//    @Transactional
//    public void update(@RequestBody TodoEntity resource) {
//        
//    }
// 
//    @PostMapping
//    @Transactional
//    public TodoEntity create(@RequestBody TodoEntity resource) {
//        return this.todoRepository.save(resource);
//    }
// 
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void delete(@PathVariable("id") Long id) {
//        this.todoRepository.deleteById(id);
//    }
    
}
