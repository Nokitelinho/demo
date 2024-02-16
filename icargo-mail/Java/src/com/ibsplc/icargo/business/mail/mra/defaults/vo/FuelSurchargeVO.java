/*
 * FuelSurchargeVO.java created on APR 23,2009	
 *Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2391
 *
 */
public class FuelSurchargeVO extends AbstractVO{
	
	
    private String companyCode;
   
	private String operationFlag;
	
	private String gpaCode;
	private String gpaName;
	private String country;

	private String rateCharge;
	private String values;
	private String currency;
	private LocalDate validityStartDate;
	private LocalDate validityEndDate;
	private int seqNum;
	private String lastUpdateUser;
    private LocalDate lastUpdateTime;
    private int  fuelCount;
	/**
	 * @return Returns the fuelCount.
	 */
	public int getFuelCount() {
		return fuelCount;
	}
	/**
	 * @param fuelCount The fuelCount to set.
	 */
	public void setFuelCount(int fuelCount) {
		this.fuelCount = fuelCount;
	}
	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * @return Returns the seqNum.
	 */
	public int getSeqNum() {
		return seqNum;
	}
	/**
	 * @param seqNum The seqNum to set.
	 */
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
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
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}
	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
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
	 * @return Returns the rateCharge.
	 */
	public String getRateCharge() {
		return rateCharge;
	}
	/**
	 * @param rateCharge The rateCharge to set.
	 */
	public void setRateCharge(String rateCharge) {
		this.rateCharge = rateCharge;
	}
	/**
	 * @return Returns the validityEndDate.
	 */
	public LocalDate getValidityEndDate() {
		return validityEndDate;
	}
	/**
	 * @param validityEndDate The validityEndDate to set.
	 */
	public void setValidityEndDate(LocalDate validityEndDate) {
		this.validityEndDate = validityEndDate;
	}
	/**
	 * @return Returns the validityStartDate.
	 */
	public LocalDate getValidityStartDate() {
		return validityStartDate;
	}
	/**
	 * @param validityStartDate The validityStartDate to set.
	 */
	public void setValidityStartDate(LocalDate validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	/**
	 * @return Returns the values.
	 */
	public String getValues() {
		return values;
	}
	/**
	 * @param values The values to set.
	 */
	public void setValues(String values) {
		this.values = values;
	}	
	
}
