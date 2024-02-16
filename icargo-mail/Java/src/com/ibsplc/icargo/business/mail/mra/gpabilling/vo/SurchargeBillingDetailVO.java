/*
 * SurchargeBillingDetailVO.java Created on Jul 15, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5255 
 * @version	0.1, Jul 15, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 15, 2015	     A-5255		First draft
 */

public class SurchargeBillingDetailVO extends AbstractVO{
	private String companyCode;
	private String chargeHead;
	private String chargeHeadDesc;
	private double applicableRate;
	private Money chargeAmt;
    private long sequenceNumber;
    private String billingBasis;
	private String consigneeDocumentNumber;
	private String consigneeSequenceNumber;
    private String gpaCode; 
    private String invoiceNumber;
    private String totalAmount;
 

	/**
	 * @return the chargeHead
	 */
	public String getChargeHead() {
		return chargeHead;
	}
	/**
	 * @param chargeHead the chargeHead to set
	 */
	public void setChargeHead(String chargeHead) {
		this.chargeHead = chargeHead;
	}
	/**
	 * @return the applicableRate
	 */
	public double getApplicableRate() {
		return applicableRate;
	}
	/**
	 * @param applicableRate the applicableRate to set
	 */
	public void setApplicableRate(double applicableRate) {
		this.applicableRate = applicableRate;
	}
	/**
	 * @return the chargeAmt
	 */
	public Money getChargeAmt() {
		return chargeAmt;
	}
	/**
	 * @param chargeAmt the chargeAmt to set
	 */
	public void setChargeAmt(Money chargeAmt) {
		this.chargeAmt = chargeAmt;
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
	 * @return the sequenceNumber
	 */
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
	 * @return the consigneeDocumentNumber
	 */
	public String getConsigneeDocumentNumber() {
		return consigneeDocumentNumber;
	}
	/**
	 * @param consigneeDocumentNumber the consigneeDocumentNumber to set
	 */
	public void setConsigneeDocumentNumber(String consigneeDocumentNumber) {
		this.consigneeDocumentNumber = consigneeDocumentNumber;
	}
	/**
	 * @return the consigneeSequenceNumber
	 */
	public String getConsigneeSequenceNumber() {
		return consigneeSequenceNumber;
	}
	/**
	 * @param consigneeSequenceNumber the consigneeSequenceNumber to set
	 */
	public void setConsigneeSequenceNumber(String consigneeSequenceNumber) {
		this.consigneeSequenceNumber = consigneeSequenceNumber;
	}
	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
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
	 * @return the chargeHeadDesc
	 */
	public String getChargeHeadDesc() {
		return chargeHeadDesc;
	}
	/**
	 * @param chargeHeadDesc the chargeHeadDesc to set
	 */
	public void setChargeHeadDesc(String chargeHeadDesc) {
		this.chargeHeadDesc = chargeHeadDesc;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
