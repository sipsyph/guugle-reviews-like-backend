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

import com.sip.chillhub.main.business.model.MemoirSearchRequest;

@Repository
public class MemoirRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<Long> findBySearchRequestParameters(MemoirSearchRequest request){
        //System.out.println(request.toString());
		final StringBuilder sqlStatement = new StringBuilder(1000); 
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlStatement.append("select id from place ");
		if(!request.isEmpty()) {
			sqlStatement.append("where ");

			if(request.getSearchString()!=null) {
				sqlStatement.append("name like :searchString ");
				sqlParams.addValue("searchString", request.getSearchString());
			}
			if(request.getCategoryId()!=null) {
				sqlStatement.append("category_type = :categoryType ");
				sqlParams.addValue("categoryType", request.getCategoryId());
			}
			if(!request.getOrderByAndSortBy().isEmpty()) {
				sqlStatement.append("order by ");
			    
			    int i=1;
			    
			    for(String orderByAndSortBy : request.getOrderByAndSortBy()) {
			    	if(i==request.getOrderByAndSortBy().size()) {
				    	sqlStatement.append(orderByAndSortBy+" ");
				    	break;
			    	}else {
				    	sqlStatement.append(orderByAndSortBy+" , ");
			    	}
			    	
			    	i++;
			    }
			    
			}
			if(request.getId()!=null) {
				sqlStatement.append("id = :id ");
				sqlParams.addValue("id", request.getId());
			}
		}
		sqlStatement.append("limit 100 ");
		
        //System.out.println(sqlStatement);
        
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
