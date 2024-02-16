/*
 * ULDIntMvtDetailVO.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;
import java.util.Calendar;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2412
 *
 */
public class ULDIntMvtDetailVO extends AbstractVO implements Serializable{
	/**
	 * 
	 */
	public static final String DUMMY_MOVEMENT = "D";
	
	public static final String ACTUAL_MOVEMENT = "A";
	private String agentCode;
	
	private String agentName;
	
	private String content;
	
	private String airport;
	
	private String fromLocation;
	
	private String toLocation;
	
	private String mvtType;
	
	private Calendar mvtDate;
	
	private String remark;
	
	private String companyCode;
	
	private String uldNumber;

	private String intSequenceNumber;

	private long intSerialNumber;
	
	private String displayMvtDate;
	
	private String returnStatus;

	/**
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return the airport
	 */
	public String getAirport() {
		return airport;
	}

	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the fromLocation
	 */
	public String getFromLocation() {
		return fromLocation;
	}

	/**
	 * @param fromLocation the fromLocation to set
	 */
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	/**
	 * @return the intSequenceNumber
	 */
	public String getIntSequenceNumber() {
		return intSequenceNumber;
	}

	/**
	 * @param intSequenceNumber the intSequenceNumber to set
	 */
	public void setIntSequenceNumber(String intSequenceNumber) {
		this.intSequenceNumber = intSequenceNumber;
	}

	/**
	 * @return the intSerialNumber
	 */
	public long getIntSerialNumber() {
		return intSerialNumber;
	}

	/**
	 * @param intSerialNumber the intSerialNumber to set
	 */
	public void setIntSerialNumber(long intSerialNumber) {
		this.intSerialNumber = intSerialNumber;
	}

	/**
	 * @return the mvtDate
	 */
	public Calendar getMvtDate() {
		return mvtDate;
	}

	/**
	 * @param mvtDate the mvtDate to set
	 */
	public void setMvtDate(Calendar mvtDate) {
		this.mvtDate = mvtDate;
	}

	/**
	 * @return the mvtType
	 */
	public String getMvtType() {
		return mvtType;
	}

	/**
	 * @param mvtType the mvtType to set
	 */
	public void setMvtType(String mvtType) {
		this.mvtType = mvtType;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the toLocation
	 */
	public String getToLocation() {
		return toLocation;
	}

	/**
	 * @param toLocation the toLocation to set
	 */
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return the displayMvtDate
	 */
	public String getDisplayMvtDate() {
		return displayMvtDate;
	}

	/**
	 * @param displayMvtDate the displayMvtDate to set
	 */
	public void setDisplayMvtDate(String displayMvtDate) {
		this.displayMvtDate = displayMvtDate;
	}

	/**
	 * @return the returnStatus
	 */
	public String getReturnStatus() {
		return returnStatus;
	}

	/**
	 * @param returnStatus the returnStatus to set
	 */
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	
}
