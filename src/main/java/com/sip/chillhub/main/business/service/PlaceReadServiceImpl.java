package com.sip.chillhub.main.business.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.PlaceSearchRequest;
import com.sip.chillhub.main.business.repository.PlaceRepository;
import com.sip.chillhub.main.infra.utils.sql.SQLGenericStatementBuilder;

@Service
public class PlaceReadServiceImpl implements PlaceReadService {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAllBySearchRequest(PlaceSearchRequest request){
		//System.out.println(request.toString());
		final StringBuilder sqlStatementBeforeWherePredicate = new StringBuilder(200);
		final StringBuilder sqlStatementAfterWherePredicate = new StringBuilder(200);
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		boolean wherePredicateEmpty = true;
		
		if(request.isObj()) {
			sqlStatementBeforeWherePredicate.append("SELECT * FROM place p ");
		}else {
			sqlStatementBeforeWherePredicate.append("SELECT id FROM place p ");
		}
		
		List<String> orderByAndSortByRequest = new ArrayList<>();
		
		if(request.getOrderByAndSortBy()!=null) {
			orderByAndSortByRequest.addAll(request.getOrderByAndSortBy());
		}
		
		if(!request.isMemoirAttributesEmpty()) {
			boolean memoirExtraWherePredicateEmpty = true; 
			final StringBuilder sqlStatementMemoirJoin = new StringBuilder(200);
			sqlStatementMemoirJoin.append("INNER JOIN LATERAL ( ");
			sqlStatementMemoirJoin.append("SELECT SUM(m.ups) AS upvotes FROM memoir m ");
			sqlStatementMemoirJoin.append("WHERE m.place_id = p.id AND ");
			
			if(request.getCategoryType()!=null && !request.getCategoryType().isEmpty()) {
				sqlStatementMemoirJoin.append("m.category_type IN (:categoryType) AND ");
				sqlParams.addValue("categoryType", request.getId());
				memoirExtraWherePredicateEmpty = false;
			}
			
			if(request.getDescType()!=null && !request.getDescType().isEmpty()) {
				sqlStatementMemoirJoin.append("m.desc_type IN (:descType) AND ");
				sqlParams.addValue("descType", request.getDescType());
				memoirExtraWherePredicateEmpty = false;
			}
			
			if(request.getPeopleTrafficType()!=null && !request.getPeopleTrafficType().isEmpty()) {
				sqlStatementMemoirJoin.append("m.people_traffic_type IN (:peopleTrafficType) AND ");
				sqlParams.addValue("peopleTrafficType", request.getPeopleTrafficType());
				memoirExtraWherePredicateEmpty = false;
			}
			
			
			
			sqlStatementBeforeWherePredicate.append(
					SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementMemoirJoin))
			.append("GROUP BY m.place_id ) AS memoir ON TRUE ");
			
		}
			
		if(!request.isEmpty()) {
			
			if(request.getId()!=null && !request.getId().isEmpty()) {
				sqlStatementAfterWherePredicate.append("p.id IN (:id) AND ");
				sqlParams.addValue("id", request.getId());
				wherePredicateEmpty = false;
			}
			
			if(request.getSearchString()!=null) {
				sqlStatementAfterWherePredicate.append("p.name LIKE :searchString AND ");
				sqlParams.addValue("searchString", request.getSearchString());
				wherePredicateEmpty = false;
			}
			
		}
		
		if(request.getSortByUpvotes()!=null) {
			System.out.println(request.getSortByUpvotes());
			orderByAndSortByRequest.add("memoir.upvotes "+request.getSortByUpvotes());
		}
		
		if(request.getCoordinatesX()!=null && request.getCoordinatesY()!=null) {
			orderByAndSortByRequest.add("abs(p.coordinates_x-:userCoordinatesX) + abs(p.coordinates_y-:userCoordinatesY) ASC");
			sqlParams.addValue("userCoordinatesX", request.getCoordinatesX());
			sqlParams.addValue("userCoordinatesY", request.getCoordinatesY());
		}
		
		if(wherePredicateEmpty) {
			sqlStatement.append(sqlStatementBeforeWherePredicate);
		}else {
			sqlStatement.append(sqlStatementBeforeWherePredicate).append("WHERE ").append(sqlStatementAfterWherePredicate);
		}
		
		if(request.isObj()) {
			return (List<T>) placeRepository.findMemoirBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatement)).toString(), 
					sqlParams);
		}
		
		return (List<T>) placeRepository.findIdBySearchRequestParameters(
				SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
						SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatement)).toString(), 
				sqlParams);
		
	}

}
