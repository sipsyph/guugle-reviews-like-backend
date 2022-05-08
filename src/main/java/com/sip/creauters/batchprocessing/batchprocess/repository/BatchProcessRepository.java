package com.sip.creauters.batchprocessing.batchprocess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sip.creauters.batchprocessing.batchprocess.model.BatchProcess;
  
@Repository
public interface BatchProcessRepository extends JpaRepository<BatchProcess, Long> {}
