package com.ibsplc.neoicargo.tracking.config;


import java.util.concurrent.TimeUnit;

import javax.cache.expiry.AccessedExpiryPolicy;

import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibsplc.neoicargo.framework.cache.CacheCustomizer;
import com.ibsplc.neoicargo.framework.cache.NativeMutableConfiguration;


@Configuration 
public class TrackingCacheConfigRetriever {

	
	
	@Bean
	CacheCustomizer trackingModuleCacheConfig() {
		return (cacheBuilder) -> {
	        cacheBuilder.configureCache("milestoneMasterCache", new NativeMutableConfiguration()
                    .addCacheNativeConfiguration(ResourcePoolsBuilder.newResourcePoolsBuilder()
                            .heap(2000, EntryUnit.ENTRIES)
                            .build())
                    .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new javax.cache.expiry.Duration(TimeUnit.DAYS, 1)))
            );
	    };
	}
	
	
	
}