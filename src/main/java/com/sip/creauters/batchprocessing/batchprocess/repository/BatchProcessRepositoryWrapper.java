package com.sip.creauters.batchprocessing.batchprocess.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sip.creauters.batchprocessing.batchprocess.model.BatchProcess;
  
@Service  
public class BatchProcessRepositoryWrapper{
	
	private final BatchProcessRepository batchProcessRepository;
	
	@Autowired
	public BatchProcessRepositoryWrapper(final BatchProcessRepository batchProcessRepository) {
		this.batchProcessRepository = batchProcessRepository;
	}
	
    public List<BatchProcess> findAll() {
        return this.batchProcessRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
 
    public BatchProcess findById(Long id) {
        return this.batchProcessRepository.findById(id).get();
    }
    
    public BatchProcess findByName(String name) {
        return this.batchProcessRepository.findByName(name);
    }
    
    public BatchProcess saveAndFlush(BatchProcess batchProcess) {
        return this.batchProcessRepository.saveAndFlush(batchProcess);
    }
    
	
}
