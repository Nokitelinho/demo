/*
 * ContainerAuditVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * 
 * @author A-5991
 * 
 */
public class ContainerAuditVO extends AuditVO {


	private String containerNumber;

	private int carrierId;

	private String flightNumber;

	private long flightSequenceNumber;

	private int legSerialNumber;

	private String assignedPort;

	private String triggerPnt;

	/**
	 * @return Returns the assignedPort.
	 */
	public String getAssignedPort() {
		return assignedPort;
	}

	/**
	 * @param assignedPort
	 *            The assignedPort to set.
	 */
	public void setAssignedPort(String assignedPort) {
		this.assignedPort = assignedPort;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legSerialNumber
	 *            The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public ContainerAuditVO(String arg0, String arg1, String arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public String getTriggerPnt() {
		return triggerPnt;
	}

	public void setTriggerPnt(String triggerPnt) {
		this.triggerPnt = triggerPnt;
	}

}
