/*
 * TxProbeMDCCorrelationFilter.java Created on 31/12/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.mdc;

import com.ibsplc.neoicargo.framework.probe.TxProbeContext;
import com.ibsplc.neoicargo.framework.probe.TxProbeProcessor;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ibsplc.neoicargo.framework.probe.TxProbeUtils.TXPROBE_CORRELATION_HEADER;

/**
 * @author jens
 */
public class TxProbeMDCCorrelationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String correlationId = request.getHeader(TXPROBE_CORRELATION_HEADER);
        if(correlationId == null)
            correlationId = TxProbeProcessor.generateUniqueId();
        MDC.put("correlationId", correlationId);
        TxProbeContext.threadContextPut(TXPROBE_CORRELATION_HEADER, correlationId);
        try{
            filterChain.doFilter(request, response);
        }finally {
            if(!response.containsHeader(TXPROBE_CORRELATION_HEADER))
                response.addHeader(TXPROBE_CORRELATION_HEADER, correlationId);
            MDC.clear();
            TxProbeContext.threadContextClear();
        }
    }
}
