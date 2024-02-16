package com.ibsplc.neoicargo.devops.snow.web;

import com.ibsplc.neoicargo.devops.snow.notification.NotificationBroker;
import com.ibsplc.neoicargo.devops.snow.notification.model.AlertManagerNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */

@Component
@Path("/alertmanager")
@Slf4j
public class PromAlterManagerReceiver {

    @Autowired
    NotificationBroker broker;

    @Path("/receive")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recieve(AlertManagerNotification alertManagerNotification){
        log.info("Received {} alert for target {} and namespace {}:: \n {}",alertManagerNotification.getPrometheusSeverityLevel(),
                alertManagerNotification.getTarget(),alertManagerNotification.getNamespace(),alertManagerNotification);
        broker.notifyListeners(alertManagerNotification);
        return Response.ok().build();
    }


}
