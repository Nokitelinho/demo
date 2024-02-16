package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class HbaMarkingModel extends BaseModel {
	private String hbaPosition;
	private String hbaType;
	private long uldRefNo;
	private String companyCode;
	private String operationFlag;
	private String flightNumber;
	private long flightSequenceNumber;
	private int carrierId;
	private int legSerialNumber;
	private String assignedPort;
	private String containerNumber;
	private String containerType;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
