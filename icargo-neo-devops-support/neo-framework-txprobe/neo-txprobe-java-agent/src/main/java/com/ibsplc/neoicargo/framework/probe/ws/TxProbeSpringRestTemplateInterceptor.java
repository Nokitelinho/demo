/*
 * TxProbeSpringRestTemplateInterceptor.java Created on 23/02/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.ws;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.ibsplc.neoicargo.framework.probe.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author jens
 */
public class TxProbeSpringRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final TxProbeFacade facade;
    private final boolean enableApiLogging;

    public TxProbeSpringRestTemplateInterceptor(TxProbeFacade facade, boolean enableApiLogging) {
        this.facade = facade;
        this.enableApiLogging = enableApiLogging;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        WebServiceProbePayload before = before(request, body);
        before.setEnableApiLogging(this.enableApiLogging);
        boolean probed = false;
        if (before.getCorrelationId() != null) {
            request.getHeaders().add(TxProbeUtils.TXPROBE_CORRELATION_HEADER, before.getCorrelationId());
            probed = this.facade.doProbe(before);
        }
        Throwable error = null;
        ClientHttpResponse answer = null;
        try {
            answer = execution.execute(request, body);
        } catch (Throwable t) {
            error = t;
            if (t instanceof IOException)
                throw (IOException) t;
            if (t instanceof RuntimeException)
                throw (RuntimeException) t;
            throw new RuntimeException(t);
        } finally {
            if (probed)
                this.facade.doProbeForced(after(before, answer, error));
        }
        return answer;
    }

    static WebServiceProbePayload after(WebServiceProbePayload before, ClientHttpResponse response, Throwable error)
            throws IOException {
        WebServiceProbePayload after = new WebServiceProbePayload();
        after.setInvocationId(before.getInvocationId());
        after.setProbe(Probe.WEBSERVICE_HTTP);
        after.setProbeState(ProbedState.AFTER);
        after.setCorrelationId(before.getCorrelationId());
        after.setEnableApiLogging(before.isEnableApiLogging());
        if (error != null) {
            after.setSuccess(false);
            after.setError(TxProbeUtils.renderException(error));
        } else
            after.setSuccess(true);
        if (response != null) {
            after.setHeaders(TxProbeUtils.renderMap(response.getHeaders()));
            String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
            after.setBody(body);
        }
        return after;
    }

    static WebServiceProbePayload before(HttpRequest request, byte[] body) {
        WebServiceProbePayload payload = new WebServiceProbePayload();
        payload.setIncoming(false);
        payload.setProbe(Probe.WEBSERVICE_HTTP);
        payload.setUrl(request.getURI().toString());
        payload.setCorrelationId(TxProbeContext.threadContext(TxProbeUtils.TXPROBE_CORRELATION_HEADER));
        payload.setBody(new String(body, StandardCharsets.UTF_8));
        payload.setHeaders(TxProbeUtils.renderMap(request.getHeaders()));
        payload.setProbeState(ProbedState.BEFORE);
        payload.setInvocationId(TxProbeWSInterceptor.requestCounter.incrementAndGet());
        return payload;
    }
}
