package com.sip.chillhub.main.business.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.sip.chillhub.main.business.model.Memoir;
import com.sip.chillhub.main.business.model.MemoirSearchRequest;

@Repository
public class MemoirRepository {

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
	
	public List<String> findMemoirBySearchRequestParameters(String sqlStatement, MapSqlParameterSource sqlParams){
		List<String> memoirs = namedParameterJdbcTemplate.queryForList(
        		sqlStatement.toString(), 
        		sqlParams,
        		String.class);
        
        if(memoirs!=null) {
        	return memoirs;
        }
        
        return null;
	}
	
	public Long createMemoir(String sqlStatement, MapSqlParameterSource sqlParams) {
		
		Long idOfCreatedMemoir = null;
		
		try {
			idOfCreatedMemoir = namedParameterJdbcTemplate.queryForObject(sqlStatement, sqlParams, Long.class);
		} catch (EmptyResultDataAccessException e) {
			System.out.println(e.getMostSpecificCause());
		}
		
		if(idOfCreatedMemoir!=null) {
			return idOfCreatedMemoir;
		}else {
			return null;
		}
	}
	
}
