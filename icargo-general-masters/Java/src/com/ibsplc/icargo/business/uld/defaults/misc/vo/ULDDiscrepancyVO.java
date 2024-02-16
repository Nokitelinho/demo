/*
 * ULDDiscrepancyVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;



import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDDiscrepancyVO extends AbstractVO{
    
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
	public static final String ENTITY ="uld.defaults.misc.ULDDiscrepancy";
	
	//Added by AsharafBinu on 06May08 for CRQAirNZ431
	public static final String ULD_MISSING = "M";
	public static final String ULD_FOUND = "F";
	public static final String ULD_RELEASED ="R";
	public static final String ULD_OUT_OF_STOCK ="O";
	public static final String ULD_IN_STOCK ="I";
	public static final String ULD_NOT_REGISTERED ="N";
	public static final String ULD_MOVED_DIS_RESOLVED = "uld.defaults.uldmovedanddiscrepancyresolved";
	public static final String ULD_RELEASED_TO_AGENT = "uld.defaults.uldreleasedtoagent";
	public static final String FOUND_CREATED = "uld.defaults.founddiscrepancycreated";
	public static final String MISSING_CREATED = "uld.defaults.missingdiscrepancycreated";
	
	//Asharaf Binu ends
	
	
	private String companyCode;
    private String uldNumber;
    private String discrepencyCode;
    private LocalDate discrepencyDate;    
    private String reportingStation;
    private String remarks;
    private String ownerStation;
    private String operationFlag;
    private String lastUpdatedUser;
    private LocalDate lastUpdatedTime; 
    private String uldStatus;
    private String transactionStatus;
    private String scmSequenceNumber;
    private String facilityDescription;
    private String facilityType;    
    private String location;
    private String closeStatus;
    private String sequenceNumber;
    private String agentCode;
    
	
	/**
	 * @return String Returns the scmSequenceNumber.
	 */
	public String getScmSequenceNumber() {
		return this.scmSequenceNumber;
	}
	/**
	 * @param scmSequenceNumber The scmSequenceNumber to set.
	 */
	public void setScmSequenceNumber(String scmSequenceNumber) {
		this.scmSequenceNumber = scmSequenceNumber;
	}
	/**
	 * @return Returns the discrepencyCode.
	 */
	public String getDiscrepencyCode() {
		return discrepencyCode;
	}
	/**
	 * @param discrepencyCode The discrepencyCode to set.
	 */
	public void setDiscrepencyCode(String discrepencyCode) {
		this.discrepencyCode = discrepencyCode;
	}
	
	/**
	 * @return Returns the discrepencyDate.
	 */
	public LocalDate getDiscrepencyDate() {
		return discrepencyDate;
	}
	/**
	 * @param discrepencyDate The discrepencyDate to set.
	 */
	public void setDiscrepencyDate(LocalDate discrepencyDate) {
		this.discrepencyDate = discrepencyDate;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return Returns the ownerStation.
	 */
	public String getOwnerStation() {
		return ownerStation;
	}
	/**
	 * @param ownerStation The ownerStation to set.
	 */
	public void setOwnerStation(String ownerStation) {
		this.ownerStation = ownerStation;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the reportingStation.
	 */
	public String getReportingStation() {
		return reportingStation;
	}
	/**
	 * @param reportingStation The reportingStation to set.
	 */
	public void setReportingStation(String reportingStation) {
		this.reportingStation = reportingStation;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	//@Column(name = "")
	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	//@Column(name = "")
	/**
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	//@Column(name = "")
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getUldStatus() {
		return uldStatus;
	}
	/**
	 * 
	 * @param uldStatus
	 */
	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
	}
	/**
	 * 
	 * @return
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * 
	 * @param transactionStatus
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	/**
	 * 
	 * @return
	 */
	public String getFacilityDescription() {
		return facilityDescription;
	}
	/**
	 * 
	 * @param facilityDescription
	 */
	public void setFacilityDescription(String facilityDescription) {
		this.facilityDescription = facilityDescription;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * 
	 * @param sequenceNumber
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * 
	 * @return
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * 
	 * @param agentCode
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getCloseStatus() {
		return closeStatus;
	}
	/**
	 * 
	 * @param closeStatus
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}
	/**
	 * 
	 * @return
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * 
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the facilityType
	 */
	public String getFacilityType() {
		return facilityType;
	}
	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
    
}
