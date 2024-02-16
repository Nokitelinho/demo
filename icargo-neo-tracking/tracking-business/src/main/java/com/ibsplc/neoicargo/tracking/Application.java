package com.ibsplc.neoicargo.tracking;

import com.ibsplc.neoicargo.framework.core.config.BootstrapConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(BootstrapConfig.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
