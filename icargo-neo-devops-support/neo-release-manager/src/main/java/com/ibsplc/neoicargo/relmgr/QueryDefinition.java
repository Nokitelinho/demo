/*
 * QueryDefinition.java Created on 05/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jens
 */
@Configuration
@ConfigurationProperties(prefix = "query")
@PropertySource(value = "classpath:/config/queries.yml", factory = ReleaseManagerConfiguration.YamlPropertySourceFactory.class)
public class QueryDefinition {

    private Map<String, String> definitions = new HashMap<>();

    public Map<String, String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Map<String, String> definitions) {
        this.definitions = definitions;
    }
}
