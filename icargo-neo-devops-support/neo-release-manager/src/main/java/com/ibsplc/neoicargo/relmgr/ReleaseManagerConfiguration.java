/*
 * ReleaseManagerConfiguration.java Created on 04/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fasterxml.jackson.jaxrs.yaml.JacksonYAMLProvider;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationFeature;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @author jens
 */
@Configuration
@EntityScan("com.ibsplc.neoicargo.relmgr.entity")
public class ReleaseManagerConfiguration {

    @Bean
    public Server restServerFactory(Bus cxfBus, ReleaseManagerJaxrsService service) {
        JAXRSServerFactoryBean serverFactory = new JAXRSServerFactoryBean();
        serverFactory.setBus(cxfBus);
        serverFactory.setAddress("/");
        serverFactory.setProvider(jaxrsJsonProvider());
        serverFactory.setProvider(jaxrsYamlProvider());
        cxfBus.getFeatures().add(new JAXRSBeanValidationFeature());
        serverFactory.setServiceBean(service);
        Server server = serverFactory.create();
        return server;
    }

    @Bean
    public JacksonJsonProvider jaxrsJsonProvider() {
        JacksonJsonProvider provider = new JacksonJsonProvider(defaultMapper());
        return provider;
    }

    @Bean
    @Primary
    public ObjectMapper defaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        configureObjectMapper(mapper);
        return mapper;
    }

    @Bean
    public JacksonYAMLProvider jaxrsYamlProvider() {
        YAMLMapper mapper = new YAMLMapper();
        configureObjectMapper(mapper);
        JacksonYAMLProvider provider = new JacksonYAMLProvider(mapper);
        return provider;
    }

    private void configureObjectMapper(ObjectMapper mapper) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new ParameterNamesModule())
                .registerModule(new JavaTimeModule());
    }

    private LoggingFeature loggingFeature() {
        LoggingFeature feature = new LoggingFeature();
        feature.setPrettyLogging(true);
        feature.setInMemThreshold(Long.MAX_VALUE);
        return feature;
    }

    /**
     * Properties source factory to load custom yaml resources
     */
    public static class YamlPropertySourceFactory implements PropertySourceFactory {

        @Override
        public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource)
                throws IOException {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(encodedResource.getResource());
            Properties properties = factory.getObject();
            return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
        }
    }

}
