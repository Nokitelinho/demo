/*
 * CamelProbePayloadMarshaller.java Created on 01/12/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.camel;

import com.ibsplc.neoicargo.framework.probe.ProbePayload;
import com.ibsplc.neoicargo.framework.probe.ProbePayloadMarshaller;
import com.ibsplc.neoicargo.framework.probe.TxProbeConfig;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePropertyKey;
import org.apache.camel.Message;
import org.apache.camel.support.MessageHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static com.ibsplc.neoicargo.framework.probe.TxProbeUtils.renderException;
import static com.ibsplc.neoicargo.framework.probe.TxProbeUtils.renderMap;

/**
 * @author jens
 */
public class CamelProbePayloadMarshaller implements ProbePayloadMarshaller {

    @Override
    public ProbePayload marshall(TxProbeConfig config, ProbePayload payload, Object... probeData) {
        CamelProbePayload camelPayload = (CamelProbePayload) payload;
        Exchange exchange = (Exchange) probeData[0];
        Message in = exchange.getIn();
        CamelProbeConfig camelConfig = config.getCamelProbeConfig();
        // populate headers and properties
        if (camelConfig.isLogHeaders() && camelConfig.isLogProperties()) {
            Map<String, Object> all = new HashMap<>(2);
            all.put("Headers", in.getHeaders());
            all.put("ExchangeProperties", exchange.getProperties());
            camelPayload.setHeaders(renderMap(all));
        } else if (camelConfig.isLogProperties()) {
            camelPayload.setHeaders(renderMap(exchange.getProperties()));
        } else if (camelConfig.isLogHeaders()) {
            camelPayload.setHeaders(renderMap(in.getHeaders()));
        }

        if (camelConfig.isLogBody())
            camelPayload.setBody(bodyForTracing(in));
        Exception error = exchange.getException();
        if (error == null)
            error = exchange.getProperty(ExchangePropertyKey.EXCEPTION_CAUGHT, Exception.class);
        if (error != null)
            camelPayload.setError(renderException(error));
        camelPayload.setSuccess(camelPayload.getError() == null);
        return camelPayload;
    }

    static String bodyForTracing(Message message) {
        return message.getBody() instanceof Future ? message.getBody().toString() : MessageHelper.extractBodyForLogging(message, (String) null, true, true, 2500);
    }
}
