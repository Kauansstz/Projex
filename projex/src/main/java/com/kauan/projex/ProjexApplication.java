package com.kauan.projex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.kauan.projex.model")
public class ProjexApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjexApplication.class, args);
	}

}
