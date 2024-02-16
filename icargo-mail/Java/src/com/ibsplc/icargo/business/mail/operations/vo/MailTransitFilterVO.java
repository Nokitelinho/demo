package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailTransitFilterVO extends AbstractVO{
	
	private String airportCode;
	private String fromDate;
	private String toDate;
	private LocalDate flightFromDate;
	private LocalDate flightToDate;
	private String flightNumber;
	private int pageNumber;
	private int pageSize;
	private int totalRecordsCount;
	private String flighFromTime; 
	private String flightToTime; 
	private LocalDate flightDate;
    private String segmentDestination;
    private String carrierCode;
	
	public String getFlighFromTime() {
		return flighFromTime;
	}
	public void setFlighFromTime(String flighFromTime) {
		this.flighFromTime = flighFromTime;
	}
	public String getFlightToTime() {
		return flightToTime;
	}
	public void setFlightToTime(String flightToTime) {
		this.flightToTime = flightToTime;
	}
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
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
	
	
	
	
	public LocalDate getFlightFromDate() {
		return flightFromDate;
	}
	public void setFlightFromDate(LocalDate flightFromDate) {
		this.flightFromDate = flightFromDate;
	}
	public LocalDate getFlightToDate() {
		return flightToDate;
	}
	public void setFlightToDate(LocalDate flightToDate) {
		this.flightToDate = flightToDate;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	public String getSegmentDestination() {
		return segmentDestination;
	}
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	

}
