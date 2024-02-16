/*
 * GPAReportingClaimDetailsVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1453
 * 
 */

public class GPAReportingClaimDetailsVO extends AbstractVO {

	/**
	 * companyCode
	 */
	private String companyCode;

	/**
	 * poaCode
	 */
	private String poaCode;

	/**
	 * billingBasis
	 */
	private String billingBasis;

	/**
	 * reportingPeriodFrom
	 */
	private LocalDate reportingPeriodFrom;

	/**
	 * reportingPeriodTo
	 */
	private LocalDate reportingPeriodTo;

	/**
	 * exceptionSequenceNumber
	 */
	private int exceptionSequenceNumber;

	/**
	 * exceptionCode
	 */
	private String exceptionCode;

	/**
	 * assignedUser
	 */
	private String assignedUser;

	/**
	 * assignedDate
	 */
	private LocalDate assignedDate;

	/**
	 * rsolvedDate
	 */
	private LocalDate resolvedDate;

	/**
	 * basisType
	 */
	private String basisType;

	/**
	 * year
	 */
	private String year;

	/**
	 * originOfficeOfExchange
	 */
	private String originOfficeOfExchange;

	/**
	 * destOfficeOfExchange
	 */
	private String destOfficeOfExchange;

	/**
	 * mailSubClass
	 */
	private String mailSubClass;

	/**
	 * mailCategoryCode
	 */
	private String mailCategoryCode;

	/**
	 * dsnNumber
	 */
	private String dsnNumber;

	/**
	 * actualWeight
	 */
	private double actualWeight;

	/**
	 * reportedWeight
	 */
	private double reportedWeight;

	/**
	 * actualRate
	 */
	private double actualRate;

	/**
	 * reportedRate
	 */
	private double reportedRate;

	/**
	 * lastUpdatedUser
	 */
	private String lastUpdatedUser;

	/**
	 * lastUpdatedime
	 */
	private LocalDate lastUpdatedime;

	/**
	 * operation flag
	 */
	private String operationFlag;

	/**
	 * reportingFrom
	 */
	private String reportingFromString;

	/**
	 * reportingTo
	 */
	private String reportingToString;

	/**
	 * poaName
	 */
	private String poaName;

	/**
	 * countryCode
	 */
	private String countryCode;

	/*
	 * added for reports
	 */
	/**
	 * totalExceptions
	 */
	private int totalExceptions;

	/**
	 * totalPendingExceptions
	 */
	private int totalPendingExceptions;

	/**
	 * totalResolvedExceptions
	 */
	private int totalResolvedExceptions;

	/**
	 * cca ref num
	 */
	private String ccaRefNum;

	/**
	 * cca status
	 */

	private String ccaStatus;

	/**
	 * actualCharge
	 */
	private double actualCharge;

	/**
	 * reportedCharge
	 */
	private double reportedCharge;

	/**
	 * @return the ccaRefNum
	 */
	public String getCcaRefNum() {
		return ccaRefNum;
	}

	/**
	 * @param ccaRefNum
	 *            the ccaRefNum to set
	 */
	public void setCcaRefNum(String ccaRefNum) {
		this.ccaRefNum = ccaRefNum;
	}

	/**
	 * @return the ccaStatus
	 */
	public String getCcaStatus() {
		return ccaStatus;
	}

	/**
	 * @param ccaStatus
	 *            the ccaStatus to set
	 */
	public void setCcaStatus(String ccaStatus) {
		this.ccaStatus = ccaStatus;
	}

	// ----------------------------------------------------------
	/**
	 * @return Returns the reportingFromString.
	 */
	public String getReportingFromString() {
		return reportingFromString;
	}

	/**
	 * @param reportingFromString
	 *            The reportingFromString to set.
	 */
	public void setReportingFromString(String reportingFromString) {
		this.reportingFromString = reportingFromString;
	}

	/**
	 * @return Returns the reportingToString.
	 */
	public String getReportingToString() {
		return reportingToString;
	}

	/**
	 * @param reportingToString
	 *            The reportingToString to set.
	 */
	public void setReportingToString(String reportingToString) {
		this.reportingToString = reportingToString;
	}

	/**
	 * @return Returns the actualRate.
	 */
	public double getActualRate() {
		return actualRate;
	}

	/**
	 * @param actualRate
	 *            The actualRate to set.
	 */
	public void setActualRate(double actualRate) {
		this.actualRate = actualRate;
	}

	/**
	 * @return Returns the actualWeight.
	 */
	public double getActualWeight() {
		return actualWeight;
	}

	/**
	 * @param actualWeight
	 *            The actualWeight to set.
	 */
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * @return Returns the reportedRate.
	 */
	public double getReportedRate() {
		return reportedRate;
	}

	/**
	 * @param reportedRate
	 *            The reportedRate to set.
	 */
	public void setReportedRate(double reportedRate) {
		this.reportedRate = reportedRate;
	}

	/**
	 * @return Returns the reportedWeight.
	 */
	public double getReportedWeight() {
		return reportedWeight;
	}

	/**
	 * @param reportedWeight
	 *            The reportedWeight to set.
	 */
	public void setReportedWeight(double reportedWeight) {
		this.reportedWeight = reportedWeight;
	}

	/**
	 * @return Returns the assignedDate.
	 */
	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate
	 *            The assignedDate to set.
	 */
	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return Returns the assignedUser.
	 */
	public String getAssignedUser() {
		return assignedUser;
	}

	/**
	 * @param assignedUser
	 *            The assignedUser to set.
	 */
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	/**
	 * @return Returns the basisType.
	 */
	public String getBasisType() {
		return basisType;
	}

	/**
	 * @param basisType
	 *            The basisType to set.
	 */
	public void setBasisType(String basisType) {
		this.basisType = basisType;
	}

	/**
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis
	 *            The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
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
	 * @return Returns the destOfficeOfExchange.
	 */
	public String getDestOfficeOfExchange() {
		return destOfficeOfExchange;
	}

	/**
	 * @param destOfficeOfExchange
	 *            The destOfficeOfExchange to set.
	 */
	public void setDestOfficeOfExchange(String destOfficeOfExchange) {
		this.destOfficeOfExchange = destOfficeOfExchange;
	}

	/**
	 * @return Returns the dsnNumber.
	 */
	public String getDsnNumber() {
		return dsnNumber;
	}

	/**
	 * @param dsnNumber
	 *            The dsnNumber to set.
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}

	/**
	 * @return Returns the exceptionCode.
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * @param exceptionCode
	 *            The exceptionCode to set.
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * @return Returns the exceptionSequenceNumber.
	 */
	public int getExceptionSequenceNumber() {
		return exceptionSequenceNumber;
	}

	/**
	 * @param exceptionSequenceNumber
	 *            The exceptionSequenceNumber to set.
	 */
	public void setExceptionSequenceNumber(int exceptionSequenceNumber) {
		this.exceptionSequenceNumber = exceptionSequenceNumber;
	}

	/**
	 * @return Returns the lastUpdatedime.
	 */
	public LocalDate getLastUpdatedime() {
		return lastUpdatedime;
	}

	/**
	 * @param lastUpdatedime
	 *            The lastUpdatedime to set.
	 */
	public void setLastUpdatedime(LocalDate lastUpdatedime) {
		this.lastUpdatedime = lastUpdatedime;
	}

	/**
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubClass.
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass
	 *            The mailSubClass to set.
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return Returns the originOfficeOfExchange.
	 */
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	/**
	 * @param originOfficeOfExchange
	 *            The originOfficeOfExchange to set.
	 */
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode
	 *            The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the reportingPeriodFrom.
	 */
	public LocalDate getReportingPeriodFrom() {
		return reportingPeriodFrom;
	}

	/**
	 * @param reportingPeriodFrom
	 *            The reportingPeriodFrom to set.
	 */
	public void setReportingPeriodFrom(LocalDate reportingPeriodFrom) {
		this.reportingPeriodFrom = reportingPeriodFrom;
	}

	/**
	 * @return Returns the reportingPeriodTo.
	 */
	public LocalDate getReportingPeriodTo() {
		return reportingPeriodTo;
	}

	/**
	 * @param reportingPeriodTo
	 *            The reportingPeriodTo to set.
	 */
	public void setReportingPeriodTo(LocalDate reportingPeriodTo) {
		this.reportingPeriodTo = reportingPeriodTo;
	}

	/**
	 * @return Returns the resolvedDate.
	 */
	public LocalDate getResolvedDate() {
		return resolvedDate;
	}

	/**
	 * @param resolvedDate
	 *            The resolvedDate to set.
	 */
	public void setResolvedDate(LocalDate resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
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
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the poaName.
	 */
	public String getPoaName() {
		return poaName;
	}

	/**
	 * @param poaName
	 *            The poaName to set.
	 */
	public void setPoaName(String poaName) {
		this.poaName = poaName;
	}

	/**
	 * @return Returns the totalExceptions.
	 */
	public int getTotalExceptions() {
		return totalExceptions;
	}

	/**
	 * @param totalExceptions
	 *            The totalExceptions to set.
	 */
	public void setTotalExceptions(int totalExceptions) {
		this.totalExceptions = totalExceptions;
	}

	/**
	 * @return Returns the totalPendingExceptions.
	 */
	public int getTotalPendingExceptions() {
		return totalPendingExceptions;
	}

	/**
	 * @param totalPendingExceptions
	 *            The totalPendingExceptions to set.
	 */
	public void setTotalPendingExceptions(int totalPendingExceptions) {
		this.totalPendingExceptions = totalPendingExceptions;
	}

	/**
	 * @return Returns the totalResolvedExceptions.
	 */
	public int getTotalResolvedExceptions() {
		return totalResolvedExceptions;
	}

	/**
	 * @param totalResolvedExceptions
	 *            The totalResolvedExceptions to set.
	 */
	public void setTotalResolvedExceptions(int totalResolvedExceptions) {
		this.totalResolvedExceptions = totalResolvedExceptions;
	}

	/**
	 * @return the actualCharge
	 */
	public double getActualCharge() {
		return actualCharge;
	}

	/**
	 * @param actualCharge
	 *            the actualCharge to set
	 */
	public void setActualCharge(double actualCharge) {
		this.actualCharge = actualCharge;
	}

	/**
	 * @return the reportedCharge
	 */
	public double getReportedCharge() {
		return reportedCharge;
	}

	/**
	 * @param reportedCharge
	 *            the reportedCharge to set
	 */
	public void setReportedCharge(double reportedCharge) {
		this.reportedCharge = reportedCharge;
	}
}
