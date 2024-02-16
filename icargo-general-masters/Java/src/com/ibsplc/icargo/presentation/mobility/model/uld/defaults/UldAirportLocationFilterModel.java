package com.ibsplc.icargo.presentation.mobility.model.uld.defaults;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UldAirportLocationFilterModel implements Serializable {
	private String companyCode;
	private String airportCode;
	private String facilitytype;
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
	public String getFacilitytype() {
		return facilitytype;
	}
	public void setFacilitytype(String facilitytype) {
		this.facilitytype = facilitytype;
	}
}
