/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailSettlementBatchDetails.java
 *
 *	Created by	:	A-3429
 *	Created on	:	18-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.common;
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailSettlementBatchDetails	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-3429	:	18-Nov-2021	:	Draft
 */
public class MailSettlementBatchDetails {

	private String companyCode;

	private String batchId;

	private long batchSequenceNum;

	private String gpaCode;

	private String mailIdr;

	private long malseqnum;

	private String invoiceNumber;

	private double appliedAmount;

	private String accountNumber;

	private double batchamount;

	private double unappliedAmount;

	private String reasonCode;

	private String remarks;

	private String settlementCurrencyCode;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	public long getBatchSequenceNum() {
		return batchSequenceNum;
	}

	public void setBatchSequenceNum(long batchSequenceNum) {
		this.batchSequenceNum = batchSequenceNum;
	}

	public String getGpaCode() {
		return gpaCode;
	}

	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	public String getMailIdr() {
		return mailIdr;
	}

	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public double getAppliedAmount() {
		return appliedAmount;
	}

	public void setAppliedAmount(double appliedAmount) {
		this.appliedAmount = appliedAmount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBatchamount() {
		return batchamount;
	}

	public void setBatchamount(double batchamount) {
		this.batchamount = batchamount;
	}

	public double getUnappliedAmount() {
		return unappliedAmount;
	}

	public void setUnappliedAmount(double unappliedAmount) {
		this.unappliedAmount = unappliedAmount;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public long getMalseqnum() {
		return malseqnum;
	}

	public void setMalseqnum(long malseqnum) {
		this.malseqnum = malseqnum;
	}

}
