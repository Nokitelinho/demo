package com.ibsplc.neoicargo.devops.snow.notification.sns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
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
class SNSNotificationsBootstrap {


    @Autowired
    @Qualifier("activeEmailAlerts")
    //severity:csv of emails
    HashMap<String,String> activeEmailAlerts;

    @Autowired
    SNSHelper snsHelper;

    public Optional<List<String>> emailListForSeverity(String severity){
        var emailList = activeEmailAlerts.get(severity);
        //Get email list for default if none defined for specific severity
        emailList = (emailList!=null)?emailList:activeEmailAlerts.get(SNSHelper.DEFAULT_EMAIL_SUB_IDENTIFIER);
        return Optional.ofNullable(SNSHelper.splitCsv(emailList));

    }

    @PostConstruct
    public void init(){
        if(snsHelper.isEnabled()) {
            log.info("Creating SNS Topics & Email Subscriptions");
            activeEmailAlerts.entrySet().forEach(entry -> {
                var severity = entry.getKey();
                var topicArn = snsHelper.lookupOrCreateTopic(SNSHelper.toTopicName(severity));
                log.info("SNS topic {} for severity {}",topicArn,severity);
                var emailList = SNSHelper.splitCsv(entry.getValue());
                log.debug("Email Notification list {} for severity {}",emailList,severity);
                if(!CollectionUtils.isEmpty(emailList)) {
                    var filteredList = snsHelper.filterEmailsWithExistingSubscriptions(topicArn, emailList);
                    log.debug("Subscriptions to be created for  {}",filteredList);
                    if (!CollectionUtils.isEmpty(filteredList)) {
                        var subArn = snsHelper.subscribeToEmails(topicArn, filteredList);
                        log.info("SNS subscription {} for emails {}", subArn, filteredList);
                    }
                }
            });
        }else{
            log.info("SNS Alerts are disabled");
        }
    }
}
