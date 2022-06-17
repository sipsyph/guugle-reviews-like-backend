package com.sip.chillhub.main.business.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

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
	
	public List<String> findCommentBySearchRequestParameters(String sqlStatement, MapSqlParameterSource sqlParams){
		List<String> memoirs = namedParameterJdbcTemplate.queryForList(
        		sqlStatement.toString(), 
        		sqlParams,
        		String.class);
        
        if(memoirs!=null) {
        	return memoirs;
        }
        
        return null;
	}
	
	public Long createComment(String sqlStatement, MapSqlParameterSource sqlParams) {
		
		Long idOfCreatedComment = null;
		
		try {
			idOfCreatedComment = namedParameterJdbcTemplate.queryForObject(sqlStatement, sqlParams, Long.class);
		} catch (EmptyResultDataAccessException e) {
			System.out.println(e.getMostSpecificCause());
		}
		
		if(idOfCreatedComment!=null) {
			return idOfCreatedComment;
		}else {
			return null;
		}
	}
	
}
