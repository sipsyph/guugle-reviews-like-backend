package com.sip.chillhub.main.infra.utils;

import java.util.List;

public class SQLGenericStatementBuilder {
	
	public static StringBuilder removeLastOccurrenceOfAndKeyword(StringBuilder sqlStatement) {
		final int indexOfLastAnd = sqlStatement.lastIndexOf("AND");
		
		if(indexOfLastAnd==-1) {
			sqlStatement.append(sqlStatement);
		}else {
			sqlStatement.append(sqlStatement.substring(0, indexOfLastAnd));
		}
		return sqlStatement;
	}

	public static StringBuilder orderBy(List<String> orderByAndSortByRequest, StringBuilder sqlStatement) {
		System.out.println("Statement without order by and limit: "+sqlStatement+" ");
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
		sqlStatement.append("limit 100 "); //TODO: Parametarize this magic 100 number
		System.out.println("Complete statement: "+sqlStatement+" ");
		return sqlStatement;
	}
	
}
