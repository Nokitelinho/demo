package com.ibsplc.neoicargo.mail.events;

import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.mail.model.MailbagModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
@Getter
@Setter
public class MailbagsFromReassignResditEvent implements DomainEvent, Serializable {
    private Collection<MailbagModel> mailBags;
    private String pol;
    private String eventCode;
}
