package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import com.ibsplc.icargo.framework.util.time.LocalDate;

public class ExistingMailbag {
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
	  private String ubrNumber;
	  private LocalDate bookingLastUpdateTime;
	  private LocalDate bookingFlightDetailLastUpdTime;
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getCurrentAirport() {
		return currentAirport;
	}
	public void setCurrentAirport(String currentAirport) {
		this.currentAirport = currentAirport;
	}
	public String getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	public String getReassign() {
		return reassign;
	}
	public void setReassign(String reassign) {
		this.reassign = reassign;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
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
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	public String getPou() {
		return pou;
	}
	public void setPou(String pou) {
		this.pou = pou;
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
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	public String getUbrNumber() {
		return ubrNumber;
	}
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}
	public LocalDate getBookingLastUpdateTime() {
		return bookingLastUpdateTime;
	}
	public void setBookingLastUpdateTime(LocalDate bookingLastUpdateTime) {
		this.bookingLastUpdateTime = bookingLastUpdateTime;
	}
	public LocalDate getBookingFlightDetailLastUpdTime() {
		return bookingFlightDetailLastUpdTime;
	}
	public void setBookingFlightDetailLastUpdTime(LocalDate bookingFlightDetailLastUpdTime) {
		this.bookingFlightDetailLastUpdTime = bookingFlightDetailLastUpdTime;
	}
	  
	  

}
