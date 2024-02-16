package com.ibsplc.neoicargo.devops.snow.notification;

import com.ibsplc.neoicargo.devops.snow.notification.model.AlertManagerNotification;

/**
 *
 * @author Binu K <binu.kurian@ibsplc.com>
 */
 /*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */
public interface AlertManagerNotificationListener {

    void onFiring(AlertManagerNotification alertManagerNotification);
    void onResolved(AlertManagerNotification alertManagerNotification);
}
