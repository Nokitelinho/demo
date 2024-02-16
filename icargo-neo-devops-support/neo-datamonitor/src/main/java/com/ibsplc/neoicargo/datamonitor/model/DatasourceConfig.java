package com.ibsplc.neoicargo.datamonitor.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("datasources")
public class DatasourceConfig {

	private String username;
	private String password;
	private String id;
	private String url;
	private String driver;

}
