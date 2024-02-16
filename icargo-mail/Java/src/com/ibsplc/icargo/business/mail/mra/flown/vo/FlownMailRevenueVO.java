/*
 * FlownMailRevenueVO.java Created on Jun 21, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author Sandeep.T 
 * For setting flown revenue details
 * 
 * Revision History
 * 
 * Version     Date      Author      Description
 * 
 * 0.1      21/06/2010   Sandeep.T    Initial draft
 */
public class FlownMailRevenueVO extends AbstractVO {
	
	public static final String REVENUE_REPORT_MSGTYP = "REVENUE_REPORT";

	public static final String MSG_STD = "IFCSTD";

	public static final String MSG_VERSION = "1";
              
	private String originCountry;
	
	private String originCity;
	
	private String destinationCountry;
	
	private String destinationCity;
	
	private String dsnNumber;
	
	private LocalDate flightDate;
	
	private String flightNumber;
	
	private String sectorFrom;
	
	private String sectorTo;
	
	private String subClass;
	
	private String airCraftType;
	
	private String regionType;
	
	private double weight;
	
	private String station;
	
	private Money amountInZAR;
	
	private String accountCode;

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the airCraftType
	 */
	public String getAirCraftType() {
		return airCraftType;
	}

	/**
	 * @param airCraftType the airCraftType to set
	 */
	public void setAirCraftType(String airCraftType) {
		this.airCraftType = airCraftType;
	}

	/**
	 * @return the amountInZAR
	 */
	public Money getAmountInZAR() {
		return amountInZAR;
	}

	/**
	 * @param amountInZAR the amountInZAR to set
	 */
	public void setAmountInZAR(Money amountInZAR) {
		this.amountInZAR = amountInZAR;
	}

	/**
	 * @return the destinationCity
	 */
	public String getDestinationCity() {
		return destinationCity;
	}

	/**
	 * @param destinationCity the destinationCity to set
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	/**
	 * @return the destinationCountry
	 */
	public String getDestinationCountry() {
		return destinationCountry;
	}

	/**
	 * @param destinationCountry the destinationCountry to set
	 */
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	/**
	 * @return the dsnNumber
	 */
	public String getDsnNumber() {
		return dsnNumber;
	}

	/**
	 * @param dsnNumber the dsnNumber to set
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
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
	 * @return the originCity
	 */
	public String getOriginCity() {
		return originCity;
	}

	/**
	 * @param originCity the originCity to set
	 */
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}

	/**
	 * @return the originCountry
	 */
	public String getOriginCountry() {
		return originCountry;
	}

	/**
	 * @param originCountry the originCountry to set
	 */
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	/**
	 * @return the regionType
	 */
	public String getRegionType() {
		return regionType;
	}

	/**
	 * @param regionType the regionType to set
	 */
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	/**
	 * @return the sectorFrom
	 */
	public String getSectorFrom() {
		return sectorFrom;
	}

	/**
	 * @param sectorFrom the sectorFrom to set
	 */
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}

	/**
	 * @return the sectorTo
	 */
	public String getSectorTo() {
		return sectorTo;
	}

	/**
	 * @param sectorTo the sectorTo to set
	 */
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}

	/**
	 * @return the station
	 */
	public String getStation() {
		return station;
	}

	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.station = station;
	}

	/**
	 * @return the subClass
	 */
	public String getSubClass() {
		return subClass;
	}

	/**
	 * @param subClass the subClass to set
	 */
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	
   
	
	
	
	
	
}
