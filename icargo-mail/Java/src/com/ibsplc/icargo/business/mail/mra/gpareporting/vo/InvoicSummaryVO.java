/*
 * InvoicSummaryVO.java Created on Dec 13, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *  
 * @author A-8464
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date           		Author          	Description
 * 
 *  0.1        Dec 13, 2018  		Sivapriya			Initial draft
 *  
 *  
 */



public class InvoicSummaryVO extends AbstractVO{

	private String gpaCodeFilter;
	private String fromDateFilter;
	private String toDateFilter;
	private String invoicRefId;
	private String fromDate;
	private String toDate;
	private String gpaCode;
	
	public String getGpaCodeFilter() {
		return gpaCodeFilter;
	}
	public void setGpaCodeFilter(String gpaCodeFilter) {
		this.gpaCodeFilter = gpaCodeFilter;
	}
	public String getFromDateFilter() {
		return fromDateFilter;
	}
	public void setFromDateFilter(String fromDateFilter) {
		this.fromDateFilter = fromDateFilter;
	}
	public String getToDateFilter() {
		return toDateFilter;
	}
	public void setToDateFilter(String toDateFilter) {
		this.toDateFilter = toDateFilter;
	}
	public String getInvoicRefId() {
		return invoicRefId;
	}
	public void setInvoicRefId(String invoicRefId) {
		this.invoicRefId = invoicRefId;
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
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	
}
