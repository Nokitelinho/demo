/*
 * ULDHistoryVO.java Created on Oct 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2619
 * 
 */
public class ULDHistoryVO extends AbstractVO {

	private String companyCode;

	private String uldNumber;

	private String uldStatus;

	private LocalDate uldTransactionDate;

	private LocalDate fromDate;

	private LocalDate toDate;

	private String carrierCode;

	private String flightNumber;

	private LocalDate flightDate;

	private String fromStation;

	private String toStation;

	private String remarks;

	private int pageNumber;

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the fromStation.
	 */
	public String getFromStation() {
		return fromStation;
	}

	/**
	 * @param fromStation
	 *            The fromStation to set.
	 */
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the uldStatus.
	 */
	public String getUldStatus() {
		return uldStatus;
	}

	/**
	 * @param uldStatus
	 *            The uldStatus to set.
	 */
	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
	}

	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber
	 *            The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return Returns the toStation.
	 */
	public String getToStation() {
		return toStation;
	}

	/**
	 * @param toStation
	 *            The toStation to set.
	 */
	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	/**
	 * @return Returns the uldTransactionDate.
	 */
	public LocalDate getUldTransactionDate() {
		return uldTransactionDate;
	}

	/**
	 * @param uldTransactionDate
	 *            The uldTransactionDate to set.
	 */
	public void setUldTransactionDate(LocalDate uldTransactionDate) {
		this.uldTransactionDate = uldTransactionDate;
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

}
