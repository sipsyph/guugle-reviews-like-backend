package com.sip.chillhub.main.business.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlaceRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<Long> findIdBySearchRequestParameters(String sqlStatement, MapSqlParameterSource sqlParams){
        
        List<Long> idList = namedParameterJdbcTemplate.queryForList(
        		sqlStatement.toString(), 
        		sqlParams,
        		Long.class);
        
        if(idList.size()>0) {
        	return idList;
        }
        
        return null;
	}
	
	public List<String> findPlaceBySearchRequestParameters(String sqlStatement, MapSqlParameterSource sqlParams){
        
        List<String> places = namedParameterJdbcTemplate.queryForList(
        		sqlStatement.toString(), 
        		sqlParams,
        		String.class);
        
        if(places!=null) {
        	return places;
        }
        
        return null;
	}
	
	public Long createPlace(String sqlStatement, MapSqlParameterSource sqlParams) {
		
		Long idOfCreatedPlace = null;
		
		try {
			idOfCreatedPlace = namedParameterJdbcTemplate.queryForObject(sqlStatement, sqlParams, Long.class);
		} catch (EmptyResultDataAccessException e) {
			System.out.println(e.getMostSpecificCause());
		}
		
		if(idOfCreatedPlace!=null) {
			return idOfCreatedPlace;
		}else {
			return null;
		}
	}
	
}
