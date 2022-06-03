package com.sip.chillhub.main.business.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.sip.chillhub.main.business.model.PlaceSearchRequest;

@Repository
public class PlaceRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<Long> findBySearchRequestParameters(String sqlStatement, MapSqlParameterSource sqlParams){
        
        List<Long> idList = namedParameterJdbcTemplate.queryForList(
        		sqlStatement.toString(), 
        		sqlParams,
        		Long.class);
        
        if(idList.size()>0) {
        	return idList;
        }
        
        return null;
	}
	
}
