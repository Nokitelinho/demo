package com.ibsplc.neoicargo.tracking.component.feature.emailnotification;

import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClient;
import com.ibsplc.neoicargo.notif.NotificationWebAPI;
import com.ibsplc.neoicargo.notif.model.EmailRequestData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@RegisterJAXRSClient(clazz = NotificationWebAPI.class, targetService = "neo-msgbroker-eai-notif-base-rt")
public class EmailClient {

    private final NotificationWebAPI notificationWebAPI;

    public void sendEmail(EmailRequestData emailRequestData) {
        notificationWebAPI.sendEmail(emailRequestData);
    }

}
