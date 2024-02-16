/*
 * RejectionMemoVO.java Created on may 18,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2391
 * 
 */

public class RejectionMemoVO extends AbstractVO {

	private String companycode;

	private int airlineIdentifier;

	private String serialNumber;

	private String airlineCode;

	private String airlineNumber;

	private String yourReferenceNumber;

	private String monthOfTrans;

	private String memoCode;

	private String airlineName;

	private String inwardInvoiceNumber;

	private LocalDate inwardInvoiceDate;

	private String inwardClearancePeriod;

	private String outwardInvoiceNumber;

	private LocalDate outwardInvoiceDate;

	private String outwardClearancePeriod;

	private String billingCurrencyCode;

	private String monthOfClearance;

	private String clearanceCurrencyCode;

	private String contractCurrencyCode;

	private Double contractBilledAmount;

	private Double contractAcceptedAmount;

	private Double contractRejectedAmount;

	private Double clearanceBilledAmount;

	private Double clearanceAcceptedAmount;

	private Double clearanceRejectedAmount;

	private Money billingBilledAmount;

	private Money billingAcceptedAmount;

	private Money billingRejectedAmount;

	private Double contractBillingExchangeRate;

	private Double billingClearanceExchangeRate;

	private String requestAuthorisationIndicator;

	private String requestAuthorisationReference;

	private LocalDate requestAuthorisationDate;

	private String duplicateBillingIndicator;

	private String duplicateBillingInvoiceNumber;

	private LocalDate duplicateBillingInvoiceDate;

	private String chargeNotCoveredByContractIndicator;

	private String outTimeLimitsForBillingIndicator;

	private String chargeNotConvertedToContractIndicator;

	private String noApprovalIndicator;

	private String noReceiptIndicator;

	private String incorrectExchangeRateIndicator;

	private String otherIndicator;

	private String memoStatus;

	private String remarks;

	private String acctTxnIdr;

	// for report
	private String outMonthOfClearance;
	
	private String lastUpdatedUser;
	
	private LocalDate lastUpdatedTime;
	/**
	 * for integartion with Capture inv and Capture Form 1 screen 
	 */
	
	private String invoiceNumber;
	
	private String screenFlag;
	
	private String clearanceperiod;
	private String interlinebillingtype;
	private String classType;
	//Added by deepthi for list exception details
	
	private Double reportedAmount;
	private Double provisionalAmount;
	private String exceptionCode;
	private String dsn;
	private String billingBasis;
	private String csgDocNum;
	private int csgSeqNum;
	private String poaCode;
	
	//Added by Deepthi as a part of AirNZ926
	//Added By Deepthi for Rejection Memo Report
	private String origin;
	private String destination;
	private String sectorFrom;
	private String sectorTo;
	
	private LocalDate rejectedDate;
	//Added by A-7794 as part of MRA revamp
	private long mailSequenceNumber;
	
	private Collection <SisSupportingDocumentDetailsVO>  sisSupportingDocumentDetailsVOs;//Added For ICRD-265471 File attachment
	
	private String attachmentIndicator;//Added For ICRD-265471 File attachment 
	
	private String rejectionStage;
	
	/**
	 * @return the rejectedDate
	 */
	public LocalDate getRejectedDate() {
		return rejectedDate;
	}

	/**
	 * @param rejectedDate the rejectedDate to set
	 */
	public void setRejectedDate(LocalDate rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return the csgDocNum
	 */
	public String getCsgDocNum() {
		return csgDocNum;
	}

	/**
	 * @param csgDocNum the csgDocNum to set
	 */
	public void setCsgDocNum(String csgDocNum) {
		this.csgDocNum = csgDocNum;
	}

	/**
	 * @return the csgSeqNum
	 */
	public int getCsgSeqNum() {
		return csgSeqNum;
	}

	/**
	 * @param csgSeqNum the csgSeqNum to set
	 */
	public void setCsgSeqNum(int csgSeqNum) {
		this.csgSeqNum = csgSeqNum;
	}

	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	/**
	 * @return the provisionalAmount
	 */
	public Double getProvisionalAmount() {
		return provisionalAmount;
	}

	/**
	 * @param provisionalAmount the provisionalAmount to set
	 */
	public void setProvisionalAmount(Double provisionalAmount) {
		this.provisionalAmount = provisionalAmount;
	}

	/**
	 * @return the reportedAmount
	 */
	public Double getReportedAmount() {
		return reportedAmount;
	}

	/**
	 * @param reportedAmount the reportedAmount to set
	 */
	public void setReportedAmount(Double reportedAmount) {
		this.reportedAmount = reportedAmount;
	}

	/**
	 * @return the exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * @param exceptionCode the exceptionCode to set
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * @return the classType
	 */
	public String getClassType() {
		return classType;
	}

	/**
	 * @param classType the classType to set
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

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
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the serialNumber.
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber
	 *            The serialNumber to set.
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
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
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the airlineNumber.
	 */
	public String getAirlineNumber() {
		return airlineNumber;
	}

	/**
	 * @param airlineNumber
	 *            The airlineNumber to set.
	 */
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
	}

	/**
	 * @return Returns the billingClearanceExchangeRate.
	 */
	public Double getBillingClearanceExchangeRate() {
		return billingClearanceExchangeRate;
	}

	/**
	 * @param billingClearanceExchangeRate
	 *            The billingClearanceExchangeRate to set.
	 */
	public void setBillingClearanceExchangeRate(
			Double billingClearanceExchangeRate) {
		this.billingClearanceExchangeRate = billingClearanceExchangeRate;
	}

	/**
	 * @return Returns the billingCurrencyCode.
	 */
	public String getBillingCurrencyCode() {
		return billingCurrencyCode;
	}

	/**
	 * @param billingCurrencyCode
	 *            The billingCurrencyCode to set.
	 */
	public void setBillingCurrencyCode(String billingCurrencyCode) {
		this.billingCurrencyCode = billingCurrencyCode;
	}
	/**
	 * @return the billingAcceptedAmount
	 */
	public Money getBillingAcceptedAmount() {
		return billingAcceptedAmount;
	}

	/**
	 * @param billingAcceptedAmount the billingAcceptedAmount to set
	 */
	public void setBillingAcceptedAmount(Money billingAcceptedAmount) {
		this.billingAcceptedAmount = billingAcceptedAmount;
	}

	/**
	 * @return the billingBilledAmount
	 */
	public Money getBillingBilledAmount() {
		return billingBilledAmount;
	}

	/**
	 * @param billingBilledAmount the billingBilledAmount to set
	 */
	public void setBillingBilledAmount(Money billingBilledAmount) {
		this.billingBilledAmount = billingBilledAmount;
	}

	/**
	 * @return the billingRejectedAmount
	 */
	public Money getBillingRejectedAmount() {
		return billingRejectedAmount;
	}

	/**
	 * @param billingRejectedAmount the billingRejectedAmount to set
	 */
	public void setBillingRejectedAmount(Money billingRejectedAmount) {
		this.billingRejectedAmount = billingRejectedAmount;
	}

	/**
	 * @return Returns the chargeNotConvertedToContractIndicator.
	 */
	public String getChargeNotConvertedToContractIndicator() {
		return chargeNotConvertedToContractIndicator;
	}

	/**
	 * @param chargeNotConvertedToContractIndicator
	 *            The chargeNotConvertedToContractIndicator to set.
	 */
	public void setChargeNotConvertedToContractIndicator(
			String chargeNotConvertedToContractIndicator) {
		this.chargeNotConvertedToContractIndicator = chargeNotConvertedToContractIndicator;
	}

	/**
	 * @return Returns the chargeNotCoveredByContractIndicator.
	 */
	public String getChargeNotCoveredByContractIndicator() {
		return chargeNotCoveredByContractIndicator;
	}

	/**
	 * @param chargeNotCoveredByContractIndicator
	 *            The chargeNotCoveredByContractIndicator to set.
	 */
	public void setChargeNotCoveredByContractIndicator(
			String chargeNotCoveredByContractIndicator) {
		this.chargeNotCoveredByContractIndicator = chargeNotCoveredByContractIndicator;
	}

	/**
	 * @return Returns the clearanceAcceptedAmount.
	 */
	public Double getClearanceAcceptedAmount() {
		return clearanceAcceptedAmount;
	}

	/**
	 * @param clearanceAcceptedAmount
	 *            The clearanceAcceptedAmount to set.
	 */
	public void setClearanceAcceptedAmount(Double clearanceAcceptedAmount) {
		this.clearanceAcceptedAmount = clearanceAcceptedAmount;
	}

	/**
	 * @return Returns the clearanceBilledAmount.
	 */
	public Double getClearanceBilledAmount() {
		return clearanceBilledAmount;
	}

	/**
	 * @param clearanceBilledAmount
	 *            The clearanceBilledAmount to set.
	 */
	public void setClearanceBilledAmount(Double clearanceBilledAmount) {
		this.clearanceBilledAmount = clearanceBilledAmount;
	}

	/**
	 * @return Returns the clearanceCurrencyCode.
	 */
	public String getClearanceCurrencyCode() {
		return clearanceCurrencyCode;
	}

	/**
	 * @param clearanceCurrencyCode
	 *            The clearanceCurrencyCode to set.
	 */
	public void setClearanceCurrencyCode(String clearanceCurrencyCode) {
		this.clearanceCurrencyCode = clearanceCurrencyCode;
	}

	/**
	 * @return Returns the clearanceRejectedAmount.
	 */
	public Double getClearanceRejectedAmount() {
		return clearanceRejectedAmount;
	}

	/**
	 * @param clearanceRejectedAmount
	 *            The clearanceRejectedAmount to set.
	 */
	public void setClearanceRejectedAmount(Double clearanceRejectedAmount) {
		this.clearanceRejectedAmount = clearanceRejectedAmount;
	}

	/**
	 * @return Returns the companycode.
	 */
	public String getCompanycode() {
		return companycode;
	}

	/**
	 * @param companycode
	 *            The companycode to set.
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	/**
	 * @return Returns the contractAcceptedAmount.
	 */
	public Double getContractAcceptedAmount() {
		return contractAcceptedAmount;
	}

	/**
	 * @param contractAcceptedAmount
	 *            The contractAcceptedAmount to set.
	 */
	public void setContractAcceptedAmount(Double contractAcceptedAmount) {
		this.contractAcceptedAmount = contractAcceptedAmount;
	}

	/**
	 * @return Returns the contractBilledAmount.
	 */
	public Double getContractBilledAmount() {
		return contractBilledAmount;
	}

	/**
	 * @param contractBilledAmount
	 *            The contractBilledAmount to set.
	 */
	public void setContractBilledAmount(Double contractBilledAmount) {
		this.contractBilledAmount = contractBilledAmount;
	}

	/**
	 * @return Returns the contractBillingExchangeRate.
	 */
	public Double getContractBillingExchangeRate() {
		return contractBillingExchangeRate;
	}

	/**
	 * @param contractBillingExchangeRate
	 *            The contractBillingExchangeRate to set.
	 */
	public void setContractBillingExchangeRate(
			Double contractBillingExchangeRate) {
		this.contractBillingExchangeRate = contractBillingExchangeRate;
	}

	/**
	 * @return Returns the contractRejectedAmount.
	 */
	public Double getContractRejectedAmount() {
		return contractRejectedAmount;
	}

	/**
	 * @param contractRejectedAmount
	 *            The contractRejectedAmount to set.
	 */
	public void setContractRejectedAmount(Double contractRejectedAmount) {
		this.contractRejectedAmount = contractRejectedAmount;
	}

	/**
	 * @return Returns the duplicateBillingIndicator.
	 */
	public String getDuplicateBillingIndicator() {
		return duplicateBillingIndicator;
	}

	/**
	 * @param duplicateBillingIndicator
	 *            The duplicateBillingIndicator to set.
	 */
	public void setDuplicateBillingIndicator(String duplicateBillingIndicator) {
		this.duplicateBillingIndicator = duplicateBillingIndicator;
	}

	/**
	 * @return Returns the duplicateBillingInvoiceDate.
	 */
	public LocalDate getDuplicateBillingInvoiceDate() {
		return duplicateBillingInvoiceDate;
	}

	/**
	 * @param duplicateBillingInvoiceDate
	 *            The duplicateBillingInvoiceDate to set.
	 */
	public void setDuplicateBillingInvoiceDate(
			LocalDate duplicateBillingInvoiceDate) {
		this.duplicateBillingInvoiceDate = duplicateBillingInvoiceDate;
	}

	/**
	 * @return Returns the duplicateBillingInvoiceNumber.
	 */
	public String getDuplicateBillingInvoiceNumber() {
		return duplicateBillingInvoiceNumber;
	}

	/**
	 * @param duplicateBillingInvoiceNumber
	 *            The duplicateBillingInvoiceNumber to set.
	 */
	public void setDuplicateBillingInvoiceNumber(
			String duplicateBillingInvoiceNumber) {
		this.duplicateBillingInvoiceNumber = duplicateBillingInvoiceNumber;
	}

	/**
	 * @return Returns the incorrectExchangeRateIndicator.
	 */
	public String getIncorrectExchangeRateIndicator() {
		return incorrectExchangeRateIndicator;
	}

	/**
	 * @param incorrectExchangeRateIndicator
	 *            The incorrectExchangeRateIndicator to set.
	 */
	public void setIncorrectExchangeRateIndicator(
			String incorrectExchangeRateIndicator) {
		this.incorrectExchangeRateIndicator = incorrectExchangeRateIndicator;
	}

	/**
	 * @return Returns the inwardClearancePeriod.
	 */
	public String getInwardClearancePeriod() {
		return inwardClearancePeriod;
	}

	/**
	 * @param inwardClearancePeriod
	 *            The inwardClearancePeriod to set.
	 */
	public void setInwardClearancePeriod(String inwardClearancePeriod) {
		this.inwardClearancePeriod = inwardClearancePeriod;
	}

	/**
	 * @return Returns the inwardInvoiceDate.
	 */
	public LocalDate getInwardInvoiceDate() {
		return inwardInvoiceDate;
	}

	/**
	 * @param inwardInvoiceDate
	 *            The inwardInvoiceDate to set.
	 */
	public void setInwardInvoiceDate(LocalDate inwardInvoiceDate) {
		this.inwardInvoiceDate = inwardInvoiceDate;
	}

	/**
	 * @return Returns the inwardInvoiceNumber.
	 */
	public String getInwardInvoiceNumber() {
		return inwardInvoiceNumber;
	}

	/**
	 * @param inwardInvoiceNumber
	 *            The inwardInvoiceNumber to set.
	 */
	public void setInwardInvoiceNumber(String inwardInvoiceNumber) {
		this.inwardInvoiceNumber = inwardInvoiceNumber;
	}

	/**
	 * @return Returns the memoCode.
	 */
	public String getMemoCode() {
		return memoCode;
	}

	/**
	 * @param memoCode
	 *            The memoCode to set.
	 */
	public void setMemoCode(String memoCode) {
		this.memoCode = memoCode;
	}

	/**
	 * @return Returns the memoStatus.
	 */
	public String getMemoStatus() {
		return memoStatus;
	}

	/**
	 * @param memoStatus
	 *            The memoStatus to set.
	 */
	public void setMemoStatus(String memoStatus) {
		this.memoStatus = memoStatus;
	}

	/**
	 * @return Returns the noApprovalIndicator.
	 */
	public String getNoApprovalIndicator() {
		return noApprovalIndicator;
	}

	/**
	 * @param noApprovalIndicator
	 *            The noApprovalIndicator to set.
	 */
	public void setNoApprovalIndicator(String noApprovalIndicator) {
		this.noApprovalIndicator = noApprovalIndicator;
	}

	/**
	 * @return Returns the noReceiptIndicator.
	 */
	public String getNoReceiptIndicator() {
		return noReceiptIndicator;
	}

	/**
	 * @param noReceiptIndicator
	 *            The noReceiptIndicator to set.
	 */
	public void setNoReceiptIndicator(String noReceiptIndicator) {
		this.noReceiptIndicator = noReceiptIndicator;
	}

	/**
	 * @return Returns the otherIndicator.
	 */
	public String getOtherIndicator() {
		return otherIndicator;
	}

	/**
	 * @param otherIndicator
	 *            The otherIndicator to set.
	 */
	public void setOtherIndicator(String otherIndicator) {
		this.otherIndicator = otherIndicator;
	}

	/**
	 * @return Returns the outTimeLimitsForBillingIndicator.
	 */
	public String getOutTimeLimitsForBillingIndicator() {
		return outTimeLimitsForBillingIndicator;
	}

	/**
	 * @param outTimeLimitsForBillingIndicator
	 *            The outTimeLimitsForBillingIndicator to set.
	 */
	public void setOutTimeLimitsForBillingIndicator(
			String outTimeLimitsForBillingIndicator) {
		this.outTimeLimitsForBillingIndicator = outTimeLimitsForBillingIndicator;
	}

	/**
	 * @return Returns the outwardClearancePeriod.
	 */
	public String getOutwardClearancePeriod() {
		return outwardClearancePeriod;
	}

	/**
	 * @param outwardClearancePeriod
	 *            The outwardClearancePeriod to set.
	 */
	public void setOutwardClearancePeriod(String outwardClearancePeriod) {
		this.outwardClearancePeriod = outwardClearancePeriod;
	}

	/**
	 * @return Returns the outwardInvoiceDate.
	 */
	public LocalDate getOutwardInvoiceDate() {
		return outwardInvoiceDate;
	}

	/**
	 * @param outwardInvoiceDate
	 *            The outwardInvoiceDate to set.
	 */
	public void setOutwardInvoiceDate(LocalDate outwardInvoiceDate) {
		this.outwardInvoiceDate = outwardInvoiceDate;
	}

	/**
	 * @return Returns the outwardInvoiceNumber.
	 */
	public String getOutwardInvoiceNumber() {
		return outwardInvoiceNumber;
	}

	/**
	 * @param outwardInvoiceNumber
	 *            The outwardInvoiceNumber to set.
	 */
	public void setOutwardInvoiceNumber(String outwardInvoiceNumber) {
		this.outwardInvoiceNumber = outwardInvoiceNumber;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the requestAuthorisationDate.
	 */
	public LocalDate getRequestAuthorisationDate() {
		return requestAuthorisationDate;
	}

	/**
	 * @param requestAuthorisationDate
	 *            The requestAuthorisationDate to set.
	 */
	public void setRequestAuthorisationDate(LocalDate requestAuthorisationDate) {
		this.requestAuthorisationDate = requestAuthorisationDate;
	}

	/**
	 * @return Returns the requestAuthorisationIndicator.
	 */
	public String getRequestAuthorisationIndicator() {
		return requestAuthorisationIndicator;
	}

	/**
	 * @param requestAuthorisationIndicator
	 *            The requestAuthorisationIndicator to set.
	 */
	public void setRequestAuthorisationIndicator(
			String requestAuthorisationIndicator) {
		this.requestAuthorisationIndicator = requestAuthorisationIndicator;
	}

	/**
	 * @return Returns the requestAuthorisationReference.
	 */
	public String getRequestAuthorisationReference() {
		return requestAuthorisationReference;
	}

	/**
	 * @param requestAuthorisationReference
	 *            The requestAuthorisationReference to set.
	 */
	public void setRequestAuthorisationReference(
			String requestAuthorisationReference) {
		this.requestAuthorisationReference = requestAuthorisationReference;
	}

	/**
	 * @return Returns the contractCurrencyCode.
	 */
	public String getContractCurrencyCode() {
		return contractCurrencyCode;
	}

	/**
	 * @param contractCurrencyCode
	 *            The contractCurrencyCode to set.
	 */
	public void setContractCurrencyCode(String contractCurrencyCode) {
		this.contractCurrencyCode = contractCurrencyCode;
	}

	/**
	 * @return Returns the monthOfClearance.
	 */
	public String getMonthOfClearance() {
		return monthOfClearance;
	}

	/**
	 * @param monthOfClearance
	 *            The monthOfClearance to set.
	 */
	public void setMonthOfClearance(String monthOfClearance) {
		this.monthOfClearance = monthOfClearance;
	}

	/**
	 * @return Returns the airlineName.
	 */
	public String getAirlineName() {
		return airlineName;
	}

	/**
	 * @param airlineName
	 *            The airlineName to set.
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	/**
	 * @return Returns the acctTxnIdr.
	 */
	public String getAcctTxnIdr() {
		return acctTxnIdr;
	}

	/**
	 * @param acctTxnIdr
	 *            The acctTxnIdr to set.
	 */
	public void setAcctTxnIdr(String acctTxnIdr) {
		this.acctTxnIdr = acctTxnIdr;
	}

	/**
	 * @return Returns the yourReferenceNumber.
	 */
	public String getYourReferenceNumber() {
		return yourReferenceNumber;
	}

	/**
	 * @param yourReferenceNumber
	 *            The yourReferenceNumber to set.
	 */
	public void setYourReferenceNumber(String yourReferenceNumber) {
		this.yourReferenceNumber = yourReferenceNumber;
	}

	/**
	 * @return Returns the monthOfTrans.
	 */
	public String getMonthOfTrans() {
		return monthOfTrans;
	}

	/**
	 * @param monthOfTrans
	 *            The monthOfTrans to set.
	 */
	public void setMonthOfTrans(String monthOfTrans) {
		this.monthOfTrans = monthOfTrans;
	}

	/**
	 * @return Returns the outMonthOfClearance.
	 */
	public String getOutMonthOfClearance() {
		return outMonthOfClearance;
	}

	/**
	 * @param outMonthOfClearance
	 *            The outMonthOfClearance to set.
	 */
	public void setOutMonthOfClearance(String outMonthOfClearance) {
		this.outMonthOfClearance = outMonthOfClearance;
	}

	

	/**
	 * @return the clearanceperiod
	 */
	public String getClearanceperiod() {
		return clearanceperiod;
	}

	/**
	 * @param clearanceperiod the clearanceperiod to set
	 */
	public void setClearanceperiod(String clearanceperiod) {
		this.clearanceperiod = clearanceperiod;
	}

	/**
	 * @return the interlinebillingtype
	 */
	public String getInterlinebillingtype() {
		return interlinebillingtype;
	}

	/**
	 * @param interlinebillingtype the interlinebillingtype to set
	 */
	public void setInterlinebillingtype(String interlinebillingtype) {
		this.interlinebillingtype = interlinebillingtype;
	}

	/**
	 * @return the screenFlag
	 */
	public String getScreenFlag() {
		return screenFlag;
	}

	/**
	 * @param screenFlag the screenFlag to set
	 */
	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the sectorFrom
	 */
	public String getSectorFrom() {
		return sectorFrom;
	}

	/**
	 * @param sectorFrom the sectorFrom to set
	 */
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}

	/**
	 * @return the sectorTo
	 */
	public String getSectorTo() {
		return sectorTo;
	}

	/**
	 * @param sectorTo the sectorTo to set
	 */
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}

	/**
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	/**
	 * @return the sisSupportingDocumentDetailsVOs
	 */
	public Collection <SisSupportingDocumentDetailsVO> getSisSupportingDocumentDetailsVOs() {
		return sisSupportingDocumentDetailsVOs;
	}

	/**
	 * @param sisSupportingDocumentDetailsVOs the sisSupportingDocumentDetailsVOs to set
	 */
	public void setSisSupportingDocumentDetailsVOs(Collection <SisSupportingDocumentDetailsVO> sisSupportingDocumentDetailsVOs) {
		this.sisSupportingDocumentDetailsVOs = sisSupportingDocumentDetailsVOs;
	}

	/**
	 * @return the attachmentIndicator
	 */
	public String getAttachmentIndicator() {
		return attachmentIndicator;
	}

	/**
	 * @param attachmentIndicator the attachmentIndicator to set
	 */
	public void setAttachmentIndicator(String attachmentIndicator) {
		this.attachmentIndicator = attachmentIndicator;
	}

	/**
	 * 	Getter for rejectionStage 
	 *	Added by : a-8061 on 29-Oct-2018
	 * 	Used for :
	 */
	public String getRejectionStage() {
		return rejectionStage;
	}

	/**
	 *  @param rejectionStage the rejectionStage to set
	 * 	Setter for rejectionStage 
	 *	Added by : a-8061 on 29-Oct-2018
	 * 	Used for :
	 */
	public void setRejectionStage(String rejectionStage) {
		this.rejectionStage = rejectionStage;
	}
	
}
