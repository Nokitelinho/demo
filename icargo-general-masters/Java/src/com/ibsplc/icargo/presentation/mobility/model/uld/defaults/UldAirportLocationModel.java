package com.ibsplc.icargo.presentation.mobility.model.uld.defaults;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UldAirportLocationModel implements Serializable {
	private String facilitytype;
	private String locationCode;
	private String locationDescription;

	public String getFacilitytype() {
		return facilitytype;
	}

	public void setFacilitytype(String facilitytype) {
		this.facilitytype = facilitytype;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}
}
