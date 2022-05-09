package com.sip.creauters.batchprocessing.batchprocess.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sip.creauters.batchprocessing.batchprocess.model.BatchProcess;

@Service
public class BatchProcessServiceImpl implements BatchProcessService {
	
	@Autowired
	private IncludeBMPToCreaturesService includeBMPToCreaturesService;
	
	@Transactional
	@Override
	public void includeBodyMassPotentialToCreatures(Boolean isMultiThreaded) {
		includeBMPToCreaturesService.splitCreatureSetWithBodyMassMovementIntoChunks(isMultiThreaded);
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
