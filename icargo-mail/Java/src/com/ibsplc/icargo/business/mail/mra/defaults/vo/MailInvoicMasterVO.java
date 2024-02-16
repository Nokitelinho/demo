/*
 * MailInvoicMasterVO.java created on Jul 19, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class MailInvoicMasterVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	private String messageReferenceNumber;
	
	private String messageTypeIdentifier;
	
	private String messageVersionNumber;
	
	private String messageReleaseNumber;
	
	private String controllingAgency;
	
	private String associationAssignedCode;
	
	private String syntaxIdentifier;
	
	private int syntaxVersion;
	
	private String recipientIdentifier;
	
	private LocalDate preparationDate;
	
	private String interchangeControlReference;
	
	private int testIndicator;
	
	private String messageName;
	
	private String messageDocumentNumber;
	
	private String messageRevisionNumber;
	
	private String paymentType;
	
	private LocalDate invoicReceivedDate;
	
	private LocalDate consignmentCompletionDate;
	
	private LocalDate criticalEntryDate;
	
	private LocalDate scheduleInvoiceDate;
	
	private LocalDate enteredDate;
	
	private String contractType;
	
	private String contractNumber;
	
	private String carrierCode;
	
	private String carrierName;
	
	private String reconciliationStatus;
	
	private String senderCodeQualifier;
	
	private String recipientCodeQualifier;
	
	private Collection<MailInvoicProductDtlVO> mailInvoicProductDetails;
	
	private Collection<MailInvoicMonetaryAmtVO> mailInvoicMonetaryDetails;
	
	private Collection<MailInvoicPriceVO> mailInvoicPriceDetails;
	
	private Collection<MailInvoicPackageVO> mailInvoicPackageDetails;
	
	private Collection<MailInvoicLocationVO> mailInvoicLocationDetails;
	
	private Collection<MailInvoicTransportationDtlVO> mailInvoicTransportationDetails;
	
	private MailInvoicTotalPaymentVO mailInvoicTotalPayment;
	

	/**
	 * @return Returns the associationAssignedCode.
	 */
	public String getAssociationAssignedCode() {
		return associationAssignedCode;
	}

	/**
	 * @param associationAssignedCode The associationAssignedCode to set.
	 */
	public void setAssociationAssignedCode(String associationAssignedCode) {
		this.associationAssignedCode = associationAssignedCode;
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
	 * @return Returns the consignmentCompletionDate.
	 */
	public LocalDate getConsignmentCompletionDate() {
		return consignmentCompletionDate;
	}

	/**
	 * @param consignmentCompletionDate The consignmentCompletionDate to set.
	 */
	public void setConsignmentCompletionDate(LocalDate consignmentCompletionDate) {
		this.consignmentCompletionDate = consignmentCompletionDate;
	}

	/**
	 * @return Returns the controllingAgency.
	 */
	public String getControllingAgency() {
		return controllingAgency;
	}

	/**
	 * @param controllingAgency The controllingAgency to set.
	 */
	public void setControllingAgency(String controllingAgency) {
		this.controllingAgency = controllingAgency;
	}

	/**
	 * @return Returns the criticalEntryDate.
	 */
	public LocalDate getCriticalEntryDate() {
		return criticalEntryDate;
	}

	/**
	 * @param criticalEntryDate The criticalEntryDate to set.
	 */
	public void setCriticalEntryDate(LocalDate criticalEntryDate) {
		this.criticalEntryDate = criticalEntryDate;
	}

	/**
	 * @return Returns the interchangeControlReference.
	 */
	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}

	/**
	 * @param interchangeControlReference The interchangeControlReference to set.
	 */
	public void setInterchangeControlReference(String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}

	

	/**
	 * @return Returns the invoiceKey.
	 */
	public String getInvoiceKey() {
		return invoiceKey;
	}

	/**
	 * @param invoiceKey The invoiceKey to set.
	 */
	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}

	/**
	 * @return Returns the invoicReceivedDate.
	 */
	public LocalDate getInvoicReceivedDate() {
		return invoicReceivedDate;
	}

	/**
	 * @param invoicReceivedDate The invoicReceivedDate to set.
	 */
	public void setInvoicReceivedDate(LocalDate invoicReceivedDate) {
		this.invoicReceivedDate = invoicReceivedDate;
	}

	/**
	 * @return Returns the messageDocumentNumber.
	 */
	public String getMessageDocumentNumber() {
		return messageDocumentNumber;
	}

	/**
	 * @param messageDocumentNumber The messageDocumentNumber to set.
	 */
	public void setMessageDocumentNumber(String messageDocumentNumber) {
		this.messageDocumentNumber = messageDocumentNumber;
	}

	/**
	 * @return Returns the messageName.
	 */
	public String getMessageName() {
		return messageName;
	}

	/**
	 * @param messageName The messageName to set.
	 */
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	/**
	 * @return Returns the messageReferenceNumber.
	 */
	public String getMessageReferenceNumber() {
		return messageReferenceNumber;
	}

	/**
	 * @param messageReferenceNumber The messageReferenceNumber to set.
	 */
	public void setMessageReferenceNumber(String messageReferenceNumber) {
		this.messageReferenceNumber = messageReferenceNumber;
	}

	/**
	 * @return Returns the messageReleaseNumber.
	 */
	public String getMessageReleaseNumber() {
		return messageReleaseNumber;
	}

	/**
	 * @param messageReleaseNumber The messageReleaseNumber to set.
	 */
	public void setMessageReleaseNumber(String messageReleaseNumber) {
		this.messageReleaseNumber = messageReleaseNumber;
	}

	/**
	 * @return Returns the messageRevisionNumber.
	 */
	public String getMessageRevisionNumber() {
		return messageRevisionNumber;
	}

	/**
	 * @param messageRevisionNumber The messageRevisionNumber to set.
	 */
	public void setMessageRevisionNumber(String messageRevisionNumber) {
		this.messageRevisionNumber = messageRevisionNumber;
	}

	/**
	 * @return Returns the messageTypeIdentifier.
	 */
	public String getMessageTypeIdentifier() {
		return messageTypeIdentifier;
	}

	/**
	 * @param messageTypeIdentifier The messageTypeIdentifier to set.
	 */
	public void setMessageTypeIdentifier(String messageTypeIdentifier) {
		this.messageTypeIdentifier = messageTypeIdentifier;
	}

	/**
	 * @return Returns the messageVersionNumber.
	 */
	public String getMessageVersionNumber() {
		return messageVersionNumber;
	}

	/**
	 * @param messageVersionNumber The messageVersionNumber to set.
	 */
	public void setMessageVersionNumber(String messageVersionNumber) {
		this.messageVersionNumber = messageVersionNumber;
	}

	/**
	 * @return Returns the paymentType.
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType The paymentType to set.
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the preparationDate.
	 */
	public LocalDate getPreparationDate() {
		return preparationDate;
	}

	/**
	 * @param preparationDate The preparationDate to set.
	 */
	public void setPreparationDate(LocalDate preparationDate) {
		this.preparationDate = preparationDate;
	}

	/**
	 * @return Returns the recipientIdentifier.
	 */
	public String getRecipientIdentifier() {
		return recipientIdentifier;
	}

	/**
	 * @param recipientIdentifier The recipientIdentifier to set.
	 */
	public void setRecipientIdentifier(String recipientIdentifier) {
		this.recipientIdentifier = recipientIdentifier;
	}

	/**
	 * @return Returns the scheduleInvoiceDate.
	 */
	public LocalDate getScheduleInvoiceDate() {
		return scheduleInvoiceDate;
	}

	/**
	 * @param scheduleInvoiceDate The scheduleInvoiceDate to set.
	 */
	public void setScheduleInvoiceDate(LocalDate scheduleInvoiceDate) {
		this.scheduleInvoiceDate = scheduleInvoiceDate;
	}

	/**
	 * @return Returns the syntaxIdentifier.
	 */
	public String getSyntaxIdentifier() {
		return syntaxIdentifier;
	}

	/**
	 * @param syntaxIdentifier The syntaxIdentifier to set.
	 */
	public void setSyntaxIdentifier(String syntaxIdentifier) {
		this.syntaxIdentifier = syntaxIdentifier;
	}

	/**
	 * @return Returns the syntaxVersion.
	 */
	public int getSyntaxVersion() {
		return syntaxVersion;
	}

	/**
	 * @param syntaxVersion The syntaxVersion to set.
	 */
	public void setSyntaxVersion(int syntaxVersion) {
		this.syntaxVersion = syntaxVersion;
	}

	/**
	 * @return Returns the testIndicator.
	 */
	public int getTestIndicator() {
		return testIndicator;
	}

	/**
	 * @param testIndicator The testIndicator to set.
	 */
	public void setTestIndicator(int testIndicator) {
		this.testIndicator = testIndicator;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the carrierName.
	 */
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName The carrierName to set.
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	/**
	 * @return Returns the contractNumber.
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber The contractNumber to set.
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return Returns the contractType.
	 */
	public String getContractType() {
		return contractType;
	}

	/**
	 * @param contractType The contractType to set.
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	/**
	 * @return Returns the enteredDate.
	 */
	public LocalDate getEnteredDate() {
		return enteredDate;
	}

	/**
	 * @param enteredDate The enteredDate to set.
	 */
	public void setEnteredDate(LocalDate enteredDate) {
		this.enteredDate = enteredDate;
	}

	/**
	 * @return Returns the recipientCodeQualifier.
	 */
	public String getRecipientCodeQualifier() {
		return recipientCodeQualifier;
	}

	/**
	 * @param recipientCodeQualifier The recipientCodeQualifier to set.
	 */
	public void setRecipientCodeQualifier(String recipientCodeQualifier) {
		this.recipientCodeQualifier = recipientCodeQualifier;
	}

	/**
	 * @return Returns the reconciliationStatus.
	 */
	public String getReconciliationStatus() {
		return reconciliationStatus;
	}

	/**
	 * @param reconciliationStatus The reconciliationStatus to set.
	 */
	public void setReconciliationStatus(String reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
	}

	/**
	 * @return Returns the senderCodeQualifier.
	 */
	public String getSenderCodeQualifier() {
		return senderCodeQualifier;
	}

	/**
	 * @param senderCodeQualifier The senderCodeQualifier to set.
	 */
	public void setSenderCodeQualifier(String senderCodeQualifier) {
		this.senderCodeQualifier = senderCodeQualifier;
	}

	/**
	 * @return Returns the mailInvoicLocationDetails.
	 */
	public Collection<MailInvoicLocationVO> getMailInvoicLocationDetails() {
		return mailInvoicLocationDetails;
	}

	/**
	 * @param mailInvoicLocationDetails The mailInvoicLocationDetails to set.
	 */
	public void setMailInvoicLocationDetails(
			Collection<MailInvoicLocationVO> mailInvoicLocationDetails) {
		this.mailInvoicLocationDetails = mailInvoicLocationDetails;
	}

	/**
	 * @return Returns the mailInvoicMonetaryDetails.
	 */
	public Collection<MailInvoicMonetaryAmtVO> getMailInvoicMonetaryDetails() {
		return mailInvoicMonetaryDetails;
	}

	/**
	 * @param mailInvoicMonetaryDetails The mailInvoicMonetaryDetails to set.
	 */
	public void setMailInvoicMonetaryDetails(
			Collection<MailInvoicMonetaryAmtVO> mailInvoicMonetaryDetails) {
		this.mailInvoicMonetaryDetails = mailInvoicMonetaryDetails;
	}

	/**
	 * @return Returns the mailInvoicPackageDetails.
	 */
	public Collection<MailInvoicPackageVO> getMailInvoicPackageDetails() {
		return mailInvoicPackageDetails;
	}

	/**
	 * @param mailInvoicPackageDetails The mailInvoicPackageDetails to set.
	 */
	public void setMailInvoicPackageDetails(
			Collection<MailInvoicPackageVO> mailInvoicPackageDetails) {
		this.mailInvoicPackageDetails = mailInvoicPackageDetails;
	}

	/**
	 * @return Returns the mailInvoicPriceDetails.
	 */
	public Collection<MailInvoicPriceVO> getMailInvoicPriceDetails() {
		return mailInvoicPriceDetails;
	}

	/**
	 * @param mailInvoicPriceDetails The mailInvoicPriceDetails to set.
	 */
	public void setMailInvoicPriceDetails(
			Collection<MailInvoicPriceVO> mailInvoicPriceDetails) {
		this.mailInvoicPriceDetails = mailInvoicPriceDetails;
	}

	/**
	 * @return Returns the mailInvoicProductDetails.
	 */
	public Collection<MailInvoicProductDtlVO> getMailInvoicProductDetails() {
		return mailInvoicProductDetails;
	}

	/**
	 * @param mailInvoicProductDetails The mailInvoicProductDetails to set.
	 */
	public void setMailInvoicProductDetails(
			Collection<MailInvoicProductDtlVO> mailInvoicProductDetails) {
		this.mailInvoicProductDetails = mailInvoicProductDetails;
	}

	/**
	 * @return Returns the mailInvoicTotalPayment.
	 */
	public MailInvoicTotalPaymentVO getMailInvoicTotalPayment() {
		return mailInvoicTotalPayment;
	}

	/**
	 * @param mailInvoicTotalPayment The mailInvoicTotalPayment to set.
	 */
	public void setMailInvoicTotalPayment(
			MailInvoicTotalPaymentVO mailInvoicTotalPayment) {
		this.mailInvoicTotalPayment = mailInvoicTotalPayment;
	}

	/**
	 * @return Returns the mailInvoicTransportationDetails.
	 */
	public Collection<MailInvoicTransportationDtlVO> getMailInvoicTransportationDetails() {
		return mailInvoicTransportationDetails;
	}

	/**
	 * @param mailInvoicTransportationDetails The mailInvoicTransportationDetails to set.
	 */
	public void setMailInvoicTransportationDetails(
			Collection<MailInvoicTransportationDtlVO> mailInvoicTransportationDetails) {
		this.mailInvoicTransportationDetails = mailInvoicTransportationDetails;
	}

	
	
	
	
	
	
	
	
}