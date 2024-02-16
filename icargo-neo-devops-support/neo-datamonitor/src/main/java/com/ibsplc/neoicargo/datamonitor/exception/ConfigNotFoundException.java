package com.ibsplc.neoicargo.datamonitor.exception;

public class ConfigNotFoundException extends RuntimeException {

	public static final String CONFIG_NOT_FOUND = "Value not found in any of the yml's of config-server for the key <%s> in application <%s> for tenant <%s> ";
	

	public ConfigNotFoundException(String key, String application, String tenant) {
		super(String.format(CONFIG_NOT_FOUND, key, application, tenant));
	}

}
