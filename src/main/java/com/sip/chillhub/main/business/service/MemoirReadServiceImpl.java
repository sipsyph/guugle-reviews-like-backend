package com.sip.chillhub.main.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.Memoir;
import com.sip.chillhub.main.business.model.MemoirSearchRequest;
import com.sip.chillhub.main.business.repository.MemoirRepository;
import com.sip.chillhub.main.infra.utils.sql.SQLGenericStatementBuilder;

@Service
public class MemoirReadServiceImpl implements MemoirReadService {
	
	@Autowired
	private MemoirRepository memoirRepository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAllBySearchRequest(MemoirSearchRequest request){
		System.out.println(request.toString());
		final StringBuilder sqlStatementUntilWherePredicate = new StringBuilder(200); 
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		if(request.isObj()) {
			sqlStatementUntilWherePredicate.append("SELECT * FROM memoir ");
		}else {
			sqlStatementUntilWherePredicate.append("SELECT id FROM memoir ");
		}
		
		if(!request.isEmpty()) {
			sqlStatementUntilWherePredicate.append("WHERE ");
			
			if(request.getId() != null && !request.getId().isEmpty()) {
				sqlStatementUntilWherePredicate.append("id IN (:id) AND ");
				sqlParams.addValue("id", request.getId());
			}
			
			if(request.getCategoryType() != null && !request.getCategoryType().isEmpty()) {
				sqlStatementUntilWherePredicate.append("category_type IN (:categoryType) AND ");
				sqlParams.addValue("categoryType", request.getCategoryType());
			}
			
			if(request.getDescType() != null && !request.getDescType().isEmpty()) {
				sqlStatementUntilWherePredicate.append("desc_type IN (:descType) AND ");
				sqlParams.addValue("descType", request.getDescType());
			}
			
			if(request.getPeopleTrafficType() != null && !request.getPeopleTrafficType().isEmpty()) {
				sqlStatementUntilWherePredicate.append("people_traffic_type IN (:peopleTrafficType) AND ");
				sqlParams.addValue("peopleTrafficType", request.getPeopleTrafficType());
			}
			
			if(request.getPlaceId() != null && !request.getPlaceId().isEmpty()) {
				sqlStatementUntilWherePredicate.append("place_id IN (:placeId) AND ");
				sqlParams.addValue("placeId", request.getPlaceId());
			}
			
			if(request.getUsrId() != null && !request.getUsrId().isEmpty()) {
				sqlStatementUntilWherePredicate.append("usr_id IN (:usrId) AND ");
				sqlParams.addValue("usrId", request.getUsrId());
			}
			
			if(request.getSearchString()!=null) {
				sqlStatementUntilWherePredicate.append("(name LIKE :searchString OR ");
				sqlParams.addValue("searchString", request.getSearchString());
				
				sqlStatementUntilWherePredicate.append("body LIKE :searchString) AND ");
				sqlParams.addValue("searchString", request.getSearchString());
			}
			
			if(request.isObj()) {
				return (List<T>) memoirRepository.findMemoirBySearchRequestParameters(
						SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), 
								SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementUntilWherePredicate)).toString(), 
						sqlParams);
			}
			
			return (List<T>) memoirRepository.findIdBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementUntilWherePredicate)).toString(), 
					sqlParams);
			
		}else {
			sqlStatement.append(sqlStatementUntilWherePredicate);
			if(request.isObj()) {
				return (List<T>) memoirRepository.findMemoirBySearchRequestParameters(
						SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), sqlStatement).toString(), 
						sqlParams);
			}
			return (List<T>) memoirRepository.findIdBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), sqlStatement).toString(), 
					sqlParams);
		}
	}
	
	public Long addMemoir(Memoir memoir) {
		System.out.println(memoir.toString());
		final StringBuilder sqlStatement = new StringBuilder(200); 
		return null;
	}

}
