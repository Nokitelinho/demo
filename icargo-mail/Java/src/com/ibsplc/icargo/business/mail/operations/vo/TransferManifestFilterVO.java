/*
 * TransferManifestFilterVO.java Created on Jun 30, 2016
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
public class TransferManifestFilterVO extends AbstractVO {

	private String referenceNumber;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String inCarrierCode;
	private String inFlightNumber;
	private LocalDate inFlightDate;
	private String outCarrierCode;
	private String outFlightNumber;
	private LocalDate outFlightDate;
	private int pageNumber;
	private String companyCode;
	private String airportCode;
	private String transferStatus;

	// Added by A-5220 for ICRD-21098 starts
	private int totalRecordsCount;

	/**
	 * @return the totalRecordsCount
	 */
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}

	/**
	 * @param totalRecordsCount
	 *            the totalRecordsCount to set
	 */
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}

	// Added by A-5220 for ICRD-21098 ends

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the inCarrierCode
	 */
	public String getInCarrierCode() {
		return inCarrierCode;
	}

	/**
	 * @param inCarrierCode
	 *            the inCarrierCode to set
	 */
	public void setInCarrierCode(String inCarrierCode) {
		this.inCarrierCode = inCarrierCode;
	}

	/**
	 * @return the inFlightDate
	 */
	public LocalDate getInFlightDate() {
		return inFlightDate;
	}

	/**
	 * @param inFlightDate
	 *            the inFlightDate to set
	 */
	public void setInFlightDate(LocalDate inFlightDate) {
		this.inFlightDate = inFlightDate;
	}

	/**
	 * @return the inFlightNumber
	 */
	public String getInFlightNumber() {
		return inFlightNumber;
	}

	/**
	 * @param inFlightNumber
	 *            the inFlightNumber to set
	 */
	public void setInFlightNumber(String inFlightNumber) {
		this.inFlightNumber = inFlightNumber;
	}

	/**
	 * @return the outCarrierCode
	 */
	public String getOutCarrierCode() {
		return outCarrierCode;
	}

	/**
	 * @param outCarrierCode
	 *            the outCarrierCode to set
	 */
	public void setOutCarrierCode(String outCarrierCode) {
		this.outCarrierCode = outCarrierCode;
	}

	/**
	 * @return the outFlightDate
	 */
	public LocalDate getOutFlightDate() {
		return outFlightDate;
	}

	/**
	 * @param outFlightDate
	 *            the outFlightDate to set
	 */
	public void setOutFlightDate(LocalDate outFlightDate) {
		this.outFlightDate = outFlightDate;
	}

	/**
	 * @return the outFlightNumber
	 */
	public String getOutFlightNumber() {
		return outFlightNumber;
	}

	/**
	 * @param outFlightNumber
	 *            the outFlightNumber to set
	 */
	public void setOutFlightNumber(String outFlightNumber) {
		this.outFlightNumber = outFlightNumber;
	}

	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @param referenceNumber
	 *            the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
}
