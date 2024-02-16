/*
 * TxProbeAutoWiringKafkaPostProcessors.java Created on 30/11/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.kafka;

import com.ibsplc.neoicargo.framework.probe.TxProbeFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

/**
 * @author jens
 */
public class TxProbeAutoWiringKafkaPostProcessors {

    static final Logger logger = LoggerFactory.getLogger(TxProbeAutoWiringKafkaPostProcessors.class);

    public static class SpringKafkaProcessor implements BeanPostProcessor {

        final TxProbeFacade facade;

        public SpringKafkaProcessor(TxProbeFacade facade) {
            this.facade = facade;
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof KafkaTemplate) {
                KafkaTracingUtils.primeKafkaTemplate((KafkaTemplate<?, ?>) bean, new TxProbeKafkaAgent(this.facade, beanName));
                logger.info("Kafka template probe support configured for bean {}", beanName);
            }
            if (bean instanceof AbstractMessageListenerContainer) {
                KafkaTracingUtils.primeListenerContainer((AbstractMessageListenerContainer) bean, new TxProbeKafkaAgent(this.facade, beanName));
                logger.info("Kafka listener container probe support configured for bean {}", beanName);
            }
            return bean;
        }

    }

}
