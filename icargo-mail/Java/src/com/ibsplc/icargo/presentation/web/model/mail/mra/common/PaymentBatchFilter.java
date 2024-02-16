/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchFilter.java
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchFilter.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	21-Oct-2021	:	Draft
 */
public class PaymentBatchFilter {
	
	private String paCode;
	private String fromDate;
	private String toDate;
	private String batchStatus;
	private int totalRecords;
	private String defaultPageSize;
	private String cmpcod;
	private String displayPage;
	
	
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(String defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	public String getCmpcod() {
		return cmpcod;
	}
	public void setCmpcod(String cmpcod) {
		this.cmpcod = cmpcod;
	}
	public String getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	
	

}
