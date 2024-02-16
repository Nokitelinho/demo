/*
 * InvoiceInFormOneVO.java Created on July 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3456
 * 
 */
public class InvoiceInFormOneVO extends AbstractVO {

	private String invoiceNumber;

	private LocalDate invoiceDate;

	/**
	 * @author a-3447
	 */
	private double totalBaseAmt;

	private Money billingTotalAmt;

	private Money totMisAmt;

	private Money totalBaseAmount;

	private double listingTotalAmt;

	private double billingTotAmt;

	private double totalMisAmt;

	/**
	 * @author a-3447
	 */

	private String lastUpdateUser;

	private LocalDate lastUpdateTime;

	private String lastUpdateUserBlg;

	private LocalDate lastUpdateTimeBlg;

	private String companyCode;

	private String billingCurrency;

	private String lstCurCode;

	private Double exgRate;

	private String invStatus;

	private String invStatusdisplay;

	private String invFormoneStatusdisplay;

	private String formOneStatus;

	private String operationFlag;

	private int airlineIdentifier;

	private String clearancePeriod;

	private String intBlgTyp;

	private String classType;

	private String airlineCode;

	private Money listingTotAmount;

	/**
	 * Function Point
	 */
	public static final String FUNCTIONPOINT_INWARDBILLING = "MI";

	/**
	 * @return the airlineCode
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the classType.
	 */
	public String getClassType() {
		return classType;
	}

	/**
	 * @param classType
	 *            The classType to set.
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}

	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod
	 *            The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the formOneStatus.
	 */
	public String getFormOneStatus() {
		return formOneStatus;
	}

	/**
	 * @param formOneStatus
	 *            The formOneStatus to set.
	 */
	public void setFormOneStatus(String formOneStatus) {
		this.formOneStatus = formOneStatus;
	}

	/**
	 * @return Returns the invStatus.
	 */
	public String getInvStatus() {
		return invStatus;
	}

	/**
	 * @param invStatus
	 *            The invStatus to set.
	 */
	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	/**
	 * @return Returns the exgRate.
	 */
	public Double getExgRate() {
		return exgRate;
	}

	/**
	 * @param exgRate
	 *            The exgRate to set.
	 */
	public void setExgRate(Double exgRate) {
		this.exgRate = exgRate;
	}

	/**
	 * @return Returns the lstCurCode.
	 */
	public String getLstCurCode() {
		return lstCurCode;
	}

	/**
	 * @param lstCurCode
	 *            The lstCurCode to set.
	 */
	public void setLstCurCode(String lstCurCode) {
		this.lstCurCode = lstCurCode;
	}

	/**
	 * @return Returns the billingCurrency.
	 */
	public String getBillingCurrency() {
		return billingCurrency;
	}

	/**
	 * @param billingCurrency
	 *            The billingCurrency to set.
	 */
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
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
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the invoiceDate.
	 */
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate
	 *            The invoiceDate to set.
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the intBlgTyp.
	 */
	public String getIntBlgTyp() {
		return intBlgTyp;
	}

	/**
	 * @param intBlgTyp
	 *            The intBlgTyp to set.
	 */
	public void setIntBlgTyp(String intBlgTyp) {
		this.intBlgTyp = intBlgTyp;
	}

	/**
	 * @return the billingTotalAmt
	 */
	public Money getBillingTotalAmt() {
		return billingTotalAmt;
	}

	/**
	 * @param billingTotalAmt
	 *            the billingTotalAmt to set
	 */
	public void setBillingTotalAmt(Money billingTotalAmt) {
		this.billingTotalAmt = billingTotalAmt;
	}

	/**
	 * @return the listingTotalAmt
	 */
	public double getListingTotalAmt() {
		return listingTotalAmt;
	}

	/**
	 * @param listingTotalAmt
	 *            the listingTotalAmt to set
	 */
	public void setListingTotalAmt(double listingTotalAmt) {
		this.listingTotalAmt = listingTotalAmt;
	}

	/**
	 * @return the totalBaseAmt
	 */
	public double getTotalBaseAmt() {
		return totalBaseAmt;
	}

	/**
	 * @param totalBaseAmt
	 *            the totalBaseAmt to set
	 */
	public void setTotalBaseAmt(double totalBaseAmt) {
		this.totalBaseAmt = totalBaseAmt;
	}

	/**
	 * @return Returns the totMisAmt.
	 */
	public Money getTotMisAmt() {
		return totMisAmt;
	}

	/**
	 * @param totMisAmt
	 *            The totMisAmt to set.
	 */
	public void setTotMisAmt(Money totMisAmt) {
		this.totMisAmt = totMisAmt;
	}

	/**
	 * @return the billingTotAmt
	 */
	public double getBillingTotAmt() {
		return billingTotAmt;
	}

	/**
	 * @param billingTotAmt
	 *            the billingTotAmt to set
	 */
	public void setBillingTotAmt(double billingTotAmt) {
		this.billingTotAmt = billingTotAmt;
	}

	/**
	 * @return the totalMisAmt
	 */
	public double getTotalMisAmt() {
		return totalMisAmt;
	}

	/**
	 * @param totalMisAmt
	 *            the totalMisAmt to set
	 */
	public void setTotalMisAmt(double totalMisAmt) {
		this.totalMisAmt = totalMisAmt;
	}

	/**
	 * @return the invFormoneStatusdisplay
	 */
	public String getInvFormoneStatusdisplay() {
		return invFormoneStatusdisplay;
	}

	/**
	 * @param invFormoneStatusdisplay
	 *            the invFormoneStatusdisplay to set
	 */
	public void setInvFormoneStatusdisplay(String invFormoneStatusdisplay) {
		this.invFormoneStatusdisplay = invFormoneStatusdisplay;
	}

	/**
	 * @return the invStatusdisplay
	 */
	public String getInvStatusdisplay() {
		return invStatusdisplay;
	}

	/**
	 * @param invStatusdisplay
	 *            the invStatusdisplay to set
	 */
	public void setInvStatusdisplay(String invStatusdisplay) {
		this.invStatusdisplay = invStatusdisplay;
	}

	/**
	 * @return the lastUpdateTimeBlg
	 */
	public LocalDate getLastUpdateTimeBlg() {
		return lastUpdateTimeBlg;
	}

	/**
	 * @param lastUpdateTimeBlg
	 *            the lastUpdateTimeBlg to set
	 */
	public void setLastUpdateTimeBlg(LocalDate lastUpdateTimeBlg) {
		this.lastUpdateTimeBlg = lastUpdateTimeBlg;
	}

	/**
	 * @return the lastUpdateUserBlg
	 */
	public String getLastUpdateUserBlg() {
		return lastUpdateUserBlg;
	}

	/**
	 * @param lastUpdateUserBlg
	 *            the lastUpdateUserBlg to set
	 */
	public void setLastUpdateUserBlg(String lastUpdateUserBlg) {
		this.lastUpdateUserBlg = lastUpdateUserBlg;
	}

	/**
	 * @return the listingTotAmount
	 */
	public Money getListingTotAmount() {
		return listingTotAmount;
	}

	/**
	 * @param listingTotAmount
	 *            the listingTotAmount to set
	 */
	public void setListingTotAmount(Money listingTotAmount) {
		this.listingTotAmount = listingTotAmount;
	}

	/**
	 * @return the totalBaseAmount
	 */
	public Money getTotalBaseAmount() {
		return totalBaseAmount;
	}

	/**
	 * @param totalBaseAmount
	 *            the totalBaseAmount to set
	 */
	public void setTotalBaseAmount(Money totalBaseAmount) {
		this.totalBaseAmount = totalBaseAmount;
	}

}
