package com.example.etlProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EtlProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtlProjectApplication.class, args);
	}

}
