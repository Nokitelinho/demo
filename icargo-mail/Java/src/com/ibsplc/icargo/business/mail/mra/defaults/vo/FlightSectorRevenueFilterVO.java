/*
 * FlightSectorRevenueFilterVO.java Created on Aug 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3429
 * 
 */
public class FlightSectorRevenueFilterVO extends AbstractVO {
	/**
	 * Company Code
	 */
	/**
	 * Comment for <code>companyCode</code>
	 */
	private String companyCode;

	/**
	 * Flight Number
	 */
	private String flightNumber;

	/**
	 * Flight carrier identifier
	 */
	private int flightCarrierId;

	/**
	 * Flight carrier Code
	 */
	private String flightCarrierCode;

	/**
	 * Flight Sequence Number
	 */
	private int flightSequenceNumber;

	/**
	 * Flight Date
	 */
	private LocalDate flightDate;

	/**
	 * Flight Date
	 */
	private String stringFlightDate;

	/**
	 * Segment Origin
	 */
	private String segmentOrigin;

	/**
	 * Segment Destination
	 */
	private String segmentDestination;

	/**
	 * Flight Segment Origin-Destination
	 */
	private String flightSegment;

	/**
	 * Segment Serial Number
	 */
	private int segmentSerialNumber;

	/**
	 * Segment Serial Number
	 */
	private String payFlag;

	/**
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode
	 *            the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return the flightCarrierId
	 */
	public int getFlightCarrierId() {
		return flightCarrierId;
	}

	/**
	 * @param flightCarrierId
	 *            the flightCarrierId to set
	 */
	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
	}

	/**
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the flightSegment
	 */
	public String getFlightSegment() {
		return flightSegment;
	}

	/**
	 * @param flightSegment
	 *            the flightSegment to set
	 */
	public void setFlightSegment(String flightSegment) {
		this.flightSegment = flightSegment;
	}

	/**
	 * @return the flightSequenceNumber
	 */
	public int getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            the flightSequenceNumber to set
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return the segmentDestination
	 */
	public String getSegmentDestination() {
		return segmentDestination;
	}

	/**
	 * @param segmentDestination
	 *            the segmentDestination to set
	 */
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}

	/**
	 * @return the segmentOrigin
	 */
	public String getSegmentOrigin() {
		return segmentOrigin;
	}

	/**
	 * @param segmentOrigin
	 *            the segmentOrigin to set
	 */
	public void setSegmentOrigin(String segmentOrigin) {
		this.segmentOrigin = segmentOrigin;
	}

	/**
	 * @return the segmentSerialNumber
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            the segmentSerialNumber to set
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return the stringFlightDate
	 */
	public String getStringFlightDate() {
		return stringFlightDate;
	}

	/**
	 * @param stringFlightDate
	 *            the stringFlightDate to set
	 */
	public void setStringFlightDate(String stringFlightDate) {
		this.stringFlightDate = stringFlightDate;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the payFlag
	 */
	public String getPayFlag() {
		return payFlag;
	}

	/**
	 * @param payFlag
	 *            the payFlag to set
	 */
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}
}
