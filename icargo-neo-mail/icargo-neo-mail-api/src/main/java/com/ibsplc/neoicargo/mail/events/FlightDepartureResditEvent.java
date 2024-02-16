package com.ibsplc.neoicargo.mail.events;

import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.mail.model.ContainerDetailsModel;
import com.ibsplc.neoicargo.mail.model.MailbagModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class FlightDepartureResditEvent implements DomainEvent, Serializable {
    private String companyCode;
    private int carrierId;
    private Collection<MailbagModel> mailBags;
    private Collection<ContainerDetailsModel> containerDetails;
    private String pol;


}