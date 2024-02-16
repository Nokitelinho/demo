/*
 * Bootstrap.java Created on 29/03/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.k8s.depctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.security.Security;

import static java.lang.System.*;

/**
 * @author jens
 */
@SpringBootApplication(
        scanBasePackages = {"com.ibsplc.neoicargo.k8s.depctrl"},
        exclude = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                RedisAutoConfiguration.class, KafkaAutoConfiguration.class,
                JmxAutoConfiguration.class,
        }
)
public class Bootstrap {

    static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        preinit();
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(Bootstrap.class)
                .bannerMode(Banner.Mode.OFF).web(WebApplicationType.NONE)
                .headless(true).main(Bootstrap.class).run(args);
        boolean answer = perform(context);
        context.stop();
        // status not passed to exit code.
        exit(0);
    }

    static void preinit() {
        // disable dns caching
        Security.setProperty("networkaddress.cache.ttl", "0");
        Security.setProperty("networkaddress.cache.negative.ttl", "0");
        setProperty("sun.net.inetaddr.ttl", "0");
    }

    static boolean perform(ConfigurableApplicationContext context) {
        Action action = context.getBean(Action.class);
        logger.info("Executing action {}", action);
        boolean answer = false;
        do {
            try {
                out.println("-------------------------------------------------------------------");
                answer = action.perform();
                if (!answer) {
                    logger.info("Dependency services are not ready waiting ...");
                    Thread.sleep(5000);
                }
            } catch (Throwable e) {
                logger.error("Error invoking action " + action, e);
                e.printStackTrace();
                break;
            }
        } while (!answer);
        logger.info("Action Response - {}", answer);
        return answer;
    }


}
