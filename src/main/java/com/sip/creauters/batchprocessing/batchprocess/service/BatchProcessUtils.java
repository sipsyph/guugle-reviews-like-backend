package com.sip.creauters.batchprocessing.batchprocess.service;

public class BatchProcessUtils {
	
	public static BatchProcessChunksDivision splitRecordsSetIntoChunks(Integer chunkCount, Integer recordsAmount, Integer maxRecordsAmountPerChunk) {
		
		System.out.println(recordsAmount);
		
		Integer recordsAmountPerChunk = 0;
		
        if(recordsAmount > chunkCount) {
        	recordsAmountPerChunk = (recordsAmount / chunkCount) + 1;
        }else {
        	recordsAmountPerChunk = recordsAmount + 1;
        }
        
        if(recordsAmountPerChunk > maxRecordsAmountPerChunk) {
        	recordsAmountPerChunk = maxRecordsAmountPerChunk;
        	chunkCount = (recordsAmount / maxRecordsAmountPerChunk) + 1;
        }
        
        return new BatchProcessChunksDivision(recordsAmountPerChunk,chunkCount);
		
	}

}
