package com.ibsplc.neoicargo.framework.icgsupport.utils.audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditFieldVO {
	private String fieldName;
	private String description;
	private String oldValue;
	private String newValue;
	private String dataDescCode;
	private int order;
}
