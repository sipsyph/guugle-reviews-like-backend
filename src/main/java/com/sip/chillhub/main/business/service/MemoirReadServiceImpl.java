package com.sip.chillhub.main.business.service;

import java.util.ArrayList;
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
		final StringBuilder selectedFields = new StringBuilder(200);
		final StringBuilder afterWhereClause = new StringBuilder(200);
		final StringBuilder sqlStatement = new StringBuilder(500);
		boolean wherePredicateEmpty = true;
		List<String> orderByAndSortByRequest = new ArrayList<>();
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		
		if(request.isObj()) {
			selectedFields.append("SELECT (SELECT ROW_TO_JSON(m) FROM (SELECT m.*) AS u) ");
		}else {
			selectedFields.append("SELECT m.id ");
		}
		
		if(request.getOrderByAndSortBy()!=null) {
			orderByAndSortByRequest.addAll(request.getOrderByAndSortBy());
		}
		
		if(!request.isEmpty()) {
			if(request.getId() != null && !request.getId().isEmpty()) {
				afterWhereClause.append("id IN (:id) AND ");
				sqlParams.addValue("id", request.getId());
				wherePredicateEmpty = false;
			}
			
			if(request.getCategoryType() != null && !request.getCategoryType().isEmpty()) {
				afterWhereClause.append("category_type IN (:categoryType) AND ");
				sqlParams.addValue("categoryType", request.getCategoryType());
				wherePredicateEmpty = false;
			}
			
			if(request.getDescType() != null && !request.getDescType().isEmpty()) {
				afterWhereClause.append("desc_type IN (:descType) AND ");
				sqlParams.addValue("descType", request.getDescType());
				wherePredicateEmpty = false;
			}
			
			if(request.getPeopleTrafficType() != null && !request.getPeopleTrafficType().isEmpty()) {
				afterWhereClause.append("people_traffic_type IN (:peopleTrafficType) AND ");
				sqlParams.addValue("peopleTrafficType", request.getPeopleTrafficType());
				wherePredicateEmpty = false;
			}
			
			if(request.getPlaceId() != null && !request.getPlaceId().isEmpty()) {
				afterWhereClause.append("place_id IN (:placeId) AND ");
				sqlParams.addValue("placeId", request.getPlaceId());
				wherePredicateEmpty = false;
			}
			
			if(request.getUsrId() != null && !request.getUsrId().isEmpty()) {
				afterWhereClause.append("usr_id IN (:usrId) AND ");
				sqlParams.addValue("usrId", request.getUsrId());
				wherePredicateEmpty = false;
			}
			
			if(request.getSearchString()!=null) {
				afterWhereClause.append("(name LIKE :searchString OR ");
				sqlParams.addValue("searchString", request.getSearchString());
				
				afterWhereClause.append("body LIKE :searchString) AND ");
				sqlParams.addValue("searchString", request.getSearchString());
				wherePredicateEmpty = false;
			}
		}
		
		selectedFields.append("FROM memoir m "); //TODO: put common sql shit like this in an enum
		
		if(wherePredicateEmpty) {
			sqlStatement.append(selectedFields);
		}else {
			sqlStatement.append(selectedFields).append("WHERE ").append(afterWhereClause);
		}
		
		if(request.isObj()) {
			return (List<T>) memoirRepository.findMemoirBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatement)).toString(), 
					sqlParams);
		}
		return (List<T>) memoirRepository.findIdBySearchRequestParameters(
				SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
						SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatement)).toString(), 
				sqlParams);
	}
	
	public Long addMemoir(Memoir memoir) {
		System.out.println(memoir.toString());
		final StringBuilder sqlStatement = new StringBuilder(200); 
		return null;
	}

}
