/*
 * MailDiscrepancyFilterVO.java Created on Jun 30, 2016
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3109
 *
 */

public class MailDiscrepancyFilterVO extends AbstractVO  {

	private String companyCode;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String flightNumber;
	private String flightCarrierCode;
	private LocalDate flightDate;
	private int legSerialNumber;
    private long flightSequenceNumber;
	private String airport;
	private String discType;
	private int flightCarrierid;


	
	
	/**
	 * @return the flightCarrierid
	 */
	public int getFlightCarrierid() {
		return flightCarrierid;
	}
	/**
	 * @param flightCarrierid the flightCarrierid to set
	 */
	public void setFlightCarrierid(int flightCarrierid) {
		this.flightCarrierid = flightCarrierid;
	}
	
	/**
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	
	
	/**
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the flightSequenceNumber
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber the flightSequenceNumber to set
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return the legSerialNumber
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	/**
	 * @param legSerialNumber the legSerialNumber to set
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the airport.
	 */
	public String getAirport() {
		return airport;
	}
	/**
	 * @param airport The airport to set.
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}
	/**
	 * @return Returns the discType.
	 */
	public String getDiscType() {
		return discType;
	}
	/**
	 * @param discType The discType to set.
	 */
	public void setDiscType(String discType) {
		this.discType = discType;
	}


}
