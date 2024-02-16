package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping;

import java.util.Collection;

public class CustomerDetails {
	
	private String customerName;
	
	private Collection<AdditionalName> additionalNames;
	
	private String additionalDetails;
	
	private String street;
	
	private String city;
	
	private String country;
	
	private String zipCode;
	
	private String station;
	
	private String customerType;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Collection<AdditionalName> getAdditionalNames() {
		return additionalNames;
	}

	public void setAdditionalNames(Collection<AdditionalName> additionalNames) {
		this.additionalNames = additionalNames;
	}

	public String getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	
	
	
}
