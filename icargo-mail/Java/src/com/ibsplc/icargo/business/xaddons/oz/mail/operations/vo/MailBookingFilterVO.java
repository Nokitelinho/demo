/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailBookingFilterVO.java
 *
 *	Created by	:	a-7531
 *	Created on	:	10-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailBookingFilterVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7531	:	10-Aug-2017	:	Draft
 */
public class MailBookingFilterVO extends AbstractVO  {
	
	private String companyCode;
	private String shipmentPrefix;
	 private String masterDocumentNumber;
	 private LocalDate bookingFrom;
	 private LocalDate bookingTo;
	 private String mailScc;
	 private String product;
	 private String orginOfBooking;
	 private String destinationOfBooking;
	 private String viaPointOfBooking;
	 private String stationOfBooking;
	 private LocalDate shipmentDate;
	 private String bookingCarrierCode;
	 private String bookingFlightNumber;
	 private String bookingFlightFrom;
	 private String bookingFlightTo;
	 private String agentCode;
	 private String customerCode;
	 private String bookingUserId;
	 private String bookingStatus;
	 private int pageNumber;
	 private int rowCount;
	 private int pageSize;
	 private String shipmentStatus;
	 private int totalRecordsCount;//added by A-7371 for ICRD-228233
	 private String mailSccFromSyspar;

	 
	public String getMailSccFromSyspar() {
		return mailSccFromSyspar;
	}
	public void setMailSccFromSyspar(String mailSccFromSyspar) {
		this.mailSccFromSyspar = mailSccFromSyspar;
	}
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
	public String getShipmentStatus() {
		return shipmentStatus;
	}
	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * 	Getter for shipmentPrefix 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 *  @param shipmentPrefix the shipmentPrefix to set
	 * 	Setter for shipmentPrefix 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	/**
	 * 	Getter for masterDocumentNumber 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	/**
	 *  @param masterDocumentNumber the masterDocumentNumber to set
	 * 	Setter for masterDocumentNumber 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	/**
	 * 	Getter for bookingFrom 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public LocalDate getBookingFrom() {
		return bookingFrom;
	}
	/**
	 *  @param bookingFrom the bookingFrom to set
	 * 	Setter for bookingFrom 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setBookingFrom(LocalDate bookingFrom) {
		this.bookingFrom = bookingFrom;
	}
	/**
	 * 	Getter for bookingTo 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public LocalDate getBookingTo() {
		return bookingTo;
	}
	/**
	 *  @param bookingTo the bookingTo to set
	 * 	Setter for bookingTo 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setBookingTo(LocalDate bookingTo) {
		this.bookingTo = bookingTo;
	}
	/**
	 * 	Getter for mailScc 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getMailScc() {
		return mailScc;
	}
	/**
	 *  @param mailScc the mailScc to set
	 * 	Setter for mailScc 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setMailScc(String mailScc) {
		this.mailScc = mailScc;
	}
	/**
	 * 	Getter for product 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getProduct() {
		return product;
	}
	/**
	 *  @param product the product to set
	 * 	Setter for product 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * 	Getter for orginOfBooking 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getOrginOfBooking() {
		return orginOfBooking;
	}
	/**
	 *  @param orginOfBooking the orginOfBooking to set
	 * 	Setter for orginOfBooking 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setOrginOfBooking(String orginOfBooking) {
		this.orginOfBooking = orginOfBooking;
	}
	/**
	 * 	Getter for destinationOfBooking 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getDestinationOfBooking() {
		return destinationOfBooking;
	}
	/**
	 *  @param destinationOfBooking the destinationOfBooking to set
	 * 	Setter for destinationOfBooking 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setDestinationOfBooking(String destinationOfBooking) {
		this.destinationOfBooking = destinationOfBooking;
	}
	/**
	 * 	Getter for viaPointOfBooking 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getViaPointOfBooking() {
		return viaPointOfBooking;
	}
	/**
	 *  @param viaPointOfBooking the viaPointOfBooking to set
	 * 	Setter for viaPointOfBooking 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setViaPointOfBooking(String viaPointOfBooking) {
		this.viaPointOfBooking = viaPointOfBooking;
	}
	/**
	 * 	Getter for stationOfBooking 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getStationOfBooking() {
		return stationOfBooking;
	}
	/**
	 *  @param stationOfBooking the stationOfBooking to set
	 * 	Setter for stationOfBooking 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setStationOfBooking(String stationOfBooking) {
		this.stationOfBooking = stationOfBooking;
	}
	/**
	 * 	Getter for shipmentDate 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public LocalDate getShipmentDate() {
		return shipmentDate;
	}
	/**
	 *  @param shipmentDate the shipmentDate to set
	 * 	Setter for shipmentDate 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setShipmentDate(LocalDate shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	/**
	 * 	Getter for bookingCarrierCode 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getBookingCarrierCode() {
		return bookingCarrierCode;
	}
	/**
	 *  @param bookingCarrierCode the bookingCarrierCode to set
	 * 	Setter for bookingCarrierCode 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setBookingCarrierCode(String bookingCarrierCode) {
		this.bookingCarrierCode = bookingCarrierCode;
	}
	/**
	 * 	Getter for bookingFlightNumber 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getBookingFlightNumber() {
		return bookingFlightNumber;
	}
	/**
	 *  @param bookingFlightNumber the bookingFlightNumber to set
	 * 	Setter for bookingFlightNumber 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setBookingFlightNumber(String bookingFlightNumber) {
		this.bookingFlightNumber = bookingFlightNumber;
	}
	/**
	 * 	Getter for bookingFlightFrom 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getBookingFlightFrom() {
		return bookingFlightFrom;
	}
	/**
	 *  @param bookingFlightFrom the bookingFlightFrom to set
	 * 	Setter for bookingFlightFrom 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setBookingFlightFrom(String bookingFlightFrom) {
		this.bookingFlightFrom = bookingFlightFrom;
	}
	/**
	 * 	Getter for bookingFlightTo 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getBookingFlightTo() {
		return bookingFlightTo;
	}
	/**
	 *  @param bookingFlightTo the bookingFlightTo to set
	 * 	Setter for bookingFlightTo 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setBookingFlightTo(String bookingFlightTo) {
		this.bookingFlightTo = bookingFlightTo;
	}
	/**
	 * 	Getter for agentCode 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 *  @param agentCode the agentCode to set
	 * 	Setter for agentCode 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * 	Getter for customerCode 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 *  @param customerCode the customerCode to set
	 * 	Setter for customerCode 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * 	Getter for bookingUserId 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getBookingUserId() {
		return bookingUserId;
	}
	/**
	 *  @param bookingUserId the bookingUserId to set
	 * 	Setter for bookingUserId 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setBookingUserId(String bookingUserId) {
		this.bookingUserId = bookingUserId;
	}
	/**
	 * 	Getter for bookingStatus 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public String getBookingStatus() {
		return bookingStatus;
	}
	/**
	 *  @param bookingStatus the bookingStatus to set
	 * 	Setter for bookingStatus 
	 *	Added by : a-7531 on 10-Aug-2017
	 * 	Used for :
	 */
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String string) {
		this.companyCode = string;
	}

}
