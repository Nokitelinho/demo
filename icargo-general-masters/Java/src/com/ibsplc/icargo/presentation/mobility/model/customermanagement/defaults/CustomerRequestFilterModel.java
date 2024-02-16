package com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomerRequestFilterModel implements Serializable {

	private String customerCode;
	private String customerShortCode;
	private String updatedAfter;
	private String customerType;
	private String internalAccountHolder;
	private String holdingCompany;
	private String holdingCompanyGroup;

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public String getCustomerShortCode() {
		return customerShortCode;
	}

	public String getUpdatedAfter() {
		return updatedAfter;
	}

	public String getInternalAccountHolder() {
		return internalAccountHolder;
	}

	public String getHoldingCompany() {
		return holdingCompany;
	}

	public String getHoldingCompanyGroup() {
		return holdingCompanyGroup;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public void setCustomerShortCode(String customerShortCode) {
		this.customerShortCode = customerShortCode;
	}

	public void setUpdatedAfter(String updatedAfter) {
		this.updatedAfter = updatedAfter;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public void setInternalAccountHolder(String internalAccountHolder) {
		this.internalAccountHolder = internalAccountHolder;
	}

	public void setHoldingCompany(String holdingCompany) {
		this.holdingCompany = holdingCompany;
	}

	public void setHoldingCompanyGroup(String holdingCompanyGroup) {
		this.holdingCompanyGroup = holdingCompanyGroup;
	}

}
