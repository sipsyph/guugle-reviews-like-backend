package com.sip.creauters.batchprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sip"})
public class CreautersBatchProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreautersBatchProcessingApplication.class, args);
	}

}