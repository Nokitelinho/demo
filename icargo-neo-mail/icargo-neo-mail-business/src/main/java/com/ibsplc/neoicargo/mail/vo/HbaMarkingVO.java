package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailBookingDetailVO.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-7531	:	09-Aug-2017	:	Draft
 */
@Setter
@Getter
public class HbaMarkingVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
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
}
