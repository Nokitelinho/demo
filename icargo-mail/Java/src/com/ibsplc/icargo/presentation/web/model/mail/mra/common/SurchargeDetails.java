/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.SurchargeDetails.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Mar 4, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.SurchargeDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Mar 4, 2019	:	Draft
 */
public class SurchargeDetails {
	
	private String companyCode;
	private String chargeHead;
	private String chargeHeadDesc;
	private double applicableRate;
	private double chargeAmt;
	private long sequenceNumber;
    private String billingBasis;
	private String consigneeDocumentNumber;
	private String consigneeSequenceNumber;
    private String gpaCode; 
    private String invoiceNumber;
    private String totalAmount;
    
    public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getChargeHead() {
		return chargeHead;
	}
	public void setChargeHead(String chargeHead) {
		this.chargeHead = chargeHead;
	}
	public String getChargeHeadDesc() {
		return chargeHeadDesc;
	}
	public void setChargeHeadDesc(String chargeHeadDesc) {
		this.chargeHeadDesc = chargeHeadDesc;
	}
	public double getApplicableRate() {
		return applicableRate;
	}
	public void setApplicableRate(double applicableRate) {
		this.applicableRate = applicableRate;
	}
	public double getChargeAmt() {
		return chargeAmt;
	}
	public void setChargeAmt(double chargeAmt) {
		this.chargeAmt = chargeAmt;
	}
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getBillingBasis() {
		return billingBasis;
	}
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	public String getConsigneeDocumentNumber() {
		return consigneeDocumentNumber;
	}
	public void setConsigneeDocumentNumber(String consigneeDocumentNumber) {
		this.consigneeDocumentNumber = consigneeDocumentNumber;
	}
	public String getConsigneeSequenceNumber() {
		return consigneeSequenceNumber;
	}
	public void setConsigneeSequenceNumber(String consigneeSequenceNumber) {
		this.consigneeSequenceNumber = consigneeSequenceNumber;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}


}
