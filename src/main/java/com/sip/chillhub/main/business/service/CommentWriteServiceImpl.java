package com.sip.chillhub.main.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.sip.chillhub.main.business.model.Comment;
import com.sip.chillhub.main.business.repository.CommentRepository;

@Service
public class CommentWriteServiceImpl implements CommentWriteService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	public Long createComment(Comment comment) {
		final StringBuilder sqlStatement = new StringBuilder(500);
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlStatement.append("INSERT into comment(usr_id,parent_comment_id,memoir_id,body) ");
		sqlStatement.append("SELECT :usrId,:parentCommentId,:memoirId,:body ");
		sqlStatement.append("RETURNING id ");
		sqlParams.addValue("usrId", comment.getUsrId());
		sqlParams.addValue("parentCommentId", comment.getParentCommentId());
		sqlParams.addValue("memoirId", comment.getMemoirId());
		sqlParams.addValue("body", comment.getBody());
		System.out.println(sqlStatement);
		
		return commentRepository.createComment(sqlStatement.toString(), sqlParams);
	}

}
