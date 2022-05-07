package com.sip.creauters.batchprocessing.batchprocess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class IncludeBMPToCreaturesServiceImpl implements IncludeBMPToCreaturesService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void splitCreatureSetWithBodyMassMovementIntoChunks() {
		
		final Integer threadCount = 9;
		
		final Integer recordsAmount = jdbcTemplate.queryForObject(
			"select count(1) from creature c " + 
			"inner join creature_classification cc on cc.id = c.creature_classification_id " + 
			"where c.body_mass != c.last_body_mass_before_change ", 
			Integer.class
		);
		
		System.out.println(recordsAmount);
		
		Integer recordsAmountPerChunk = 0;
		
        if(recordsAmount > threadCount.intValue()) {
        	recordsAmountPerChunk = (recordsAmount / threadCount.intValue()) + 1;
        }else {
        	recordsAmountPerChunk = recordsAmount + 1;
        }
        
        jdbcTemplate.update("truncate table creature_batch_chunk_temp ");
        
		
		//TODO: parametarize 5 here
    	for(int currentChunk = 1; currentChunk<=threadCount; currentChunk++) {
    		
    		jdbcTemplate.update(
    			"insert into creature_batch_chunk_temp(creature_id,chunk_id) "+ 
    			"select c.id, (?) from creature c " + 
    			"inner join creature_classification cc on cc.id = c.creature_classification_id " + 
    			"where c.body_mass != c.last_body_mass_before_change and " +
    			"not exists (select 1 from creature_batch_chunk_temp tmp where tmp.creature_id = c.id)" +
    			"limit ? ",
    			currentChunk,
    			recordsAmountPerChunk
    		);
    		
    		System.out.println(currentChunk);
        }
		
	}
	
}
