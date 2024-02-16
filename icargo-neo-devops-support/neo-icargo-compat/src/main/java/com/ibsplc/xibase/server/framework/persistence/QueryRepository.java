/*
 * QueryRepository.java Created on 24/05/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.persistence;

import com.ibsplc.neoicargo.framework.core.context.tenant.support.TenantApplicationContext;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.object.AbstractObjectQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.parser.QueryDefinitionsParser;
import com.ibsplc.xibase.server.framework.persistence.query.parser.Types;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author jens
 */
public class QueryRepository {

    static final Logger logger = LoggerFactory.getLogger(QueryRepository.class);

    static String PROP_PREFIX = "icargo.persistence";

    static Map<String, QueryConfiguration> QUERY_CACHE = Collections.synchronizedMap(new HashMap<>(4));

    public static QueryConfiguration resolveQueryConfiguration(String module, TenantApplicationContext tenantContext) {
        String key = tenantContext.tenant() + "-" + module;
        return QUERY_CACHE.computeIfAbsent(key, k -> {
            ConfigurableEnvironment env = tenantContext.getEnvironment();
            QueryConfiguration configuration = new QueryConfiguration();
            configuration.queryXmlResource = env.getProperty(format("%s.%s.queryXml", PROP_PREFIX, module));
            configuration.sqlDaoKlass = loadKlass(env.getProperty(format("%s.%s.daoClass", PROP_PREFIX, module)));
            configuration.objectDaoKlass = loadKlass(env.getProperty(format("%s.%s.objectDaoClass", PROP_PREFIX, module)));
            loadQueryXml(configuration, tenantContext);
            return configuration;
        });
    }

    @SneakyThrows
    static void loadQueryXml(QueryConfiguration configuration, TenantApplicationContext context) {
        QueryDefinitionsParser parser = new QueryDefinitionsParser();
        logger.info("Loading queryXml resource {} ...", configuration.queryXmlResource);
        Resource resource = context.getResource(configuration.queryXmlResource);
        if (!resource.exists())
            throw new IllegalStateException(format("Resource not present %s", configuration.queryXmlResource));
        parser.parse(resource.getInputStream());
        configuration.procMappings = parser.getMappings(Types.PROCEDURE);
        configuration.qryMappings = parser.getMappings(Types.NATIVE);
    }

    @SneakyThrows
    static <T> Class<T> loadKlass(String name) {
        return name != null ? (Class<T>) QueryRepository.class.getClassLoader().loadClass(name) : null;
    }


    public static class QueryConfiguration {
        public String queryXmlResource;
        public Class<? extends AbstractQueryDAO> sqlDaoKlass;
        public Class<? extends AbstractObjectQueryDAO> objectDaoKlass;
        public HashMap<String, String> qryMappings;
        public HashMap<String, String> procMappings;
    }

}
