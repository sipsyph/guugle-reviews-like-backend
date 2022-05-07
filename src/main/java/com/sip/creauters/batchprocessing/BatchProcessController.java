package com.sip.creauters.batchprocessing;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sip.creauters.batchprocessing.batchprocess.model.BatchProcess;
import com.sip.creauters.batchprocessing.batchprocess.service.BatchProcessService;

@RestController
@RequestMapping("/execute")
public class BatchProcessController {
    @Autowired
	private BatchProcessRepository batchProcessRepository;
    @Autowired
    private BatchProcessService batchProcessService;

    public BatchProcessController(BatchProcessRepository batchProcessRepository,
    		BatchProcessService batchProcessService) {
        this.batchProcessRepository = batchProcessRepository;
    }

    @GetMapping
    public List<BatchProcess> findAll() {
        return this.batchProcessRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
 
    @GetMapping("/{id}")
    public BatchProcess findById(@PathVariable("id") Long id) {
        return this.batchProcessRepository.findById(id).get();
    }

    @PostMapping("/includeBodyMassPotentialToCreatures")
    public void includeBodyMassPotentialToCreatures() {
    	batchProcessService.includeBodyMassPotentialToCreatures();
    }
    
    @PostMapping("/decayDeadCreatures")
    public void decayDeadCreatures() {
        
    }
    
    @PostMapping("/giveBirthByDuePregnantCreatures")
    public void giveBirthByDuePregnantCreatures() {
        
    }
    
}
