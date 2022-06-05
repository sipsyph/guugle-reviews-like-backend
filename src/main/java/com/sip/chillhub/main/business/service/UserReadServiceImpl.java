package com.sip.chillhub.main.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.UserSearchRequest;
import com.sip.chillhub.main.business.repository.UserRepository;
import com.sip.chillhub.main.infra.utils.SQLGenericStatementBuilder;

@Service
public class UserReadServiceImpl implements UserReadService {

	@Autowired
	private UserRepository userRepository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAllBySearchRequest(UserSearchRequest request){
		//System.out.println(request.toString());
		final StringBuilder sqlStatementUntilWherePredicate = new StringBuilder(200); 
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		if(request.isObj()) {
			sqlStatementUntilWherePredicate.append("SELECT * FROM usr ");
		}else {
			sqlStatementUntilWherePredicate.append("SELECT id FROM usr ");
		}
		if(!request.isEmpty()) {
			sqlStatementUntilWherePredicate.append("WHERE ");
			
			if(request.getId()!=null) {
				sqlStatementUntilWherePredicate.append("id = :id AND ");
				sqlParams.addValue("id", request.getId());
			}
			
			if(request.getName()!=null) {
				sqlStatementUntilWherePredicate.append("name LIKE :name AND ");
				sqlParams.addValue("name", request.getName());
			}
			
			if(request.isObj()) {
				return (List<T>) userRepository.findMemoirBySearchRequestParameters(
						SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), 
								SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementUntilWherePredicate)).toString(), 
						sqlParams);
			}
			
			return (List<T>) userRepository.findIdBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementUntilWherePredicate)).toString(), 
					sqlParams);
			
		}else {
			sqlStatement.append(sqlStatementUntilWherePredicate);
			
			if(request.isObj()) {
				return (List<T>) userRepository.findMemoirBySearchRequestParameters(
						SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), sqlStatement).toString(), 
						sqlParams);
			}
			return (List<T>) userRepository.findIdBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), sqlStatement).toString(), 
					sqlParams);
		}
	}
}
