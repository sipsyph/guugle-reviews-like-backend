package com.sip.creauters.batchprocessing.batchprocess.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
		Date start = new Date();
		System.out.println("Batch process started at "+start);
		final Integer chunkCount = jdbcTemplate.queryForObject(
			"select cast(ac.value as unsigned) from app_configuration ac " +
			"where ac.`name` = 'includeBodyMassPotentialToCreaturesChunkCount'", 
			Integer.class
		);
		final Integer maxRecordsAmountPerChunk = jdbcTemplate.queryForObject(
			"select cast(ac.value as unsigned) from app_configuration ac " +
			"where ac.`name` = 'includeBodyMassPotentialToCreaturesMaxRecordsPerChunk'", 
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
    		
    		namedJdbcTemplate.update(
    			"insert into creature_batch_chunk_temp(creature_id,chunk_id) " 
    			+"select c.id, (:currentChunk) from creature c "  
    			+"inner join creature_classification cc on cc.id = c.creature_classification_id "  
    			+"where c.body_mass != c.last_body_mass_before_change and " 
    			+"not exists (select 1 from creature_batch_chunk_temp tmp where tmp.creature_id = c.id) " 
    			+"and ( "+shouldCompoundOrIncludeTodayWherePredicate+" ) "
    			+"limit :updatedRecordsAmountPerChunk ",
    			new MapSqlParameterSource()
        		.addValue("currentChunk", Integer.valueOf(currentChunk))
        		.addValue("updatedRecordsAmountPerChunk", updatedRecordsAmountPerChunk)
        		.addValue("todayIsLastWorkingDayOfWeek", false)
        		.addValue("todayIsLastWorkingDayOfMonth", false)
        		.addValue("todayIsLastWorkingDayOfQuarter", false)
        		.addValue("todayIsLastWorkingDayOfSemiAnnual", false)
        		.addValue("todayIsLastWorkingDayOfAnnual", false)
    		);
    		
    		System.out.println("Inserted into temp table for chunk "+currentChunk);
        }
    	
    	List<Future<Integer>> futures = new ArrayList<Future<Integer>>();;
		for(Integer currentChunk = 1; currentChunk<=updatedChunkCount; currentChunk++) {
			final Integer finalCurChunk = currentChunk;
			if(isMultiThreaded) {
				futures.add(executorService.submit(new Callable<Integer>() { 
        			
        			@Override
        			public Integer call() {
        				processCreatureSetWithBodyMassMovement(finalCurChunk);
        				Date end = new Date();
        		    	System.out.println("Chunk finished at "+end);
        		    	System.out.println("Chunk took "+(end.getTime()-start.getTime())/1000+" seconds");
        				return finalCurChunk;
        			}
        			
        		}));
			}else {
				processCreatureSetWithBodyMassMovement(finalCurChunk);
			}
		}
		
		if(isMultiThreaded) {
			executorService.shutdown();
			//FIXME: executorService.awaitTermination future.get and CountDownLatch blocks but doesnt seem to let the threads finish
	    	for(Future<Integer> future: futures) {
	    		try {
	    			System.out.println("Finished at "+future.get());
				} catch (InterruptedException e) {
					System.out.println("An error occurred."+ e);
				} catch (ExecutionException e) {
					System.out.println("An error occurred."+ e);
				}
	    	}
//			System.out.println("All threads finished");
			
		}
    	
		Date end = new Date();
    	System.out.println("Batch process finished at "+end);
    	System.out.println("Batch process took "+(end.getTime()-start.getTime())/1000+" seconds");
	}
	
	private void processCreatureSetWithBodyMassMovement(Integer finalCurChunk) {
		System.out.println("Thread executed on chunk "+finalCurChunk);
		
		try {
			System.out.println("Thread executing on chunk "+finalCurChunk);
			namedJdbcTemplate.update(
					" update creature "+
					" inner join creature_batch_chunk_temp as c_temp on c_temp.creature_id = creature.id "+
					" inner join creature_classification as c_classif on c_classif.id = creature.creature_classification_id "+
					" set creature.accumulated_daily_body_mass = "+
					" (accumulated_daily_body_mass + (last_body_mass_before_change * (cast(:dateToday as date) - cast(last_body_mass_change_date as date)) ) + body_mass), "+
					" creature.average_daily_body_mass = "+
					" ( "+
					" (accumulated_daily_body_mass + (last_body_mass_before_change * (cast(:dateToday as date) - cast(last_body_mass_change_date as date)) ) + body_mass) "+
					" + (body_mass * ( "+
					" cast( ( "+
					" case c_classif.body_mass_potential_compounding_type "+
					" when 1 then :dateToday "+
					" when 2 then :lastDayOfWeek "+
					" when 3 then :lastDayOfMonth "+
					" when 4 then :lastDayOfQuarter "+
					" when 5 then :lastDayOfHalfYear "+
					" when 6 then :lastDayOfYear "+
					" else :dateToday "+
					" end "+
					" ) as date) "+
					" - cast(:dateToday as date) "+
					" ) "+
					" ) "+
					" / "+
					" case c_classif.body_mass_potential_compounding_type "+
					" when 1 then 1 "+
					" when 2 then 7 "+
					" when 3 then 30 when 4 then 90 when 5 then 180 when 6 then 360 else 1 "+
					" end "+
					" ) "+
					" where c_temp.chunk_id = :currentChunk "
					,
					new MapSqlParameterSource()
					.addValue("currentChunk", Integer.valueOf(finalCurChunk))
					.addValue("dateToday", LocalDate.of(2022, 5, 9))
					.addValue("lastDayOfWeek", LocalDate.of(2022, 5, 15)) //Dates are hard coded for now for convenience
					.addValue("lastDayOfMonth", LocalDate.of(2022, 5, 31))
					.addValue("lastDayOfQuarter", LocalDate.of(2022, 6, 30))
					.addValue("lastDayOfHalfYear", LocalDate.of(2022, 6, 30))
					.addValue("lastDayOfYear", LocalDate.of(2022, 12, 31))
				);
			System.out.println("Finished insert to temp table at chunk "+finalCurChunk);
			//Bottleneck query taking around 12 seconds to execute! Remove when necessary.
			Integer dumbBottleneckQuery = jdbcTemplate.queryForObject(
					"select count(1) from creature_classification cc ,creature a ,creature_classification b ", 
					Integer.class
				);
			System.out.println("Finished bottleneck select count "+dumbBottleneckQuery+" at chunk "+finalCurChunk);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
