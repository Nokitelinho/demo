package com.ibsplc.neoicargo.devops.snow.config;

import liquibase.pro.packaged.B;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.SnsClientBuilder;

import java.util.HashMap;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			May 31, 2021 	  Binu K			First draft
 */
@Configuration
public class AWSConfig {




    @Bean
    public SnsClient snsClient(){
        return   SnsClient.builder().build();
    }
}
