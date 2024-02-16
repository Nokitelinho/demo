/*
 * ExceptionInInvoiceVO.java Created on Feb20, 2007
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
 * @author A-2391 
 */
public class ExceptionInInvoiceVO extends AbstractVO{
	private String companyCode;	
	
	private String operationFlag;
	private int airlineIdentifier;
	private String airlineCode;
	private String invoiceNumber;
	private String clearancePeriod;
	private String remark;
	private String exceptionStatus;
	private String memoStatus;
	private Double provisionalAmount;
	private Double reportedAmount;
	private Double differenceAmount;
	private String contractCurrency;
	private String interlineBillingType;
	private String memoCode;
	private LocalDate fromdate;
	private LocalDate toDate;
	private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	
	
	private Money provAmt;
	private Money reportedAmt;
	private Money diffAmt;
	
	//Added as part of ICRD-265471
	private String dsnIdr;
	private long malseqnum;
	

	public String getDsnIdr() {
		return dsnIdr;
	}
	public void setDsnIdr(String dsnIdr) {
		this.dsnIdr = dsnIdr;
	}
	
	public long getMalseqnum() {
		return malseqnum;
	}
	public void setMalseqnum(long malseqnum) {
		this.malseqnum = malseqnum;
	}
	/*public String getMalseqnum() {
		return malseqnum;
	}
	public void setMalseqnum(String malseqnum) {
		this.malseqnum = malseqnum;
	}*/
	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return Returns the fromdate.
	 */
	public LocalDate getFromdate() {
		return fromdate;
	}
	/**
	 * @param fromdate The fromdate to set.
	 */
	public void setFromdate(LocalDate fromdate) {
		this.fromdate = fromdate;
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
	 * @return Returns the memoCode.
	 */
	public String getMemoCode() {
		return memoCode;
	}
	/**
	 * @param memoCode The memoCode to set.
	 */
	public void setMemoCode(String memoCode) {
		this.memoCode = memoCode;
	}
	/**
	 * @return Returns the interlineBillingType.
	 */
	public String getInterlineBillingType() {
		return interlineBillingType;
	}
	/**
	 * @param interlineBillingType The interlineBillingType to set.
	 */
	public void setInterlineBillingType(String interlineBillingType) {
		this.interlineBillingType = interlineBillingType;
	}
	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	
	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}
	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
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
	 * @return Returns the contractCurrency.
	 */
	public String getContractCurrency() {
		return contractCurrency;
	}
	/**
	 * @param contractCurrency The contractCurrency to set.
	 */
	public void setContractCurrency(String contractCurrency) {
		this.contractCurrency = contractCurrency;
	}
	/**
	 * @return Returns the differenceAmount.
	 */
	public Double getDifferenceAmount() {
		return differenceAmount;
	}
	/**
	 * @param differenceAmount The differenceAmount to set.
	 */
	public void setDifferenceAmount(Double differenceAmount) {
		this.differenceAmount = differenceAmount;
	}
	/**
	 * @return Returns the exceptionStatus.
	 */
	public String getExceptionStatus() {
		return exceptionStatus;
	}
	/**
	 * @param exceptionStatus The exceptionStatus to set.
	 */
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}
	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * @return Returns the memoStatus.
	 */
	public String getMemoStatus() {
		return memoStatus;
	}
	/**
	 * @param memoStatus The memoStatus to set.
	 */
	public void setMemoStatus(String memoStatus) {
		this.memoStatus = memoStatus;
	}
	/**
	 * @return Returns the provisionalAmount.
	 */
	public Double getProvisionalAmount() {
		return provisionalAmount;
	}
	/**
	 * @param provisionalAmount The provisionalAmount to set.
	 */
	public void setProvisionalAmount(Double provisionalAmount) {
		this.provisionalAmount = provisionalAmount;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the reportedAmount.
	 */
	public Double getReportedAmount() {
		return reportedAmount;
	}
	/**
	 * @param reportedAmount The reportedAmount to set.
	 */
	public void setReportedAmount(Double reportedAmount) {
		this.reportedAmount = reportedAmount;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
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
	public Money getDiffAmt() {
		return diffAmt;
	}
	public void setDiffAmt(Money diffAmt) {
		this.diffAmt = diffAmt;
	}
	public Money getProvAmt() {
		return provAmt;
	}
	public void setProvAmt(Money provAmt) {
		this.provAmt = provAmt;
	}
	public Money getReportedAmt() {
		return reportedAmt;
	}
	public void setReportedAmt(Money reportedAmt) {
		this.reportedAmt = reportedAmt;
	}
	
	

}
