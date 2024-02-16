package com.ibsplc.neoicargo.mail.events;

import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.mail.model.ContainerDetailsModel;
import com.ibsplc.neoicargo.mail.model.MailbagModel;
import com.ibsplc.neoicargo.mail.model.OperationalFlightModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
@Getter
@Setter
public class ContainerReassignResditEvent implements DomainEvent, Serializable {
    Collection<MailbagModel> mailbags;
    Collection<ContainerDetailsModel> containerDetails;
    OperationalFlightModel toFlightVO;
    boolean hasFlightDeparted;
}
