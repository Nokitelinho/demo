/*
 * MailStatusFilterVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
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

public class MailStatusFilterVO extends AbstractVO  {

	private String companyCode;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String carrierCode;
	private String flightNumber;
	private String flightCarrierCode;
	private LocalDate flightDate;
	private int legSerialNumber;
    private long flightSequenceNumber;
	private String pol;
	private String pou;
	private String pacode;
	private String currentStatus;
	private int carrierid;
	private int flightCarrierid;


	
	/**
	 * @return the carrierid
	 */
	public int getCarrierid() {
		return carrierid;
	}
	/**
	 * @param carrierid the carrierid to set
	 */
	public void setCarrierid(int carrierid) {
		this.carrierid = carrierid;
	}
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
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * @return the currentStatus
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}
	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
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
	 * @return the pacode
	 */
	public String getPacode() {
		return pacode;
	}
	/**
	 * @param pacode the pacode to set
	 */
	public void setPacode(String pacode) {
		this.pacode = pacode;
	}
	/**
	 * @return the pol
	 */
	public String getPol() {
		return pol;
	}
	/**
	 * @param pol the pol to set
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}
	/**
	 * @return the pou
	 */
	public String getPou() {
		return pou;
	}
	/**
	 * @param pou the pou to set
	 */
	public void setPou(String pou) {
		this.pou = pou;
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


}
