/*
 * CamelProbePayloadEnableEvaluator.java Created on 01/12/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.camel;

import com.ibsplc.neoicargo.framework.probe.ProbePayload;
import com.ibsplc.neoicargo.framework.probe.TxProbeConfig;
import com.ibsplc.neoicargo.framework.probe.TxProbeEnabledEvaluator;
import com.ibsplc.neoicargo.framework.probe.TxProbeUtils;
import org.apache.camel.Exchange;
import org.apache.camel.NamedNode;

/**
 * @author jens
 */
public class CamelProbePayloadEnableEvaluator implements TxProbeEnabledEvaluator {

    @Override
    public boolean isProbeEnabled(TxProbeConfig config, ProbePayload probePayload, Object... probeData) {
        Exchange exchange = (Exchange) probeData[0];
        if(config.getCamelProbeConfig().isRouteDisabled(exchange.getFromRouteId()))
            return false;
        return true;
    }
}
