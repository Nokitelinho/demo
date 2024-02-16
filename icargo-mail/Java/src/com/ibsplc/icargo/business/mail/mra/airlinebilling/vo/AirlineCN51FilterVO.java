/*
 * AirlineCN51FilterVO.java Created on Feb 15,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2049
 *
 */
public class AirlineCN51FilterVO extends AbstractVO {

	/**
	 * indicates the companyCode
	 */
	private String companyCode;

	/**
	 * indicates the airlineCode
	 */
	private String airlineCode;
	
	/**
	 * indicates the airline number
	 */
	private int airlineNumber;
	
	/**
	 * indicates the airline name
	 */
	private String airlineName; //added for reports by a-2458 
	
	/**
	 * indicates the airlineIdentifier
	 */
	private String strAirlineIdentifier;
	
	/**
	 * indicates the airlineIdentifier
	 */
	private int airlineIdentifier;

	/**
	 * indicates the IATA clearance period
	 */
	private String iataClearancePeriod;

	/**
	 * indicates the CN51 period
	 */
	private String cn51Period;

	/**
	 * indicates invoice Reference Number
	 */
	private String invoiceReferenceNumber;

	/**
	 * indicates the categoryCode
	 */
	private String categoryCode;


	/**
	 * indicates the carriage From Station
	 */
	private String carriageStationFrom ;

	/**
	 * indicates the carriage To Station
	 */
	private String carriageStationTo ;
	
	/**
	 * indicates the interlineBillingType
	 */
	private String interlineBillingType;

	/**
	 * 
	 */
	private LocalDate fromDate;
	
	/**
	 * 
	 */
	private LocalDate toDate;
	/**
	 * 
	 */
	private String airlineNum;//added for reports by a-2458
	
	
 /*  added for View CN51 Screen */
	private LocalDate billedDateFrom;
	
	private LocalDate billedDateTo;
	//added for capture invoice screen
	private LocalDate invDate;
	//Added By Deepthi
	private int pageNumber;
	private int totalRecords;
	//ICRD-265471
	private String billingType;
	//Added by A-7794 as part of ICRD-294064
	private String listingcurrencycode;
	private String invoiceStatus;
	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	private String invoiceNo;
	
	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public LocalDate getBilledDateFrom() {
		return billedDateFrom;
	}

	public void setBilledDateFrom(LocalDate billedDateFrom) {
		this.billedDateFrom = billedDateFrom;
	}

	public LocalDate getBilledDateTo() {
		return billedDateTo;
	}

	public void setBilledDateTo(LocalDate billedDateTo) {
		this.billedDateTo = billedDateTo;
	}
	/* added for View CN51 Screen */
	

	/**
	 * @return Returns the interlineBillingType.
	 */
	public String getInterlineBillingType() {
		return interlineBillingType;
	}

	/**
	 * @param interlineBillingType The interlineBillingType to set.
	 */
	public void setInterlineBillingType(String interlineBillingType) {
		this.interlineBillingType = interlineBillingType;
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
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the carriageStationFrom.
	 */
	public String getCarriageStationFrom() {
		return carriageStationFrom;
	}

	/**
	 * @param carriageStationFrom The carriageStationFrom to set.
	 */
	public void setCarriageStationFrom(String carriageStationFrom) {
		this.carriageStationFrom = carriageStationFrom;
	}

	/**
	 * @return Returns the carriageStationTo.
	 */
	public String getCarriageStationTo() {
		return carriageStationTo;
	}

	/**
	 * @param carriageStationTo The carriageStationTo to set.
	 */
	public void setCarriageStationTo(String carriageStationTo) {
		this.carriageStationTo = carriageStationTo;
	}

	/**
	 * @return Returns the categoryCode.
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode The categoryCode to set.
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * @return Returns the cn51Period.
	 */
	public String getCn51Period() {
		return cn51Period;
	}

	/**
	 * @param cn51Period The cn51Period to set.
	 */
	public void setCn51Period(String cn51Period) {
		this.cn51Period = cn51Period;
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
	 * @return Returns the iataClearancePeriod.
	 */
	public String getIataClearancePeriod() {
		return iataClearancePeriod;
	}

	/**
	 * @param iataClearancePeriod The iataClearancePeriod to set.
	 */
	public void setIataClearancePeriod(String iataClearancePeriod) {
		this.iataClearancePeriod = iataClearancePeriod;
	}

	/**
	 * @return Returns the invoiceReferenceNumber.
	 */
	public String getInvoiceReferenceNumber() {
		return invoiceReferenceNumber;
	}

	/**
	 * @param invoiceReferenceNumber The invoiceReferenceNumber to set.
	 */
	public void setInvoiceReferenceNumber(String invoiceReferenceNumber) {
		this.invoiceReferenceNumber = invoiceReferenceNumber;
	}

	/**
	 * @return Returns the airlineNumber.
	 */
	public int getAirlineNumber() {
		return airlineNumber;
	}

	/**
	 * @param airlineNumber The airlineNumber to set.
	 */
	public void setAirlineNumber(int airlineNumber) {
		this.airlineNumber = airlineNumber;
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

	public String getAirlineNum() {
		return airlineNum;
	}

	public void setAirlineNum(String airlineNum) {
		this.airlineNum = airlineNum;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	/**
	 * @return Returns the strAirlineIdentifier.
	 */
	public String getStrAirlineIdentifier() {
		return strAirlineIdentifier;
	}

	/**
	 * @param strAirlineIdentifier The strAirlineIdentifier to set.
	 */
	public void setStrAirlineIdentifier(String strAirlineIdentifier) {
		this.strAirlineIdentifier = strAirlineIdentifier;
	}

	public LocalDate getInvDate() {
		return invDate;
	}

	public void setInvDate(LocalDate invDate) {
		this.invDate = invDate;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getListingcurrencycode() {
		return listingcurrencycode;
	}

	public void setListingcurrencycode(String listingcurrencycode) {
		this.listingcurrencycode = listingcurrencycode;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	





}
