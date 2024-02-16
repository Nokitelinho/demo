/*
 * CustomerGroupLoyaltyProgrammeVO.java Created on APR 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.profile.vo;



import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2048
 *
 */
public class CustomerGroupLoyaltyProgrammeVO  extends AbstractVO{

    private String companyCode;
    private String groupCode;
    private String loyaltyProgramCode;
    private int sequenceNumber;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDate loyaltyFromDate;
    private LocalDate loyaltyToDate;
    private int pointsAccruded;
    private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private String operationalFlag;
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
	 * @return LocalDate Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return this.fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return String Returns the groupCode.
	 */
	public String getGroupCode() {
		return this.groupCode;
	}
	/**
	 * @param groupCode The groupCode to set.
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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
	 * @return String Returns the loyaltyProgramCode.
	 */
	public String getLoyaltyProgramCode() {
		return this.loyaltyProgramCode;
	}
	/**
	 * @param loyaltyProgramCode The loyaltyProgramCode to set.
	 */
	public void setLoyaltyProgramCode(String loyaltyProgramCode) {
		this.loyaltyProgramCode = loyaltyProgramCode;
	}
	/**
	 * @return String Returns the operationalFlag.
	 */
	public String getOperationalFlag() {
		return this.operationalFlag;
	}
	/**
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 * @return int Returns the pointsAccruded.
	 */
	public int getPointsAccruded() {
		return this.pointsAccruded;
	}
	/**
	 * @param pointsAccruded The pointsAccruded to set.
	 */
	public void setPointsAccruded(int pointsAccruded) {
		this.pointsAccruded = pointsAccruded;
	}
	/**
	 * @return int Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return LocalDate Returns the toDate.
	 */
	public LocalDate getToDate() {
		return this.toDate;
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
