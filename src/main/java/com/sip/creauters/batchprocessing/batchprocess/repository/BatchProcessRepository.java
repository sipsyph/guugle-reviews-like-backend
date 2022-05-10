package com.sip.creauters.batchprocessing.batchprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sip.creauters.batchprocessing.batchprocess.model.BatchProcess;
  
@Repository
public interface BatchProcessRepository extends JpaRepository<BatchProcess, Long> {
	
	@Query(value = "select * from batch_process where name = :name", nativeQuery = true)
	BatchProcess findByName(@Param("name")String name);
	
}
