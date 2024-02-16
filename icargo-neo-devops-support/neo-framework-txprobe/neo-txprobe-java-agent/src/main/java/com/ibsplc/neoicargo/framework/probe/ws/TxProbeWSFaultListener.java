/*
 * TxProbeWSFaultListener.java Created on 21-Jun-2017
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.ws;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.ibsplc.neoicargo.framework.probe.TxProbeFacade;
import com.ibsplc.neoicargo.framework.probe.TxProbeUtils;
import com.ibsplc.neoicargo.framework.probe.ws.TxProbeWSInterceptor.KEY;
import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			21-Jun-2017       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class TxProbeWSFaultListener implements FaultListener {

    static final Logger logger = LoggerFactory.getLogger(TxProbeWSFaultListener.class);

    static final String FAULT_LOGGED = "txprobe.fault.logged";

    static final AtomicInteger counter = new AtomicInteger();

    private final TxProbeFacade facade;

    public TxProbeWSFaultListener(TxProbeFacade facade) {
        this.facade = facade;
    }

    /* (non-Javadoc)
     * @see org.apache.cxf.logging.FaultListener#faultOccurred(java.lang.Exception, java.lang.String, org.apache.cxf.message.Message)
     */
    @Override
    public boolean faultOccurred(Exception exception, String description, Message message) {
        if(logger.isWarnEnabled())
            logger.warn("Error invoking webservice endpoint " + description + " {}", exception);
        // check if enabled
        String correlationId = (String) message.getExchange().get(TxProbeUtils.TXPROBE_CORRELATION_HEADER);
        if (correlationId == null)
            return true;
        // check if fault was logged in the interceptor
        String faultLogged = (String) message.getExchange().get(FAULT_LOGGED);
        if(Boolean.parseBoolean(faultLogged))
            return true;

        String errorStack = TxProbeUtils.renderException(exception);
        WebServiceProbePayload payload = new WebServiceProbePayload();
        payload.setProbeState(ProbedState.ON);
        payload.setCorrelationId(correlationId);
        payload.setInvocationId(counter.incrementAndGet());
        payload.setBody(description == null ? "-" : description);
        payload.setSoapAction("FAULT_ENDPOINT_STACKTRACE");
        payload.setError(errorStack);
        payload.setSuccess(false);

        // try to get the context object if present
        @SuppressWarnings("unchecked")
        EnumMap<KEY, Object> probeCtx = (EnumMap<KEY, Object>) message.getExchange().get(TxProbeWSInterceptor.PROBE_CTX_KEY);

        if (probeCtx == null || probeCtx.isEmpty() || probeCtx.get(KEY.PAYLOAD) == null) {
            // defaulting
            payload.setUrl("STACKTRACE");
            payload.setUser("WSUSER");
            payload.setProbe(Probe.WEBSERVICE_HTTP);
        } else {
            WebServiceProbePayload before = (WebServiceProbePayload) probeCtx.get(KEY.PAYLOAD);
            payload.setUrl(before.getUrl());
            payload.setUser(before.getUser());
            payload.setProbe(before.getProbe());
        }

        this.facade.doProbe(payload, TxProbeUtils.EMPTY_ARRAY);
        return true;
    }

}
