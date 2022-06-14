package com.sip.chillhub.main.business.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.Memoir;
import com.sip.chillhub.main.business.repository.MemoirRepository;

@Service
public class MemoirWriteServiceImpl implements MemoirWriteService {
	
	@Autowired
	private MemoirRepository memoirRepository;
	
	public Long createMemoir(Memoir memoir) {
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlStatement.append("INSERT into memoir(place_id,usr_id,name,body,category_type,desc_type,people_traffic_type) ");
		sqlStatement.append("SELECT :placeId,:usrId,:name,:body,:categoryType,:descType,:peopleTrafficType ");
		sqlStatement.append("RETURNING id ");
		sqlParams.addValue("placeId", memoir.getPlaceId());
		sqlParams.addValue("usrId", memoir.getUsrId());
		sqlParams.addValue("name", memoir.getName());
		sqlParams.addValue("body", memoir.getBody());
		sqlParams.addValue("categoryType", memoir.getCategoryType());
		sqlParams.addValue("descType", memoir.getDescType());
		sqlParams.addValue("peopleTrafficType", memoir.getPeopleTrafficType());
		System.out.println(sqlStatement);
		
		return memoirRepository.createMemoir(sqlStatement.toString(), sqlParams);
	}

}
