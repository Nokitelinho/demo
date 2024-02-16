package com.ibsplc.neoicargo.mailmasters;

import com.ibsplc.neoicargo.framework.cache.CacheCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class MailMastersCacheConfigRetriever {

    /**
     * @param
     * @return
     * This method is used to for cache building
     */
    @Bean
    CacheCustomizer masterModuleCacheConfig() {
        return (cacheBuilder) -> {
            cacheBuilder.configureCache("postalAdministrationCache",false)
                    .configureCache("mailOfficeOfExchangeCache",new MutableConfiguration<>()
                            .setExpiryPolicyFactory(javax.cache.expiry.AccessedExpiryPolicy.factoryOf(new javax.cache.expiry.Duration(TimeUnit.MINUTES, 5))),false)
                    .configureCache("officeOfExchangesForAirportCache",false)
                    .configureCache("officeOfExchangesForPaCodeCache",false)
                    .configureCache("mailboxidCache",false)
.configureCache("airportForOfficeOfExchangeCache",false)
            .configureCache("mailEventCache", false)
            .configureCache("postalAdministrationPartyIdentifierCache", false);

        };
    }
}

