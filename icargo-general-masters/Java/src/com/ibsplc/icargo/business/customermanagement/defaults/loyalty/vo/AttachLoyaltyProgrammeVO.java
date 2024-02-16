/*
 * AttachLoyaltyProgrammeVO.java Created on Dec 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;



import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class AttachLoyaltyProgrammeVO  extends AbstractVO implements Serializable{
    
	/**
	 * @param String
	 */
	public static final String MODULE ="customermanagement";
	/**
	 * @param String
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * @param String
	 */
	public static final String ENTITY ="customermanagement.defaults.loyalty.AttachLoyaltyProgramme";
	
	
	
	
    private String companyCode;
    private String customerCode;
    private String loyaltyProgrammeCode;
    private String sequenceNumber;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDate loyaltyFromDate;
    private LocalDate loyaltyToDate;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	
	private String groupFlag;
	
	private String operationFlag;
	
	private boolean entryPoints;
	
	/**
	 * 
	 * @return
	 */
	public boolean isEntryPoints() {
		return entryPoints;
	}
	/**
	 * 
	 * @param entryPoints
	 */
	public void setEntryPoints(boolean entryPoints) {
		this.entryPoints = entryPoints;
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
	 * @return String Returns the groupFlag.
	 */
	public String getGroupFlag() {
		return this.groupFlag;
	}
	/**
	 * @param groupFlag The groupFlag to set.
	 */
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
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
	 * @return Returns the customerCode.
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
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
	 * @return Returns the loyaltyProgrammeCode.
	 */
	public String getLoyaltyProgrammeCode() {
		return loyaltyProgrammeCode;
	}
	/**
	 * @param loyaltyProgrammeCode The loyaltyProgrammeCode to set.
	 */
	public void setLoyaltyProgrammeCode(String loyaltyProgrammeCode) {
		this.loyaltyProgrammeCode = loyaltyProgrammeCode;
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
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return Returns the loyaltyFromDate.
	 */
	public LocalDate getLoyaltyFromDate() {
		return loyaltyFromDate;
	}
	/**
	 * @param loyaltyFromDate The loyaltyFromDate to set.
	 */
	public void setLoyaltyFromDate(LocalDate loyaltyFromDate) {
		this.loyaltyFromDate = loyaltyFromDate;
	}
	/**
	 * @return Returns the loyaltyToDate.
	 */
	public LocalDate getLoyaltyToDate() {
		return loyaltyToDate;
	}
	/**
	 * @param loyaltyToDate The loyaltyToDate to set.
	 */
	public void setLoyaltyToDate(LocalDate loyaltyToDate) {
		this.loyaltyToDate = loyaltyToDate;
	}

}
