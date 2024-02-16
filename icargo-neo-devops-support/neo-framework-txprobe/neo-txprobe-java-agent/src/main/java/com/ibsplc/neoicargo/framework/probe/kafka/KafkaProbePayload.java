/*
 * KafkaProbePayload.java Created on 07/12/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.kafka;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.ibsplc.neoicargo.framework.probe.ProbePayload;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author jens
 */
public class KafkaProbePayload extends ProbePayload {

    private String topic;
    @Setter
    private boolean incoming;

    /**
     *
     * @param topic
     */
    public KafkaProbePayload(String topic) {
        this.topic = topic;
        this.setProbeState(ProbedState.ON);
        this.setProbe(Probe.KAFKA);
    }

    @Override
    public int fieldCount() {
        return super.fieldCount() + 2;
    }

    @Override
    public void writeTo(Serializable[][] dupletMap) {
        super.writeTo(dupletMap);
        int x = super.fieldCount() - 1;
        dupletMap[++x][0] = "topic";
        dupletMap[x][1] = this.topic;
        dupletMap[++x][0] = "incoming";
        dupletMap[x][1] = this.incoming;
    }

}
