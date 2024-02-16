package com.ibsplc.neoicargo.devops.snow.notification.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.internal.util.EC2MetadataUtils;

import javax.annotation.PostConstruct;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			May 26, 2021 	  Binu K			First draft
 */
@Component
@Getter
@Slf4j
public class AWSRuntimeMetadata  {

    protected static final String UNDEFINED = "undefined";
    String region  = UNDEFINED;
    String accountId = UNDEFINED;
    String instanceIp = UNDEFINED;

    @PostConstruct
    public void init(){
        try {
            var instanceInfo = EC2MetadataUtils.getInstanceInfo();
            region = nullSafeSet(instanceInfo.getRegion());
            accountId = nullSafeSet(instanceInfo.getAccountId());
            instanceIp = nullSafeSet(instanceInfo.getPrivateIp());
        }catch (SdkClientException sdkClientException){
            log.error("Not able to get AWS EC2 instance metadata",sdkClientException);
            log.error("You may not be running on AWS");
        }
    }

    private String nullSafeSet(String value){
        if(StringUtils.hasText(value)){
           return value;
        }
        return UNDEFINED;

    }

}
