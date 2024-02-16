/*
 * RejectionMemoForm.java Created on May 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2408
 * 
 */
public class RejectionMemoForm extends ScreenModel {

	private static final String BUNDLE = "rejectionmemobundle";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";

	private String memoCode;

	private String invoiceNumber;

	private String yourInvoiceNumber;

	private String airlineCode;

	private String yourInvoiceDate;

	private String yourReferenceNumber;

	private String monthOfClearance;

	private String monthOfTransaction;

	private String requestAuthorisationIndicator;

	private String requestAuthorisationReference;

	private String requestAuthorisationDate;

	private String duplicateBillingInvoiceNumber;

	private String duplicateBillingInvoiceDate;

	private String remarks;

	private String contractBilledAmount;

	private String contractAcceptedAmount;

	private String contractRejectedAmount;

	private String clearanceBilledAmount;

	private String clearanceAcceptedAmount;

	private String clearanceRejectedAmount;

	private String billingBilledAmount;

	private String billingAcceptedAmount;

	private String billingRejectedAmount;

	private String billingCurrencyCode;

	private String contractCurrencyCode;

	private String clearanceCurrencyCode;

	private String contractBillingExchangeRate;

	private String billingClearanceExchangeRate;

	private String duplicateBillingIndicator;

	private String chargeNotCoveredByContractIndicator;

	private String outTimeLimitsForBillingIndicator;

	private String chargeNotConvertedToContractIndicator;

	private String noApprovalIndicator;

	private String noReceiptIndicator;

	private String incorrectExchangeRateIndicator;

	private String otherIndicator;

	private String memoStatus;

	private String screenFlag;

	private String fromScreenFlag;

	private String airlineIdentifier;

	private String inwardClearancePeriod;

	private String cn66CloseFlag;
	
	//Added By Deepthi for Rejection Memo
	
	private String billedAmount;
	
	private String acceptedAmount;
	
	private String rejectedAmount;
	
	private String billCurBilledAmount;
	
	private String bilCuracceptedAmount;
	 
	private String bilCurrejectedAmount;
	
	private String dsn;
	
	private String lovClicked;
	
	private String origin;
	
	private String destination;
	
	private String sectorFrom;
	
	private String sectorTo;
	
	private String selectIndex;
	
	private String[] fileNameCheck;
	
	private String attachmentIndicator;
	
	/**
	 * @author a-3447 for calling from capture invoice Screen
	 */
	private String invokingScreen;

	/**
	 * @return Returns the SCREENID.
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return Returns the PRODUCT.
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SUBPRODUCT.
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
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
	 * @return Returns the monthOfTransaction.
	 */
	public String getMonthOfTransaction() {
		return monthOfTransaction;
	}

	/**
	 * @param monthOfTransaction
	 *            The monthOfTransaction to set.
	 */
	public void setMonthOfTransaction(String monthOfTransaction) {
		this.monthOfTransaction = monthOfTransaction;
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
	 * @return Returns the billingAcceptedAmount.
	 */
	public String getBillingAcceptedAmount() {
		return billingAcceptedAmount;
	}

	/**
	 * @param billingAcceptedAmount
	 *            The billingAcceptedAmount to set.
	 */
	public void setBillingAcceptedAmount(String billingAcceptedAmount) {
		this.billingAcceptedAmount = billingAcceptedAmount;
	}

	/**
	 * @return Returns the billingBilledAmount.
	 */
	public String getBillingBilledAmount() {
		return billingBilledAmount;
	}

	/**
	 * @param billingBilledAmount
	 *            The billingBilledAmount to set.
	 */
	public void setBillingBilledAmount(String billingBilledAmount) {
		this.billingBilledAmount = billingBilledAmount;
	}

	/**
	 * @return Returns the billingRejectedAmount.
	 */
	public String getBillingRejectedAmount() {
		return billingRejectedAmount;
	}

	/**
	 * @param billingRejectedAmount
	 *            The billingRejectedAmount to set.
	 */
	public void setBillingRejectedAmount(String billingRejectedAmount) {
		this.billingRejectedAmount = billingRejectedAmount;
	}

	/**
	 * @return Returns the clearanceAcceptedAmount.
	 */
	public String getClearanceAcceptedAmount() {
		return clearanceAcceptedAmount;
	}

	/**
	 * @param clearanceAcceptedAmount
	 *            The clearanceAcceptedAmount to set.
	 */
	public void setClearanceAcceptedAmount(String clearanceAcceptedAmount) {
		this.clearanceAcceptedAmount = clearanceAcceptedAmount;
	}

	/**
	 * @return Returns the clearanceBilledAmount.
	 */
	public String getClearanceBilledAmount() {
		return clearanceBilledAmount;
	}

	/**
	 * @param clearanceBilledAmount
	 *            The clearanceBilledAmount to set.
	 */
	public void setClearanceBilledAmount(String clearanceBilledAmount) {
		this.clearanceBilledAmount = clearanceBilledAmount;
	}

	/**
	 * @return Returns the clearanceRejectedAmount.
	 */
	public String getClearanceRejectedAmount() {
		return clearanceRejectedAmount;
	}

	/**
	 * @param clearanceRejectedAmount
	 *            The clearanceRejectedAmount to set.
	 */
	public void setClearanceRejectedAmount(String clearanceRejectedAmount) {
		this.clearanceRejectedAmount = clearanceRejectedAmount;
	}

	/**
	 * @return Returns the contractAcceptedAmount.
	 */
	public String getContractAcceptedAmount() {
		return contractAcceptedAmount;
	}

	/**
	 * @param contractAcceptedAmount
	 *            The contractAcceptedAmount to set.
	 */
	public void setContractAcceptedAmount(String contractAcceptedAmount) {
		this.contractAcceptedAmount = contractAcceptedAmount;
	}

	/**
	 * @return Returns the contractBilledAmount.
	 */
	public String getContractBilledAmount() {
		return contractBilledAmount;
	}

	/**
	 * @param contractBilledAmount
	 *            The contractBilledAmount to set.
	 */
	public void setContractBilledAmount(String contractBilledAmount) {
		this.contractBilledAmount = contractBilledAmount;
	}

	/**
	 * @return Returns the contractRejectedAmount.
	 */
	public String getContractRejectedAmount() {
		return contractRejectedAmount;
	}

	/**
	 * @param contractRejectedAmount
	 *            The contractRejectedAmount to set.
	 */
	public void setContractRejectedAmount(String contractRejectedAmount) {
		this.contractRejectedAmount = contractRejectedAmount;
	}

	/**
	 * @return Returns the billingClearanceExchangeRate.
	 */
	public String getBillingClearanceExchangeRate() {
		return billingClearanceExchangeRate;
	}

	/**
	 * @param billingClearanceExchangeRate
	 *            The billingClearanceExchangeRate to set.
	 */
	public void setBillingClearanceExchangeRate(
			String billingClearanceExchangeRate) {
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
	 * @return Returns the contractBillingExchangeRate.
	 */
	public String getContractBillingExchangeRate() {
		return contractBillingExchangeRate;
	}

	/**
	 * @param contractBillingExchangeRate
	 *            The contractBillingExchangeRate to set.
	 */
	public void setContractBillingExchangeRate(
			String contractBillingExchangeRate) {
		this.contractBillingExchangeRate = contractBillingExchangeRate;
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
	 * @return Returns the yourInvoiceDate.
	 */
	public String getYourInvoiceDate() {
		return yourInvoiceDate;
	}

	/**
	 * @param yourInvoiceDate
	 *            The yourInvoiceDate to set.
	 */
	public void setYourInvoiceDate(String yourInvoiceDate) {
		this.yourInvoiceDate = yourInvoiceDate;
	}

	/**
	 * @return Returns the yourInvoiceNumber.
	 */
	public String getYourInvoiceNumber() {
		return yourInvoiceNumber;
	}

	/**
	 * @param yourInvoiceNumber
	 *            The yourInvoiceNumber to set.
	 */
	public void setYourInvoiceNumber(String yourInvoiceNumber) {
		this.yourInvoiceNumber = yourInvoiceNumber;
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
	 * @return Returns the duplicateBillingInvoiceDate.
	 */
	public String getDuplicateBillingInvoiceDate() {
		return duplicateBillingInvoiceDate;
	}

	/**
	 * @param duplicateBillingInvoiceDate
	 *            The duplicateBillingInvoiceDate to set.
	 */
	public void setDuplicateBillingInvoiceDate(
			String duplicateBillingInvoiceDate) {
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
	 * @return Returns the requestAuthorisationDate.
	 */
	public String getRequestAuthorisationDate() {
		return requestAuthorisationDate;
	}

	/**
	 * @param requestAuthorisationDate
	 *            The requestAuthorisationDate to set.
	 */
	public void setRequestAuthorisationDate(String requestAuthorisationDate) {
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
	 * @return Returns the fromScreenFlag.
	 */
	public String getFromScreenFlag() {
		return fromScreenFlag;
	}

	/**
	 * @param fromScreenFlag
	 *            The fromScreenFlag to set.
	 */
	public void setFromScreenFlag(String fromScreenFlag) {
		this.fromScreenFlag = fromScreenFlag;
	}

	/**
	 * @return Returns the screenFlag.
	 */
	public String getScreenFlag() {
		return screenFlag;
	}

	/**
	 * @param screenFlag
	 *            The screenFlag to set.
	 */
	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public String getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(String airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
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
	 * @return Returns the cn66CloseFlag.
	 */
	public String getCn66CloseFlag() {
		return cn66CloseFlag;
	}

	/**
	 * @param cn66CloseFlag
	 *            The cn66CloseFlag to set.
	 */
	public void setCn66CloseFlag(String cn66CloseFlag) {
		this.cn66CloseFlag = cn66CloseFlag;
	}

	/**
	 * @return the invokingScreen
	 */
	public String getInvokingScreen() {
		return invokingScreen;
	}

	/**
	 * @param invokingScreen
	 *            the invokingScreen to set
	 */
	public void setInvokingScreen(String invokingScreen) {
		this.invokingScreen = invokingScreen;
	}

	/**
	 * @return the acceptedAmount
	 */
	public String getAcceptedAmount() {
		return acceptedAmount;
	}

	/**
	 * @param acceptedAmount the acceptedAmount to set
	 */
	public void setAcceptedAmount(String acceptedAmount) {
		this.acceptedAmount = acceptedAmount;
	}

	/**
	 * @return the billedAmount
	 */
	public String getBilledAmount() {
		return billedAmount;
	}

	/**
	 * @param billedAmount the billedAmount to set
	 */
	public void setBilledAmount(String billedAmount) {
		this.billedAmount = billedAmount;
	}

	/**
	 * @return the rejectedAmount
	 */
	public String getRejectedAmount() {
		return rejectedAmount;
	}

	/**
	 * @param rejectedAmount the rejectedAmount to set
	 */
	public void setRejectedAmount(String rejectedAmount) {
		this.rejectedAmount = rejectedAmount;
	}

	/**
	 * @return the bilCuracceptedAmount
	 */
	public String getBilCuracceptedAmount() {
		return bilCuracceptedAmount;
	}

	/**
	 * @param bilCuracceptedAmount the bilCuracceptedAmount to set
	 */
	public void setBilCuracceptedAmount(String bilCuracceptedAmount) {
		this.bilCuracceptedAmount = bilCuracceptedAmount;
	}

	/**
	 * @return the bilCurrejectedAmount
	 */
	public String getBilCurrejectedAmount() {
		return bilCurrejectedAmount;
	}

	/**
	 * @param bilCurrejectedAmount the bilCurrejectedAmount to set
	 */
	public void setBilCurrejectedAmount(String bilCurrejectedAmount) {
		this.bilCurrejectedAmount = bilCurrejectedAmount;
	}

	/**
	 * @return the billCurBilledAmount
	 */
	public String getBillCurBilledAmount() {
		return billCurBilledAmount;
	}

	/**
	 * @param billCurBilledAmount the billCurBilledAmount to set
	 */
	public void setBillCurBilledAmount(String billCurBilledAmount) {
		this.billCurBilledAmount = billCurBilledAmount;
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
	 * @return the lovClicked
	 */
	public String getLovClicked() {
		return lovClicked;
	}

	/**
	 * @param lovClicked the lovClicked to set
	 */
	public void setLovClicked(String lovClicked) {
		this.lovClicked = lovClicked;
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
	 * @return the selectIndex
	 */
	public String getSelectIndex() {
		return selectIndex;
	}

	/**
	 * @param selectIndex the selectIndex to set
	 */
	public void setSelectIndex(String selectIndex) {
		this.selectIndex = selectIndex;
	}

	/**
	 * @return the fileNameCheck
	 */
	public String[] getFileNameCheck() {
		return fileNameCheck;
	}

	/**
	 * @param fileNameCheck the fileNameCheck to set
	 */
	public void setFileNameCheck(String[] fileNameCheck) {
		this.fileNameCheck = fileNameCheck;
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
}