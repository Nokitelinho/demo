/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailBookingDetailVO.java
 *
 *	Created by	:	A-7531
 *	Created on	:	09-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailBookingDetailVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	09-Aug-2017	:	Draft
 */
public class HbaMarkingVO extends AbstractVO {

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
	
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getHbaPosition() {
		return hbaPosition;
	}
	public void setHbaPosition(String hbaPosition) {
		this.hbaPosition = hbaPosition;
	}
	public String getHbaType() {
		return hbaType;
	}
	public void setHbaType(String hbaType) {
		this.hbaType = hbaType;
	}
	public long getUldRefNo() {
		return uldRefNo;
	}
	public void setUldRefNo(long uldRefNo) {
		this.uldRefNo = uldRefNo;
	}
	public String getAssignedPort() {
		return assignedPort;
	}
	public void setAssignedPort(String assignedPort) {
		this.assignedPort = assignedPort;
	}

	



}
