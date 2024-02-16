package com.ibsplc.neoicargo.cca.events;

import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;

public class CCAUpdateEvent extends CCAMasterData implements DomainEvent {
    private static final long serialVersionUID = -6423891351911003278L;
}
