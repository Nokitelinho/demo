/*
 * DamageMailFilterVO.java Created on JUN 30, 2016
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
 * @author A-5991
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			JUN 30, 2016			 	   A-5991			First Draft
 */
public class DamageMailFilterVO extends AbstractVO {
	
	private LocalDate fromDate;
	private LocalDate toDate;
	private String airport;
	private String damageCode;
	private String airline;
	private Integer airlineId;
	//added by A-5844 for ICRD-67196
	private String flightCarrierCode; 
	private String flightNumber; 
	private LocalDate flightDate; 
	private String flightOrigin; 
	private String flightDestination; 
	private String gpaCode; 
	private String originOE; 
	private String destinationOE; 
	private String subClassGroup; 
	private String subClassCode; 
	private String companyCode; 
	
	/**
	 * @return the airlineId
	 */
	public Integer getAirlineId() {
		return airlineId;
	}
	/**
	 * @param airlineId the airlineId to set
	 */
	public void setAirlineId(Integer airlineId) {
		this.airlineId = airlineId;
	}
	/**
	 * @return the airport
	 */
	public String getAirport() {
		return airport;
	}
	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}
	/**
	 * @return the damageCode
	 */
	public String getDamageCode() {
		return damageCode;
	}
	/**
	 * @param damageCode the damageCode to set
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
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
	 * @return the airline
	 */
	public String getAirline() {
		return airline;
	}
	/**
	 * @param airline the airline to set
	 */
	public void setAirline(String airline) {
		this.airline = airline;
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
	 * @return the flightOrigin
	 */
	public String getFlightOrigin() {
		return flightOrigin;
	}
	/**
	 * @param flightOrigin the flightOrigin to set
	 */
	public void setFlightOrigin(String flightOrigin) {
		this.flightOrigin = flightOrigin;
	}
	/**
	 * @return the flightDestination
	 */
	public String getFlightDestination() {
		return flightDestination;
	}
	/**
	 * @param flightDestination the flightDestination to set
	 */
	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
	}
	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the originOE
	 */
	public String getOriginOE() {
		return originOE;
	}
	/**
	 * @param originOE the originOE to set
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}
	/**
	 * @return the destinationOE
	 */
	public String getDestinationOE() {
		return destinationOE;
	}
	/**
	 * @param destinationOE the destinationOE to set
	 */
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}
	/**
	 * @return the subClassGroup
	 */
	public String getSubClassGroup() {
		return subClassGroup;
	}
	/**
	 * @param subClassGroup the subClassGroup to set
	 */
	public void setSubClassGroup(String subClassGroup) {
		this.subClassGroup = subClassGroup;
	}
	/**
	 * @return the subClassCode
	 */
	public String getSubClassCode() {
		return subClassCode;
	}
	/**
	 * @param subClassCode the subClassCode to set
	 */
	public void setSubClassCode(String subClassCode) {
		this.subClassCode = subClassCode;
	}
	/**
	 * 	Getter for companyCode 
	 *	Added by : A-4803 on 07-May-2015
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-4803 on 07-May-2015
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	
}
