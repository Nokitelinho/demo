package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common;

import com.ibsplc.icargo.framework.util.time.LocalDate;

public class CustomerDetails {
	
	private String customerCode;
	private String iataCode;
	private String status;
	private String accountNumber;
	private LocalDate validFrom;
	private LocalDate validTo;
	private String email;
	private String billingCurrency;
	private String contactcustomerName;
	private String contactcustomerTitle;
	private String contactcustomerPhone;
	private String contactcustomerEmail;
	private String contactcustomerFax;

	
	public String getBillingCurrency() {
		return billingCurrency;
	}
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getIataCode() {
		return iataCode;
	}
	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public LocalDate getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}
	public LocalDate getValidTo() {
		return validTo;
	}
	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getAccountSpecialist() {
		return accountSpecialist;
	}
	public void setAccountSpecialist(String accountSpecialist) {
		this.accountSpecialist = accountSpecialist;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	private String customerName;
	private String phoneNumber;
	private String fax;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String accountSpecialist;
	private String instruction;
	/**
	 * @return the contactcustomerName
	 */
	public String getContactcustomerName() {
		return contactcustomerName;
	}
	/**
	 * @param contactcustomerName the contactcustomerName to set
	 */
	public void setContactcustomerName(String contactcustomerName) {
		this.contactcustomerName = contactcustomerName;
	}
	/**
	 * @return the contactcustomerTitle
	 */
	public String getContactcustomerTitle() {
		return contactcustomerTitle;
	}
	/**
	 * @param contactcustomerTitle the contactcustomerTitle to set
	 */
	public void setContactcustomerTitle(String contactcustomerTitle) {
		this.contactcustomerTitle = contactcustomerTitle;
	}
	/**
	 * @return the contactcustomerPhone
	 */
	public String getContactcustomerPhone() {
		return contactcustomerPhone;
	}
	/**
	 * @param contactcustomerPhone the contactcustomerPhone to set
	 */
	public void setContactcustomerPhone(String contactcustomerPhone) {
		this.contactcustomerPhone = contactcustomerPhone;
	}
	/**
	 * @return the contactcustomerEmail
	 */
	public String getContactcustomerEmail() {
		return contactcustomerEmail;
	}
	/**
	 * @param contactcustomerEmail the contactcustomerEmail to set
	 */
	public void setContactcustomerEmail(String contactcustomerEmail) {
		this.contactcustomerEmail = contactcustomerEmail;
	}
	/**
	 * @return the contactcustomerFax
	 */
	public String getContactcustomerFax() {
		return contactcustomerFax;
	}
	/**
	 * @param contactcustomerFax the contactcustomerFax to set
	 */
	public void setContactcustomerFax(String contactcustomerFax) {
		this.contactcustomerFax = contactcustomerFax;
	}
	
	
	

}
