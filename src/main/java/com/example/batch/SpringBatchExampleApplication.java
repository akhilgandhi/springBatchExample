package com.example.batch;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchExampleApplication{

	public static void main(String[] args) {

		System.exit(SpringApplication.exit(SpringApplication.run(SpringBatchExampleApplication.class, args)));
	}

}
