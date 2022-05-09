package com.sip.creauters.batchprocessing.batchprocess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class IncludeBMPToCreaturesServiceImpl implements IncludeBMPToCreaturesService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void splitCreatureSetWithBodyMassMovementIntoChunks() {
		
		final Integer chunkCount = jdbcTemplate.queryForObject(
			"select cast(ac.value as integer) from app_configuration ac " +
			"where ac.\"name\" = 'includeBodyMassPotentialToCreaturesChunkCount'", 
			Integer.class
		);
		final Integer maxRecordsAmountPerChunk = jdbcTemplate.queryForObject(
			"select cast(ac.value as integer) from app_configuration ac " +
			"where ac.\"name\" = 'includeBodyMassPotentialToCreaturesMaxRecordsPerChunk'", 
			Integer.class
		);
		
		final Integer recordsAmount = jdbcTemplate.queryForObject(
			"select count(1) from creature c " + 
			"inner join creature_classification cc on cc.id = c.creature_classification_id " + 
			"where c.body_mass != c.last_body_mass_before_change ", 
			Integer.class
		);
		
		BatchProcessChunksDivision chunksDivision = BatchProcessUtils.splitRecordsSetIntoChunks(chunkCount, recordsAmount, maxRecordsAmountPerChunk);
		
		final Integer updatedRecordsAmountPerChunk = chunksDivision.getRecordsAmountPerChunk();
		final Integer updatedChunkCount = chunksDivision.getChunkCount();
        
        jdbcTemplate.update("truncate table creature_batch_chunk_temp ");
		
    	for(int currentChunk = 1; currentChunk<=updatedChunkCount; currentChunk++) {
    		
    		jdbcTemplate.update(
    			"insert into creature_batch_chunk_temp(creature_id,chunk_id) "+ 
    			"select c.id, (?) from creature c " + 
    			"inner join creature_classification cc on cc.id = c.creature_classification_id " + 
    			"where c.body_mass != c.last_body_mass_before_change and " +
    			"not exists (select 1 from creature_batch_chunk_temp tmp where tmp.creature_id = c.id)" +
    			"limit ? ",
    			currentChunk,
    			updatedRecordsAmountPerChunk
    		);
    		
    		System.out.println(currentChunk);
        }
    	
    	
	}
	
	public void processCreatureSetWithBodyMassMovement() {
		//TODO: Add into the accumulated_daily_body_mass and update average_daily_body_mass based on this
	}
	
	public void splitCreatureSetWithBodyMassPotentialDueForInclusionIntoChunks() {
		//TODO: Replicate splitCreatureSetWithBodyMassMovementIntoChunks somewhat
	}
	
	public void processCreatureSetWithBodyMassPotentialDueForInclusion() {
		//TODO: "Post" the body mass potential accrued finally into the body_mass that is due based on the body_mass_potential_inclusion_type
		// clear the body_mass_potential value since this has been posted already, and to prepare for further accruals
		// and calculate body mass potential from average_daily_body_mass then add the value into the body_mass_potential
	}
	
}
