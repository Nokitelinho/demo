/*
 * SectorRevenueDetailsVO.java Created on Aug 19, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3429
 * 
 */
public class SectorRevenueDetailsVO extends AbstractVO {

	/**
	 * Company Code
	 */
	/**
	 * <code>companyCode</code>
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
	 * flightSegmentStatus
	 */
	private String flightSegmentStatus;

	/**
	 * flightStatus
	 */
	private String flightStatus;

	/**
	 * dsn
	 */
	private String dsn;

	/**
	 * errorPresent
	 */
	private String errorPresent;

	/**
	 * grossWeight
	 */
	private double grossWeight;

	/**
	 * currency
	 */
	private String currency;

	/**
	 * weightCharge
	 */
	private String weightCharge;

	/**
	 * netRevenue
	 */
	private Money netRevenue;

	/**
	 * weightChargeBase
	 */
	private Money weightChargeBase;

	/**
	 * totalGrossWeight
	 */
	private double totalGrossWeight;

	/**
	 * totalWeightCharge
	 */
	private double totalWeightCharge;

	/**
	 * totalNetRevenue
	 */
	private double totalNetRevenue;

	/**
	 * accStatus
	 */
	private String accStatus;
	
	//added by A-3229 for desp enquiry flown details
	/**
	 * pieces
	 */
	private int pieces;
	/**
	 * sector
	 */
	private String sector;
	
	/**
	 * Billing basis
	 */
	private String blgbas;
	/**
	 * consignment Document Number
	 */
	private String csgdocnum;
	/**
	 * consignment Sequence Number
	 */
	private int csgseqnum;
	/**
	 * poaCode
	 */
	private String poaCode;
	
	
	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector
	 *            the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	
	
	/**
	 * @return the pieces
	 */
	public int getPieces() {
		return pieces;
	}

	/**
	 * @param pieces
	 *            the pieces to set
	 */
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	/**
	 * @return the accStatus
	 */
	public String getAccStatus() {
		return accStatus;
	}

	/**
	 * @param accStatus
	 *            the accStatus to set
	 */
	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn
	 *            the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return the errorPresent
	 */
	public String getErrorPresent() {
		return errorPresent;
	}

	/**
	 * @param errorPresent
	 *            the errorPresent to set
	 */
	public void setErrorPresent(String errorPresent) {
		this.errorPresent = errorPresent;
	}

	

	/**
	 * @return the netRevenue
	 */
	public Money getNetRevenue() {
		return netRevenue;
	}

	/**
	 * @param netRevenue the netRevenue to set
	 */
	public void setNetRevenue(Money netRevenue) {
		this.netRevenue = netRevenue;
	}

	/**
	 * @return the weightCharge
	 */
	public String getWeightCharge() {
		return weightCharge;
	}

	/**
	 * @param weightCharge
	 *            the weightCharge to set
	 */
	public void setWeightCharge(String weightCharge) {
		this.weightCharge = weightCharge;
	}

	/**
	 * @return the grossWeight
	 */
	public double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * @param grossWeight
	 *            the grossWeight to set
	 */
	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * @return the totalGrossWeight
	 */
	public double getTotalGrossWeight() {
		return totalGrossWeight;
	}

	/**
	 * @param totalGrossWeight
	 *            the totalGrossWeight to set
	 */
	public void setTotalGrossWeight(double totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}

	/**
	 * @return the totalNetRevenue
	 */
	public double getTotalNetRevenue() {
		return totalNetRevenue;
	}

	/**
	 * @param totalNetRevenue
	 *            the totalNetRevenue to set
	 */
	public void setTotalNetRevenue(double totalNetRevenue) {
		this.totalNetRevenue = totalNetRevenue;
	}

	/**
	 * @return the totalWeightCharge
	 */
	public double getTotalWeightCharge() {
		return totalWeightCharge;
	}

	/**
	 * @param totalWeightCharge
	 *            the totalWeightCharge to set
	 */
	public void setTotalWeightCharge(double totalWeightCharge) {
		this.totalWeightCharge = totalWeightCharge;
	}

	
	/**
	 * @return the weightChargeBase
	 */
	public Money getWeightChargeBase() {
		return weightChargeBase;
	}

	/**
	 * @param weightChargeBase the weightChargeBase to set
	 */
	public void setWeightChargeBase(Money weightChargeBase) {
		this.weightChargeBase = weightChargeBase;
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
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode
	 *            The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightCarrierId.
	 */
	public int getFlightCarrierId() {
		return flightCarrierId;
	}

	/**
	 * @param flightCarrierId
	 *            The flightCarrierId to set.
	 */
	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
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
	 * @return Returns the flightSegment.
	 */
	public String getFlightSegment() {
		return flightSegment;
	}

	/**
	 * @param flightSegment
	 *            The flightSegment to set.
	 */
	public void setFlightSegment(String flightSegment) {
		this.flightSegment = flightSegment;
	}

	/**
	 * @return Returns the flightSegmentStatus.
	 */
	public String getFlightSegmentStatus() {
		return flightSegmentStatus;
	}

	/**
	 * @param flightSegmentStatus
	 *            The flightSegmentStatus to set.
	 */
	public void setFlightSegmentStatus(String flightSegmentStatus) {
		this.flightSegmentStatus = flightSegmentStatus;
	}

	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public int getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the segmentDestination.
	 */
	public String getSegmentDestination() {
		return segmentDestination;
	}

	/**
	 * @param segmentDestination
	 *            The segmentDestination to set.
	 */
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}

	/**
	 * @return Returns the segmentOrigin.
	 */
	public String getSegmentOrigin() {
		return segmentOrigin;
	}

	/**
	 * @param segmentOrigin
	 *            The segmentOrigin to set.
	 */
	public void setSegmentOrigin(String segmentOrigin) {
		this.segmentOrigin = segmentOrigin;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the stringFlightDate.
	 */
	public String getStringFlightDate() {
		return stringFlightDate;
	}

	/**
	 * @param stringFlightDate
	 *            The stringFlightDate to set.
	 */
	public void setStringFlightDate(String stringFlightDate) {
		this.stringFlightDate = stringFlightDate;
	}

	/**
	 * @return Returns the flightStatus.
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus
	 *            The flightStatus to set.
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return the blgbas
	 */
	public String getBlgbas() {
		return blgbas;
	}

	/**
	 * @param blgbas the blgbas to set
	 */
	public void setBlgbas(String blgbas) {
		this.blgbas = blgbas;
	}

	/**
	 * @return the csgdocnum
	 */
	public String getCsgdocnum() {
		return csgdocnum;
	}

	/**
	 * @param csgdocnum the csgdocnum to set
	 */
	public void setCsgdocnum(String csgdocnum) {
		this.csgdocnum = csgdocnum;
	}

	/**
	 * @return the csgseqnum
	 */
	public int getCsgseqnum() {
		return csgseqnum;
	}

	/**
	 * @param csgseqnum the csgseqnum to set
	 */
	public void setCsgseqnum(int csgseqnum) {
		this.csgseqnum = csgseqnum;
	}

	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
}