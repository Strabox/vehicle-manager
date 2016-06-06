package com.pt.pires;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pt.pires.util.FileUtil;

@SpringBootApplication
public class VehicleManagerApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(VehicleManagerApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(){
		return (String[] args) -> {
			FileUtil.makeDir(FileUtil.ROOT);
		};
	}
	
}
