/*
 * KafkaProbeEnableEvaluator.java Created on 08/12/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.kafka;

import com.ibsplc.neoicargo.framework.probe.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

import static com.ibsplc.neoicargo.framework.probe.TxProbeUtils.TXPROBE_CORRELATION_HEADER;
import static com.ibsplc.neoicargo.framework.probe.kafka.KafkaTracingUtils.correlationId;

/**
 * @author jens
 */
public class KafkaProbeEnableEvaluator implements TxProbeEnabledEvaluator {

    @Override
    public boolean isProbeEnabled(TxProbeConfig config, ProbePayload probePayload, Object... probeData) {
        String topic;
        if (probeData[0] instanceof ProducerRecord) {
            ProducerRecord<?, ?> producerRecord = (ProducerRecord<?, ?>) probeData[0];
            String correlationId = correlationId(producerRecord.headers());
            correlationId = correlationId == null ? TxProbeContext.threadContext(TXPROBE_CORRELATION_HEADER) : correlationId;
            if (correlationId == null)
                return false;
            probePayload.setCorrelationId(correlationId);
            topic = producerRecord.topic();
        } else {
            ConsumerRecord<?, ?> consumerRecord = (ConsumerRecord<?, ?>) probeData[0];
            String correlationId = correlationId(consumerRecord.headers());
            if (correlationId == null) {
                correlationId = TxProbeFacade.generateCorrelationId();
                consumerRecord.headers().add(TXPROBE_CORRELATION_HEADER, correlationId.getBytes());
            }
            probePayload.setCorrelationId(correlationId);
            topic = consumerRecord.topic();
        }
        boolean topicDisabled = config.getKafkaProbeConfig().isTopicDisabled(topic);
        return !topicDisabled;
    }

}
