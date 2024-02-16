package com.ibsplc.neoicargo.mail.events;

import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.mail.model.ContainerDetailsModel;
import com.ibsplc.neoicargo.mail.model.MailArrivalModel;
import com.ibsplc.neoicargo.mail.model.MailbagModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class ArrivalResditEvent implements DomainEvent, Serializable {
    MailArrivalModel mailArrivalDetails;
    Collection<MailbagModel> arrivedMailbags;
    Collection<ContainerDetailsModel> arrivedContainers;
}
