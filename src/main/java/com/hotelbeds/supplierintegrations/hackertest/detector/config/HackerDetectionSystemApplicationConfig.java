package com.hotelbeds.supplierintegrations.hackertest.detector.config;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.hotelbeds.supplierintegrations.hackertest")
public class HackerDetectionSystemApplicationConfig {

	public static void main(String[] args) {
		SpringApplication.run(HackerDetectionSystemApplicationConfig.class, args);
	}

}
