/*
 * MailInvoicProductDtl.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicProductDtlVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVPRDDTL")
@Staleable
@Deprecated
public class MailInvoicProductDtl {
	
	private MailInvoicProductDtlPK mailInvoicProductDtlPK;
	
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
	 * 
	 */
	public MailInvoicProductDtl(){
		
	}
	public MailInvoicProductDtl(MailInvoicProductDtlVO mailInvoicProductVO)
	throws SystemException{
		MailInvoicProductDtlPK productPK=new MailInvoicProductDtlPK();
		
		productPK.setCompanyCode(mailInvoicProductVO.getCompanyCode());
		productPK.setInvoiceKey(mailInvoicProductVO.getInvoiceKey());
		productPK.setPoaCode(mailInvoicProductVO.getPoaCode());
		productPK.setReceptacleIdentifier(mailInvoicProductVO.getReceptacleIdentifier());
		productPK.setSectorDestination(mailInvoicProductVO.getSectorDestination());
		productPK.setSectorOrigin(mailInvoicProductVO.getSectorOrigin());
		this.setMailInvoicProductDtlPK(productPK);
		populateAttributes(mailInvoicProductVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}

	}

	/**
	 * @return Returns the adjustmentIndicator.
	 */
	@Column(name="ADJIND")
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
	@Column(name="ADJRSNCOD")
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
	@Column(name="CLMADJCOD")
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
	@Column(name="CLMRSNCOD")
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
	@Column(name="CLMSTA")
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
	@Column(name="CLMTXT")
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
	@Column(name="CODLSTQFR")
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
	@Column(name="CODLSTAGC")
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
	 * @return Returns the consignmentDocumentNumber.
	 */
	@Column(name="CONDOCNUM")
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
	@Column(name="CNTSERNUM")
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
	@Column(name="GCM")
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
	 * @return Returns the lateIndicator.
	 */
	@Column(name="LATIND")
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
	@Column(name="LINNUM")
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
	@Column(name="MALCATCOD")
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
	@Column(name="MALCLSCOD")
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
	 * @return Returns the mailInvoicProductDtlPK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="invoiceKey", column=@Column(name="INVKEY")),
			@AttributeOverride(name="poaCode", column=@Column(name="POACOD")),
			@AttributeOverride(name="receptacleIdentifier", column=@Column(name="RCPIDR")),
			@AttributeOverride(name="sectorOrigin", column=@Column(name="SECORG")),
			@AttributeOverride(name="sectorDestination", column=@Column(name="SECDST"))}
		)
	public MailInvoicProductDtlPK getMailInvoicProductDtlPK() {
		return mailInvoicProductDtlPK;
	}

	/**
	 * @param mailInvoicProductDtlPK The mailInvoicProductDtlPK to set.
	 */
	public void setMailInvoicProductDtlPK(
			MailInvoicProductDtlPK mailInvoicProductDtlPK) {
		this.mailInvoicProductDtlPK = mailInvoicProductDtlPK;
	}

	/**
	 * @return Returns the mailSubclassCode.
	 */
	@Column(name="MALSUBCOD")
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
	@Column(name="MISSNTIND")
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
	@Column(name="PAYCYCIND")
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
	@Column(name="PMTEVLCOD")
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
	@Column(name="PMTRATCOD")
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
	@Column(name="PAYPATIND")
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
	@Column(name="RATTYPIND")
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
	 * @return Returns the scanPaymentIndicator.
	 */
	@Column(name="SCNPMTIND")
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
	@Column(name="SCNPERIND")
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
	 * @return Returns the specialRateTypeDescription.
	 */
	@Column(name="RATTYPDSC")
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
	 * @param mailInvoicProductVO
	 */
	private void populateAttributes(MailInvoicProductDtlVO mailInvoicProductVO){
		
		this.setLineNumber(mailInvoicProductVO.getLineNumber());
		this.setAdjustmentIndicator(mailInvoicProductVO.getAdjustmentIndicator());
		this.setPayPathIndicator(mailInvoicProductVO.getPayPathIndicator());
		this.setPaymentEvaluationCode(mailInvoicProductVO.getPaymentEvaluationCode());
		this.setMissentIndicator(mailInvoicProductVO.getMissentIndicator());
		this.setLateIndicator(mailInvoicProductVO.getLateIndicator());
		this.setScanPerformanceIndicator(mailInvoicProductVO.getScanPerformanceIndicator());
		this.setScanPaymentIndicator(mailInvoicProductVO.getScanPaymentIndicator());
		this.setRateTypeIndicator(mailInvoicProductVO.getRateTypeIndicator());
		this.setPayCycleIndicator(mailInvoicProductVO.getPayCycleIndicator());
		this.setAdjustmentReasonCode(mailInvoicProductVO.getAdjustmentReasonCode());
		this.setGreatCircleMiles(mailInvoicProductVO.getGreatCircleMiles());
		this.setConsignmentDocumentNumber(mailInvoicProductVO.getConsignmentDocumentNumber());
		this.setMailCategoryCode(mailInvoicProductVO.getMailCategoryCode());
		this.setMailClassCode(mailInvoicProductVO.getMailClassCode());
		this.setMailSubclassCode(mailInvoicProductVO.getMailSubclassCode());
		this.setPaymentRateCode(mailInvoicProductVO.getPaymentRateCode());
		this.setContainerSerialNumber(mailInvoicProductVO.getContainerSerialNumber());
		this.setCodeListQualifier(mailInvoicProductVO.getCodeListQualifier());
		this.setCodeListResponsibleAgency(mailInvoicProductVO.getCodeListResponsibleAgency());
		this.setSpecialRateTypeDescription(mailInvoicProductVO.getSpecialRateTypeDescription());
		this.setClaimAdjustmentCode(mailInvoicProductVO.getClaimAdjustmentCode());
		this.setClaimReasonCode(mailInvoicProductVO.getClaimReasonCode());
		this.setClaimStatus(mailInvoicProductVO.getClaimStatus());
		this.setClaimText(mailInvoicProductVO.getClaimText());

	}
	/**
	 * @param mailInvoicProductVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailInvoicProductDtl find(MailInvoicProductDtlVO mailInvoicProductVO)
	throws SystemException,FinderException{
		MailInvoicProductDtlPK productPKfind=new MailInvoicProductDtlPK();
				
		productPKfind.setCompanyCode(mailInvoicProductVO.getCompanyCode());
		productPKfind.setInvoiceKey(mailInvoicProductVO.getInvoiceKey());
		productPKfind.setPoaCode(mailInvoicProductVO.getPoaCode());
		productPKfind.setReceptacleIdentifier(mailInvoicProductVO.getReceptacleIdentifier());
		productPKfind.setSectorDestination(mailInvoicProductVO.getSectorDestination());
		productPKfind.setSectorOrigin(mailInvoicProductVO.getSectorOrigin());
				return PersistenceController.getEntityManager().find(
						MailInvoicProductDtl.class, productPKfind);
	}
	
}