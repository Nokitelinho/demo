/*
 * ULDDamageDetailsVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDDiscrepancyMicroVO extends AbstractVO {

	private String companyCode;
    private String uldNumber;
    private String discrepencyCode;
    private String discrepencyDate;
    private String reportingStation;
    private String remarks;
    private String airportCode;
    private String operationFlag;
    private String lastUpdatedUser;
    private String lastUpdatedTime;
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
	public String getDiscrepencyDate() {
		return discrepencyDate;
	}
	/**
	 * @param discrepencyDate The discrepencyDate to set.
	 */
	public void setDiscrepencyDate(String discrepencyDate) {
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
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
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
	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(String lastUpdatedTime) {
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

	public String getUldStatus() {
		return uldStatus;
	}
	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
	}
	/**
	 * @return Returns the agentCode.
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * @param agentCode The agentCode to set.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * @return Returns the closeStatus.
	 */
	public String getCloseStatus() {
		return closeStatus;
	}
	/**
	 * @param closeStatus The closeStatus to set.
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}
	/**
	 * @return Returns the facilityDescription.
	 */
	public String getFacilityDescription() {
		return facilityDescription;
	}
	/**
	 * @param facilityDescription The facilityDescription to set.
	 */
	public void setFacilityDescription(String facilityDescription) {
		this.facilityDescription = facilityDescription;
	}
	/**
	 * @return Returns the facilityType.
	 */
	public String getFacilityType() {
		return facilityType;
	}
	/**
	 * @param facilityType The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return Returns the scmSequenceNumber.
	 */
	public String getScmSequenceNumber() {
		return scmSequenceNumber;
	}
	/**
	 * @param scmSequenceNumber The scmSequenceNumber to set.
	 */
	public void setScmSequenceNumber(String scmSequenceNumber) {
		this.scmSequenceNumber = scmSequenceNumber;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the transactionStatus.
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * @param transactionStatus The transactionStatus to set.
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
}
