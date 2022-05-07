package com.sip.creauters.batchprocessing.batchprocess.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchProcessServiceImpl implements BatchProcessService {
	
	@Autowired
	private IncludeBMPToCreaturesService includeBMPToCreaturesService;
	
	@Transactional
	@Override
	public void includeBodyMassPotentialToCreatures() {
		includeBMPToCreaturesService.splitCreatureSetWithBodyMassMovementIntoChunks();
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
