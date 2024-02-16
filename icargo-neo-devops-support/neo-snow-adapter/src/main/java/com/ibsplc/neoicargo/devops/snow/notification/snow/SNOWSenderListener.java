package com.ibsplc.neoicargo.devops.snow.notification.snow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.devops.snow.config.SNOWConfig;
import com.ibsplc.neoicargo.devops.snow.config.SNOWMappingConfig;
import com.ibsplc.neoicargo.devops.snow.notification.AlertManagerNotificationListener;
import com.ibsplc.neoicargo.devops.snow.notification.model.Alert;
import com.ibsplc.neoicargo.devops.snow.notification.model.AlertManagerNotification;
import com.ibsplc.neoicargo.devops.snow.notification.utils.AWSRuntimeMetadata;
import com.ibsplc.neoicargo.devops.snow.web.model.SNOWEvent;
import com.ibsplc.neoicargo.devops.snow.web.model.SNOWRecord;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */
@Component
@Slf4j
public class SNOWSenderListener implements AlertManagerNotificationListener {




    @Autowired
    SNOWConfig snowConfig;
    @Autowired
    SNOWMappingConfig snowMappingConfig;
    @Autowired
    SNOWClient snowClient;

    @Autowired
    AWSRuntimeMetadata awsRuntimeMetadata;


    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onFiring(AlertManagerNotification alertManagerNotification) {
        if(snowConfig.shouldNotify(alertManagerNotification)) {
            var record = toSNOWRecord(alertManagerNotification);
            snowClient.createEvent(record);
        }else{
            log.info("Ignoring alert {}",alertManagerNotification);
        }
    }

    @Override
    public void onResolved(AlertManagerNotification alertManagerNotification) {

    }

    @SneakyThrows
    public SNOWRecord toSNOWRecord(AlertManagerNotification alertManagerNotification) {
        var snowEvent = new SNOWEvent();
        snowEvent.setSource(snowConfig.getSourceName());
        snowEvent.setResource(alertManagerNotification.getResourceName());
        snowEvent.setNode(awsRuntimeMetadata.getInstanceIp());
        //alertname as metricname
        var metricName = alertManagerNotification.getAlertName();
        var type = snowMappingConfig.snowTypeMapping(metricName);
        snowEvent.setMetric_name(metricName);
        snowEvent.setType(type);
        snowEvent.setSeverity(severity(alertManagerNotification.getPrometheusSeverityLevel()));
        snowEvent.setDescription(alertManagerNotification.getDescription());
        snowEvent.addAnAdditionalInfo("aws a/c", awsRuntimeMetadata.getAccountId());
        snowEvent.addAnAdditionalInfo("aws region", awsRuntimeMetadata.getRegion());
        snowEvent.addAnAdditionalInfo("k8s namespace", alertManagerNotification.getNamespace());
        snowEvent.addAnAdditionalInfo("alerts", alertManagerNotification);

        snowEvent.buildAdditionalInfo(objectMapper);
        return new SNOWRecord(snowEvent);
    }





    static int severity(String promLevel) {
        int snowSev = SNOWEvent.SnowAlterManagerSeverities.info.getSnowLevel();
        log.debug("Mapping prometheus alert level {}", promLevel);
        var snowSeveiry = SNOWEvent.SnowAlterManagerSeverities.toSnowAlterManagerSeverities(promLevel);
        log.debug("Mapping prometheus alert level {} to snowSeverity {}", promLevel, snowSeveiry);
        if (snowSeveiry.isPresent()) {
            snowSev = snowSeveiry.get().getSnowLevel();
        }

        return snowSev;
    }





}
