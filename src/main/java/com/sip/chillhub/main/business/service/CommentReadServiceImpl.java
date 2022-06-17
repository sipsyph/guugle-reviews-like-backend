package com.sip.chillhub.main.business.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.CommentSearchRequest;
import com.sip.chillhub.main.business.model.Memoir;
import com.sip.chillhub.main.business.model.MemoirSearchRequest;
import com.sip.chillhub.main.business.repository.CommentRepository;
import com.sip.chillhub.main.business.repository.MemoirRepository;
import com.sip.chillhub.main.infra.utils.sql.SQLGenericStatementBuilder;

@Service
public class CommentReadServiceImpl implements CommentReadService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAllBySearchRequest(CommentSearchRequest request){
		System.out.println(request.toString());
		final StringBuilder selectedFields = new StringBuilder(200);
		final StringBuilder afterWhereClause = new StringBuilder(200);
		final StringBuilder sqlStatement = new StringBuilder(500);
		boolean wherePredicateEmpty = true;
		List<String> orderByAndSortByRequest = new ArrayList<>();
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		
		if(request.isObj()) {
			selectedFields.append("SELECT (SELECT ROW_TO_JSON(c) FROM (SELECT c.*) AS c) ");
		}else {
			selectedFields.append("SELECT c.id ");
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
			
			if(request.getId() != null && !request.getId().isEmpty()) {
				afterWhereClause.append("parent_comment_id IN (:parentCommentId) AND ");
				sqlParams.addValue("parentCommentId", request.getId());
				wherePredicateEmpty = false;
			}
			
			if(request.getMemoirId() != null && !request.getMemoirId().isEmpty()) {
				afterWhereClause.append("memoir_id IN (:memoirId) AND ");
				sqlParams.addValue("memoirId", request.getMemoirId());
				wherePredicateEmpty = false;
			}
			
			if(request.getUsrId() != null && !request.getUsrId().isEmpty()) {
				afterWhereClause.append("usr_id IN (:usrId) AND ");
				sqlParams.addValue("usrId", request.getUsrId());
				wherePredicateEmpty = false;
			}
			
			if(request.getSearchString()!=null) {
				afterWhereClause.append("body LIKE :searchString AND ");
				sqlParams.addValue("searchString", request.getSearchString());
				wherePredicateEmpty = false;
			}
		}
		
		if(request.getSortByUpvotes()!=null) {
			System.out.println(request.getSortByUpvotes());
			orderByAndSortByRequest.add("(c.ups - c.downs) "+request.getSortByUpvotes());
		}
		
		selectedFields.append("FROM comment c "); //TODO: put common sql shit like this in an enum
		
		if(wherePredicateEmpty) {
			sqlStatement.append(selectedFields);
		}else {
			sqlStatement.append(selectedFields).append("WHERE ").append(afterWhereClause);
		}
		
		if(request.isObj()) {
			return (List<T>) commentRepository.findCommentBySearchRequestParameters(
					SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
							SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatement)).toString(), 
					sqlParams);
		}
		return (List<T>) commentRepository.findIdBySearchRequestParameters(
				SQLGenericStatementBuilder.orderBy(orderByAndSortByRequest, 
						SQLGenericStatementBuilder.removeLastOccurrenceOfAndKeyword(sqlStatement)).toString(), 
				sqlParams);
	}

}
