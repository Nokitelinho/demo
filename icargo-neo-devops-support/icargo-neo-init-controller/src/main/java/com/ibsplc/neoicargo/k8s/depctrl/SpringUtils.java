/*
 * SpringUtils.java Created on 13/04/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.k8s.depctrl;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.redis.RedisHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.*;

/**
 * @author jens
 */
public class SpringUtils {


    public static ConfigurableApplicationContext resolveApplicationContext(String tenant, String applicationName, String profile, String configServerUrl){
        Map<String, Object> baseEnv = new HashMap<>();
        baseEnv.put("spring.cloud.config.profile", profile);
        baseEnv.put("spring.cloud.config.label", tenant);
        baseEnv.put("spring.cloud.config.uri", configServerUrl);
        baseEnv.put("spring.application.name", applicationName);
        baseEnv.put("endpoints.jmx.enabled", "false");
        baseEnv.put("spring.jmx.enabled", "false");
        baseEnv.put("management.health.kafka.enabled", "true");
        baseEnv.put("management.health.redis.enabled", "true");
        baseEnv.put("management.health.db.enabled", "true");
        baseEnv.put("management.health.binders.enabled", "true");

        StandardEnvironment bootstrapEnv = new StandardEnvironment();
        bootstrapEnv.getPropertySources().addFirst(new MapPropertySource("bootstrapProperties", baseEnv));

        List<Class<?>> sources = new ArrayList<>();
        sources.add(DataSourceAutoConfiguration.class);
        sources.add(RedisAutoConfiguration.class);
        sources.add(KafkaAutoConfiguration.class);
        sources.add(DataSourceHealthContributorAutoConfiguration.class);
        sources.add(RedisHealthContributorAutoConfiguration.class);

        Collections.reverse(sources);
        SpringApplicationBuilder springBuilder = new SpringApplicationBuilder()
                .headless(true).bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE).registerShutdownHook(false).logStartupInfo(false)
                .profiles(profile).environment(bootstrapEnv)
                .sources(sources.toArray(Class[]::new));
        ConfigurableApplicationContext applicationContext = springBuilder.build().run(new String[]{"--debug"});
        return applicationContext;
    }


}
