/*
 * AirlineBillingFilterVO.java Created on Aug 7, 2008
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-3434
 *
 */

public class AirlineBillingFilterVO extends AbstractVO{
	
	private String companyCode; 
    private LocalDate fromDate;
 	private LocalDate toDate;
 	private LocalDate receiveDate;
 	private String airlineCode;
	private String billingType;
	private String billingStatus;
	private String sectorFrom;
	private String sectorTo;
	private int airlineId;
	private int pageNumber;
	private int absoluteIndex;
	private String originOfficeOfExchange;
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public String getMailCategory() {
		return mailCategory;
	}
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}
	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}
	public String getRegisteredIndicator() {
		return registeredIndicator;
	}
	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	private String year;
	private String subClass;
	private String mailCategory;
	private String highestNumberIndicator;
	private String registeredIndicator;
	private String receptacleSerialNumber;
	private String destinationOfficeOfExchange;
	private String dsn;
	//added by A-5223 for ICRD-21098 starts
	private int totalRecords;
	//added by A-5223 for ICRD-21098 ends
	
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
	 * @return Returns the airlineId.
	 */
	public int getAirlineId() {
		return airlineId;
	}
	/**
	 * @param airlineId The airlineId to set.
	 */
	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
	}
	/**
	 * @return Returns the receiveDate.
	 */
	public LocalDate getReceiveDate() {
		return receiveDate;
	}
	/**
	 * @param receiveDate The receiveDate to set.
	 */
	public void setReceiveDate(LocalDate receiveDate) {
		this.receiveDate = receiveDate;
	}
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
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return Returns the billingStatus.
	 */
	public String getBillingStatus() {
		return billingStatus;
	}
	/**
	 * @param billingStatus The billingStatus to set.
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	/**
	 * @return Returns the billingType.
	 */
	public String getBillingType() {
		return billingType;
	}
	/**
	 * @param billingType The billingType to set.
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the sectorFrom.
	 */
	public String getSectorFrom() {
		return sectorFrom;
	}
	/**
	 * @param sectorFrom The sectorFrom to set.
	 */
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}
	/**
	 * @return Returns the sectorTo.
	 */
	public String getSectorTo() {
		return sectorTo;
	}
	/**
	 * @param sectorTo The sectorTo to set.
	 */
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}
	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
	/**
	 * 	Getter for totalRecords 
	 *	Added by : a-5223 on 15-Oct-2012
	 * 	Used for : getting total records
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 *  @param totalRecords the totalRecords to set
	 * 	Setter for totalRecords 
	 *	Added by : a-5223 on 15-Oct-2012
	 * 	Used for :setting total records
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
}
