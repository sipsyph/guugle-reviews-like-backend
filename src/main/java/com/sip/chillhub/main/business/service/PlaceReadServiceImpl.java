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
		final StringBuilder selectedFields = new StringBuilder(200);
		final StringBuilder beforeWhereClause = new StringBuilder(200);
		final StringBuilder afterWhereClause = new StringBuilder(200);
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		boolean wherePredicateEmpty = true;
		List<String> orderByAndSortByRequest = new ArrayList<>();
		
		if(request.isObj()) {
			selectedFields.append("SELECT (SELECT ROW_TO_JSON(p) FROM (SELECT p.* ");
		}else {
			selectedFields.append("SELECT p.id ");
		}
		
		if(request.getOrderByAndSortBy()!=null) {
			orderByAndSortByRequest.addAll(request.getOrderByAndSortBy());
		}
		
		if(!request.isMemoirAttributesEmpty()) {
			final StringBuilder memoirJoin = new StringBuilder(200);
			final StringBuilder memoirJoinSelectedFields = new StringBuilder(200);
			final StringBuilder memoirJoinWhereClause = new StringBuilder(200);
			
			memoirJoinSelectedFields.append("SELECT SUM(m.ups) - SUM(m.downs) AS upvotes ");
			memoirJoinWhereClause.append("WHERE m.place_id = p.id AND ");
			
			if(request.getCategoryType()!=null && !request.getCategoryType().isEmpty()) {
				memoirJoinWhereClause.append("m.category_type IN (:categoryType) AND ");
				sqlParams.addValue("categoryType", request.getCategoryType());
			}
			
			if(request.getDescType()!=null && !request.getDescType().isEmpty()) {
				memoirJoinSelectedFields.append(", SUM( ");
				memoirJoinWhereClause.append("( ");
				int i = 1;
				final int arrSize = request.getDescType().size();
				for(Integer descElem : request.getDescType()) {
					if(i==arrSize) {
						memoirJoinSelectedFields.append
						("(case when (:cT").append(i).append("= ANY(m.desc_type) ) then ").append(arrSize-(i-1)).append(" else 0 end) ");
						memoirJoinWhereClause.append(" :cT").append(i).append("= ANY(m.desc_type) ");
					}else {
						memoirJoinSelectedFields.append
						("(case when (:cT").append(i).append("= ANY(m.desc_type) ) then ").append(arrSize-(i-1)).append(" else 0 end) + ");
						memoirJoinWhereClause.append(" :cT").append(i).append("= ANY(m.desc_type) OR ");
					}
					sqlParams.addValue("cT"+i, descElem);
					i++;
				}
				memoirJoinWhereClause.append(") AND ");
				memoirJoinSelectedFields.append(") AS descMatchScore");
				orderByAndSortByRequest.add("memoir.descMatchScore DESC ");
			}
			
			if(request.getPeopleTrafficType()!=null && !request.getPeopleTrafficType().isEmpty()) {
				memoirJoinWhereClause.append("m.people_traffic_type IN (:peopleTrafficType) AND ");
				sqlParams.addValue("peopleTrafficType", request.getPeopleTrafficType());
			}
			
			if(request.isObj()) {
				selectedFields.append(",memoir.upvotes "); //Include this in the main select of place
			}
			memoirJoin.append("INNER JOIN LATERAL ( ");
			memoirJoin.append(memoirJoinSelectedFields).append(" FROM memoir m ").append( 
					SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(memoirJoinWhereClause) );
			beforeWhereClause.append(memoirJoin).append("GROUP BY m.place_id ) AS memoir ON TRUE ");
			
		}
			
		if(!request.isEmpty()) {
			
			if(request.getId()!=null && !request.getId().isEmpty()) {
				afterWhereClause.append("p.id IN (:id) AND ");
				sqlParams.addValue("id", request.getId());
				wherePredicateEmpty = false;
			}
			
			if(request.getSearchString()!=null) {
				afterWhereClause.append("p.name LIKE :searchString AND ");
				//sqlParams.addValue("searchString", request.getSearchString());
				afterWhereClause.append("p.address LIKE :searchString AND ");
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
		
		if(request.isObj()) {
			selectedFields.append(") as p) ");
		}
		
		selectedFields.append("FROM place p "); //TODO: put common sql shit like this in an enum
		
		if(wherePredicateEmpty) {
			sqlStatement.append(selectedFields).append(beforeWhereClause);
		}else {
			sqlStatement.append(selectedFields).append(beforeWhereClause).append("WHERE ").append(
					SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(afterWhereClause));
		}
		
		if(request.isObj()) {
			return (List<T>) placeRepository.findPlaceBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
							sqlStatement).toString(), 
					sqlParams);
		}
		
		return (List<T>) placeRepository.findIdBySearchRequestParameters(
				SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
						sqlStatement).toString(), 
				sqlParams);
	}
}
