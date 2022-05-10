package com.sip.creauters.batchprocessing.batchprocess.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sip.creauters.batchprocessing.batchprocess.model.BatchProcess;
import com.sip.creauters.batchprocessing.batchprocess.repository.BatchProcessRepository;
import com.sip.creauters.batchprocessing.batchprocess.repository.BatchProcessRepositoryWrapper;

@Service
public class BatchProcessServiceImpl implements BatchProcessService {
	
	@Autowired
	private IncludeBMPToCreaturesService includeBMPToCreaturesService;
	@Autowired
	private BatchProcessRepositoryWrapper batchProcessRepository;
	
	@Transactional
	@Override
	public void includeBodyMassPotentialToCreatures(Boolean isMultiThreaded) {
		BatchProcess includeBMPToCreatures = batchProcessRepository.findByName("includeBodyMassPotentialToCreatures");
		includeBMPToCreatures.setIsRunning(true);
		BatchProcess changed = batchProcessRepository.saveAndFlush(includeBMPToCreatures);
		includeBMPToCreaturesService.splitCreatureSetWithBodyMassMovementIntoChunks(isMultiThreaded);
		changed.setIsRunning(false);
		batchProcessRepository.saveAndFlush(changed);
	}
	
	@Transactional
	@Override
	public void decayDeadCreatures() {
		
	}
	
	@Transactional
	@Override
	public void giveBirthByDuePregnantCreatures() {
		
	}

}
