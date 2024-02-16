/*
 * CamelProbePayload.java Created on 01/12/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.camel;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.ibsplc.neoicargo.framework.probe.ProbePayload;

import java.io.Serializable;

/**
 * @author jens
 */
public class CamelProbePayload extends ProbePayload {

    private boolean incoming;
    private String interfaceSystem;

    public CamelProbePayload(String routeId, boolean incoming, String correlationId) {
        this.setProbe(Probe.INTERFACE_MESSAGE);
        this.setProbeState(ProbedState.ON);
        this.setInvocationId(routeId);
        this.setCorrelationId(correlationId);
        this.incoming = incoming;
    }

    @Override
    public int fieldCount() {
        return super.fieldCount() + 2;
    }

    @Override
    public void writeTo(Serializable[][] dupletMap) {
        super.writeTo(dupletMap);
        int x = super.fieldCount() - 1;
        dupletMap[++x][0] = "incoming";
        dupletMap[x][1] = this.incoming;
        dupletMap[++x][0] = "interfaceSystem";
        dupletMap[x][1] = this.interfaceSystem;
    }

    public String getInterfaceSystem() {
        return interfaceSystem;
    }

    public void setInterfaceSystem(String interfaceSystem) {
        this.interfaceSystem = interfaceSystem;
    }
}
