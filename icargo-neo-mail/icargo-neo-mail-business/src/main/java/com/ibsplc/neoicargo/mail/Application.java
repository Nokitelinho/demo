package com.ibsplc.neoicargo.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

import com.ibsplc.neoicargo.framework.core.config.BootstrapConfig;

@Import(BootstrapConfig.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
