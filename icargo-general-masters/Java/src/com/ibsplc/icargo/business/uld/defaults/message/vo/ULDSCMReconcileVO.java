/*
 * ULDSCMReconcileVO.java Created on AUG 01, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**
 * @author A-2048
 */
public class ULDSCMReconcileVO extends AbstractVO {


    private String airportCode;
    private String companyCode;
    
    private int airlineIdentifier;
  
    private String sequenceNumber;
   
    private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	
	private LocalDate stockCheckDate;
	
	private String operationFlag;
	
	private boolean isCustomFilterPresent;
	private Collection<ULDSCMReconcileDetailsVO> reconcileDetailsVOs;
	
	private boolean hasUldErrors;
	
	private String messageSendFlag;

	private String facility;
	
	private String location;
	
	//Added by nisha on 16Jun08 for QF1019
	private String remarks;
	private boolean isFromFinalize;
	
	
	/**
	 * @return the isFromFinalize
	 */
	public boolean isFromFinalize() {
		return isFromFinalize;
	}


	/**
	 * @param isFromFinalize the isFromFinalize to set
	 */
	public void setFromFinalize(boolean isFromFinalize) {
		this.isFromFinalize = isFromFinalize;
	}


	/**
	 * @return String Returns the messageSendFlag.
	 */
	public String getMessageSendFlag() {
		return this.messageSendFlag;
	}


	/**
	 * @param messageSendFlag The messageSendFlag to set.
	 */
	public void setMessageSendFlag(String messageSendFlag) {
		this.messageSendFlag = messageSendFlag;
	}


	/**
	 * @return boolean Returns the hasUldErrors.
	 */
	public boolean isHasUldErrors() {
		return this.hasUldErrors;
	}


	/**
	 * @param hasUldErrors The hasUldErrors to set.
	 */
	public void setHasUldErrors(boolean hasUldErrors) {
		this.hasUldErrors = hasUldErrors;
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
	 * @return Collection<ULDSCMReconcileDetailsVO> Returns the reconcileDetailsVOs.
	 */
	public Collection<ULDSCMReconcileDetailsVO> getReconcileDetailsVOs() {
		return this.reconcileDetailsVOs;
	}


	/**
	 * @param reconcileDetailsVOs The reconcileDetailsVOs to set.
	 */
	public void setReconcileDetailsVOs(
			Collection<ULDSCMReconcileDetailsVO> reconcileDetailsVOs) {
		this.reconcileDetailsVOs = reconcileDetailsVOs;
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
	 * @return LocalDate Returns the stockCheckDate.
	 */
	public LocalDate getStockCheckDate() {
		return this.stockCheckDate;
	}


	/**
	 * @param stockCheckDate The stockCheckDate to set.
	 */
	public void setStockCheckDate(LocalDate stockCheckDate) {
		this.stockCheckDate = stockCheckDate;
	}


	/**
	 * @return int Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return this.airlineIdentifier;
	}


	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
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
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}


	/**
	 * @param facility the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the isCustomFilterPresent
	 */
	public boolean isCustomFilterPresent() {
		return isCustomFilterPresent;
	}
	/**
	 * @param isCustomFilterPresent the isCustomFilterPresent to set
	 */
	public void setCustomFilterPresent(boolean isCustomFilterPresent) {
		this.isCustomFilterPresent = isCustomFilterPresent;
	}
	
    
    
}
