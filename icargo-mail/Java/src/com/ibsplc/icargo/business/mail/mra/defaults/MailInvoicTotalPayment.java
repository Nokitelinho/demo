/*
 * MailInvoicTotalPayment.java Created on July 19, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicTotalPaymentVO;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVTTL")
@Staleable
@Deprecated
public class MailInvoicTotalPayment {
	private MailInvoicMasterPK mailInvoicMasterPK;
	
	private int controlValue;
	
	private int segmentNumber;
	
	private int messageReferenceNumber;
	
	private double totalAdjustedAmount;
	
	private double totalAdjustmentAmount;
	
	/**
	 * 
	 */
	public MailInvoicTotalPayment(){
		
	}

	/**
	 * @param paymentVO
	 */
	public MailInvoicTotalPayment(MailInvoicTotalPaymentVO paymentVO){
		MailInvoicMasterPK mailInvoicPK=new MailInvoicMasterPK();
		
		mailInvoicPK.setCompanyCode(paymentVO.getCompanyCode());
		mailInvoicPK.setInvoiceKey(paymentVO.getInvoiceKey());
		mailInvoicPK.setPoaCode(paymentVO.getPoaCode());
		
		this.setMailInvoicTotalPaymentPK(mailInvoicPK);
		populateAttributes(paymentVO);
		
	}
	/**
	 * @return Returns the controlValue.
	 */
	@Column(name="CTLVAL")
	public int getControlValue() {
		return controlValue;
	}

	/**
	 * @param controlValue The controlValue to set.
	 */
	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}

	/**
	 * @return Returns the mailInvoicTotalPaymentPK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="invoiceKey", column=@Column(name="INVKEY")),
			@AttributeOverride(name="poaCode", column=@Column(name="POACOD"))}
		)
	public MailInvoicMasterPK getMailInvoicTotalPaymentPK() {
		 return mailInvoicMasterPK;
	 }

	/**
	 * @param mailInvoicTotalPaymentPK The mailInvoicTotalPaymentPK to set.
	 */
	public void setMailInvoicTotalPaymentPK(
			MailInvoicMasterPK mailInvoicMstrPK) {
		this.mailInvoicMasterPK = mailInvoicMstrPK;
	}

	/**
	 * @return Returns the messageReferenceNumber.
	 */
	@Column(name="MSGREFNUM")
	public int getMessageReferenceNumber() {
		return messageReferenceNumber;
	}

	/**
	 * @param messageReferenceNumber The messageReferenceNumber to set.
	 */
	public void setMessageReferenceNumber(int messageReferenceNumber) {
		this.messageReferenceNumber = messageReferenceNumber;
	}

	/**
	 * @return Returns the segmentNumber.
	 */
	@Column(name="SEGNUM")
	public int getSegmentNumber() {
		return segmentNumber;
	}

	/**
	 * @param segmentNumber The segmentNumber to set.
	 */
	public void setSegmentNumber(int segmentNumber) {
		this.segmentNumber = segmentNumber;
	}

	/**
	 * @return Returns the totalAdjustedAmount.
	 */
	@Column(name="TOTAJDAMT")
	public double getTotalAdjustedAmount() {
		return totalAdjustedAmount;
	}

	/**
	 * @param totalAdjustedAmount The totalAdjustedAmount to set.
	 */
	public void setTotalAdjustedAmount(double totalAdjustedAmount) {
		this.totalAdjustedAmount = totalAdjustedAmount;
	}

	/**
	 * @return Returns the totalAdjustmentAmount.
	 */
	@Column(name="TOTAJTAMT")
	public double getTotalAdjustmentAmount() {
		return totalAdjustmentAmount;
	}

	/**
	 * @param totalAdjustmentAmount The totalAdjustmentAmount to set.
	 */
	public void setTotalAdjustmentAmount(double totalAdjustmentAmount) {
		this.totalAdjustmentAmount = totalAdjustmentAmount;
	}
	private void populateAttributes(MailInvoicTotalPaymentVO paymentVO){
		this.setControlValue(paymentVO.getControlValue());
		this.setSegmentNumber(paymentVO.getSegmentNumber());
		this.setMessageReferenceNumber(paymentVO.getMessageReferenceNumber());
		this.setTotalAdjustedAmount(paymentVO.getTotalAdjustedAmount());
		this.setTotalAdjustmentAmount(paymentVO.getTotalAdjustmentAmount());
		
	}
}