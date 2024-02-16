/*
 * MailInvoicProductDtlVO.java created on Jul 19, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class MailInvoicProductDtlVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	private String receptacleIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	private int lineNumber;
	
	private String adjustmentIndicator;
	
	private String payPathIndicator;
	
	private String paymentEvaluationCode;
	
	private String missentIndicator;
	
	private String lateIndicator;
	
	private String scanPerformanceIndicator;
	
	private String scanPaymentIndicator;
	
	private String rateTypeIndicator;
	
	private String payCycleIndicator;
	
	private String adjustmentReasonCode;
	
	private String greatCircleMiles;
	
	private String consignmentDocumentNumber;
	
	private String mailCategoryCode;

	private String mailClassCode;
	
	private String mailSubclassCode;
	
	private String paymentRateCode;
	
	private String containerSerialNumber;
	
	private String codeListQualifier;
	
	private String codeListResponsibleAgency;
	
	private String specialRateTypeDescription;
	
	private String claimAdjustmentCode;
	
	private String claimReasonCode;
	
	private String claimStatus;
	
	private String claimText;

	/**
	 * @return Returns the adjustmentIndicator.
	 */
	public String getAdjustmentIndicator() {
		return adjustmentIndicator;
	}

	/**
	 * @param adjustmentIndicator The adjustmentIndicator to set.
	 */
	public void setAdjustmentIndicator(String adjustmentIndicator) {
		this.adjustmentIndicator = adjustmentIndicator;
	}

	/**
	 * @return Returns the adjustmentReasonCode.
	 */
	public String getAdjustmentReasonCode() {
		return adjustmentReasonCode;
	}

	/**
	 * @param adjustmentReasonCode The adjustmentReasonCode to set.
	 */
	public void setAdjustmentReasonCode(String adjustmentReasonCode) {
		this.adjustmentReasonCode = adjustmentReasonCode;
	}

	/**
	 * @return Returns the claimAdjustmentCode.
	 */
	public String getClaimAdjustmentCode() {
		return claimAdjustmentCode;
	}

	/**
	 * @param claimAdjustmentCode The claimAdjustmentCode to set.
	 */
	public void setClaimAdjustmentCode(String claimAdjustmentCode) {
		this.claimAdjustmentCode = claimAdjustmentCode;
	}

	/**
	 * @return Returns the claimReasonCode.
	 */
	public String getClaimReasonCode() {
		return claimReasonCode;
	}

	/**
	 * @param claimReasonCode The claimReasonCode to set.
	 */
	public void setClaimReasonCode(String claimReasonCode) {
		this.claimReasonCode = claimReasonCode;
	}

	/**
	 * @return Returns the claimStatus.
	 */
	public String getClaimStatus() {
		return claimStatus;
	}

	/**
	 * @param claimStatus The claimStatus to set.
	 */
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	/**
	 * @return Returns the claimText.
	 */
	public String getClaimText() {
		return claimText;
	}

	/**
	 * @param claimText The claimText to set.
	 */
	public void setClaimText(String claimText) {
		this.claimText = claimText;
	}

	/**
	 * @return Returns the codeListQualifier.
	 */
	public String getCodeListQualifier() {
		return codeListQualifier;
	}

	/**
	 * @param codeListQualifier The codeListQualifier to set.
	 */
	public void setCodeListQualifier(String codeListQualifier) {
		this.codeListQualifier = codeListQualifier;
	}

	/**
	 * @return Returns the codeListResponsibleAgency.
	 */
	public String getCodeListResponsibleAgency() {
		return codeListResponsibleAgency;
	}

	/**
	 * @param codeListResponsibleAgency The codeListResponsibleAgency to set.
	 */
	public void setCodeListResponsibleAgency(String codeListResponsibleAgency) {
		this.codeListResponsibleAgency = codeListResponsibleAgency;
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
	 * @return Returns the consignmentDocumentNumber.
	 */
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}

	/**
	 * @param consignmentDocumentNumber The consignmentDocumentNumber to set.
	 */
	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}

	/**
	 * @return Returns the containerSerialNumber.
	 */
	public String getContainerSerialNumber() {
		return containerSerialNumber;
	}

	/**
	 * @param containerSerialNumber The containerSerialNumber to set.
	 */
	public void setContainerSerialNumber(String containerSerialNumber) {
		this.containerSerialNumber = containerSerialNumber;
	}

	/**
	 * @return Returns the greatCircleMiles.
	 */
	public String getGreatCircleMiles() {
		return greatCircleMiles;
	}

	/**
	 * @param greatCircleMiles The greatCircleMiles to set.
	 */
	public void setGreatCircleMiles(String greatCircleMiles) {
		this.greatCircleMiles = greatCircleMiles;
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
	 * @return Returns the lateIndicator.
	 */
	public String getLateIndicator() {
		return lateIndicator;
	}

	/**
	 * @param lateIndicator The lateIndicator to set.
	 */
	public void setLateIndicator(String lateIndicator) {
		this.lateIndicator = lateIndicator;
	}

	/**
	 * @return Returns the lineNumber.
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber The lineNumber to set.
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailClassCode.
	 */
	public String getMailClassCode() {
		return mailClassCode;
	}

	/**
	 * @param mailClassCode The mailClassCode to set.
	 */
	public void setMailClassCode(String mailClassCode) {
		this.mailClassCode = mailClassCode;
	}

	/**
	 * @return Returns the mailSubclassCode.
	 */
	public String getMailSubclassCode() {
		return mailSubclassCode;
	}

	/**
	 * @param mailSubclassCode The mailSubclassCode to set.
	 */
	public void setMailSubclassCode(String mailSubclassCode) {
		this.mailSubclassCode = mailSubclassCode;
	}

	/**
	 * @return Returns the missentIndicator.
	 */
	public String getMissentIndicator() {
		return missentIndicator;
	}

	/**
	 * @param missentIndicator The missentIndicator to set.
	 */
	public void setMissentIndicator(String missentIndicator) {
		this.missentIndicator = missentIndicator;
	}

	/**
	 * @return Returns the payCycleIndicator.
	 */
	public String getPayCycleIndicator() {
		return payCycleIndicator;
	}

	/**
	 * @param payCycleIndicator The payCycleIndicator to set.
	 */
	public void setPayCycleIndicator(String payCycleIndicator) {
		this.payCycleIndicator = payCycleIndicator;
	}

	/**
	 * @return Returns the paymentEvaluationCode.
	 */
	public String getPaymentEvaluationCode() {
		return paymentEvaluationCode;
	}

	/**
	 * @param paymentEvaluationCode The paymentEvaluationCode to set.
	 */
	public void setPaymentEvaluationCode(String paymentEvaluationCode) {
		this.paymentEvaluationCode = paymentEvaluationCode;
	}

	/**
	 * @return Returns the paymentRateCode.
	 */
	public String getPaymentRateCode() {
		return paymentRateCode;
	}

	/**
	 * @param paymentRateCode The paymentRateCode to set.
	 */
	public void setPaymentRateCode(String paymentRateCode) {
		this.paymentRateCode = paymentRateCode;
	}

	/**
	 * @return Returns the payPathIndicator.
	 */
	public String getPayPathIndicator() {
		return payPathIndicator;
	}

	/**
	 * @param payPathIndicator The payPathIndicator to set.
	 */
	public void setPayPathIndicator(String payPathIndicator) {
		this.payPathIndicator = payPathIndicator;
	}

	/**
	 * @return Returns the rateTypeIndicator.
	 */
	public String getRateTypeIndicator() {
		return rateTypeIndicator;
	}

	/**
	 * @param rateTypeIndicator The rateTypeIndicator to set.
	 */
	public void setRateTypeIndicator(String rateTypeIndicator) {
		this.rateTypeIndicator = rateTypeIndicator;
	}

	/**
	 * @return Returns the receptacleIdentifier.
	 */
	public String getReceptacleIdentifier() {
		return receptacleIdentifier;
	}

	/**
	 * @param receptacleIdentifier The receptacleIdentifier to set.
	 */
	public void setReceptacleIdentifier(String receptacleIdentifier) {
		this.receptacleIdentifier = receptacleIdentifier;
	}

	/**
	 * @return Returns the scanPaymentIndicator.
	 */
	public String getScanPaymentIndicator() {
		return scanPaymentIndicator;
	}

	/**
	 * @param scanPaymentIndicator The scanPaymentIndicator to set.
	 */
	public void setScanPaymentIndicator(String scanPaymentIndicator) {
		this.scanPaymentIndicator = scanPaymentIndicator;
	}

	/**
	 * @return Returns the scanPerformanceIndicator.
	 */
	public String getScanPerformanceIndicator() {
		return scanPerformanceIndicator;
	}

	/**
	 * @param scanPerformanceIndicator The scanPerformanceIndicator to set.
	 */
	public void setScanPerformanceIndicator(String scanPerformanceIndicator) {
		this.scanPerformanceIndicator = scanPerformanceIndicator;
	}

	/**
	 * @return Returns the sectorDestination.
	 */
	public String getSectorDestination() {
		return sectorDestination;
	}

	/**
	 * @param sectorDestination The sectorDestination to set.
	 */
	public void setSectorDestination(String sectorDestination) {
		this.sectorDestination = sectorDestination;
	}

	/**
	 * @return Returns the sectorOrigin.
	 */
	public String getSectorOrigin() {
		return sectorOrigin;
	}

	/**
	 * @param sectorOrigin The sectorOrigin to set.
	 */
	public void setSectorOrigin(String sectorOrigin) {
		this.sectorOrigin = sectorOrigin;
	}

	/**
	 * @return Returns the specialRateTypeDescription.
	 */
	public String getSpecialRateTypeDescription() {
		return specialRateTypeDescription;
	}

	/**
	 * @param specialRateTypeDescription The specialRateTypeDescription to set.
	 */
	public void setSpecialRateTypeDescription(String specialRateTypeDescription) {
		this.specialRateTypeDescription = specialRateTypeDescription;
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
	
		
	
}