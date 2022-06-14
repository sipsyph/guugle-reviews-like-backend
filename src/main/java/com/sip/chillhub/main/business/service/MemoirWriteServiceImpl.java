package com.sip.chillhub.main.business.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.User;
import com.sip.chillhub.main.business.repository.UserRepository;

@Service
public class MemoirWriteServiceImpl implements MemoirWriteService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Long createMemoir(User user) {
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlStatement.append("INSERT into usr(name,pass,\"desc\",usr_type,is_premium,coins,avatar_img,name_style,last_login_date,del) ");
		sqlStatement.append("SELECT :name,:pass,:desc,:usrType,:isPremium,:coins,:avatarImg,:nameStyle,:lastLoginDate,false ");
		sqlStatement.append("ON CONFLICT DO NOTHING RETURNING id ");
		sqlParams.addValue("name", user.getName());
		sqlParams.addValue("pass", user.getPass());
		sqlParams.addValue("desc", user.getDesc());
		sqlParams.addValue("usrType", user.getUsrType());
		sqlParams.addValue("isPremium", user.isPremium());
		sqlParams.addValue("coins", user.getCoins());
		sqlParams.addValue("avatarImg", user.getAvatarImg());
		sqlParams.addValue("nameStyle", user.getNameStyle());
		sqlParams.addValue("lastLoginDate", new Date());
		
		System.out.println(sqlStatement);
		
		return userRepository.createUser(sqlStatement.toString(), sqlParams);
	}

}
