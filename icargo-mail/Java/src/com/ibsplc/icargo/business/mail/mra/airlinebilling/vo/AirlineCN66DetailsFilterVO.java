/*
 * AirlineCN66DetailsFilterVO.java Created on Jun 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 *
 * @author A-2407
 *
 */
/**
 * @author a-2408
 *
 */
public class AirlineCN66DetailsFilterVO extends AbstractVO {
	private String companyCode;

	private String clearancePeriod;

	private int airlineId;

	private String airlineCode;

	private String cn66Period;

	private String invoiceRefNumber;

	private String category;

	private String carriageFrom;

	private String carriageTo;

	private String despatchStatus;

	private String interlineBillingType;

	private String lastUpdatedUser;
	//Added By A-3434 for Pagination
	private int pageNumber;
	private int totalRecords;
	
	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	private String billingType; //added by A-7929 as part of ICRD-265471
	
	private String despatchSerialNumber;
	
	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return Returns the carriageFrom.
	 */
	public String getCarriageFrom() {
		return carriageFrom;
	}

	/**
	 * @param carriageFrom
	 *            The carriageFrom to set.
	 */
	public void setCarriageFrom(String carriageFrom) {
		this.carriageFrom = carriageFrom;
	}

	/**
	 * @return Returns the carriageTo.
	 */
	public String getCarriageTo() {
		return carriageTo;
	}

	/**
	 * @param carriageTo
	 *            The carriageTo to set.
	 */
	public void setCarriageTo(String carriageTo) {
		this.carriageTo = carriageTo;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod
	 *            The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the despatchStatus.
	 */
	public String getDespatchStatus() {
		return despatchStatus;
	}

	/**
	 * @param despatchStatus
	 *            The despatchStatus to set.
	 */
	public void setDespatchStatus(String despatchStatus) {
		this.despatchStatus = despatchStatus;
	}

	/**
	 * @return Returns the cn66Period.
	 */
	public String getCn66Period() {
		return cn66Period;
	}

	/**
	 * @param cn66Period
	 *            The cn66Period to set.
	 */
	public void setCn66Period(String cn66Period) {
		this.cn66Period = cn66Period;
	}

	/**
	 * @return Returns the interlineBillingType.
	 */
	public String getInterlineBillingType() {
		return interlineBillingType;
	}

	/**
	 * @param interlineBillingType
	 *            The interlineBillingType to set.
	 */
	public void setInterlineBillingType(String interlineBillingType) {
		this.interlineBillingType = interlineBillingType;
	}

	/**
	 * @return Returns the invoiceRefNumber.
	 */
	public String getInvoiceRefNumber() {
		return invoiceRefNumber;
	}

	/**
	 * @param invoiceRefNumber
	 *            The invoiceRefNumber to set.
	 */
	public void setInvoiceRefNumber(String invoiceRefNumber) {
		this.invoiceRefNumber = invoiceRefNumber;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the airlineId.
	 */
	public int getAirlineId() {
		return airlineId;
	}

	/**
	 * @param airlineId
	 *            The airlineId to set.
	 */
	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the despatchSerialNumber
	 */
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	/**
	 * @param despatchSerialNumber the despatchSerialNumber to set
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}


}
