package com.zworks.pdsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PdSysApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PdSysApplication.class); 
        app.run(args);
	}
}
