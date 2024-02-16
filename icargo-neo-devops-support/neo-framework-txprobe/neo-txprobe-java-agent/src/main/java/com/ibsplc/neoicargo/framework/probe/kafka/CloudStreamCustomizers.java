/*
 * CloudStreamCustomizers.java Created on 30/11/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.kafka;

import com.ibsplc.neoicargo.framework.probe.TxProbeFacade;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.cloud.stream.config.ProducerMessageHandlerCustomizer;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

/**
 * @author jens
 */
public class CloudStreamCustomizers {

    static final Logger logger = LoggerFactory.getLogger(CloudStreamCustomizers.class);

    public static class TxProbeProducerMessageHandlerCustomizer implements ProducerMessageHandlerCustomizer<KafkaProducerMessageHandler<?, ?>> {

        private final TxProbeFacade facade;

        public TxProbeProducerMessageHandlerCustomizer(TxProbeFacade facade) {
            this.facade = facade;
        }

        @Override
        @SneakyThrows
        public void configure(KafkaProducerMessageHandler<?, ?> handler, String destinationName) {
            KafkaTemplate<?, ?> kafkaTemplate = handler.getKafkaTemplate();
            TxProbeKafkaAgent agent = new TxProbeKafkaAgent(this.facade, "cloud_kafkaTemplate");
            KafkaTracingUtils.primeKafkaTemplate(kafkaTemplate, agent);
            logger.info("Kafka cloud stream producer probe support configured.");
        }
    }

    public static class TxProbeListenerContainerCustomizer implements ListenerContainerCustomizer<AbstractMessageListenerContainer<?, ?>> {

        private final TxProbeFacade facade;

        public TxProbeListenerContainerCustomizer(TxProbeFacade facade) {
            this.facade = facade;
        }

        @Override
        @SneakyThrows
        public void configure(AbstractMessageListenerContainer<?, ?> container, String destinationName, String group) {
            TxProbeKafkaAgent agent = new TxProbeKafkaAgent(this.facade, "cloud_listenerContainer");
            KafkaTracingUtils.primeListenerContainer(container, agent);
            logger.info("Kafka cloud stream consumer probe support configured.");
        }
    }

}
