/*
 * FlightMessageFilterVO.java Created on jul 08, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message.vo;



import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1347
 *
 */
public class FlightMessageFilterVO extends AbstractVO{


private String companyCode;
private int flightCarrierIdentifier;
private String flightNumber;
private long flightSequenceNumber;
private int segmentSerialNumber;
private String airportCode;
//E-Export  for UCM-OUT,
//I-Import for UCN-IN
private String direction;//This is not for Quering .This is just for putting the message
//Direction as either IN or OUT
/**
 * @return String Returns the companyCode.
 */
public String getCompanyCode() {
	return this.companyCode;
}
/**
 * @param companyCode The companyCode to set.
 */
public void setCompanyCode(String companyCode) {
	this.companyCode = companyCode;
}
/**
 * @return String Returns the direction.
 */
public String getDirection() {
	return this.direction;
}
/**
 * @param direction The direction to set.
 */
public void setDirection(String direction) {
	this.direction = direction;
}
/**
 * @return int Returns the flightCarrierIdentifier.
 */
public int getFlightCarrierIdentifier() {
	return this.flightCarrierIdentifier;
}
/**
 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
 */
public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
	this.flightCarrierIdentifier = flightCarrierIdentifier;
}
/**
 * @return String Returns the flightNumber.
 */
public String getFlightNumber() {
	return this.flightNumber;
}
/**
 * @param flightNumber The flightNumber to set.
 */
public void setFlightNumber(String flightNumber) {
	this.flightNumber = flightNumber;
}
/**
 * @return long Returns the flightSequenceNumber.
 */
public long getFlightSequenceNumber() {
	return this.flightSequenceNumber;
}
/**
 * @param flightSequenceNumber The flightSequenceNumber to set.
 */
public void setFlightSequenceNumber(long flightSequenceNumber) {
	this.flightSequenceNumber = flightSequenceNumber;
}
/**
 * @return int Returns the segmentSerialNumber.
 */
public int getSegmentSerialNumber() {
	return this.segmentSerialNumber;
}
/**
 * @param segmentSerialNumber The segmentSerialNumber to set.
 */
public void setSegmentSerialNumber(int segmentSerialNumber) {
	this.segmentSerialNumber = segmentSerialNumber;
}
/**
 * @return String Returns the airportCode.
 */
public String getAirportCode() {
	return this.airportCode;
}
/**
 * @param airportCode The airportCode to set.
 */
public void setAirportCode(String airportCode) {
	this.airportCode = airportCode;
}



}
