/*
 * GPAReportingFlightDetailsVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1453
 *
 */

public class GPAReportingFlightDetailsVO extends AbstractVO {
	/**
	 * companyCode
	 */
	private String companyCode;

	/**
	 *  poaCode
	 */
	private String poaCode;

	/**
	 * billingbasis
	 */
	private String billingBasis;

	/**
	 * reportingFrom
	 */
	private LocalDate reportingFrom;

	/**
	 * reportingTo
	 */
	private LocalDate reportingTo;

	/**
	 * sequenceNumber
	 */
	private int sequenceNumber;

	private int flightSequenceNumber;

	private String carriageFrom;

	private String carriageTo;

	private String flightNumber;

	private String flightCarrierCode;
	
	private String operationFlag;
	
	private String reportingFromString;
	
	private String reportingToString;

	/**
	 * @author A-3447
	 */
	private String billingIdentifier;
	
	/**
	 * @return the billingIdentifier
	 */
	public String getBillingIdentifier() {
		return billingIdentifier;
	}

	/**
	 * @param billingIdentifier the billingIdentifier to set
	 */
	public void setBillingIdentifier(String billingIdentifier) {
		this.billingIdentifier = billingIdentifier;
	}

	/**
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return Returns the carriageFrom.
	 */
	public String getCarriageFrom() {
		return carriageFrom;
	}

	/**
	 * @param carriageFrom The carriageFrom to set.
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
	 * @param carriageTo The carriageTo to set.
	 */
	public void setCarriageTo(String carriageTo) {
		this.carriageTo = carriageTo;
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
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public int getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the reportingFrom.
	 */
	public LocalDate getReportingFrom() {
		return reportingFrom;
	}

	/**
	 * @param reportingFrom The reportingFrom to set.
	 */
	public void setReportingFrom(LocalDate reportingFrom) {
		this.reportingFrom = reportingFrom;
	}

	/**
	 * @return Returns the reportingTo.
	 */
	public LocalDate getReportingTo() {
		return reportingTo;
	}

	/**
	 * @param reportingTo The reportingTo to set.
	 */
	public void setReportingTo(LocalDate reportingTo) {
		this.reportingTo = reportingTo;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the reportingFromString.
	 */
	public String getReportingFromString() {
		return reportingFromString;
	}

	/**
	 * @param reportingFromString The reportingFromString to set.
	 */
	public void setReportingFromString(String reportingFromString) {
		this.reportingFromString = reportingFromString;
	}

	/**
	 * @return Returns the reportingToString.
	 */
	public String getReportingToString() {
		return reportingToString;
	}

	/**
	 * @param reportingToString The reportingToString to set.
	 */
	public void setReportingToString(String reportingToString) {
		this.reportingToString = reportingToString;
	}

}
