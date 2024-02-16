/*
 * KafkaProbePayloadMarshaller.java Created on 07/12/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.kafka;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.probe.ProbePayload;
import com.ibsplc.neoicargo.framework.probe.ProbePayloadMarshaller;
import com.ibsplc.neoicargo.framework.probe.TxProbeConfig;
import com.ibsplc.neoicargo.framework.probe.TxProbeUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jens
 */
public class KafkaProbePayloadMarshaller implements ProbePayloadMarshaller {

    static final Logger logger = LoggerFactory.getLogger(KafkaProbePayloadMarshaller.class);

    @Override
    public ProbePayload marshall(TxProbeConfig config, ProbePayload load, Object... probeData) {
        KafkaProbePayload payload = (KafkaProbePayload) load;
        if (probeData[0] instanceof ProducerRecord) {
            ProducerRecord<?, ?> producerRecord = (ProducerRecord<?, ?>) probeData[0];
            populateKafkaPayload(payload, producerRecord.key(), producerRecord.value(), producerRecord.headers());
            if (probeData.length > 1 && probeData[1] instanceof Exception) {
                Exception exception = (Exception) probeData[1];
                payload.setError(TxProbeUtils.renderException(exception));
                payload.setSuccess(false);
            } else {
                ContextUtil contextUtil = config.getApplicationContext().getBean(ContextUtil.class);
                payload.setUser(contextUtil.callerLoginProfile().getUserId());
            }
        } else if (probeData[0] instanceof ConsumerRecord) {
            ConsumerRecord<?, ?> consumerRecord = (ConsumerRecord<?, ?>) probeData[0];
            populateKafkaPayload(payload, consumerRecord.key(), consumerRecord.value(), consumerRecord.headers());
        }
        return payload;
    }

    static void populateKafkaPayload(KafkaProbePayload payload, Object key, Object body, Headers headers) {
        payload.setSuccess(true);
        if (key != null)
            payload.setInvocationId(key.toString());
        else
            payload.setInvocationId("-");
        if (body != null) {
            if (body instanceof byte[])
                payload.setBody(new String((byte[]) body));
            else {
                try {
                    payload.setBody(body.toString());
                } catch (Exception e) {
                    logger.warn("Error while converting body to string {} {}", e.getClass(), e.getMessage());
                    payload.setBody(body.getClass().toString());
                }
            }
        } else
            payload.setBody("-");
        Map<String, String> headerMap = new HashMap<>(8);
        headers.iterator().forEachRemaining(h -> headerMap.put(h.key(), new String(h.value().clone())));
        payload.setHeaders(TxProbeUtils.renderMap(headerMap));
    }
}
