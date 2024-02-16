package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.Collection;

public class FlightCarrierFilter {

	 private String flightNumber;
	 private String flightDate;
	 private String flightOperationalStatus;
	 private String fromDate;
	 private String toDate;
	 private String paCode;
	 private String airportCode;
	 private String carrierCode;
	 private String destination;
	 private CarditFilter carditFilter;
	 private int flightDisplayPage;
	 private int carrierDisplayPage;
	 private String assignTo;
	 private String fromTime;
	 private String toTime;
	 private int recordsPerPage;
	 private Collection<String> flightStatus;
	 private String operatingReference;
	 private boolean mailFlightOnly;
	 
	 private String pou;
	 
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFlightOperationalStatus() {
		return flightOperationalStatus;
	}
	public void setFlightOperationalStatus(String flightOperationalStatus) {
		this.flightOperationalStatus = flightOperationalStatus;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public CarditFilter getCarditFilter() {
		return carditFilter;
	}
	public void setCarditFilter(CarditFilter carditFilter) {
		this.carditFilter = carditFilter;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public int getFlightDisplayPage() {
		return flightDisplayPage;
	}
	public void setFlightDisplayPage(int flightDisplayPage) {
		this.flightDisplayPage = flightDisplayPage;
	}
	public int getCarrierDisplayPage() {
		return carrierDisplayPage;
	}
	public void setCarrierDisplayPage(int carrierDisplayPage) {
		this.carrierDisplayPage = carrierDisplayPage;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}
	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}
	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public Collection<String> getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(Collection<String> flightStatus) {
		this.flightStatus = flightStatus;
	}
	public String getOperatingReference() {
		return operatingReference;
	}
	public void setOperatingReference(String operatingReference) {
		this.operatingReference = operatingReference;
	}
	public String getPou() {
		return pou;
	}
	public void setPou(String pou) {
		this.pou = pou;
	}
	public boolean isMailFlightOnly() {
		return mailFlightOnly;
	}
	public void setMailFlightOnly(boolean mailFlightOnly) {
		this.mailFlightOnly = mailFlightOnly;
	}
	
	 
}
