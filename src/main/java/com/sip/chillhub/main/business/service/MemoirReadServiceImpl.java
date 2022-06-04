package com.sip.chillhub.main.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.MemoirSearchRequest;
import com.sip.chillhub.main.business.repository.MemoirRepository;
import com.sip.chillhub.main.infra.utils.SQLGenericStatementBuilder;

@Service
public class MemoirReadServiceImpl implements MemoirReadService {
	
	@Autowired
	private MemoirRepository memoirRepository;
	
	
	@Override
	public List<Long> findAllBySearchRequest(MemoirSearchRequest request){
		//System.out.println(request.toString());
		final StringBuilder sqlStatementUntilWherePredicate = new StringBuilder(200); 
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlStatementUntilWherePredicate.append("SELECT id FROM memoir ");
		if(!request.isEmpty()) {
			sqlStatementUntilWherePredicate.append("WHERE ");
			
			if(request.getId()!=null) {
				sqlStatementUntilWherePredicate.append("id = :id AND ");
				sqlParams.addValue("id", request.getId());
			}
			
			if(request.getPlaceId()!=null) {
				sqlStatementUntilWherePredicate.append("place_id = :placeId AND ");
				sqlParams.addValue("placeId", request.getId());
			}
			
			if(request.getSearchString()!=null) {
				sqlStatementUntilWherePredicate.append("name LIKE :searchString AND ");
				sqlParams.addValue("searchString", request.getSearchString());
			}
			
			return memoirRepository.findBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementUntilWherePredicate)).toString(), 
					sqlParams);
			
		}else {
			sqlStatement.append(sqlStatementUntilWherePredicate);
			
			return memoirRepository.findBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(request.getOrderByAndSortBy(), sqlStatement).toString(), 
					sqlParams);
		}
	}

}
