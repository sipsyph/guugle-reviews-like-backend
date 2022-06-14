package com.sip.chillhub.main.business.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.sip.chillhub.main.business.model.User;

@Repository
public class UserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<String> findUserBySearchRequestParameters(String sqlStatement, MapSqlParameterSource sqlParams){
        
		List<String> users = namedParameterJdbcTemplate.queryForList(
        		sqlStatement.toString(), 
        		sqlParams,
        		String.class);
        
        if(users!=null) {
        	return users;
        }
        
        return null;
	}
	
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
	
	public Long createUser(String sqlStatement, MapSqlParameterSource sqlParams) {
		
		Long idOfCreatedUser = null;
		
		try {
			idOfCreatedUser = namedParameterJdbcTemplate.queryForObject(sqlStatement, sqlParams, Long.class);
		} catch (EmptyResultDataAccessException e) {
			System.out.println(e.getMostSpecificCause());
		}
		
		if(idOfCreatedUser!=null) {
			return idOfCreatedUser;
		}else {
			return null;
		}
	}
}
