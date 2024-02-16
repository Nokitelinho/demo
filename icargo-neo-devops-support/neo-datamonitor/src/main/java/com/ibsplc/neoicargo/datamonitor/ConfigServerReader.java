package com.ibsplc.neoicargo.datamonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import com.ibsplc.neoicargo.datamonitor.model.DataMonitorConfigList;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

@Component
public class ConfigServerReader {

	@Value("${spring.cloud.config.uri}")
	private String configServerUrl;

	@Autowired
	private ContextUtil contextUtil;

	private Map<String, ConfigurableApplicationContext> contextMap = new HashMap<>();

	public Object getConfig(String applicationName, String profile, String key) {
		return getContext(applicationName, profile).getEnvironment().getProperty(key);
	}

	public Object getBean(String applicationName, String profile, Class claz) {
		return getContext(applicationName, profile).getBean(claz);
	}

	public void closeAllContexts() {
		for(ConfigurableApplicationContext context : contextMap.values()) {
			context.close();
		}
	}
	
	private ConfigurableApplicationContext getContext(String applicationName, String profile) {
		ConfigurableApplicationContext context = null;
		if (contextMap.get(applicationName) == null) {
			context = resolveApplicationContext(applicationName, profile);
			contextMap.put(applicationName, context);
		} else {
			context = contextMap.get(applicationName);
		}
		return context;
	}
	
	

	private ConfigurableApplicationContext resolveApplicationContext(String applicationName, String profile) {

		Map<String, Object> baseEnv = new HashMap<>();
		baseEnv.put("spring.cloud.config.profile", profile);
		baseEnv.put("spring.cloud.config.label", contextUtil.getTenant());
		baseEnv.put("spring.cloud.config.uri", configServerUrl);
		baseEnv.put("spring.application.name", applicationName);
		baseEnv.put("endpoints.jmx.enabled", "false");
		baseEnv.put("spring.jmx.enabled", "false");
		//baseEnv.put("management.health.kafka.enabled", "true");
		//baseEnv.put("management.health.redis.enabled", "true");
		//baseEnv.put("management.health.db.enabled", "true");
		//baseEnv.put("management.health.binders.enabled", "true");

		StandardEnvironment bootstrapEnv = new StandardEnvironment();
		bootstrapEnv.getPropertySources().addFirst(new MapPropertySource("bootstrapProperties", baseEnv));

		List<Class<?>> sources = new ArrayList<>();
		sources.add(RemoteDataMonitorConfiguration.class);

		//Collections.reverse(sources);
		SpringApplicationBuilder springBuilder = new SpringApplicationBuilder().headless(true).bannerMode(Banner.Mode.OFF).web(WebApplicationType.NONE)
				.registerShutdownHook(false).logStartupInfo(false).profiles(profile).environment(bootstrapEnv).sources(sources.toArray(Class[]::new));
		ConfigurableApplicationContext applicationContext = springBuilder.build().run(new String[] {  });

		return applicationContext;
	}

	@Configuration
	@EnableConfigurationProperties(DataMonitorConfigList.class)
	static class RemoteDataMonitorConfiguration {

	}


}
