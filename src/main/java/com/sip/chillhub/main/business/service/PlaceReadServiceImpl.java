package com.sip.chillhub.main.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.PlaceSearchRequest;
import com.sip.chillhub.main.business.repository.PlaceRepository;

@Service
public class PlaceReadServiceImpl implements PlaceReadService {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	
	@Override
	public List<Long> findAllBySearchRequest(PlaceSearchRequest request){
		//System.out.println(request.toString());
		final StringBuilder sqlStatementUntilWhereClause = new StringBuilder(200); 
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlStatementUntilWhereClause.append("SELECT id FROM place ");
		if(!request.isEmpty()) {
			sqlStatementUntilWhereClause.append("WHERE ");
			
			if(request.getId()!=null) {
				sqlStatementUntilWhereClause.append("id = :id AND ");
				sqlParams.addValue("id", request.getId());
			}
			
			if(request.getSearchString()!=null) {
				sqlStatementUntilWhereClause.append("name like :searchString AND ");
				sqlParams.addValue("searchString", request.getSearchString());
			}
			
			final int indexOfLastAnd = sqlStatementUntilWhereClause.lastIndexOf("AND");
			
			if(indexOfLastAnd==-1) {
				sqlStatement.append(sqlStatementUntilWhereClause);
			}else {
				sqlStatement.append(sqlStatementUntilWhereClause.substring(0, indexOfLastAnd));
			}
			
		}else {
			sqlStatement.append(sqlStatementUntilWhereClause);
		}
		
		System.out.println("Statement without order by and limit: "+sqlStatement+" ");
		
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
		
		sqlStatement.append("limit 100 ");
		
        System.out.println("Complete statement: "+sqlStatement+" ");
		return placeRepository.findBySearchRequestParameters(sqlStatement.toString(), sqlParams);
	}

}
