/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchDetails.java
 *
 *	Created by	:	A-4809
 *	Created on	:	21-Oct-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	21-Oct-2021	:	Draft
 */
public class PaymentBatchDetails {

	public String batchID;
	public String batchStatus;
	public String currency;
	public double batchAmt;
	public double appliedAmt;
	public double unappliedAmt;
	public String date;
	public String remark;
	public String companyCode;
	public String paCode;
	public long batchSequenceNum;
	private String processID;
	public String fileName;
	
	public String getBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getBatchAmt() {
		return batchAmt;
	}
	public void setBatchAmt(double batchAmt) {
		this.batchAmt = batchAmt;
	}
	public double getAppliedAmt() {
		return appliedAmt;
	}
	public void setAppliedAmt(double appliedAmt) {
		this.appliedAmt = appliedAmt;
	}
	public double getUnappliedAmt() {
		return unappliedAmt;
	}
	public void setUnappliedAmt(double unappliedAmt) {
		this.unappliedAmt = unappliedAmt;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 	Getter for companyCode 
	 *	Added by : A-4809 on 21-Oct-2021
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-4809 on 21-Oct-2021
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for paCode 
	 *	Added by : A-4809 on 10-Nov-2021
	 * 	Used for :
	 */
	public String getPaCode() {
		return paCode;
	}
	/**
	 *  @param paCode the paCode to set
	 * 	Setter for paCode 
	 *	Added by : A-4809 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	/**
	 * 	Getter for batchSequenceNum 
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public long getBatchSequenceNum() {
		return batchSequenceNum;
	}
	/**
	 *  @param batchSequenceNum the batchSequenceNum to set
	 * 	Setter for batchSequenceNum 
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setBatchSequenceNum(long batchSequenceNum) {
		this.batchSequenceNum = batchSequenceNum;
	}
	/**
	 * 	Getter for processID 
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getProcessID() {
		return processID;
	}
	/**
	 *  @param processID the processID to set
	 * 	Setter for processID 
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setProcessID(String processID) {
		this.processID = processID;
	}
	/**
	 * 	Getter for fileName 
	 *	Added by : A-4809 on 03-Dec-2021
	 * 	Used for :
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 *  @param fileName the fileName to set
	 * 	Setter for fileName 
	 *	Added by : A-4809 on 03-Dec-2021
	 * 	Used for :
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
}
