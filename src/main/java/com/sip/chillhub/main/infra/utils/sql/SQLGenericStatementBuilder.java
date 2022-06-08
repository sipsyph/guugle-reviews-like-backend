package com.sip.chillhub.main.infra.utils.sql;

import java.util.List;

public class SQLGenericStatementBuilder {
	
	public static StringBuilder removeLastOccurrenceOfAndKeyword(StringBuilder sqlStatement) {
		System.out.println("Statement before removal of AND: "+sqlStatement+" ");
		final int indexOfLastAnd = sqlStatement.lastIndexOf("AND");
		System.out.println("indexOfLastAnd: "+indexOfLastAnd);
		if(indexOfLastAnd==-1) {
			return sqlStatement;
		}else {
			return new StringBuilder(sqlStatement.capacity()).append(sqlStatement.substring(0, indexOfLastAnd));
		}
	}
	
//	public static StringBuilder removeLastOccurrenceOfAndKeyword(StringBuilder sqlStatement, boolean isExtraWherePredicateExists) {
//		if(isExtraWherePredicateExists) {
//			System.out.println("Statement before removal of AND: "+sqlStatement+" ");
//			final int indexOfLastAnd = sqlStatement.lastIndexOf("AND");
//			
//			if(indexOfLastAnd==-1) {
//				return sqlStatement;
//			}else {
//				return new StringBuilder(sqlStatement.capacity()).append(sqlStatement.substring(0, indexOfLastAnd));
//			}
//		}else {
//			return sqlStatement;
//		}
//	}

	public static StringBuilder orderBy(List<String> orderByAndSortByRequest, StringBuilder sqlStatement) {
		System.out.println("Statement without order by and limit: "+sqlStatement+" ");
		if(orderByAndSortByRequest!=null && !orderByAndSortByRequest.isEmpty() ) {
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
