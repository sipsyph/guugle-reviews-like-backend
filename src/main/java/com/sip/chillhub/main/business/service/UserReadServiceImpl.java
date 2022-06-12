package com.sip.chillhub.main.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.UserSearchRequest;
import com.sip.chillhub.main.business.repository.UserRepository;
import com.sip.chillhub.main.infra.utils.sql.SQLGenericStatementBuilder;

@Service
public class UserReadServiceImpl implements UserReadService {

	@Autowired
	private UserRepository userRepository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAllBySearchRequest(UserSearchRequest request){
		//System.out.println(request.toString());
		final StringBuilder sqlStatement = new StringBuilder(200); 
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		if(request.isObj()) {
			sqlStatement.append("SELECT (SELECT ROW_TO_JSON(u) FROM (SELECT u.*) AS u) FROM usr u ");
			
		}else {
			sqlStatement.append("SELECT u.id FROM usr u ");
		}
		if(!request.isEmpty()) {
			sqlStatement.append("WHERE ");
			
			if(request.getId()!=null && !request.getId().isEmpty()) {
				sqlStatement.append("id IN (:id) AND ");
				sqlParams.addValue("id", request.getId());
			}
			
			if(request.getName()!=null && !request.getName().isEmpty()) {
				sqlStatement.append("name LIKE :name AND ");
				sqlParams.addValue("name", request.getName());
			}
		}
		
		if(request.isObj()) {
			return (List<T>) userRepository.findUserBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatement)).toString(), 
					sqlParams);
		}else {
			return (List<T>) userRepository.findIdBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatement)).toString(), 
					sqlParams);
		}

			
	}
}
