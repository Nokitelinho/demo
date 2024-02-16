package com.ibsplc.neoicargo.mail.events;

import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.mail.model.ContainerModel;
import com.ibsplc.neoicargo.mail.model.MailbagModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class MailbagReassignResditEvent implements DomainEvent, Serializable {
    private Collection<MailbagModel> transferredMails;
    private ContainerModel container;
}
