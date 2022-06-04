package com.sip.chillhub.main.infra.utils;

import java.util.List;

public class SQLGenericStatementBuilder {

	public static StringBuilder orderBy(List<String> orderByAndSortByRequest, StringBuilder sqlStatement) {
		if(!orderByAndSortByRequest.isEmpty()) {
			sqlStatement.append("ORDER BY ");
		    
		    int i=1;
		    
		    for(String orderByAndSortBy : orderByAndSortByRequest) {
		    	if(i==orderByAndSortByRequest.size()) {
			    	sqlStatement.append(orderByAndSortBy+" ");
			    	break;
		    	}else {
			    	sqlStatement.append(orderByAndSortBy+" , ");
		    	}
		    	
		    	i++;
		    }
		}
		
		sqlStatement.append("limit 100 "); //Parametarize this magic 100 number
		
		return sqlStatement;
	}
	
}
