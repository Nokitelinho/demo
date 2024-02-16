package com.ibsplc.neoicargo.devops.snow.notification;

import com.ibsplc.neoicargo.devops.snow.notification.model.AlertManagerNotification;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Marker
 *
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			May 25, 2021 	  Binu K			First draft
 */

@Getter
@Setter
public abstract class NotifyConfiguration {
    protected boolean enabled;
    protected List<String> alertsToNotify = new ArrayList<>();


    public boolean shouldNotify(AlertManagerNotification alertManagerNotification) {
        boolean notify = false;
        if(enabled) {
            var alertName = alertManagerNotification.getAlertName();
            if (StringUtils.hasText(alertName)) {
                notify = CollectionUtils.isEmpty(alertsToNotify) || alertsToNotify.stream().anyMatch(alertName::contains);
            }
        }
        return notify;
    }


}
