package com.sip.creauters.batchprocessing.batchprocess.batchconfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

}
