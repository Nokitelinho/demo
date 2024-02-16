package com.ibsplc.neoicargo.cca.events;

import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;

public class CCADeleteEvent extends CCAMasterData implements DomainEvent {
    private static final long serialVersionUID = -2455835296993404571L;
}
