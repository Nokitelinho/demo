/*
 * TxProbeKafkaAgent.java Created on 30/11/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.kafka;

import com.ibsplc.neoicargo.framework.probe.TxProbeFacade;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jens
 */
public class TxProbeKafkaAgent {

    static final Logger logger = LoggerFactory.getLogger(TxProbeKafkaAgent.class);

    private final TxProbeFacade facade;
    private final String source;

    public TxProbeKafkaAgent(TxProbeFacade facade, String source) {
        this.facade = facade;
        this.source = source;
    }

    public void onReceive(ConsumerRecord<?, ?> record) {
        logger.debug("onReceive {} - {}", this.source, record.key());
        KafkaProbePayload payload = new KafkaProbePayload(record.topic());
        payload.setIncoming(true);
        facade.doEagerProbe(payload, record);
    }

    public void onSend(ProducerRecord<?, ?> record) {
        logger.debug("onSend {} - {}", this.source, record.key());
        KafkaProbePayload payload = new KafkaProbePayload(record.topic());
        facade.doEagerProbe(payload, record);
    }

    public void onSendError(ProducerRecord record, Exception e) {
        KafkaProbePayload payload = new KafkaProbePayload(record.topic());
        facade.doEagerProbe(payload, record, e);
    }


}
