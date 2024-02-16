/*
 * ProrationFactorVO.java Created on Mar 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2518
 * 
 */
public class ProrationFactorVO extends AbstractVO {
	/**
	 * Company code
	 */
	private String companyCode;

	/**
	 * Origin city code
	 */
	private String originCityCode;

	/**
	 * Origin city name
	 */
	private String originCityName;

	/**
	 * Destination city code
	 */
	private String destinationCityCode;

	/**
	 * Destination city name
	 */
	private String destinationCityName;

	/**
	 * Proration factor status - Possible values can be 'New', 'Active',
	 * 'Inactive' or 'Cancelled'
	 */
	private String prorationFactorStatus;

	/**
	 * Proration factor source - Possible values can be 'Manual' or 'System'
	 */
	private String prorationFactorSource;

	/**
	 * Proration factor
	 */
	private double prorationFactor;

	/**
	 * Valid from date
	 */
	private LocalDate fromDate;

	/**
	 * Valid to date
	 */
	private LocalDate toDate;

	/**
	 * Last updated user
	 */
	private String lastUpdatedUser;

	/**
	 * Last updated time
	 */
	private LocalDate lastUpdatedTime;

	/**
	 * Sequence Number
	 */
	private int sequenceNumber;

	/**
	 * Operation flag
	 */
	private String operationFlag;

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the destinationCityCode.
	 */
	public String getDestinationCityCode() {
		return destinationCityCode;
	}

	/**
	 * @param destinationCityCode
	 *            The destinationCityCode to set.
	 */
	public void setDestinationCityCode(String destinationCityCode) {
		this.destinationCityCode = destinationCityCode;
	}

	/**
	 * @return Returns the destinationCityName.
	 */
	public String getDestinationCityName() {
		return destinationCityName;
	}

	/**
	 * @param destinationCityName
	 *            The destinationCityName to set.
	 */
	public void setDestinationCityName(String destinationCityName) {
		this.destinationCityName = destinationCityName;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the originCityCode.
	 */
	public String getOriginCityCode() {
		return originCityCode;
	}

	/**
	 * @param originCityCode
	 *            The originCityCode to set.
	 */
	public void setOriginCityCode(String originCityCode) {
		this.originCityCode = originCityCode;
	}

	/**
	 * @return Returns the originCityName.
	 */
	public String getOriginCityName() {
		return originCityName;
	}

	/**
	 * @param originCityName
	 *            The originCityName to set.
	 */
	public void setOriginCityName(String originCityName) {
		this.originCityName = originCityName;
	}

	/**
	 * @return Returns the prorationFactor.
	 */
	public double getProrationFactor() {
		return prorationFactor;
	}

	/**
	 * @param prorationFactor
	 *            The prorationFactor to set.
	 */
	public void setProrationFactor(double prorationFactor) {
		this.prorationFactor = prorationFactor;
	}

	/**
	 * @return Returns the prorationFactorSource.
	 */
	public String getProrationFactorSource() {
		return prorationFactorSource;
	}

	/**
	 * @param prorationFactorSource
	 *            The prorationFactorSource to set.
	 */
	public void setProrationFactorSource(String prorationFactorSource) {
		this.prorationFactorSource = prorationFactorSource;
	}

	/**
	 * @return Returns the prorationFactorStatus.
	 */
	public String getProrationFactorStatus() {
		return prorationFactorStatus;
	}

	/**
	 * @param prorationFactorStatus
	 *            The prorationFactorStatus to set.
	 */
	public void setProrationFactorStatus(String prorationFactorStatus) {
		this.prorationFactorStatus = prorationFactorStatus;
	}

	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

}
