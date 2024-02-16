package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

public class MailAcceptancePK {
	private String companyCode;
	private String airportCode;
	private int flightCarrierId;
	private String flightNumber;
	private long flightSeqNum;
	private int legSerNum;
	private String destination;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
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
	public long getFlightSeqNum() {
		return flightSeqNum;
	}
	public void setFlightSeqNum(long flightSeqNum) {
		this.flightSeqNum = flightSeqNum;
	}
	public int getLegSerNum() {
		return legSerNum;
	}
	public void setLegSerNum(int legSerNum) {
		this.legSerNum = legSerNum;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	

}
