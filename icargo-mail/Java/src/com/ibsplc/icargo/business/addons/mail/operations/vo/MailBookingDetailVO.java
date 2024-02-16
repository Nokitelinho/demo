package com.ibsplc.icargo.business.addons.mail.operations.vo;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailBookingDetailVO.java
 *
 *	Created by	:	A-7531
 *	Created on	:	09-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */


import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailBookingDetailVO extends AbstractVO {

	private String shipmentPrefix;
	private String masterDocumentNumber;
	private int ownerId;
    private int duplicateNumber;
    private int sequenceNumber;
	private String bookingCarrierCode;
	private String bookingFlightNumber;
	private int bookingFlightSequenceNumber;
	private LocalDate bookingFlightDate;
	private String agentCode;
	private String mailScc;
	private LocalDate shipmentDate;
	private String awbOrgin;
	private String awbDestination;
	private int bookedPieces;
	private double bookedWeight;
	private double bookedVolume;
	private String bookingStatus;
	private String shipmentStatus;
	private String bookingStation;
	private LocalDate bookingDate;
	private String remarks;
	private int statedPieces;
	private String companyCode;
	private int segementserialNumber;
	private int serialNumber;
	private long mailSequenceNumber;
private String origin;
private String destination;
private int bookingFlightCarrierid;
    private String selectedFlightNumber;
    private LocalDate selectedFlightDate; 
	private boolean splitBooking;
	private boolean destinationCheckReq;
	private String awbNumber;
	private String standardPieces;
	private String standardWeight;
	private String volume;
	private String pol;
	private String pou;
	private String scc;
	private String plannedPieces;
	private String plannedWeight;
	private String plannedFlight;
	private String plannedSegment;
	private LocalDate flightDate;
	private String shipmentDescription;
	private String flightTime;
	private String carrierCode;
	private String attachmentSource;

	public boolean isDestinationCheckReq() {
		return destinationCheckReq;
	}

	public void setDestinationCheckReq(boolean destinationCheckReq) {
		this.destinationCheckReq = destinationCheckReq;
	}

    //A-8061 added for ICRD-232728
    private int attachedMailBagCount;
    
    private Collection<MailBookingDetailVO> bookedFlights;
    
	public String getOrigin() {
	return origin;
}

public void setOrigin(String origin) {
	this.origin = origin;
}

public String getDestination() {
	return destination;
}

public void setDestination(String destination) {
	this.destination = destination;
}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getDuplicateNumber() {
		return duplicateNumber;
	}

	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getSegementserialNumber() {
		return segementserialNumber;
	}

	public void setSegementserialNumber(int segementserialNumber) {
		this.segementserialNumber = segementserialNumber;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Getter for shipmentPrefix Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	/**
	 * @param shipmentPrefix
	 *            the shipmentPrefix to set Setter for shipmentPrefix Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}

	/**
	 * Getter for masterDocumentNumber Added by : A-7531 on 09-Aug-2017 Used for
	 * :
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	/**
	 * @param masterDocumentNumber
	 *            the masterDocumentNumber to set Setter for
	 *            masterDocumentNumber Added by : A-7531 on 09-Aug-2017 Used for
	 *            :
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}

	/**
	 * Getter for bookingCarrierCode Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getBookingCarrierCode() {
		return bookingCarrierCode;
	}

	/**
	 * @param bookingCarrierCode
	 *            the bookingCarrierCode to set Setter for bookingCarrierCode
	 *            Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public void setBookingCarrierCode(String bookingCarrierCode) {
		this.bookingCarrierCode = bookingCarrierCode;
	}

	/**
	 * Getter for bookingFlightNumber Added by : A-7531 on 09-Aug-2017 Used for
	 * :
	 */
	public String getBookingFlightNumber() {
		return bookingFlightNumber;
	}

	/**
	 * @param bookingFlightNumber
	 *            the bookingFlightNumber to set Setter for bookingFlightNumber
	 *            Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public void setBookingFlightNumber(String bookingFlightNumber) {
		this.bookingFlightNumber = bookingFlightNumber;
	}

	/**
	 * Getter for bookingFlightDate Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public LocalDate getBookingFlightDate() {
		return bookingFlightDate;
	}

	/**
	 * @param bookingFlightDate
	 *            the bookingFlightDate to set Setter for bookingFlightDate
	 *            Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public void setBookingFlightDate(LocalDate bookingFlightDate) {
		this.bookingFlightDate = bookingFlightDate;
	}

	/**
	 * Getter for agentCode Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode
	 *            the agentCode to set Setter for agentCode Added by : A-7531 on
	 *            09-Aug-2017 Used for :
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * Getter for mailScc Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getMailScc() {
		return mailScc;
	}

	/**
	 * @param mailScc
	 *            the mailScc to set Setter for mailScc Added by : A-7531 on
	 *            09-Aug-2017 Used for :
	 */
	public void setMailScc(String mailScc) {
		this.mailScc = mailScc;
	}

	/**
	 * Getter for shipmentDate Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public LocalDate getShipmentDate() {
		return shipmentDate;
	}

	/**
	 * @param shipmentDate
	 *            the shipmentDate to set Setter for shipmentDate Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setShipmentDate(LocalDate shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	/**
	 * Getter for awbOrgin Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getAwbOrgin() {
		return awbOrgin;
	}

	/**
	 * @param awbOrgin
	 *            the awbOrgin to set Setter for awbOrgin Added by : A-7531 on
	 *            09-Aug-2017 Used for :
	 */
	public void setAwbOrgin(String awbOrgin) {
		this.awbOrgin = awbOrgin;
	}

	/**
	 * Getter for awbDestination Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getAwbDestination() {
		return awbDestination;
	}

	/**
	 * @param awbDestination
	 *            the awbDestination to set Setter for awbDestination Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setAwbDestination(String awbDestination) {
		this.awbDestination = awbDestination;
	}

	/**
	 * Getter for bookedPieces Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public int getBookedPieces() {
		return bookedPieces;
	}

	/**
	 * @param bookedPieces
	 *            the bookedPieces to set Setter for bookedPieces Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setBookedPieces(int bookedPieces) {
		this.bookedPieces = bookedPieces;
	}

	/**
	 * Getter for bookedWeight Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public double getBookedWeight() {
		return bookedWeight;
	}

	/**
	 * @param bookedWeight
	 *            the bookedWeight to set Setter for bookedWeight Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setBookedWeight(double bookedWeight) {
		this.bookedWeight = bookedWeight;
	}

	/**
	 * Getter for bookedVolume Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public double getBookedVolume() {
		return bookedVolume;
	}

	/**
	 * @param bookedVolume
	 *            the bookedVolume to set Setter for bookedVolume Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setBookedVolume(double bookedVolume) {
		this.bookedVolume = bookedVolume;
	}

	/**
	 * Getter for bookingStatus Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getBookingStatus() {
		return bookingStatus;
	}

	/**
	 * @param bookingStatus
	 *            the bookingStatus to set Setter for bookingStatus Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	/**
	 * Getter for shipmentStatus Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getShipmentStatus() {
		return shipmentStatus;
	}

	/**
	 * @param shipmentStatus
	 *            the shipmentStatus to set Setter for shipmentStatus Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	/**
	 * Getter for bookingStation Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getBookingStation() {
		return bookingStation;
	}

	/**
	 * @param bookingStation
	 *            the bookingStation to set Setter for bookingStation Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setBookingStation(String bookingStation) {
		this.bookingStation = bookingStation;
	}

	/**
	 * Getter for bookingDate Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public LocalDate getBookingDate() {
		return bookingDate;
	}

	/**
	 * @param bookingDate
	 *            the bookingDate to set Setter for bookingDate Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Getter for remarks Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set Setter for remarks Added by : A-7531 on
	 *            09-Aug-2017 Used for :
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Getter for statedPieces Added by : A-7531 on 09-Aug-2017 Used for :
	 */
	public int getStatedPieces() {
		return statedPieces;
	}

	/**
	 * @param statedPieces
	 *            the statedPieces to set Setter for statedPieces Added by :
	 *            A-7531 on 09-Aug-2017 Used for :
	 */
	public void setStatedPieces(int statedPieces) {
		this.statedPieces = statedPieces;
	}

	public int getBookingFlightCarrierid() {
		return bookingFlightCarrierid;
	}

	public void setBookingFlightCarrierid(int bookingFlightCarrierid) {
		this.bookingFlightCarrierid = bookingFlightCarrierid;
	}

	public LocalDate getSelectedFlightDate() {
		return selectedFlightDate;
	}

	public void setSelectedFlightDate(LocalDate selectedFlightDate) {
		this.selectedFlightDate = selectedFlightDate;
	}

	public String getSelectedFlightNumber() {
		return selectedFlightNumber;
	}

	public void setSelectedFlightNumber(String selectedFlightNumber) {
		this.selectedFlightNumber = selectedFlightNumber;
	}

	public int getBookingFlightSequenceNumber() {
		return bookingFlightSequenceNumber;
	}

	public void setBookingFlightSequenceNumber(int bookingFlightSequenceNumber) {
		this.bookingFlightSequenceNumber = bookingFlightSequenceNumber;
	}

	public int getAttachedMailBagCount() {
		return attachedMailBagCount;
	}

	public void setAttachedMailBagCount(int attachedMailBagCount) {
		this.attachedMailBagCount = attachedMailBagCount;
	}

	public Collection<MailBookingDetailVO> getBookedFlights() {
		return bookedFlights;
	}

	public void setBookedFlights(Collection<MailBookingDetailVO> bookedFlights) {
		this.bookedFlights = bookedFlights;
	}

	public boolean isSplitBooking() {
		return splitBooking;
	}

	public void setSplitBooking(boolean splitBooking) {
		this.splitBooking = splitBooking;
	}

	public String getAwbNumber() {
		return awbNumber;
	}

	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}

	public String getStandardPieces() {
		return standardPieces;
	}

	public void setStandardPieces(String standardPieces) {
		this.standardPieces = standardPieces;
	}

	public String getStandardWeight() {
		return standardWeight;
	}

	public void setStandardWeight(String standardWeight) {
		this.standardWeight = standardWeight;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public String getScc() {
		return scc;
	}

	public void setScc(String scc) {
		this.scc = scc;
	}

	public String getPlannedPieces() {
		return plannedPieces;
	}

	public void setPlannedPieces(String plannedPieces) {
		this.plannedPieces = plannedPieces;
	}

	public String getPlannedWeight() {
		return plannedWeight;
	}

	public void setPlannedWeight(String plannedWeight) {
		this.plannedWeight = plannedWeight;
	}

	public String getPlannedFlight() {
		return plannedFlight;
	}

	public void setPlannedFlight(String plannedFlight) {
		this.plannedFlight = plannedFlight;
	}

	public String getPlannedSegment() {
		return plannedSegment;
	}

	public void setPlannedSegment(String plannedSegment) {
		this.plannedSegment = plannedSegment;
	}

	public LocalDate getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	public String getShipmentDescription() {
		return shipmentDescription;
	}

	public void setShipmentDescription(String shipmentDescription) {
		this.shipmentDescription = shipmentDescription;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getAttachmentSource() {
		return attachmentSource;
	}

	public void setAttachmentSource(String attachmentSource) {
		this.attachmentSource = attachmentSource;
	}
}

