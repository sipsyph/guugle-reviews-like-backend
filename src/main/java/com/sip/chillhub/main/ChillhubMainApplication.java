package com.sip.chillhub.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sip"})
public class ChillhubMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChillhubMainApplication.class, args);
	}

}