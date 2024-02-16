/*
 * StockHolderVO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoPublishVO;

/**
 * @author A-1358
 * 
 */
public class EmbargoRulesVO extends AbstractVO {

	
	/**
	 * This Key is used in Key Genaration for Embargo Entity
	 */
	public static final String KEY_EMBARGO = "RECO";
	
	/**
	 * This Key is used in Key Genaration for EmbargoParameter Entity
	 */
	public static final String KEY_EMBARGO_PARAMETER = "EMBARGO_PARAMETER";
	
	private String operationalFlag;

	private String companyCode;

	private String embargoReferenceNumber;

	private LocalDate startDate;

	private LocalDate endDate;

	private int embargoVersion;
	
	private String ruleType;
	
	private String scc;
	private String sccGroup;
	
	
	public String getScc() {
		return scc;
	}
	public void setScc(String scc) {
		this.scc = scc;
	}
	public String getSccGroup() {
		return sccGroup;
	}
	public void setSccGroup(String sccGroup) {
		this.sccGroup = sccGroup;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public int getEmbargoVersion() {
		return embargoVersion;
	}

	public void setEmbargoVersion(int embargoVersion) {
		this.embargoVersion = embargoVersion;
	}

	/*
	 * Embargo applicable to shipments with this origin
	 */
	private String origin;

	/*
	 * Indicates whether the origin is a country or station Possible values are
	 * 'C' or 'S'
	 */
	private String originType;

	/*
	 * Embargo applicable to shipments with this destination
	 */
	private String destination;

	/*
	 * Indicates whether the destination is a country or station Possible values
	 * are 'C' or 'S'
	 */
	private String destinationType;

	/*
	 * Possible values are 'Active', 'Cancelled',
	 */
	private String status;

	/*
	 * Indicates the severity of the embargo. This field determines whether the
	 * embargo results in an error, warning or information
	 */
	private String embargoLevel;

	private String embargoDescription;

	private String remarks;

	/*
	 * Indicates whether the embargo is enabled or disabled
	 */
	private boolean isSuspended;

	// for ANA CR Coo/Frozen/CC Embargo added by Sinish
	private boolean isCool;
	private boolean isFrozen;
	private boolean isCC;

	private String segmentOrigin;
	private String segmentDestination;
	//for ANA CR Coo/Frozen/CC Embargo added by Sinish ends
	public String getSegmentOrigin() {
		return segmentOrigin;
	}
	public void setSegmentOrigin(String segmentOrigin) {
		this.segmentOrigin = segmentOrigin;
	}
	public String getSegmentDestination() {
		return segmentDestination;
	}
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}
	
	/*
	 * Holds the parameters over which the embargo is defined
	 */
	private Collection<EmbargoParameterVO> parameters;
	
	private Collection<EmbargoGeographicLevelVO> geographicLevels;

	
	/*
	 * For Optimistic locking
	 */
	private LocalDate lastUpdatedTime;

	/*
	 * For Optimistic locking
	 */
	private String lastUpdatedUser;
	
	private String daysOfOperation;
	private String daysOfOperationApplicableOn;
	private String daysOfOperationFlag;
	public String getDaysOfOperationFlag() {
		return daysOfOperationFlag;
	}
	public void setDaysOfOperationFlag(String daysOfOperationFlag) {
		this.daysOfOperationFlag = daysOfOperationFlag;
	}

	private String viaPointType;
	private String viaPoint;
	//Added by A-5219 for CRQ ICRD-23740
	private EmbargoPublishVO embargoPublishVO; 
	
	private String category;
	private String complianceType;
	private String applicableTransactions;
	// Added by A-8374 for ICRD-340405 starts
	private String dateString;
	private String timeString;
	private boolean isSuspendedStatusChanged;
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public String getTimeString() {
		return timeString;
	}
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	public boolean isSuspendedStatusChanged() {
		return isSuspendedStatusChanged;
	}
	public void setSuspendedStatusChanged(boolean isSuspendedStatusChanged) {
		this.isSuspendedStatusChanged = isSuspendedStatusChanged;
	}
	// Added by A-8374 for ICRD-340405 ends
	// added for ICRD-213193 by A-7815
	private Collection<EmbargoLocalLanguageVO> localLanguageVOs;
	
	public String getApplicableTransactions() {
		return applicableTransactions;
	}

	public void setApplicableTransactions(String applicableTransactions) {
		this.applicableTransactions = applicableTransactions;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getComplianceType() {
		return complianceType;
	}

	public void setComplianceType(String complianceType) {
		this.complianceType = complianceType;
	}

	public Collection<EmbargoGeographicLevelVO> getGeographicLevels() {
		return geographicLevels;
	}

	public void setGeographicLevels(
			Collection<EmbargoGeographicLevelVO> geographicLevels) {
		this.geographicLevels = geographicLevels;
	}

	//Added by A-5160 for ICRD-27155
	private String airportCode;

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
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
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the destinationType.
	 */
	public String getDestinationType() {
		return destinationType;
	}

	/**
	 * @param destinationType The destinationType to set.
	 */
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	/**
	 * @return Returns the embargoDescription.
	 */
	public String getEmbargoDescription() {
		return embargoDescription;
	}

	/**
	 * @param embargoDescription The embargoDescription to set.
	 */
	public void setEmbargoDescription(String embargoDescription) {
		this.embargoDescription = embargoDescription;
	}

	/**
	 * @return Returns the embargoLevel.
	 */
	public String getEmbargoLevel() {
		return embargoLevel;
	}

	/**
	 * @param embargoLevel The embargoLevel to set.
	 */
	public void setEmbargoLevel(String embargoLevel) {
		this.embargoLevel = embargoLevel;
	}

	/**
	 * @return Returns the embargoReferenceNumber.
	 */
	public String getEmbargoReferenceNumber() {
		return embargoReferenceNumber;
	}

	/**
	 * @param embargoReferenceNumber The embargoReferenceNumber to set.
	 */
	public void setEmbargoReferenceNumber(String embargoReferenceNumber) {
		this.embargoReferenceNumber = embargoReferenceNumber;
	}

	/**
	 * @return Returns the endDate.
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

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

	/**
	 * @return Returns the operationalFlag.
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}

	/**
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the originType.
	 */
	public String getOriginType() {
		return originType;
	}

	/**
	 * @param originType The originType to set.
	 */
	public void setOriginType(String originType) {
		this.originType = originType;
	}

	/**
	 * @return Returns the parameters.
	 */
	public Collection<EmbargoParameterVO> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters The parameters to set.
	 */
	public void setParameters(Collection<EmbargoParameterVO> parameters) {
		this.parameters = parameters;
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
	 * @return Returns the startDate.
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the isSuspended.
	 */
	public boolean getIsSuspended() {
		return isSuspended;
	}

	/**
	 * @param isSuspended The isSuspended to set.
	 */
	public void setIsSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	/**
	 * @return Returns the isCool.
	 */
	public boolean getIsCool() {
		return isCool;
	}
	/**
	 * @param isCool The isCool to set.
	 */
	public void setIsCool(boolean isCool) {
		this.isCool = isCool;
	}

	/**
	 * @return Returns the isFrozen.
	 */
	public boolean getIsFrozen() {
		return isFrozen;
	}
	/**
	 * @param isFrozen The isFrozen to set.
	 */
	public void setIsFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	/**
	 * @return Returns the isCC.
	 */
	public boolean getIsCC() {
		return isCC;
	}
	/**
	 * @param isCC The isCC to set.
	 */
	public void setIsCC(boolean isCC) {
		this.isCC = isCC;
	}


	/**
	 * @return Returns the daysOfOperation.
	 */
	public String getDaysOfOperation() {
		return daysOfOperation;
	}

	/**
	 * @param daysOfOperation The daysOfOperation to set.
	 */
	public void setDaysOfOperation(String daysOfOperation) {
		this.daysOfOperation = daysOfOperation;
	}

	/**
	 * @return Returns the daysOfOperationApplicableOn.
	 */
	public String getDaysOfOperationApplicableOn() {
		return daysOfOperationApplicableOn;
	}

	/**
	 * @param daysOfOperationApplicableOn The daysOfOperationApplicableOn to set.
	 */
	public void setDaysOfOperationApplicableOn(String daysOfOperationApplicableOn) {
		this.daysOfOperationApplicableOn = daysOfOperationApplicableOn;
	}

	/**
	 * @return Returns the viaPointType.
	 */
	public String getViaPointType() {
		return viaPointType;
	}

	/**
	 * @param viaPointType The viaPointType to set.
	 */
	public void setViaPointType(String viaPointType) {
		this.viaPointType = viaPointType;
	}

	/**
	 * @return Returns the viaPoint.
	 */
	public String getViaPoint() {
		return viaPoint;
	}

	/**
	 * @param viaPoint The viaPoint to set.
	 */
	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
	}
	/**
	 * @param embargoPublishVO the embargoPublishVO to set
	 */
	public void setEmbargoPublishVO(EmbargoPublishVO embargoPublishVO) {
		this.embargoPublishVO = embargoPublishVO;
	}
	/**
	 * @return the embargoPublishVO
	 */
	public EmbargoPublishVO getEmbargoPublishVO() {
		return embargoPublishVO;
	}
	/**
	 * 	Getter for localLanguageVOs 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for : ICRD-213193
	 */
	public Collection<EmbargoLocalLanguageVO> getLocalLanguageVOs() {
		return localLanguageVOs;
	}
	/**
	 *  @param localLanguageVOs the localLanguageVOs to set
	 * 	Setter for localLanguageVOs 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for : ICRD-213193
	 */
	public void setLocalLanguageVOs(
			Collection<EmbargoLocalLanguageVO> localLanguageVOs) {
		this.localLanguageVOs = localLanguageVOs;
	}

	

}
