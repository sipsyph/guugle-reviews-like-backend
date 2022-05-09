package com.sip.creauters.batchprocessing.batchprocess.service;

public class BatchProcessChunksDivision {
	
    private Integer recordsAmountPerChunk;
    
    private Integer chunkCount;
    
	public BatchProcessChunksDivision(Integer recordsAmountPerChunk, Integer chunkCount) {
		this.recordsAmountPerChunk = recordsAmountPerChunk;
		this.chunkCount = chunkCount;
	}

	public Integer getRecordsAmountPerChunk() {
		return recordsAmountPerChunk;
	}

	public void setRecordsAmountPerChunk(Integer recordsAmountPerChunk) {
		this.recordsAmountPerChunk = recordsAmountPerChunk;
	}

	public Integer getChunkCount() {
		return chunkCount;
	}

	public void setChunkCount(Integer chunkCount) {
		this.chunkCount = chunkCount;
	}

}
