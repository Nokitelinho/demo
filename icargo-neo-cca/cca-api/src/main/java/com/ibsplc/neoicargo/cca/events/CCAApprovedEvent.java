package com.ibsplc.neoicargo.cca.events;

import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;

/**
 * This event uses for AutoCCA, since AutoCCA approved automatically
 */
public class CCAApprovedEvent extends CCAMasterData implements DomainEvent {
    private static final long serialVersionUID = -2489013074298135526L;
}
