/*
 * TxProbeAutoWiringPostProcessor.java Created on 24-Apr-2018
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.ws;

import com.ibsplc.neoicargo.framework.core.lang.notation.apis.EnableApiLogging;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.logging.FaultListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			24-Apr-2018       		Jens J P 			First Draft
 */

/**
 * @author Jens J P
 */
public class TxProbeAutoWiringWsPostProcessor implements BeanPostProcessor {

    static final Logger logger = LoggerFactory.getLogger(TxProbeAutoWiringWsPostProcessor.class);

    private final TxProbeWSFeature featureAgent;
    private final ApplicationContext context;

    public TxProbeAutoWiringWsPostProcessor(TxProbeWSFeature featureAgent, ApplicationContext context) {
        this.featureAgent = featureAgent;
        this.context = context;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private void primeCxfBus(Bus bus) {
        bus.getFeatures().add(this.featureAgent);
        bus.setProperty(FaultListener.class.getName(), new TxProbeWSFaultListener(this.featureAgent.getFacade()));
        logger.info("WebService Probe Support configured for CxfBus.");
    }

    private void primeRestTemplate(RestTemplate template, boolean enableApiLogging){
        ClientHttpRequestFactory requestFactory = template.getRequestFactory();
        if(requestFactory instanceof SimpleClientHttpRequestFactory){
            SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = (SimpleClientHttpRequestFactory)requestFactory;
            simpleClientHttpRequestFactory.setOutputStreaming(false);
        }else {
            SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
            simpleClientHttpRequestFactory.setOutputStreaming(false);
            requestFactory = simpleClientHttpRequestFactory;
        }
        template.setRequestFactory(new BufferingClientHttpRequestFactory(requestFactory));
        template.getInterceptors().add(new TxProbeSpringRestTemplateInterceptor(this.featureAgent.getFacade(), enableApiLogging));
        logger.info("WebService Probe Support configured for RestTemplate.");
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Bus)
            primeCxfBus((Bus) bean);
        else if(bean instanceof JAXRSClientFactoryBean){
            JAXRSClientFactoryBean factoryBean = (JAXRSClientFactoryBean)bean;
            logger.info("JAXRSClientFactoryBean :: {}", factoryBean);
        }else if(bean instanceof RestTemplate) {
            boolean enableApiLogging = this.context.findAnnotationOnBean(beanName, EnableApiLogging.class) != null;
            primeRestTemplate((RestTemplate) bean, enableApiLogging);
        }
        return bean;
    }


}
