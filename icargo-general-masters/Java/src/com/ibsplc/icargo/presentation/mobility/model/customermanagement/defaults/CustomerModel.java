package com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomerModel implements Serializable {

	private String customerCode;
	private String customerShortCode;
	private String customerName;
	private AddressDetailModel addressDetails;
	private String customerStatus;
	private String customerType;
	private String internalAccountHolder;
	private String validFromDate;
	private String validToDate;

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public void setAddressDetails(AddressDetailModel addressDetails) {
		this.addressDetails = addressDetails;
	}

	public void setInternalAccountHolder(String internalAccountHolder) {
		this.internalAccountHolder = internalAccountHolder;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setValidFromDate(String validFromDate) {
		this.validFromDate = validFromDate;
	}

	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	}

	public void setCustomerStatus(String status) {
		this.customerStatus = status;
	}

	public void setCustomerShortCode(String customerShortCode) {
		this.customerShortCode = customerShortCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerShortCode() {
		return customerShortCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public AddressDetailModel getAddressDetails() {
		return addressDetails;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public String getCustomerType() {
		return customerType;
	}

	public String getInternalAccountHolder() {
		return internalAccountHolder;
	}

	public String getValidFromDate() {
		return validFromDate;
	}

	public String getValidToDate() {
		return validToDate;
	}

}
