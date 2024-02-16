/*
 * MailInvoicTotalPaymentVO.java created on Jul 19, 2007
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
public class MailInvoicTotalPaymentVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	//total no of lines
	private int controlValue;
	
	private int segmentNumber;
	
	private int messageReferenceNumber;
	
	private double totalAdjustedAmount;
	
	private double totalAdjustmentAmount;

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
	 * @return Returns the controlValue.
	 */
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
	 * @return Returns the messageReferenceNumber.
	 */
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
	 * @return Returns the segmentNumber.
	 */
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
	public double getTotalAdjustmentAmount() {
		return totalAdjustmentAmount;
	}

	/**
	 * @param totalAdjustmentAmount The totalAdjustmentAmount to set.
	 */
	public void setTotalAdjustmentAmount(double totalAdjustmentAmount) {
		this.totalAdjustmentAmount = totalAdjustmentAmount;
	}
	
	
	
	
	
}