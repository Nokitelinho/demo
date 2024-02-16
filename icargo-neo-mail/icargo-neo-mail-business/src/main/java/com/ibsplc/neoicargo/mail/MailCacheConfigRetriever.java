package com.ibsplc.neoicargo.mail;

import com.ibsplc.neoicargo.framework.cache.CacheCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class MailCacheConfigRetriever {

    /**
     * @param
     * @return
     * This method is used to for cache building
     */
    @Bean
    CacheCustomizer mailModuleCacheConfig() {
        return (cacheBuilder) -> {
            cacheBuilder.configureCache("securityScreeningValidationCache",new MutableConfiguration<>()
                    .setExpiryPolicyFactory(javax.cache.expiry.AccessedExpiryPolicy.factoryOf(new javax.cache.expiry.Duration(TimeUnit.MINUTES, 10))),false);
        };
    }
}