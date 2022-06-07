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
		final StringBuilder sqlStatementUntilWherePredicate = new StringBuilder(200); 
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		if(request.isObj()) {
			sqlStatementUntilWherePredicate.append("SELECT * FROM place p ");
		}else {
			sqlStatementUntilWherePredicate.append("SELECT id FROM place p ");
		}
		
		List<String> orderByAndSortByRequest = new ArrayList<>();
		
		if(request.getOrderByAndSortBy()!=null) {
			orderByAndSortByRequest.addAll(request.getOrderByAndSortBy());
		}
		
		if(!request.isMemoirAttributesEmpty()) {
			
			sqlStatementUntilWherePredicate.append("INNER JOIN LATERAL ( ");
			sqlStatementUntilWherePredicate.append("SELECT SUM(m.ups) AS upvotes FROM memoir m ");
			
			if(!request.isMemoirAttributesEmptyExceptUpvotesSort()) {
				sqlStatementUntilWherePredicate.append("WHERE");
			}
			
			if(request.getCategoryType()!=null && !request.getCategoryType().isEmpty()) {
				sqlStatementUntilWherePredicate.append("m.category_type IN (:categoryType) AND ");
				sqlParams.addValue("categoryType", request.getId());
			}
			
			if(request.getDescType()!=null && !request.getDescType().isEmpty()) {
				sqlStatementUntilWherePredicate.append("m.desc_type IN (:descType) AND ");
				sqlParams.addValue("descType", request.getDescType());
			}
			
			if(request.getPeopleTrafficType()!=null && !request.getPeopleTrafficType().isEmpty()) {
				sqlStatementUntilWherePredicate.append("m.people_traffic_type IN (:peopleTrafficType) AND ");
				sqlParams.addValue("peopleTrafficType", request.getPeopleTrafficType());
			}
			
			sqlStatementUntilWherePredicate.append("GROUP BY m.place_id ) AS memoir ON true ");
			
		}
			
		if(!request.isEmpty()) {
			
			sqlStatementUntilWherePredicate.append("WHERE ");
			
			if(request.getId()!=null && !request.getId().isEmpty()) {
				sqlStatementUntilWherePredicate.append("p.id IN (:id) AND ");
				sqlParams.addValue("id", request.getId());
			}
			
			if(request.getSearchString()!=null) {
				sqlStatementUntilWherePredicate.append("p.name LIKE :searchString AND ");
				sqlParams.addValue("searchString", request.getSearchString());
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
			
			if(request.isObj()) {
				return (List<T>) placeRepository.findMemoirBySearchRequestParameters(
						SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
								SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementUntilWherePredicate)).toString(), 
						sqlParams);
			}
			
			return (List<T>) placeRepository.findIdBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatementUntilWherePredicate)).toString(), 
					sqlParams);
			
		}else {
			sqlStatement.append(sqlStatementUntilWherePredicate);
			
			if(request.getSortByUpvotes()!=null) {
				System.out.println(request.getSortByUpvotes());
				orderByAndSortByRequest.add("memoir.upvotes "+request.getSortByUpvotes());
			}
			
			if(request.getCoordinatesX()!=null && request.getCoordinatesY()!=null) {
				orderByAndSortByRequest.add("abs(p.coordinates_x-:userCoordinatesX) + abs(p.coordinates_y-:userCoordinatesY) ASC");
				sqlParams.addValue("userCoordinatesX", request.getCoordinatesX());
				sqlParams.addValue("userCoordinatesY", request.getCoordinatesY());
			}
			
			if(request.isObj()) {
				return (List<T>) placeRepository.findMemoirBySearchRequestParameters(
						SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, sqlStatement).toString(), 
						sqlParams);
			}
			return (List<T>) placeRepository.findIdBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, sqlStatement).toString(), 
					sqlParams);
		}
	}

}
