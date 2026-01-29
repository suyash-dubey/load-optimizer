package com.smartload.optimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoadOptimizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoadOptimizerApplication.class, args);
	}

}
/*Invoke-RestMethod `
  -Uri "http://localhost:8080/api/v1/load-optimizer/optimize" `
  -Method POST `
  -ContentType "application/json" `
  -Body (Get-Content request.json -Raw)
*/
/*
Invoke-RestMethod http://localhost:8080/actuator/health
 */