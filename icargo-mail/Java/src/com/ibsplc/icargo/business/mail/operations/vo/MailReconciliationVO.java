/*
 * MailReconciliationVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3109
 * 
 */
public class MailReconciliationVO extends AbstractVO {
	private String companyCode;

	private String pol;

	private String pou;

	private int carrierId;
	
	private int segSerNum;
	
	private int legSerialNumber;

	private String flightNumber;

	private long flightSequenceNumber;

	private String carrierCode;

	private LocalDate flightDate;

	private String direction;

	private String receptacleId;

	private String exception;

	private String mailStatus;

	private String user;
	
	private String airportCode;

	private LocalDate operationTime;
	
    private LocalDate lastUpdateTime;
	
	private String lastUpdateUser;
	
	/**
	 * added for bug 57441
	 */
	
	private String splitMailId;

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the carrierId
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            the carrierId to set
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}

	/**
	 * @param exception
	 *            the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

	/**
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the flightSequenceNumber
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            the flightSequenceNumber to set
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return the mailStatus
	 */
	public String getMailStatus() {
		return mailStatus;
	}

	/**
	 * @param mailStatus
	 *            the mailStatus to set
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	/**
	 * @return the operationTime
	 */
	public LocalDate getOperationTime() {
		return operationTime;
	}

	/**
	 * @param operationTime
	 *            the operationTime to set
	 */
	public void setOperationTime(LocalDate operationTime) {
		this.operationTime = operationTime;
	}

	/**
	 * @return the pol
	 */
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol
	 *            the pol to set
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return the pou
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            the pou to set
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return the receptacleId
	 */
	public String getReceptacleId() {
		return receptacleId;
	}

	/**
	 * @param receptacleId
	 *            the receptacleId to set
	 */
	public void setReceptacleId(String receptacleId) {
		this.receptacleId = receptacleId;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	

	/**
	 * @return the legSerialNumber
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legSerialNumber the legSerialNumber to set
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return the segSerNum
	 */
	public int getSegSerNum() {
		return segSerNum;
	}

	/**
	 * @param segSerNum the segSerNum to set
	 */
	public void setSegSerNum(int segSerNum) {
		this.segSerNum = segSerNum;
	}

	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return the splitMailId
	 */
	public String getSplitMailId() {
		return splitMailId;
	}

	/**
	 * @param splitMailId the splitMailId to set
	 */
	public void setSplitMailId(String splitMailId) {
		this.splitMailId = splitMailId;
	}

}
