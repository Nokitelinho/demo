/*
 * MailInvoicEnquirySummaryVO.java created on Jul 19, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2270
 *
 */
public class MailInvoicEnquirySummaryVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
    private String carrierCode;
	
	private String carrierName;
	
	private String contractType;
	
	private LocalDate scheduledInvoiceDate;
	
	private String controlValue;
	
	private Money totalAdjustmentAmount;
	
	private String reconcilStatus;
	
	private String paymentType;
	
	private Collection<MailInvoicEnquiryDetailsVO> mailInvoicEnquiryDetailsVOs;

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the carrierName
	 */
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName the carrierName to set
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the contractType
	 */
	public String getContractType() {
		return contractType;
	}

	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

		/**
	 * @return the controlValue
	 */
	public String getControlValue() {
		return controlValue;
	}

	/**
	 * @param controlValue the controlValue to set
	 */
	public void setControlValue(String controlValue) {
		this.controlValue = controlValue;
	}

	/**
	 * @return the invoiceKey
	 */
	public String getInvoiceKey() {
		return invoiceKey;
	}

	/**
	 * @param invoiceKey the invoiceKey to set
	 */
	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}

	

		/**
	 * @return the mailInvoicEnquiryDetailsVOs
	 */
	public Collection<MailInvoicEnquiryDetailsVO> getMailInvoicEnquiryDetailsVOs() {
		return mailInvoicEnquiryDetailsVOs;
	}

	/**
	 * @param mailInvoicEnquiryDetailsVOs the mailInvoicEnquiryDetailsVOs to set
	 */
	public void setMailInvoicEnquiryDetailsVOs(
			Collection<MailInvoicEnquiryDetailsVO> mailInvoicEnquiryDetailsVOs) {
		this.mailInvoicEnquiryDetailsVOs = mailInvoicEnquiryDetailsVOs;
	}


	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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
	 * @return the reconcilStatus
	 */
	public String getReconcilStatus() {
		return reconcilStatus;
	}

	/**
	 * @param reconcilStatus the reconcilStatus to set
	 */
	public void setReconcilStatus(String reconcilStatus) {
		this.reconcilStatus = reconcilStatus;
	}

	/**
	 * @return the scheduledInvoiceDate
	 */
	public LocalDate getScheduledInvoiceDate() {
		return scheduledInvoiceDate;
	}

	/**
	 * @param scheduledInvoiceDate the scheduledInvoiceDate to set
	 */
	public void setScheduledInvoiceDate(LocalDate scheduledInvoiceDate) {
		this.scheduledInvoiceDate = scheduledInvoiceDate;
	}

	/**
	 * @return Returns the totalAdjustmentAmount.
	 */
	public Money getTotalAdjustmentAmount() {
		return totalAdjustmentAmount;
	}

	/**
	 * @param totalAdjustmentAmount The totalAdjustmentAmount to set.
	 */
	public void setTotalAdjustmentAmount(Money totalAdjustmentAmount) {
		this.totalAdjustmentAmount = totalAdjustmentAmount;
	}

	
	

		
}