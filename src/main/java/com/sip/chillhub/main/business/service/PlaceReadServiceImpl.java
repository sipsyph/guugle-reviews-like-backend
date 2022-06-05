package com.sip.chillhub.main.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.PlaceSearchRequest;
import com.sip.chillhub.main.business.repository.PlaceRepository;
import com.sip.chillhub.main.infra.utils.SQLGenericStatementBuilder;

@Service
public class PlaceReadServiceImpl implements PlaceReadService {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAllBySearchRequest(PlaceSearchRequest request){
		//System.out.println(request.toString());
		final StringBuilder sqlStatementUntilWherePredicate = new StringBuilder(200); 
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		if(request.isObj()) {
			sqlStatementUntilWherePredicate.append("SELECT * FROM place ");
		}else {
			sqlStatementUntilWherePredicate.append("SELECT id FROM place ");
		}
		if(!request.isEmpty()) {
			sqlStatementUntilWherePredicate.append("WHERE ");
			
			if(request.getId()!=null && !request.getId().isEmpty()) {
				sqlStatementUntilWherePredicate.append("id IN (:id) AND ");
				sqlParams.addValue("id", request.getId());
			}
			
			if(request.getSearchString()!=null) {
				sqlStatementUntilWherePredicate.append("name LIKE :searchString AND ");
				sqlParams.addValue("searchString", request.getSearchString());
			}
			
			if(request.isObj()) {
				return (List<T>) placeRepository.findMemoirBySearchRequestParameters(
						SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), 
								SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementUntilWherePredicate)).toString(), 
						sqlParams);
			}
			
			return (List<T>) placeRepository.findIdBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementUntilWherePredicate)).toString(), 
					sqlParams);
			
		}else {
			sqlStatement.append(sqlStatementUntilWherePredicate);
			
			if(request.isObj()) {
				return (List<T>) placeRepository.findMemoirBySearchRequestParameters(
						SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), sqlStatement).toString(), 
						sqlParams);
			}
			return (List<T>) placeRepository.findIdBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), sqlStatement).toString(), 
					sqlParams);
		}
	}

}
