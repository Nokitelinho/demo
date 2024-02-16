package com.ibsplc.neoicargo.datamonitor.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ibsplc.neoicargo.datamonitor.ConfigServerReader;
import com.ibsplc.neoicargo.datamonitor.constants.ConstantLiterals;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NativeRepository {

	@Autowired
	private ContextUtil contextUtil;

	@Autowired
	private ConfigServerReader configReader;

	private Map<String, JdbcTemplate> jdbcTemplateMap = new HashMap<String, JdbcTemplate>();

	public long executeQuery(String sql, String application) {
		return jdbcTemplateMap.get(application).queryForObject(sql, Long.class);
	}

	public List<Map<String, Object>> queryForList(String sql, String application, boolean isCqrs) {
		log.info("companyCode -> {}", contextUtil.getTenant().toUpperCase());
		String dsName = isCqrs ? ConstantLiterals.CQRS_DATASOURCE_NAME : application;
		return jdbcTemplateMap.get(dsName).queryForList(sql, new Object[] { contextUtil.getTenant().toUpperCase() });
	}

	public void addJdbcTemplate(String application, String profile) {
		DataSource ds = constructDataSource(application, profile);
		jdbcTemplateMap.put(application, new JdbcTemplate(ds));
	}

	public void addCqrsJdbcTemplate(String application, String profile) {
		// if cqrs datasource is already added, then skip this function.
		if (!jdbcTemplateMap.containsKey(ConstantLiterals.CQRS_DATASOURCE_NAME)) {
			DataSource ds = constructCqrsDataSource(application, profile);
			jdbcTemplateMap.put(ConstantLiterals.CQRS_DATASOURCE_NAME, new JdbcTemplate(ds));
		}
	}

	private DataSource constructDataSource(String applicationName, String profile) {
		log.info("applicationName : {}", applicationName);
		log.info("profile : {}", profile);
		log.info("tenant : {}", contextUtil.getTenant());
		DataSourceBuilder dsb = DataSourceBuilder.create();
		// dsb.driverClassName(configReader.getConfig(applicationName, profile,
		// ConstantLiterals.DATASOURCE_TYPE).toString());
		dsb.driverClassName("org.postgresql.Driver");
		String url;
		Object readonlyurl = configReader.getConfig(applicationName, profile, ConstantLiterals.DATASOURCE_READONLY_URL);
		url = (readonlyurl == null) ? configReader.getConfig(applicationName, profile, ConstantLiterals.DATASOURCE_URL).toString() : readonlyurl.toString();
		dsb.url(url);
		dsb.username(configReader.getConfig(applicationName, profile, ConstantLiterals.DATASOURCE_USERNAME).toString());
		dsb.password(configReader.getConfig(applicationName, profile, ConstantLiterals.DATASOURCE_PASSWORD).toString());
		return dsb.build();
	}

	private DataSource constructCqrsDataSource(String applicationName, String profile) {
		log.info("applicationName : {}", applicationName);
		log.info("profile : {}", profile);
		log.info("tenant : {}", contextUtil.getTenant());
		DataSourceBuilder dsb = DataSourceBuilder.create();
		// dsb.driverClassName(configReader.getConfig(applicationName, profile,
		// ConstantLiterals.DATASOURCE_TYPE).toString());
		dsb.driverClassName("org.postgresql.Driver");
		String url;
		Object readonlyurl = configReader.getConfig(applicationName, profile, ConstantLiterals.CQRS_DATASOURCE_READONLY_URL);
		url = (readonlyurl == null) ? configReader.getConfig(applicationName, profile, ConstantLiterals.CQRS_DATASOURCE_URL).toString()
				: readonlyurl.toString();
		dsb.url(url);
		dsb.username(configReader.getConfig(applicationName, profile, ConstantLiterals.CQRS_DATASOURCE_USERNAME).toString());
		dsb.password(configReader.getConfig(applicationName, profile, ConstantLiterals.CQRS_DATASOURCE_PASSWORD).toString());
		return dsb.build();
	}
}