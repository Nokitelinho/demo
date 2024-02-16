package com.ibsplc.neoicargo.devops.snow.notification.sns;

import com.ibsplc.neoicargo.devops.snow.notification.AlertManagerNotificationListener;
import com.ibsplc.neoicargo.devops.snow.notification.model.AlertManagerNotification;
import com.ibsplc.neoicargo.devops.snow.notification.utils.AWSRuntimeMetadata;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 01, 2021 	  Binu K			First draft
 */
@Component
@Slf4j
public class SNSPublisherListener implements AlertManagerNotificationListener {

    @Autowired
    SNSHelper snsHelper;

    @Autowired
    AWSRuntimeMetadata awsRuntimeMetadata;


    private String subject(AlertManagerNotification alertManagerNotification){
        return String.format("<%s><%s  for %s on k8s namespace %s >",
                alertManagerNotification.getPrometheusSeverityLevel().toUpperCase(),alertManagerNotification.getAlertName(),
                alertManagerNotification.getTarget(),alertManagerNotification.getNamespace());
    }

    private String body(AlertManagerNotification alertManagerNotification){
        var line1 = String.format("%s Alert: %s for %s on k8s namespace %s.\n\n\n",
                alertManagerNotification.getPrometheusSeverityLevel().toUpperCase(),alertManagerNotification.getAlertName(),
                alertManagerNotification.getResourceName(),alertManagerNotification.getNamespace());
        var line2 =  String.format("AWS Details: A/C - %s, Region - %s, EKS instance IP - %s\n",awsRuntimeMetadata.getAccountId(),awsRuntimeMetadata.getRegion(),awsRuntimeMetadata.getInstanceIp());
        var line3="Note: The instance IP is the EKS instance that received the alert. This is not the IP of the instance that triggered the alert.\n\n\n\n";
        var line4=String.format("Description: %s\n%s\n",alertManagerNotification.getDescription(),alertManagerNotification);
        return line1 + line2 + line3 + line4;
    }




    @Override
    public void onFiring(AlertManagerNotification alertManagerNotification) {
        log.info("Triggered with {} of severity {} ",alertManagerNotification.getAlertName(),alertManagerNotification.getPrometheusSeverityLevel());
        var alertName = alertManagerNotification.getAlertName();
        if(snsHelper.shouldAlert(alertName)){
            var severity = alertManagerNotification.getPrometheusSeverityLevel();
            var topicName = SNSHelper.toTopicName(severity);
            var defaultTopicName = SNSHelper.toTopicName(null);
            snsHelper.lookupTopic(topicName).or(()->snsHelper.lookupTopic(defaultTopicName)).ifPresent(topicArn->{
                val result = snsHelper.publishToTopic(topicArn,subject(alertManagerNotification),body(alertManagerNotification));
                log.info("Publish {} alert {}  to topic {} - {}",severity,subject(alertManagerNotification),topicArn,result?"Succeeded":"Failed");
            });
        }else{
            log.info("Ignoring alert {} as it is disabled.",alertManagerNotification);
        }

    }

    @Override
    public void onResolved(AlertManagerNotification alertManagerNotification) {

    }
}
