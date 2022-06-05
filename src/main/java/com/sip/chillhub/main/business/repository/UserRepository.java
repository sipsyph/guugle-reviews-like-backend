package com.sip.chillhub.main.business.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.sip.chillhub.main.business.model.User;

@Repository
public class UserRepository {
	
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
	
	public List<User> findMemoirBySearchRequestParameters(String sqlStatement, MapSqlParameterSource sqlParams){
        
        List<User> users = namedParameterJdbcTemplate.query(
        		sqlStatement.toString(), 
        		sqlParams,
        		(rs, rowNum) -> new User(
        				rs.getLong("id"),
        				rs.getString("name"),
        				rs.getString("pass"),
        				rs.getString("desc"),
        				rs.getInt("usr_type"),
        				rs.getBoolean("is_premium"),
        				rs.getInt("coins"),
        				rs.getString("avatar_img"),
        				rs.getInt("name_style"),
        				rs.getDate("last_login_date"),
        				rs.getBoolean("del")
        				));
        
        if(users.size()>0) {
        	return users;
        }
        
        return null;
	}
	
	public Integer createUser(String sqlStatement, MapSqlParameterSource sqlParams) {
		return namedParameterJdbcTemplate.update(sqlStatement, sqlParams);
	}
}
