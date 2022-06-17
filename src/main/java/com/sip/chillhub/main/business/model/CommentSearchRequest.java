package com.sip.chillhub.main.business.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class CommentSearchRequest extends BaseSearchRequest {

	public List<Long> getUsrId() {
		return usrId;
	}

	public void setUsrId(List<Long> usrId) {
		this.usrId = usrId;
	}

	public List<Long> getMemoirId() {
		return memoirId;
	}

	public void setMemoirId(List<Long> memoirId) {
		this.memoirId = memoirId;
	}

	public String getSortByUpvotes() {
		return sortByUpvotes;
	}

	public void setSortByUpvotes(String sortByUpvotes) {
		this.sortByUpvotes = sortByUpvotes;
	}

	private List<Long> usrId;
	private List<Long> memoirId;
	private String sortByUpvotes;

	public boolean isEmpty() {
		if((id==null||id.isEmpty())&&
				(usrId==null||usrId.isEmpty())&&
				(memoirId==null||memoirId.isEmpty())&&
				searchString==null) {
			return true;
		}
		return false;
	}


	
}
