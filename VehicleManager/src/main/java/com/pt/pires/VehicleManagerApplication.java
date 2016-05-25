package com.pt.pires;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VehicleManagerApplication{

	public static String ROOT = "files-dir";
	
	public static void main(String[] args) {
		SpringApplication.run(VehicleManagerApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(){
		return (String[] args) -> {
			new File(ROOT).mkdir();
		};
	}
	
}
