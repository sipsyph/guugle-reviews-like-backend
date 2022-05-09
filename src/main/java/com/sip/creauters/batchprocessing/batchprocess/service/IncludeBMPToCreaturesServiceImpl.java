package com.sip.creauters.batchprocessing.batchprocess.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class IncludeBMPToCreaturesServiceImpl implements IncludeBMPToCreaturesService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	static final String shouldCompoundOrIncludeTodayWherePredicate = 
			"( " +
			"	( " +
			"		cc.body_mass_potential_compounding_type = 1 or  " +
			"		(cc.body_mass_potential_compounding_type = 2 and :todayIsLastWorkingDayOfWeek) or  " +
			"		(cc.body_mass_potential_compounding_type = 3 and :todayIsLastWorkingDayOfMonth) or " +
			"		(cc.body_mass_potential_compounding_type = 4 and :todayIsLastWorkingDayOfQuarter) or " +
			"		(cc.body_mass_potential_compounding_type = 5 and :todayIsLastWorkingDayOfSemiAnnual) or " +
			"		(cc.body_mass_potential_compounding_type = 6 and :todayIsLastWorkingDayOfAnnual) " +
			"	) or " +
			"	( " +
			"		cc.body_mass_potential_inclusion_type = 1 or " +
			"		(cc.body_mass_potential_inclusion_type = 2 and :todayIsLastWorkingDayOfWeek) or " +
			"		(cc.body_mass_potential_inclusion_type = 3 and :todayIsLastWorkingDayOfMonth) or " +
			"		(cc.body_mass_potential_inclusion_type = 4 and :todayIsLastWorkingDayOfQuarter) or " +
			"		(cc.body_mass_potential_inclusion_type = 5 and :todayIsLastWorkingDayOfSemiAnnual) or " +
			"		(cc.body_mass_potential_inclusion_type = 6 and :todayIsLastWorkingDayOfAnnual) " + 
			"	) " +
			") ";

	public void splitCreatureSetWithBodyMassMovementIntoChunks(Boolean isMultiThreaded) {
		
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
		
		final Integer recordsAmount = namedJdbcTemplate.queryForObject(
			"select count(1) from creature c "
			+ "inner join creature_classification cc on cc.id = c.creature_classification_id " 
			+ "where c.body_mass != c.last_body_mass_before_change "
			+ "and ( "+shouldCompoundOrIncludeTodayWherePredicate+" )", 
			new MapSqlParameterSource()
			.addValue("todayIsLastWorkingDayOfWeek", false)
			.addValue("todayIsLastWorkingDayOfMonth", false)
			.addValue("todayIsLastWorkingDayOfQuarter", false)
			.addValue("todayIsLastWorkingDayOfSemiAnnual", false)
			.addValue("todayIsLastWorkingDayOfAnnual", false),
			Integer.class
		);
		
		BatchProcessChunksDivision chunksDivision = BatchProcessUtils.splitRecordsSetIntoChunks(chunkCount, recordsAmount, maxRecordsAmountPerChunk);
		
		final Integer updatedRecordsAmountPerChunk = chunksDivision.getRecordsAmountPerChunk();
		final Integer updatedChunkCount = chunksDivision.getChunkCount();
        
        jdbcTemplate.update("truncate table creature_batch_chunk_temp ");
		
        ExecutorService executorService = Executors.newFixedThreadPool(updatedChunkCount);
        
    	for(Integer currentChunk = 1; currentChunk<=updatedChunkCount; currentChunk++) {
    		
    		final Integer finalCurChunk = currentChunk;
    		
    		namedJdbcTemplate.update(
    			"insert into creature_batch_chunk_temp(creature_id,chunk_id) " 
    			+"select c.id, (:currentChunk) from creature c "  
    			+"inner join creature_classification cc on cc.id = c.creature_classification_id "  
    			+"where c.body_mass != c.last_body_mass_before_change and " 
    			+"not exists (select 1 from creature_batch_chunk_temp tmp where tmp.creature_id = c.id) " 
    			+"and ( "+shouldCompoundOrIncludeTodayWherePredicate+" ) "
    			+"limit :updatedRecordsAmountPerChunk ",
    			new MapSqlParameterSource()
        		.addValue("currentChunk", Integer.valueOf(finalCurChunk))
        		.addValue("updatedRecordsAmountPerChunk", updatedRecordsAmountPerChunk)
        		.addValue("todayIsLastWorkingDayOfWeek", false)
        		.addValue("todayIsLastWorkingDayOfMonth", false)
        		.addValue("todayIsLastWorkingDayOfQuarter", false)
        		.addValue("todayIsLastWorkingDayOfSemiAnnual", false)
        		.addValue("todayIsLastWorkingDayOfAnnual", false)
    		);
    		
    		if(isMultiThreaded) {
        		executorService.execute(() -> {
        			processCreatureSetWithBodyMassMovement(finalCurChunk);
        		});
    		}else {
    			processCreatureSetWithBodyMassMovement(finalCurChunk);
    		}
    		
    		System.out.println(finalCurChunk);
        }
    	
    	if(isMultiThreaded) {
    		executorService.shutdown();
    		System.out.println("All threads finished");
    	}
    	
    	System.out.println("Batch process finished");
	}
	
	private void processCreatureSetWithBodyMassMovement(Integer finalCurChunk) {
		System.out.println("Thread executed on chunk "+finalCurChunk);
		
		try {
			System.out.println("Thread executing on chunk "+finalCurChunk);
			namedJdbcTemplate.update(
					"update creature "
					+"set accumulated_daily_body_mass = 9999.99, " //accumulated_daily_body_mass + (last_body_mass_before_change * (:dateToday::date - last_body_mass_change_date::date) ) + body_mass
					+"average_daily_body_mass = ( "
					+"(accumulated_daily_body_mass + (last_body_mass_before_change * (:dateToday::date - last_body_mass_change_date::date) ) + body_mass) "
					+"+ (body_mass * ( "
					+"( "
					+"case c_classif.body_mass_potential_compounding_type "
					+"when 1 then :dateToday "
					+"when 2 then :lastDayOfWeek "
					+"when 3 then :lastDayOfMonth "
					+"when 4 then :lastDayOfQuarter "
					+"when 5 then :lastDayOfHalfYear "
					+"when 6 then :lastDayOfYear "
					+"else :dateToday "
					+"end "
					+")::date "
					+"- :dateToday::date "
					+") "
					+") "
					+"/ "
					+"case c_classif.body_mass_potential_compounding_type "
					+"when 1 then 1 "
					+"when 2 then 7 "
					+"when 3 then DATE_PART('days', date_trunc('month', :dateToday::date) + '1 month'::interval - '1 day'::interval) "
					+"when 4 then 90 " //hard coded 90 as amount of days in quarter for convenience
					+"when 5 then 180 " //hard coded to 180 as amount of days in halfyear for convenience
					+"when 6 then 360 " //hard coded 360 as amount of days in year for convenience
					+"else 1 "
					+"end "
					+") "
					+"from creature_batch_chunk_temp as c_temp, creature_classification as c_classif "
					+"where creature.id = c_temp.creature_id and c_temp.chunk_id = :currentChunk and c_classif.id = creature.creature_classification_id and creature.id = :currentChunk",
					new MapSqlParameterSource()
					.addValue("currentChunk", Integer.valueOf(finalCurChunk))
					.addValue("dateToday", LocalDate.of(2022, 5, 9))
					.addValue("lastDayOfWeek", LocalDate.of(2022, 5, 15)) //Dates are hard coded for now for convenience
					.addValue("lastDayOfMonth", LocalDate.of(2022, 5, 31))
					.addValue("lastDayOfQuarter", LocalDate.of(2022, 6, 30))
					.addValue("lastDayOfHalfYear", LocalDate.of(2022, 6, 30))
					.addValue("lastDayOfYear", LocalDate.of(2022, 12, 31))
				);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println("Finished thread execution on chunk "+finalCurChunk);
		}
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
