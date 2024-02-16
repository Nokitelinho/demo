/*
 * TxProbeConfigurator.java Created on 29-Dec-2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;

import com.ibsplc.neoicargo.framework.probe.camel.CamelProbeConfig;
import com.ibsplc.neoicargo.framework.probe.camel.TxProbeCamelTracer;
import com.ibsplc.neoicargo.framework.probe.dispatcher.RingBufferDispatcher;
import com.ibsplc.neoicargo.framework.probe.http.HttpProbeConfig;
import com.ibsplc.neoicargo.framework.probe.http.TxProbeHttpFilter;
import com.ibsplc.neoicargo.framework.probe.kafka.CloudStreamCustomizers;
import com.ibsplc.neoicargo.framework.probe.kafka.KafkaProbeConfig;
import com.ibsplc.neoicargo.framework.probe.kafka.TxProbeAutoWiringKafkaPostProcessors;
import com.ibsplc.neoicargo.framework.probe.sql.SqlProbeConfig;
import com.ibsplc.neoicargo.framework.probe.sql.SqlTxProbingAgent;
import com.ibsplc.neoicargo.framework.probe.sql.TxProbeAutoWiringSqlPostProcessor;
import com.ibsplc.neoicargo.framework.probe.ws.TxProbeAutoWiringWsPostProcessor;
import com.ibsplc.neoicargo.framework.probe.ws.TxProbeWSFaultListener;
import com.ibsplc.neoicargo.framework.probe.ws.TxProbeWSFeature;
import com.ibsplc.neoicargo.framework.probe.ws.WebServiceProbeConfig;
import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2017       		Jens J P 			First Draft
 */

/**
 * @author Jens J P
 */

@ImportAutoConfiguration({TxProbeConfigurator.CoreConfig.class,
        TxProbeConfigurator.HttpProbe.class,
        TxProbeConfigurator.SqlProbe.class,
        TxProbeConfigurator.WsProbe.class,
        TxProbeConfigurator.KafkaProbe.class,
        TxProbeConfigurator.CamelProbeSupport.class
})
@ConditionalOnProperty(name = "neo.txprobe.enabled")
public class TxProbeConfigurator {

    static final Logger logger = LoggerFactory.getLogger(TxProbeConfigurator.class);

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "neo.txprobe.enabled")
    static class CoreConfig {


        @Value("${com.ibsplc.neoicargo.tenant}")
        private String tenant;

        @Value("${spring.application.name}")
        private String serviceName;

        @Value("${git.commit.id.abbrev:unk}")
        private String version;


        /* (non-Javadoc)
         * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
         */
        @Bean
        public TxProbeConfig txProbeConfig(AggregationServerConfig aggregationServerConfig, ObjectProvider<ApplicationContext> tenantContext,
                                           ObjectProvider<HttpProbeConfig> httpConfig, ObjectProvider<SqlProbeConfig> sqlConfig,
                                           ObjectProvider<WebServiceProbeConfig> wsConfig, ObjectProvider<KafkaProbeConfig> kafkaConfig,
                                           ObjectProvider<CamelProbeConfig> camelConfig) {
            TxProbeConfig config = new TxProbeConfig();
            config.setEnabled(true);
            config.setTenant(this.tenant);
            config.setServiceName(this.serviceName);
            config.setVersion(this.version);
            config.setApplicationContextProvider(tenantContext);

            config.setHttpProbeConfig(httpConfig.getIfAvailable());
            config.setSqlProbeConfig(sqlConfig.getIfAvailable());
            config.setWebServiceProbeConfig(wsConfig.getIfAvailable());
            config.setKafkaProbeConfig(kafkaConfig.getIfAvailable());
            config.setCamelProbeConfig(camelConfig.getIfAvailable());

            config.setEnableHttpProbing(config.getHttpProbeConfig() != null);
            config.setEnableSqlProbing(config.getSqlProbeConfig() != null);
            config.setEnableHttpWebServiceProbing(config.getWebServiceProbeConfig() != null);
            config.setEnableJmsWebServiceProbing(config.getWebServiceProbeConfig() != null);
            config.setEnableKafkaProbing(config.getKafkaProbeConfig() != null);
            config.setEnableCamelProbing(config.getCamelProbeConfig() != null);

            RingBufferDispatcher dispatcher = new RingBufferDispatcher();
            dispatcher.setMaxQueueDepth(2048);
            config.setDispatcher(dispatcher);

            config.setAggregationServerConfig(aggregationServerConfig);

            logger.info("TxProbeConfig {}", config);
            return config;
        }

        @Bean
        @ConfigurationProperties(prefix = "neo.txprobe.aggregator")
        public AggregationServerConfig aggregationServerConfig() {
            AggregationServerConfig aggregationServerConfig = new AggregationServerConfig();
            aggregationServerConfig.setChannelType("nio");
            return aggregationServerConfig;
        }

        @Bean
        public TxProbeProcessor txProbeProcessor(TxProbeConfig config) {
            return new TxProbeProcessor(config);
        }

        @Bean
        public TxProbeFacade txProbeFacade(TxProbeProcessor probeProcessor) {
            return new TxProbeFacade(probeProcessor);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication
    @ConditionalOnProperty(name = {"neo.txprobe.http.enabled", "neo.txprobe.enabled"})
    public static class HttpProbe {

        @Bean
        @ConfigurationProperties(prefix = "neo.txprobe.http")
        public HttpProbeConfig httpConfig(UserResolver<HttpServletRequest> userResolver) {
            return new HttpProbeConfig(userResolver);
        }

        @Bean
        public FilterRegistrationBean<TxProbeHttpFilter> txProbeHttpFilter(@Value("${neo.txprobe.http.paths}") String[] httpUrlPaths, TxProbeFacade facade) {
            FilterRegistrationBean<TxProbeHttpFilter> filterBean = new FilterRegistrationBean<>();
            filterBean.setFilter(new TxProbeHttpFilter(facade));
            filterBean.setName("txProbeHttpFilter");
            List<String> urlPaths = Arrays.asList(httpUrlPaths);
            filterBean.setUrlPatterns(urlPaths);
            logger.info("Http Probe Support configured, probe aware urls {}", urlPaths);
            return filterBean;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = {"neo.txprobe.sql.enabled", "neo.txprobe.enabled"})
    @ConditionalOnClass(name = "com.zaxxer.hikari.HikariDataSource")
    public static class SqlProbe {

        @Bean
        @ConfigurationProperties(prefix = "neo.txprobe.sql")
        public SqlProbeConfig sqlConfig() {
            return new SqlProbeConfig();
        }

        @Bean
        public TxProbeAutoWiringSqlPostProcessor txProbeBeanPostProcessor(TxProbeFacade facade) {
            return new TxProbeAutoWiringSqlPostProcessor(new SqlTxProbingAgent(facade));
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = {"neo.txprobe.ws.enabled", "neo.txprobe.enabled"})
    @ConditionalOnClass(name = "org.apache.cxf.Bus")
    public static class WsProbe {

        @Bean
        @ConfigurationProperties(prefix = "neo.txprobe.ws")
        public WebServiceProbeConfig wsConfig() {
            return new WebServiceProbeConfig();
        }

        @Bean
        public TxProbeAutoWiringWsPostProcessor txProbeAutoWiringWsPostProcessor(TxProbeFacade facade, ApplicationContext context) {
            TxProbeWSFeature feature = new TxProbeWSFeature(facade, false);
            return new TxProbeAutoWiringWsPostProcessor(feature, context);
        }

        @Bean("txProbeWSFeature")
        public TxProbeWSFeature txProbeWSFeature(TxProbeFacade facade) {
            return new TxProbeWSFeature(facade, false);
        }

        @Bean
        public TxProbeWSFaultListener txProbeWSFaultListener(TxProbeFacade facade) {
            return new TxProbeWSFaultListener(facade);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = {"neo.txprobe.kafka.enabled", "neo.txprobe.enabled"})
    @ConditionalOnClass(name = {"org.springframework.kafka.core.KafkaTemplate", "org.springframework.kafka.listener.KafkaMessageListenerContainer"})
    public static class KafkaProbe {

        @Bean
        @ConfigurationProperties(prefix = "neo.txprobe.kafka")
        public KafkaProbeConfig kafkaProbeConfig() {
            return new KafkaProbeConfig();
        }

        @Bean
        @ConditionalOnClass(name = {"org.springframework.cloud.stream.binder.kafka.config.KafkaBinderConfiguration",
                "org.springframework.cloud.stream.config.ListenerContainerCustomizer"})
        public Object txProbeListenerContainerCustomizer(TxProbeFacade facade) {
            return new CloudStreamCustomizers.TxProbeListenerContainerCustomizer(facade);
        }

        @Bean
        @ConditionalOnClass(name = {"org.springframework.cloud.stream.binder.kafka.config.KafkaBinderConfiguration",
                "org.springframework.cloud.stream.config.ProducerMessageHandlerCustomizer"})
        public Object txProbeProducerMessageHandlerCustomizer(TxProbeFacade facade) {
            return new CloudStreamCustomizers.TxProbeProducerMessageHandlerCustomizer(facade);
        }

        @Bean("txProbeKafkaAutowiringPostProcessor")
        public TxProbeAutoWiringKafkaPostProcessors.SpringKafkaProcessor txProbeKafkaAutowiringPostProcessor(TxProbeFacade facade) {
            return new TxProbeAutoWiringKafkaPostProcessors.SpringKafkaProcessor(facade);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = {"neo.txprobe.camel.enabled", "neo.txprobe.enabled"})
    @ConditionalOnClass(name = {"org.apache.camel.CamelContext", "org.apache.camel.spring.SpringCamelContext", "org.apache.camel.spring.boot.CamelContextConfiguration"})
    public static class CamelProbeSupport {

        @Bean
        @ConfigurationProperties(prefix = "neo.txprobe.camel")
        public CamelProbeConfig camelProbeConfig(){
           return new CamelProbeConfig();
        }

        @Bean("txProbeCamelContextConfiguration")
        public CamelContextConfiguration txProbeCamelContextConfiguration(TxProbeFacade facade) {

            return new CamelContextConfiguration() {
                @Override
                public void beforeApplicationStart(CamelContext camelContext) {
                    logger.info("Camel txProbe tracing enabled for context {}", camelContext);
                    ExtendedCamelContext springCamelContext = camelContext.adapt(ExtendedCamelContext.class);
                    springCamelContext.setUseBreadcrumb(true);
                    springCamelContext.setTracer(new TxProbeCamelTracer(facade));
                }

                @Override
                public void afterApplicationStart(CamelContext camelContext) {
                    //noop
                }
            };
        }

    }


}
