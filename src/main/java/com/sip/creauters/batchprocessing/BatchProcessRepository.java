package com.sip.creauters.batchprocessing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
  
@Repository  
interface BatchProcessRepository extends JpaRepository<BatchProcess, Long> {}
