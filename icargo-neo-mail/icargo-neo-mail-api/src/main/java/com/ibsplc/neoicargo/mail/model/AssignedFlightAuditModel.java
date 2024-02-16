package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class AssignedFlightAuditModel extends BaseModel {
	private String airportCode;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
