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

import com.sip.chillhub.main.business.model.Memoir;
import com.sip.chillhub.main.business.model.Place;
import com.sip.chillhub.main.business.model.PlaceSearchRequest;

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
	
	public List<Place> findMemoirBySearchRequestParameters(String sqlStatement, MapSqlParameterSource sqlParams){
        
        List<Place> places = namedParameterJdbcTemplate.query(
        		sqlStatement.toString(), 
        		sqlParams,
        		(rs, rowNum) -> new Place(
							rs.getLong("id"),
							rs.getString("display_name"),
							rs.getString("name"),
							rs.getDouble("coordinates_x"),
							rs.getDouble("coordinates_y"),
							rs.getDouble("avg_rating"),
							rs.getInt("memoirs_amount"),
							rs.getInt("engages_amount")
        				));
        
        if(places.size()>0) {
        	return places;
        }
        
        return null;
	}
	
}
