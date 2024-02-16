package com.ibsplc.icargo.tools.deployer.data;

public enum PropertyItems {

	SCRIPT_EXECUTING_COMMAND("sh"), 
	SCRIPT_FULL_NAME("/opt/icob/icargoUpgrade.sh"),
	SCRIPT_METHOD_TAIL_LOG("taillog"),
	SCRIPT_METHOD_UPGRADE("upgrade"),
	PROCESS_TIMEOUT_MINUTES("30");

	private String hardcodedValue;

	private PropertyItems(String label) {
		this.hardcodedValue = label;
	}

	public String getHardcodedValue() {
		return hardcodedValue;
	}

}
