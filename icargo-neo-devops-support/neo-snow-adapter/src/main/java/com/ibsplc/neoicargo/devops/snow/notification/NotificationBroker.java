package com.ibsplc.neoicargo.devops.snow.notification;

import com.ibsplc.neoicargo.devops.snow.notification.AlertManagerNotificationListener;
import com.ibsplc.neoicargo.devops.snow.notification.model.AlertManagerNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
public class NotificationBroker {
    @Autowired(required = false)
    List<AlertManagerNotificationListener> listeners;

    @Async
    public void notifyListeners(AlertManagerNotification alertManagerNotification){
        if(!CollectionUtils.isEmpty(listeners)){

            listeners.forEach(listener->{
                try {
                    switch (alertManagerNotification.getStatus()) {
                        case resolved:
                            listener.onResolved(alertManagerNotification);
                            break;
                        case firing:
                        default:
                            listener.onFiring(alertManagerNotification);
                    }
                }catch (Exception e){
                    log.error("Failed notifying listener {}",listener,e);
                }
            });
        }
    }
}
