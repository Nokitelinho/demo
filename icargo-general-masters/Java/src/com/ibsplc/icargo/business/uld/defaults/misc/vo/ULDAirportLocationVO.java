/*
 * ULDAirportLocationVO.java Created on jul 13, 2006
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
 * @author A-1496
 *
 */
public class ULDAirportLocationVO extends AbstractVO implements Serializable{
	
	/**
	 * 
	 */
	public static final String MODULE ="uld";
	/**
	 * 
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * 
	 */
	public static final String ENTITY ="uld.defaults.misc.ULDAirportLocation";
	/**
	 * 
	 */
	public static final String FACLITY_AGENTLOC = "AGT";
	/**
	 * 
	 */
	public static final String FACLITY_REPAIRDOC = "RPR";
	/**
	 * 
	 */
	public static final String FACLITY_ULDDOC = "ULD";
	
	
	private String companyCode;
	private String airportCode;
	private String facilityType;
	//added by a-3278 for QF1006 on 04Aug08
	private String content;
	//a-3278 ends
	private String sequenceNumber;
	private String facilityCode;
	private String description;
	private String operationFlag;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private String defaultFlag; 
	/**
	 * @return String Returns the defaultFlag.
	 */
	public String getDefaultFlag() {
		return this.defaultFlag;
	}
	/**
	 * @param defaultFlag The defaultFlag to set.
	 */
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	/**
	 * @return String Returns the airportCode.
	 */
	public String getAirportCode() {
		return this.airportCode;
	}
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return String Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
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
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return String Returns the facilityCode.
	 */
	public String getFacilityCode() {
		return this.facilityCode;
	}
	/**
	 * @param facilityCode The facilityCode to set.
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	/**
	 * @return String Returns the facilityType.
	 */
	public String getFacilityType() {
		return this.facilityType;
	}
	/**
	 * @param facilityType The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return LocalDate Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
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
	 * @param lastUpdatedUser The lastUpdatedUser to set.
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
	 * @param operationFlag The operationFlag to set.
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
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
	
	
}
