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
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Long> findBySearchRequestParameters(PlaceSearchRequest placeSearchRequest){
        System.out.println(placeSearchRequest.toString());
		final StringBuilder sqlStatement = new StringBuilder(1000); 
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlStatement.append("select id from place ");
		if(!placeSearchRequest.isEmpty()) {
			sqlStatement.append("where ");

			if(placeSearchRequest.getSearchString()!=null) {
				sqlStatement.append("name like :searchString ");
				sqlParams.addValue("searchString", placeSearchRequest.getSearchString());
			}
			if(placeSearchRequest.getCategoryId()!=null) {
				sqlStatement.append("category_type = :categoryType ");
				sqlParams.addValue("categoryType", placeSearchRequest.getCategoryId());
			}
			if(!placeSearchRequest.getOrderByAndSortBy().isEmpty()) {
				sqlStatement.append("order by ");
			    
			    int i=1;
			    
			    for(String orderByAndSortBy : placeSearchRequest.getOrderByAndSortBy()) {
			    	if(i==placeSearchRequest.getOrderByAndSortBy().size()) {
				    	sqlStatement.append(orderByAndSortBy+" ");
				    	break;
			    	}else {
				    	sqlStatement.append(orderByAndSortBy+" , ");
			    	}
			    	
			    	i++;
			    }
			    
			    
			}
			if(placeSearchRequest.getId()!=null) {
				sqlStatement.append("id = :id ");
				sqlParams.addValue("id", placeSearchRequest.getId());
			}
		}
		sqlStatement.append("limit 100 ");
		
        System.out.println(sqlStatement);
        
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
