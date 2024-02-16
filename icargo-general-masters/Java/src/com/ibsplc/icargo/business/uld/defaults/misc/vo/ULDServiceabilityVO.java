/*
 * ULDServiceabilityVO.java Created on Aug 27, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2052
 */
public class ULDServiceabilityVO extends AbstractVO implements Serializable {

	/**
	 * 
	 */
	public static final String MODULE = "uld";

	/**
	 * 
	 */
	public static final String SUBMODULE = "defaults";

	/**
	 * 
	 */
	public static final String ENTITY = "uld.defaults.misc.ULDServiceability";

	private String companyCode;

	private String partyType;

	private String sequenceNumber;

	private String code;

	private String description;

	private String operationFlag;

	private LocalDate lastUpdatedTime;

	private String lastUpdatedUser;

	/**
	 * @return String Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return String Returns the description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return String Returns the Code.
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return String Returns the facilityType.
	 */
	public String getPartyType() {
		return this.partyType;
	}

	/**
	 * @param partyType
	 *            The partyType to set.
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	/**
	 * @return LocalDate Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return String Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return this.operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return String Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return this.sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

}
