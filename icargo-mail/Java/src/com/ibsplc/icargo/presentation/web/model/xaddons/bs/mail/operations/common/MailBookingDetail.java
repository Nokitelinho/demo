package com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common;

import java.util.Collection;

import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common.MailBookingDetail.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	08-Oct-2019		:	Draft
 */
public class MailBookingDetail {
	
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String ownerId;
    private String duplicateNumber;
    private String sequenceNumber;
	private String bookingCarrierCode;
	private String bookingFlightNumber;
	private String bookingFlightSequenceNumber;
	private String bookingFlightDate;
	private String agentCode;
	private String mailScc;
	private String shipmentDate;
	private String awbOrgin;
	private String awbDestination;
	private String bookedPieces;
	private String bookedWeight;
	private String bookedVolume;
	private String bookingStatus;
	private String shipmentStatus;
	private String bookingStation;
	private String bookingDate;
	private String remarks;
	private String statedPieces;
	private String companyCode;
	private String segementserialNumber;
	private String serialNumber;
	private String mailSequenceNumber;
	private String origin;
	private String destination;
	private String bookingFlightCarrierid;
    private String selectedFlightNumber;
    private String selectedFlightDate; 
	private boolean splitBooking;
	private boolean destinationCheckReq;
	private String attachedMailBagCount;
	private String[] flightDetails;
	private Collection<MailBookingDetailVO> bookedFlights;
	
	
	
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getDuplicateNumber() {
		return duplicateNumber;
	}
	public void setDuplicateNumber(String duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getBookingCarrierCode() {
		return bookingCarrierCode;
	}
	public void setBookingCarrierCode(String bookingCarrierCode) {
		this.bookingCarrierCode = bookingCarrierCode;
	}
	public String getBookingFlightNumber() {
		return bookingFlightNumber;
	}
	public void setBookingFlightNumber(String bookingFlightNumber) {
		this.bookingFlightNumber = bookingFlightNumber;
	}
	public String getBookingFlightSequenceNumber() {
		return bookingFlightSequenceNumber;
	}
	public void setBookingFlightSequenceNumber(String bookingFlightSequenceNumber) {
		this.bookingFlightSequenceNumber = bookingFlightSequenceNumber;
	}
	public String getBookingFlightDate() {
		return bookingFlightDate;
	}
	public void setBookingFlightDate(String bookingFlightDate) {
		this.bookingFlightDate = bookingFlightDate;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getMailScc() {
		return mailScc;
	}
	public void setMailScc(String mailScc) {
		this.mailScc = mailScc;
	}
	public String getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public String getAwbOrgin() {
		return awbOrgin;
	}
	public void setAwbOrgin(String awbOrgin) {
		this.awbOrgin = awbOrgin;
	}
	public String getAwbDestination() {
		return awbDestination;
	}
	public void setAwbDestination(String awbDestination) {
		this.awbDestination = awbDestination;
	}
	public String getBookedPieces() {
		return bookedPieces;
	}
	public void setBookedPieces(String bookedPieces) {
		this.bookedPieces = bookedPieces;
	}
	public String getBookedWeight() {
		return bookedWeight;
	}
	public void setBookedWeight(String bookedWeight) {
		this.bookedWeight = bookedWeight;
	}
	public String getBookedVolume() {
		return bookedVolume;
	}
	public void setBookedVolume(String bookedVolume) {
		this.bookedVolume = bookedVolume;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public String getShipmentStatus() {
		return shipmentStatus;
	}
	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	public String getBookingStation() {
		return bookingStation;
	}
	public void setBookingStation(String bookingStation) {
		this.bookingStation = bookingStation;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatedPieces() {
		return statedPieces;
	}
	public void setStatedPieces(String statedPieces) {
		this.statedPieces = statedPieces;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getSegementserialNumber() {
		return segementserialNumber;
	}
	public void setSegementserialNumber(String segementserialNumber) {
		this.segementserialNumber = segementserialNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(String mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
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
	public String getBookingFlightCarrierid() {
		return bookingFlightCarrierid;
	}
	public void setBookingFlightCarrierid(String bookingFlightCarrierid) {
		this.bookingFlightCarrierid = bookingFlightCarrierid;
	}
	public String getSelectedFlightNumber() {
		return selectedFlightNumber;
	}
	public void setSelectedFlightNumber(String selectedFlightNumber) {
		this.selectedFlightNumber = selectedFlightNumber;
	}
	public String getSelectedFlightDate() {
		return selectedFlightDate;
	}
	public void setSelectedFlightDate(String selectedFlightDate) {
		this.selectedFlightDate = selectedFlightDate;
	}
	public boolean isSplitBooking() {
		return splitBooking;
	}
	public void setSplitBooking(boolean splitBooking) {
		this.splitBooking = splitBooking;
	}
	public boolean isDestinationCheckReq() {
		return destinationCheckReq;
	}
	public void setDestinationCheckReq(boolean destinationCheckReq) {
		this.destinationCheckReq = destinationCheckReq;
	}
	public String getAttachedMailBagCount() {
		return attachedMailBagCount;
	}
	public void setAttachedMailBagCount(String attachedMailBagCount) {
		this.attachedMailBagCount = attachedMailBagCount;
	}
	public String[] getFlightDetails() {
		return flightDetails;
	}
	public void setFlightDetails(String[] flightDetails) {
		this.flightDetails = flightDetails;
	}
	public Collection<MailBookingDetailVO> getBookedFlights() {
		return bookedFlights;
	}
	public void setBookedFlights(Collection<MailBookingDetailVO> bookedFlights) {
		this.bookedFlights = bookedFlights;
	}
	
	
	

}
