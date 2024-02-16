/*
 * TxProbeCamelTracer.java Created on 01/12/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.camel;

import com.ibsplc.neoicargo.framework.probe.TxProbeFacade;
import com.ibsplc.neoicargo.framework.probe.TxProbeUtils;
import org.apache.camel.Exchange;
import org.apache.camel.NamedNode;
import org.apache.camel.NamedRoute;
import org.apache.camel.Route;
import org.apache.camel.model.SendDefinition;
import org.apache.camel.model.ToDefinition;
import org.apache.camel.spi.ExchangeFormatter;
import org.apache.camel.spi.Tracer;
import org.apache.camel.support.service.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.regex.Pattern;

import static com.ibsplc.neoicargo.framework.probe.TxProbeUtils.TXPROBE_CORRELATION_HEADER;

/**
 * @author jens
 */
public class TxProbeCamelTracer extends ServiceSupport implements Tracer {

    static final Logger logger = LoggerFactory.getLogger(TxProbeCamelTracer.class);
    static final String ICARGO_INTF_SYSTEM = "icargo.txprobe.intfSys";

    private boolean isEnabled = true;
    private boolean isTraceBeforeAfterRoute = true;
    private String tracePattern = "%-4.4s [%-12.12s] [%-33.33s]";
    private ExchangeFormatter formatter = new TxProbeExchangeFormatter();
    private long traceCounter;

    private final TxProbeFacade facade;

    public TxProbeCamelTracer(TxProbeFacade facade) {
        this.facade = facade;
    }

    @Override
    public boolean shouldTrace(NamedNode definition) {
        return true;
    }

    @Override
    public void traceBeforeNode(NamedNode node, Exchange exchange) {
        injectTxProbeCorrelationId(exchange);
    }

    @Override
    public void traceAfterNode(NamedNode node, Exchange exchange) {
        String correlationId = injectTxProbeCorrelationId(exchange);
        if(node instanceof ToDefinition || exchange.getException() != null) {
            CamelProbePayload payload = new CamelProbePayload("node:" + node.getId(), isIncoming(exchange), correlationId);
            payload.setInterfaceSystem(resolveIntfSystem(null, exchange));
            facade.doEagerProbe(payload, exchange);
            traceCounter++;
        }
    }

    @Override
    public void traceBeforeRoute(NamedRoute route, Exchange exchange) {
        String correlationId = injectTxProbeCorrelationId(exchange);
        boolean original = route.getRouteId().equals(exchange.getFromRouteId());
        if(original) {
            CamelProbePayload payload = new CamelProbePayload("route:" + route.getRouteId(), isIncoming(exchange), correlationId);
            payload.setInterfaceSystem(resolveIntfSystem(route.getRouteId(), exchange));
            facade.doEagerProbe(payload, exchange);
            traceCounter++;
        }
    }

    @Override
    public void traceAfterRoute(Route route, Exchange exchange) {
        //NOOP
    }

    static String resolveIntfSystem(String routeId, Exchange exg) {
        if(routeId != null && routeId.contains(".")) {
            String[] splits = routeId.split(Pattern.quote("."));
            if(splits.length > 1) {
                String intfSystem = splits[splits.length - 1];
                exg.setProperty(ICARGO_INTF_SYSTEM, intfSystem);
                return intfSystem;
            }
        } else
            return exg.getProperty(ICARGO_INTF_SYSTEM, "NA", String.class);
        return "NA";
    }


    static boolean isIncoming(Exchange exchange) {
        return !(exchange.getFromEndpoint().getEndpointBaseUri().startsWith("kafka"));
    }

    static String injectTxProbeCorrelationId(Exchange exchange) {
        // check the correlation id in the properties else look in the headers
        String correlationId = exchange.getProperty(TXPROBE_CORRELATION_HEADER, exchange.getIn().getHeader(TXPROBE_CORRELATION_HEADER, String.class), String.class);
        // if not present (ie new exchange) then create it using the breadcrumb or the exchangeid
        correlationId = correlationId == null ? exchange.getIn().getHeader(Exchange.BREADCRUMB_ID, exchange.getExchangeId(), String.class) : correlationId;
        exchange.setProperty(TXPROBE_CORRELATION_HEADER, correlationId);
        exchange.getIn().setHeader(TXPROBE_CORRELATION_HEADER, correlationId);
        MDC.put("correlationId", correlationId);
        return correlationId;
    }

    @Override
    public long getTraceCounter() {
        return this.traceCounter;
    }

    @Override
    public void resetTraceCounter() {
        this.traceCounter = 0;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public String getTracePattern() {
        return this.tracePattern;
    }

    @Override
    public void setTracePattern(String tracePattern) {
        this.tracePattern = tracePattern;
    }

    @Override
    public boolean isTraceBeforeAndAfterRoute() {
        return this.isTraceBeforeAfterRoute;
    }

    @Override
    public void setTraceBeforeAndAfterRoute(boolean traceBeforeAndAfterRoute) {
        this.isTraceBeforeAfterRoute = traceBeforeAndAfterRoute;
    }

    @Override
    public ExchangeFormatter getExchangeFormatter() {
        return this.formatter;
    }

    @Override
    public void setExchangeFormatter(ExchangeFormatter exchangeFormatter) {
        this.formatter = exchangeFormatter;
    }
}
