package com.sip.creauters.batchprocessing.batchprocess.service;

public interface BatchProcessService {
	
	public void includeBodyMassPotentialToCreatures(Boolean isMultiThreaded);
	
	public void decayDeadCreatures();
	
	public void giveBirthByDuePregnantCreatures();
}
