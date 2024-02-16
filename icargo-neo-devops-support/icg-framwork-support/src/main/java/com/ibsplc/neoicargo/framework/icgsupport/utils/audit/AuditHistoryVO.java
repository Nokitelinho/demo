package com.ibsplc.neoicargo.framework.icgsupport.utils.audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditHistoryVO extends AuditVO {

	private String oldValue;
	private String newValue;
	private String dataDescCode;
	private String historyEvent;

	public AuditHistoryVO(String moduleName, String subModuleName, String entityName) {
		super(moduleName, subModuleName, entityName);
	}

}
