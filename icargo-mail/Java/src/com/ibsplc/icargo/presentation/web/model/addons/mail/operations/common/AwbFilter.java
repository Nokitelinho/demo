package com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common;

import java.io.Serializable;

public class AwbFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agentCode;
	private String bookingCarrierCode;
	private String bookingFlightFrom;
	private String bookingFlightNumber;
	private String bookingFlightTo;
	private String bookingFrom;
	private String bookingTo;
	private String bookingStatus;
	private String bookingUserId;
	private String customerCode;
	private String destinationOfBooking;
	private String mailScc;
	private String masterDocumentNumber;
	private String orginOfBooking;
	private String mailProduct;
	private String shipmentDate;
	private String shipmentPrefix;
	private String stationOfBooking;
	private String viaPointOfBooking;
	private String screenName;
	private String plannedFlightDateFrom;
	private String plannedFlightDateTo;
	private String flightNumber;
	private String carrierCode;
	private String flightDate;
	private String origin;
	private String destination;
	private String pol;
	private String pou;
	private boolean onListButtonClick;
	private String manifestDateFrom;
	private String manifestDateTo;
	
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getBookingCarrierCode() {
		return bookingCarrierCode;
	}
	public void setBookingCarrierCode(String bookingCarrierCode) {
		this.bookingCarrierCode = bookingCarrierCode;
	}
	public String getBookingFlightFrom() {
		return bookingFlightFrom;
	}
	public void setBookingFlightFrom(String bookingFlightFrom) {
		this.bookingFlightFrom = bookingFlightFrom;
	}
	public String getBookingFlightNumber() {
		return bookingFlightNumber;
	}
	public void setBookingFlightNumber(String bookingFlightNumber) {
		this.bookingFlightNumber = bookingFlightNumber;
	}
	public String getBookingFlightTo() {
		return bookingFlightTo;
	}
	public void setBookingFlightTo(String bookingFlightTo) {
		this.bookingFlightTo = bookingFlightTo;
	}
	public String getBookingFrom() {
		return bookingFrom;
	}
	public void setBookingFrom(String bookingFrom) {
		this.bookingFrom = bookingFrom;
	}
	public String getBookingTo() {
		return bookingTo;
	}
	public void setBookingTo(String bookingTo) {
		this.bookingTo = bookingTo;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public String getBookingUserId() {
		return bookingUserId;
	}
	public void setBookingUserId(String bookingUserId) {
		this.bookingUserId = bookingUserId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getDestinationOfBooking() {
		return destinationOfBooking;
	}
	public void setDestinationOfBooking(String destinationOfBooking) {
		this.destinationOfBooking = destinationOfBooking;
	}
	public String getMailScc() {
		return mailScc;
	}
	public void setMailScc(String mailScc) {
		this.mailScc = mailScc;
	}
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public String getOrginOfBooking() {
		return orginOfBooking;
	}
	public void setOrginOfBooking(String orginOfBooking) {
		this.orginOfBooking = orginOfBooking;
	}
	public String getMailProduct() {
		return mailProduct;
	}
	public void setMailProduct(String mailProduct) {
		this.mailProduct = mailProduct;
	}
	public String getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	public String getStationOfBooking() {
		return stationOfBooking;
	}
	public void setStationOfBooking(String stationOfBooking) {
		this.stationOfBooking = stationOfBooking;
	}
	public String getViaPointOfBooking() {
		return viaPointOfBooking;
	}
	public void setViaPointOfBooking(String viaPointOfBooking) {
		this.viaPointOfBooking = viaPointOfBooking;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getPlannedFlightDateFrom() {
		return plannedFlightDateFrom;
	}
	public void setPlannedFlightDateFrom(String plannedFlightDateFrom) {
		this.plannedFlightDateFrom = plannedFlightDateFrom;
	}
	public String getPlannedFlightDateTo() {
		return plannedFlightDateTo;
	}
	public void setPlannedFlightDateTo(String plannedFlightDateTo) {
		this.plannedFlightDateTo = plannedFlightDateTo;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
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
	public boolean isOnListButtonClick() {
		return onListButtonClick;
	}
	public void setOnListButtonClick(boolean onListButtonClick) {
		this.onListButtonClick = onListButtonClick;
	}
	public String getManifestDateFrom() {
		return manifestDateFrom;
	}
	public void setManifestDateFrom(String manifestDateFrom) {
		this.manifestDateFrom = manifestDateFrom;
	}
	public String getManifestDateTo() {
		return manifestDateTo;
	}
	public void setManifestDateTo(String manifestDateTo) {
		this.manifestDateTo = manifestDateTo;
	}
	
	
}
