package com.ibsplc.neoicargo.devops.snow;

import com.ibsplc.neoicargo.devops.snow.config.SNOWConfig;
import com.ibsplc.neoicargo.devops.snow.config.SNOWMappingConfig;
import com.ibsplc.neoicargo.devops.snow.notification.sns.SNSHelper;
import com.ibsplc.neoicargo.devops.snow.notification.utils.AWSRuntimeMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;

@SpringBootApplication
@ComponentScan("com.ibsplc.neoicargo.devops.snow")
@Slf4j
public class SnowAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnowAdapterApplication.class, args);
	}


	@Bean public CommandLineRunner commandLineRunner(SNOWMappingConfig snowMappingConfig,
													 SNOWConfig snowConfig,
													 @Qualifier("activeEmailAlerts")
															 HashMap<String,String> activeEmailAlerts,
													 AWSRuntimeMetadata awsRuntimeMetadata){
		return args -> {
			log.info("SNOW Config follows:: {}",snowConfig);
			log.info("SNOW Mappings follows:: {}",snowMappingConfig);
			log.info("Active configured SNS Email alert distribution list follows:: {}",activeEmailAlerts);
			log.info("Hosted on AWS a/c {} in region {} and and on node {}",awsRuntimeMetadata.getAccountId(),awsRuntimeMetadata.getRegion(),awsRuntimeMetadata.getInstanceIp());
		};
	}



}
