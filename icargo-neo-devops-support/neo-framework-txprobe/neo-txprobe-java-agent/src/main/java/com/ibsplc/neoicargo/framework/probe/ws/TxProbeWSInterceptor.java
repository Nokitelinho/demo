/*
 * TxProbeWSInterceptor.java Created on 08-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.ws;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.ibsplc.neoicargo.framework.core.lang.notation.apis.EnableApiLogging;
import com.ibsplc.neoicargo.framework.probe.*;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			08-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public abstract class TxProbeWSInterceptor extends WSMessageInterceptor {

    static final Logger logger = LoggerFactory.getLogger(TxProbeWSInterceptor.class);

    static AtomicLong requestCounter = new AtomicLong(0L);

    protected enum KEY {
        CLEAR_ON_EXIT, PAYLOAD, IS_FAULT, PROBED
    }

    static final String PROBE_CTX_KEY = TxProbeWSInterceptor.class.getName() + ".PROBE_CTX_KEY";

    // instance variables
    private Probe probe = Probe.WEBSERVICE_HTTP;
    private String correlationIdHeader;
    private boolean generateIfAbsent = true;
    protected final TxProbeFacade facade;
    private boolean useMdc;

    public TxProbeWSInterceptor(TxProbeFacade facade, String phase) {
        super(phase);
        this.facade = facade;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.services.jaxws.interceptors.WSMessageInterceptor#processMessage(java.lang.String, java.lang.String)
     */
    @Override
    protected void processMessage(String message, String direction) {
        // NOOP
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.services.jaxws.interceptors.WSMessageInterceptor#processFault(java.lang.String, java.lang.String)
     */
    @Override
    protected void processFault(String message, String direction) {
        // NOOP
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.services.jaxws.interceptors.WSMessageInterceptor#processFault(java.lang.String, java.lang.String)
     */
    @Override
    protected void processFault(Message cxfMessage, String message, String direction) {
        probeProcess(cxfMessage, message, false);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.services.jaxws.interceptors.WSMessageInterceptor#processMessage(java.lang.String, java.lang.String)
     */
    @Override
    protected void processMessage(Message cxfMessage, String message, String direction) {
        probeProcess(cxfMessage, message, true);
    }

    protected void probeProcess(Message cxfMessage, String message, boolean isSuccess) {
        Map<KEY, Object> probeCtx = probeContext(cxfMessage);
        WebServiceProbePayload payload = (WebServiceProbePayload) probeCtx.get(KEY.PAYLOAD);
        Boolean isFault = (Boolean) probeCtx.get(KEY.IS_FAULT);
        boolean enableApiLogging = Boolean.TRUE.equals(cxfMessage.getContextualProperty(EnableApiLogging.CTX_KEY));
        if (Boolean.TRUE.equals(isFault))
            isSuccess = false;
        //
        if (payload == null) {
            logger.warn("Probe Context is null");
            return;
        }
        payload.setSuccess(isSuccess);
        payload.setBody(message);
        payload.setEnableApiLogging(enableApiLogging);
        // do the dispatch
        if (payload.getProbeState() == ProbedState.BEFORE || payload.getProbeState() == ProbedState.ON) {
            boolean probed = facade.doProbe(payload, TxProbeUtils.EMPTY_ARRAY);
            if (!probed) {
                probeCtx.clear();
            } else {
                TxProbeContext.threadContextPut(TxProbeUtils.TXPROBE_CORRELATION_HEADER, payload.getCorrelationId());
            }
        } else
            facade.doProbeForced(payload, TxProbeUtils.EMPTY_ARRAY);
        // clear the context
        if (Boolean.TRUE.equals(probeCtx.get(KEY.CLEAR_ON_EXIT)))
            probeCtx.clear();
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.services.jaxws.interceptors.WSMessageInterceptor#handleMessage(org.apache.cxf.message.Message)
     */
    @Override
    public void handleMessage(Message message) throws Fault {
        // check if probe is enabled
        if (!facade.isProbeEnabled(getProbe()))
            return;
        ProbedState state = resolveProbeState(message);
        WebServiceProbePayload payload = new WebServiceProbePayload();
        payload.setProbe(getProbe());
        payload.setProbeState(state);
        payload.setIncoming(!Boolean.TRUE.equals(message.get(Message.REQUESTOR_ROLE)));
        populateWSPayload(payload, message);
        Map<KEY, Object> probeCtx = probeContext(message);
        String correlationId = null;
        if (state == ProbedState.AFTER) {
            WebServiceProbePayload before = (WebServiceProbePayload) probeCtx.get(KEY.PAYLOAD);
            // indicates disabled
            if (before == null) {
                probeCtx.clear();
                return;
            }
            correlationId = before.getCorrelationId();
            payload.setCorrelationId(before.getCorrelationId());
            payload.setUser(before.getUser());
            payload.setSoapAction(before.getSoapAction());
            payload.setUrl(before.getUrl());
            payload.setInvocationId(before.getInvocationId());
        } else {
            payload.setCorrelationId(resolveCorrelationId(message));
            // disable if absent
            if (payload.getCorrelationId() == null) {
                return;
            }
            correlationId = payload.getCorrelationId();
            message.getExchange().put(TxProbeUtils.TXPROBE_CORRELATION_HEADER, payload.getCorrelationId());
            payload.setInvocationId(requestCounter.incrementAndGet());
        }
        injectCorrelationIdToHeader(correlationId, message);
        boolean isLastCall = isLastCall(message);
        probeCtx.put(KEY.CLEAR_ON_EXIT, isLastCall);
        probeCtx.put(KEY.PAYLOAD, payload);
        super.handleMessage(message);
    }

    @SuppressWarnings("unchecked")
    protected String resolveCorrelationId(Message message) {
        boolean isClient = Boolean.TRUE.equals(message.get(Message.REQUESTOR_ROLE));
        if(isUseMdc()){
            String correlationId = MDC.get("correlationId");
            if(correlationId != null)
                return correlationId;
        }
        if (!isClient && getCorrelationIdHeader() != null) {
            Object protoHeaders = message.get(Message.PROTOCOL_HEADERS);
            if (protoHeaders instanceof Map) {
                Map<String, Object> protoMap = Map.class.cast(protoHeaders);
                Object corrleationId = protoMap.get(getCorrelationIdHeader());
                if (corrleationId == null) {
                    corrleationId = message.get(getCorrelationIdHeader());
                    if (corrleationId == null) {
                        if (isGenerateIfAbsent())
                            return facade.generateCorrelationId();
                        else
                            return null;
                    } else
                        return corrleationId.toString();
                }
                if (corrleationId instanceof Collection) {
                    return (String) Collection.class.cast(corrleationId).iterator().next();
                } else
                    return (String) corrleationId;
            }
        } else {
            if (isClient)
                return resolveCorrelationIdFromContext();
            return facade.generateCorrelationId();
        }
        return null;
    }

    private String resolveCorrelationIdFromContext() {
        return TxProbeContext.threadContext(TxProbeUtils.TXPROBE_CORRELATION_HEADER);
    }

    @SuppressWarnings("unchecked")
    static void injectCorrelationIdToHeader(String correlationId, Message message) {
        Object protoHeaders = message.get(Message.PROTOCOL_HEADERS);
        if (protoHeaders instanceof Map) {
            Map<String, Object> protoMap = Map.class.cast(protoHeaders);
            protoMap.put(TxProbeUtils.TXPROBE_CORRELATION_HEADER, Collections.singletonList(correlationId));
        } else {
            Map<String, Object> protoMap = new TreeMap<>();
            protoMap.put(TxProbeUtils.TXPROBE_CORRELATION_HEADER, Collections.singletonList(correlationId));
            message.put(Message.PROTOCOL_HEADERS, protoMap);
        }
    }

    @SuppressWarnings("unchecked")
    protected void populateWSPayload(WebServiceProbePayload payload, Message message) {
        Map<String, Object> headers = new HashMap<>();
        if (!Boolean.TRUE.equals(message.get(Message.DECOUPLED_CHANNEL_MESSAGE))) {
            Integer responseCode = (Integer) message.get(Message.RESPONSE_CODE);
            Throwable error = null;
            if (responseCode == null) {
                Exchange exchange = message.getExchange();
                error = exchange.get(Throwable.class);
                // if an exception mapper is configured
                Response response = (Response) exchange.get(Response.class);
                if (response != null)
                    responseCode = response.getStatus();
            }
            if (responseCode == null) {
                List messageContentsList = message.getContent(List.class);
                if(messageContentsList != null) {
                    for (Object x : messageContentsList) {
                        if (x instanceof Response) {
                            responseCode = ((Response) x).getStatus();
                            break;
                        }
                    }
                }
            }
            if (responseCode != null) {
                headers.put("RESPONSE_CODE", responseCode);
                if (responseCode != 200)
                    probeContext(message).put(KEY.IS_FAULT, Boolean.TRUE);
            }
            if (error != null) {
                payload.setError(TxProbeUtils.renderException(error));
                message.getExchange().put(TxProbeWSFaultListener.FAULT_LOGGED, "true");
            }
        }

        String encoding = (String) message.get(Message.ENCODING);
        if (encoding != null)
            headers.put("ENCODING", encoding);

        String httpMethod = (String) message.get(Message.HTTP_REQUEST_METHOD);
        if (httpMethod != null)
            headers.put("HTTP_METHOD", httpMethod);

        String ct = (String) message.get("Content-Type");
        if (ct != null)
            headers.put("Content-Type", ct);

        Object protoHeaders = message.get(Message.PROTOCOL_HEADERS);
        if (protoHeaders instanceof Map) {
            Map<String, Object> protoMap = Map.class.cast(protoHeaders);
            payload.setSoapAction(resolveSoapAction(protoMap));
            headers.putAll(protoMap);
        }
        if (this.probe == Probe.WEBSERVICE_HTTP) {
            String uri = (String) message.get("org.apache.cxf.request.url");
            if (uri == null) {
                String address = (String) message.get(Message.ENDPOINT_ADDRESS);
                uri = (String) message.get("org.apache.cxf.request.uri");
                if (uri != null && uri.startsWith("/")) {
                    if (address != null && !address.startsWith(uri)) {
                        if (address.endsWith("/") && address.length() > 1) {
                            address = address.substring(0, address.length());
                        }
                        uri = address + uri;
                    }
                } else
                    uri = address;
            }

            if (uri != null) {
                StringBuilder address = new StringBuilder(uri);
                String query = (String) message.get(Message.QUERY_STRING);
                if (query != null)
                    address.append('?').append(query);
                payload.setUrl(address.toString());
            }
        } else {
            Object jmsRequestUri = message.get("SOAPJMS_requestURI");
            if (jmsRequestUri != null) {
                if (jmsRequestUri instanceof Collection<?> && !Collection.class.cast(jmsRequestUri).isEmpty())
                    payload.setUrl((String) Collection.class.cast(jmsRequestUri).iterator().next());
                else
                    payload.setUrl(jmsRequestUri.toString());
            }
        }
        // use render map
        payload.setHeaders(headers.toString());
    }

    protected String resolveSoapAction(Map<String, Object> protoMap) {
        Object soapAction = protoMap.get("SOAPAction");
        if (soapAction == null)
            soapAction = protoMap.get("SOAPJMS_soapAction");
        if (soapAction == null) {
            for (Map.Entry<String, Object> e : protoMap.entrySet()) {
                // tibco ws jms workaround
                if ("SoapAction".equals(e.getKey()) || "SOAPACTION".equalsIgnoreCase(e.getKey())) {
                    soapAction = e.getValue();
                    break;
                }
            }
        }
        if (soapAction != null) {
            if (soapAction instanceof String)
                return (String) soapAction;
            if (soapAction instanceof Collection) {
                Collection<?> soapActionColl = (Collection<?>) soapAction;
                return soapActionColl.isEmpty() ? null : soapActionColl.iterator().next().toString();
            }
            return soapAction.toString();
        }
        return null;
    }

    protected ProbedState resolveProbeState(Message message) {
        if (message.getExchange().isOneWay())
            return ProbedState.ON;
        boolean isInbound = Boolean.TRUE.equals(message.get(Message.INBOUND_MESSAGE));
        boolean isClient = Boolean.TRUE.equals(message.get(Message.REQUESTOR_ROLE));
        if (isClient) {
            if (isInbound)
                return ProbedState.AFTER;
            else
                return ProbedState.BEFORE;
        } else {
            if (isInbound)
                return ProbedState.BEFORE;
            else
                return ProbedState.AFTER;
        }
    }

    protected boolean isLastCall(Message message) {
        if (message.getExchange().isOneWay())
            return true;
        boolean isClient = Boolean.TRUE.equals(message.get(Message.REQUESTOR_ROLE));
        boolean isInbound = Boolean.TRUE.equals(message.get(Message.INBOUND_MESSAGE));
        return isClient == isInbound;
    }

    @SuppressWarnings("unchecked")
    static EnumMap<KEY, Object> probeContext(Message message) {
        EnumMap<KEY, Object> ctx = (EnumMap<KEY, Object>) message.getExchange().get(PROBE_CTX_KEY);
        if (ctx == null) {
            ctx = new EnumMap<>(KEY.class);
            message.getExchange().put(PROBE_CTX_KEY, ctx);
        }
        return ctx;
    }

    @Override
    protected String wsInterceptorContextKey() {
        return "TxProbeWSInterceptor.WSInterceptorContext.key";
    }

    /**
     * @return the probe
     */
    public Probe getProbe() {
        return probe;
    }

    /**
     * @param probe the probe to set
     */
    public void setProbe(Probe probe) {
        this.probe = probe;
    }

    /**
     * @return the correlationIdHeader
     */
    public String getCorrelationIdHeader() {
        return correlationIdHeader;
    }

    /**
     * @param correlationIdHeader the correlationIdHeader to set
     */
    public void setCorrelationIdHeader(String correlationIdHeader) {
        this.correlationIdHeader = correlationIdHeader;
    }

    /**
     * @return the generateIfAbsent
     */
    public boolean isGenerateIfAbsent() {
        return generateIfAbsent;
    }

    /**
     * @param generateIfAbsent the generateIfAbsent to set
     */
    public void setGenerateIfAbsent(boolean generateIfAbsent) {
        this.generateIfAbsent = generateIfAbsent;
    }

    public boolean isUseMdc() {
        return useMdc;
    }

    public void setUseMdc(boolean useMdc) {
        this.useMdc = useMdc;
    }
}
