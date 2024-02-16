/*
 * TxProbingHttpFilter.java Created on 30-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.http;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.neoicargo.framework.probe.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import static com.ibsplc.neoicargo.framework.probe.TxProbeUtils.TXPROBE_CORRELATION_HEADER;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			30-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class TxProbeHttpFilter implements Filter, TxProbeFacadeAware {

    private static AtomicLong requestCounter = new AtomicLong(0L);
    private TxProbeFacade facade;

    @Override
    public void setTxProbeFacade(TxProbeFacade facade) {
        this.facade = facade;
    }

    public TxProbeHttpFilter(TxProbeFacade facade){
        this.facade = facade;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        if (this.facade == null)
            return;
        ProbePayload payload = ProbePayload.before(Probe.HTTP, requestCounter.incrementAndGet());
        Throwable error = null;
        final Thread currentThread = Thread.currentThread();
        final String threadName = currentThread.getName();
        final boolean probed = facade.doEagerProbe(payload, request, response, error);
        if (probed) {
            TxProbeContext.threadContextPut(TXPROBE_CORRELATION_HEADER, payload.getCorrelationId());
            String threadNameAltered = new StringBuilder(threadName.length() + payload.getCorrelationId().length() + 2)
                    .append(threadName).append('[').append(payload.getCorrelationId()).append(']').toString();
            currentThread.setName(threadNameAltered);
        }
        try {
            fc.doFilter(request, response);
        } catch (IOException | ServletException | RuntimeException e) {
            error = e;
            throw e;
        } finally {
            if (probed) {
                currentThread.setName(threadName);
                facade.doEagerProbeForced(ProbePayload.after(payload), request, response, error);
                if (response instanceof HttpServletResponse) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.addHeader(TXPROBE_CORRELATION_HEADER, payload.getCorrelationId());
                }
            }
        }
    }

}
