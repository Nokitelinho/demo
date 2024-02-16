/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryFilter.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Dec 17, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryFilter.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Dec 17, 2018	:	Draft
 */
public class GPABillingEntryFilter {
	
	private String fromDate;
	private String toDate;
	private String consignmentNumber;
	private String billingStatus;
	private String gpaCode;
	private String originOE;
	private String destinationOE;
	private String category;
	private String subClass;
	private String year;
	private String dsn;
	private String rsn;
	private String hni;
	private String ri;
	private String mailbag;
	private String origin;
	private String destination;	
	private String uspsMailPerformance;
	private String rateBasis;
	private int totalRecords;
	private int defaultPageSize=10;
	private String cmpcod;
	private String displayPage;
	private String paBuilt ;
	private boolean exportToExcel=false;
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
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	public String getOriginOE() {
		return originOE;
	}
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}
	public String getDestinationOE() {
		return destinationOE;
	}
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	public String getHni() {
		return hni;
	}
	public void setHni(String hni) {
		this.hni = hni;
	}
	public String getRi() {
		return ri;
	}
	public void setRi(String ri) {
		this.ri = ri;
	}
	public String getMailbag() {
		return mailbag;
	}
	public void setMailbag(String mailbag) {
		this.mailbag = mailbag;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getUspsMailPerformance() {
		return uspsMailPerformance;
	}
	public void setUspsMailPerformance(String uspsMailPerformance) {
		this.uspsMailPerformance = uspsMailPerformance;
	}
	public String getRateBasis() {
		return rateBasis;
	}
	public void setRateBasis(String rateBasis) {
		this.rateBasis = rateBasis;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
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
	public String getPaBuilt() {
		return paBuilt;
	}
	public void setPaBuilt(String paBuilt) {
		this.paBuilt = paBuilt;
	}
	public boolean isExportToExcel() {
		return exportToExcel;
	}
	public void setExportToExcel(boolean exportToExcel) {
		this.exportToExcel = exportToExcel;
	}

	
	
	

}
