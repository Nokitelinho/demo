/*
 * ExistingMailbagVO.java Created on JUN 30, 2016
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
public class ExistingMailbagVO extends AbstractVO{

private static final long serialVersionUID = 1L;
private String mailId;
private String carrierCode;
private String flightNumber;
private String currentAirport;
private String flightStatus;
private LocalDate flightDate;
private String reassign;
private String containerNumber;
private String pol;
private long flightSequenceNumber;
private int legSerialNumber;
private int segmentSerialNumber;
private String pou;
private String containerType;
private String finalDestination;
private int carrierId;
private long malseqnum;

/*
 * Added by RENO For CR : AirNZ 404 : Mail Allocation
 */
private String ubrNumber;
private LocalDate bookingLastUpdateTime;
private LocalDate bookingFlightDetailLastUpdTime;

public int getCarrierId() {
	return carrierId;
}
public void setCarrierId(int carrierId) {
	this.carrierId = carrierId;
}
public String getContainerType() {
	return containerType;
}
public void setContainerType(String containerType) {
	this.containerType = containerType;
}
public String getFinalDestination() {
	return finalDestination;
}
public void setFinalDestination(String finalDestination) {
	this.finalDestination = finalDestination;
}
public String getPou() {
	return pou;
}
public void setPou(String pou) {
	this.pou = pou;
}
public int getSegmentSerialNumber() {
	return segmentSerialNumber;
}
public void setSegmentSerialNumber(int segmentSerialNumber) {
	this.segmentSerialNumber = segmentSerialNumber;
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
public String getPol() {
	return pol;
}
public void setPol(String pol) {
	this.pol = pol;
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
 * @return the currentAirport
 */
public String getCurrentAirport() {
	return currentAirport;
}
/**
 * @param currentAirport the currentAirport to set
 */
public void setCurrentAirport(String currentAirport) {
	this.currentAirport = currentAirport;
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
 * @return the flightStatus
 */
public String getFlightStatus() {
	return flightStatus;
}
/**
 * @param flightStatus the flightStatus to set
 */
public void setFlightStatus(String flightStatus) {
	this.flightStatus = flightStatus;
}
/**
 * @return the mailId
 */
public String getMailId() {
	return mailId;
}
/**
 * @param mailId the mailId to set
 */
public void setMailId(String mailId) {
	this.mailId = mailId;
}
/**
 * @return the reassign
 */
public String getReassign() {
	return reassign;
}
/**
 * @param reassign the reassign to set
 */
public void setReassign(String reassign) {
	this.reassign = reassign;
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
public String getContainerNumber() {
	return containerNumber;
}
public void setContainerNumber(String containerNumber) {
	this.containerNumber = containerNumber;
}
public LocalDate getBookingFlightDetailLastUpdTime() {
	return bookingFlightDetailLastUpdTime;
}
public void setBookingFlightDetailLastUpdTime(
		LocalDate bookingFlightDetailLastUpdTime) {
	this.bookingFlightDetailLastUpdTime = bookingFlightDetailLastUpdTime;
}
public LocalDate getBookingLastUpdateTime() {
	return bookingLastUpdateTime;
}
public void setBookingLastUpdateTime(LocalDate bookingLastUpdateTime) {
	this.bookingLastUpdateTime = bookingLastUpdateTime;
}
public String getUbrNumber() {
	return ubrNumber;
}
public void setUbrNumber(String ubrNumber) {
	this.ubrNumber = ubrNumber;
}
public long getMalseqnum() {
	return malseqnum;
}
public void setMalseqnum(long malseqnum) {
	this.malseqnum = malseqnum;
}

}
