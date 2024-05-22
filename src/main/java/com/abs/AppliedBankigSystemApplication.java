package com.abs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppliedBankigSystemApplication {
	
	private static final Logger Logger=LoggerFactory.getLogger(AppliedBankigSystemApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AppliedBankigSystemApplication.class, args);
		Logger.info("Application Started!!!");
	}

}
