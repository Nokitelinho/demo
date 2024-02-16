/*
 * LoyaltyProgrammeVO.java Created on Dec 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;



import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class LoyaltyProgrammeVO  extends AbstractVO {

	/**
     * module
     */
	public static final String MODULE ="customermanagement"; 
	/**
	 * submodule
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * entity
	 */
	public static final String ENTITY ="customermanagement.defaults.LoyaltyProgramme";
	
    private String companyCode;
    private String loyaltyProgrammeCode;
    private String loyaltyProgrammeDesc;
    private double entryPoints;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double expiryPeriod;
    private String expiryDuration;
    private String activeStatus;
    private String attibute;
	private String units;
	private double points;
	private double amount;
	private String operationFlag;
    private Collection<LoyaltyParameterVO> loyaltyParameterVOs;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private boolean dateChanged;
	private LocalDate currentDate;
	
	/**
	 * @return Returns the activeStatus.
	 */
	public String getActiveStatus() {
		return activeStatus;
	}
	/**
	 * @param activeStatus The activeStatus to set.
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
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
	 * @return Returns the entryPoints.
	 */
	public double getEntryPoints() {
		return entryPoints;
	}
	/**
	 * @param entryPoints The entryPoints to set.
	 */
	public void setEntryPoints(double entryPoints) {
		this.entryPoints = entryPoints;
	}
	/**
	 * @return Returns the expiryDuration.
	 */
	public String getExpiryDuration() {
		return expiryDuration;
	}
	/**
	 * @param expiryDuration The expiryDuration to set.
	 */
	public void setExpiryDuration(String expiryDuration) {
		this.expiryDuration = expiryDuration;
	}
	/**
	 * @return Returns the expiryPeriod.
	 */
	public double getExpiryPeriod() {
		return expiryPeriod;
	}
	/**
	 * @param expiryPeriod The expiryPeriod to set.
	 */
	public void setExpiryPeriod(double expiryPeriod) {
		this.expiryPeriod = expiryPeriod;
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
	 * @return Returns the loyaltyParameterVOs.
	 */
	public Collection<LoyaltyParameterVO> getLoyaltyParameterVOs() {
		return loyaltyParameterVOs;
	}
	/**
	 * @param loyaltyParameterVOs The loyaltyParameterVOs to set.
	 */
	public void setLoyaltyParameterVOs(
			Collection<LoyaltyParameterVO> loyaltyParameterVOs) {
		this.loyaltyParameterVOs = loyaltyParameterVOs;
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
	 * @return Returns the loyaltyProgrammeDesc.
	 */
	public String getLoyaltyProgrammeDesc() {
		return loyaltyProgrammeDesc;
	}
	/**
	 * @param loyaltyProgrammeDesc The loyaltyProgrammeDesc to set.
	 */
	public void setLoyaltyProgrammeDesc(String loyaltyProgrammeDesc) {
		this.loyaltyProgrammeDesc = loyaltyProgrammeDesc;
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
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the attibute.
	 */
	public String getAttibute() {
		return attibute;
	}
	/**
	 * @param attibute The attibute to set.
	 */
	public void setAttibute(String attibute) {
		this.attibute = attibute;
	}
	/**
	 * @return Returns the points.
	 */
	public double getPoints() {
		return points;
	}
	/**
	 * @param points The points to set.
	 */
	public void setPoints(double points) {
		this.points = points;
	}
	/**
	 * @return Returns the units.
	 */
	public String getUnits() {
		return units;
	}
	/**
	 * @param units The units to set.
	 */
	public void setUnits(String units) {
		this.units = units;
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
	 * @return Returns the dateChanged.
	 */
	public boolean isDateChanged() {
		return dateChanged;
	}
	/**
	 * @param dateChanged The dateChanged to set.
	 */
	public void setDateChanged(boolean dateChanged) {
		this.dateChanged = dateChanged;
	}
	/**
	 * @return Returns the currentDate.
	 */
	public LocalDate getCurrentDate() {
		return currentDate;
	}
	/**
	 * @param currentDate The currentDate to set.
	 */
	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
	}

}
