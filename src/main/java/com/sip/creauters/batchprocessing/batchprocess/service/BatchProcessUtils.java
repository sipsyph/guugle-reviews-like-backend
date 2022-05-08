package com.sip.creauters.batchprocessing.batchprocess.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sip.creauters.batchprocessing.batchprocess.model.BatchProcess;

public class BatchProcessUtils {
	
	public static Integer splitRecordsSetIntoChunks(Integer chunkCount, Integer recordsAmount, Integer maxRecordsAmountPerChunk) {
		System.out.println(recordsAmount);
		
		Integer recordsAmountPerChunk = 0;
		
		//TODO: if recordsAmountPerChunk is more than maxRecordsAmountPerChunk
		//then add more chunks so that maxRecordsAmountPerChunk will be met
		
        if(recordsAmount > chunkCount.intValue()) {
        	recordsAmountPerChunk = (recordsAmount / chunkCount.intValue()) + 1;
        }else {
        	recordsAmountPerChunk = recordsAmount + 1;
        }
        
        return recordsAmountPerChunk;
		
	}

}
