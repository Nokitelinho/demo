/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightValidation.java Created on	:	08-Jun-2018
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightValidation.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-2257 :
 * 08-Jun-2018 : Draft
 */
public class FlightValidation {
	
	 private String companyCode;
	  private String carrierCode;
	  private int flightCarrierId;
	  private String flightNumber;
	  private long flightSequenceNumber;
	  private int legSerialNumber;
	  private String flightRoute;
	  private String departureTime;
	  private String aircraftType;
	  private String flightType;
	  private String std;
	  private String atd;
	  private String tailNumber;
	  private String applicableDateAtRequestedAirport;
	  private String timeToDeparture;
	  private String legStatus;
	  private boolean timeToDepartureDifferenceFlag;
	  private boolean isTBADueRouteChange;
	  private String flightDate;
	  private String flightStatus;
	  private String operationalStatus;
	  
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public int getFlightCarrierId() {
		return flightCarrierId;
	}
	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	public String getFlightRoute() {
		return flightRoute;
	}
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getAircraftType() {
		return aircraftType;
	}
	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public String getStd() {
		return std;
	}
	public void setStd(String std) {
		this.std = std;
	}
	public String getAtd() {
		return atd;
	}
	public void setAtd(String atd) {
		this.atd = atd;
	}
	public String getTailNumber() {
		return tailNumber;
	}
	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}
	public String getApplicableDateAtRequestedAirport() {
		return applicableDateAtRequestedAirport;
	}
	public void setApplicableDateAtRequestedAirport(String applicableDateAtRequestedAirport) {
		this.applicableDateAtRequestedAirport = applicableDateAtRequestedAirport;
	}
	public String getTimeToDeparture() {
		return timeToDeparture;
	}
	public void setTimeToDeparture(String timeToDeparture) {
		this.timeToDeparture = timeToDeparture;
	}
	public String getLegStatus() {
		return legStatus;
	}
	public void setLegStatus(String legStatus) {
		this.legStatus = legStatus;
	}
	public boolean isTimeToDepartureDifferenceFlag() {
		return timeToDepartureDifferenceFlag;
	}
	public void setTimeToDepartureDifferenceFlag(boolean timeToDepartureDifferenceFlag) {
		this.timeToDepartureDifferenceFlag = timeToDepartureDifferenceFlag;
	}
	public boolean isTBADueRouteChange() {
		return isTBADueRouteChange;
	}
	public void setTBADueRouteChange(boolean isTBADueRouteChange) {
		this.isTBADueRouteChange = isTBADueRouteChange;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}
	public String getOperationalStatus() {
		return operationalStatus;
	}
	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}
	
	
}