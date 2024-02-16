package com.ibsplc.neoicargo.devops.snow.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationFeature;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */

@Configuration
@EnableAsync
@PropertySource(value="classpath:/config/snow/mappings-default.yaml",ignoreResourceNotFound=true,factory = DefaultConfiguration.YamlPropertySourceFactory.class)
@PropertySource(value="classpath:/config/snow/mappings.yaml",ignoreResourceNotFound=true,factory = DefaultConfiguration.YamlPropertySourceFactory.class)
public class DefaultConfiguration {


    @Bean
    JAXRSBeanValidationFeature jaxrsBeanValidationFeature(){
        return new JAXRSBeanValidationFeature();
    }

    @Bean
    public JacksonJsonProvider jaxrsJsonProvider() {
        JacksonJsonProvider provider = new JacksonJsonProvider(defaultMapper());
        return provider;
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }

    @Bean
    @Primary
    public ObjectMapper defaultMapper(){
        ObjectMapper mapper = new ObjectMapper();
        configureObjectMapper(mapper);
        return mapper;
    }

    @Bean
    @ConfigurationProperties("snow")
    public SNOWMappingConfig snowMappingConfig(){
        return new SNOWMappingConfig();
    }

    @Bean("activeEmailAlerts")
    @ConfigurationProperties("notify.aws.sns.email.active")
    public HashMap<String,String> activeEmailAlerts(){
        return new HashMap<>();
    }


    @Bean("defaultAWSTags")
    @ConfigurationProperties("notify.aws.default-tags")
    public HashMap<String,String> defaultAWSTags(){
        return new HashMap<>();
    }

    @Bean("inActiveEmailAlerts")
    @ConfigurationProperties("notify.aws.sns.email.inactive")
    public List<String> inActiveEmailAlerts(){
        return new ArrayList<>();
    }

    @Bean
    @ConfigurationProperties("notify.snow")
    public SNOWConfig snowConfiguration(){
        return new SNOWConfig();
    }


    private void configureObjectMapper(ObjectMapper mapper) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new ParameterNamesModule())
                .registerModule(new JavaTimeModule());
    }

    private LoggingFeature loggingFeature(){
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
        public org.springframework.core.env.PropertySource<?> createPropertySource(String name, EncodedResource encodedResource)
                throws IOException {
            if(encodedResource.getResource().exists()){
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(encodedResource.getResource());
            Properties properties = factory.getObject();
            return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
            }
            return new MapPropertySource("unused",new HashMap<>());
        }
    }
}
