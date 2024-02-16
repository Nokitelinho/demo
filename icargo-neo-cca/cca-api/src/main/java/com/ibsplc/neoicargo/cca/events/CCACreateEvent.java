package com.ibsplc.neoicargo.cca.events;

import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;

public class CCACreateEvent extends CCAMasterData implements DomainEvent {
    private static final long serialVersionUID = -1916316662041107672L;
}
