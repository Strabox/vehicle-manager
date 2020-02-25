package com.pt.pires;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling		//IMPORTANT: Enables Spring @Scheduled annotations
public class VehicleManagerApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(VehicleManagerApplication.class, args);
	}
	
}
