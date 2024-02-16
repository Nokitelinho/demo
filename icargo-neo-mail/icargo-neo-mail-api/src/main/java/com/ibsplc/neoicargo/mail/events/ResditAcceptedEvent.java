package com.ibsplc.neoicargo.mail.events;

import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.mail.model.ContainerDetailsModel;
import com.ibsplc.neoicargo.mail.model.MailAcceptanceModel;
import com.ibsplc.neoicargo.mail.model.MailbagModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class ResditAcceptedEvent implements DomainEvent, Serializable {
    private MailAcceptanceModel  mailAcceptanceModel;
    private Collection<MailbagModel> acceptedMailbags;
    private Collection<ContainerDetailsModel> acceptedUlds;
    private boolean hasFlightDeparted;
}
