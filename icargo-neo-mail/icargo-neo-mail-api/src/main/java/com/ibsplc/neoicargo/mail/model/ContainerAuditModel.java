package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ContainerAuditModel extends BaseModel {
	private String containerNumber;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private String assignedPort;
	private String triggerPnt;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
