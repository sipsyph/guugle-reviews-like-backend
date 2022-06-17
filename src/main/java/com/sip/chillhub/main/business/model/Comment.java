package com.sip.chillhub.main.business.model;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class Comment {

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentCommentId() {
		return parentCommentId;
	}
	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}
	public Long getMemoirId() {
		return memoirId;
	}
	public void setMemoirId(Long memoirId) {
		this.memoirId = memoirId;
	}
	public Long getUsrId() {
		return usrId;
	}
	public void setUsrId(Long usrId) {
		this.usrId = usrId;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getUps() {
		return ups;
	}
	public void setUps(Integer ups) {
		this.ups = ups;
	}
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	public Comment(Long id, Long parentCommentId, Long memoirId, Long usrId, String body, Date createdDate, Integer ups,
			boolean del) {
		super();
		this.id = id;
		this.parentCommentId = parentCommentId;
		this.memoirId = memoirId;
		this.usrId = usrId;
		this.body = body;
		this.createdDate = createdDate;
		this.ups = ups;
		this.del = del;
	}
	
	public Comment() {
		
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", parentCommentId=" + parentCommentId + ", memoirId=" + memoirId + ", usrId="
				+ usrId + ", body=" + body + ", createdDate=" + createdDate + ", ups=" + ups + ", del=" + del + "]";
	}
	
	private Long id;
	private Long parentCommentId;
	private Long memoirId;
	private Long usrId;
	private String body;
	private Date createdDate;
	private Integer ups;
	private boolean del;

}
