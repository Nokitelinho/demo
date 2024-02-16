package com.ibsplc.icargo.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ibsplc.icargo")
@SpringBootApplication
public class SpringBootIcargoToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootIcargoToolsApplication.class, args);
	}

}