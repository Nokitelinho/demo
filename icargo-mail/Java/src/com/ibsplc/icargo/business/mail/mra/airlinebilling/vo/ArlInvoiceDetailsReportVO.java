/*
 * ArlInvoiceDetailsReportVO.java Created on Mar 17,2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2391
 * 
 */
public class ArlInvoiceDetailsReportVO extends AbstractVO {

	private String gpaCode;

	private String sector;

	private LocalDate fromBillingPeriod;

	private LocalDate toBillingPeriod;

	private String invoiceNumber;

	private LocalDate billedDate;

	private String billingCurrencyCode;

	private String contractCurrencyCode;

	private double totalAmountinBillingCurrency;

	private double totalAmountinContractCurrency;

	private String fromDateString;

	private String toDateString;

	private String billedDateString;

	private String arlName;

	private String address;

	private String countryCode;

	private String conPerson;

	private String state;

	private String country;

	private String mobile;

	private String postCod;

	private String phone1;

	private String phone2;

	private String fax;

	private String email;

	private String city;

	private String mailCategoryCode;
	
	private double totalAmountinsettlementCurrency;
	
	private String clrPrd;
	/**
	 * @return the clrPrd
	 */
	public String getClrPrd() {
		return clrPrd;
	}

	/**
	 * @param clrPrd
	 *            the clrPrd to set
	 */
	public void setClrPrd(String clrPrd) {
		this.clrPrd = clrPrd;
	}

	/**
	 * @return the mailCategoryCode
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the conPerson
	 */
	public String getConPerson() {
		return conPerson;
	}

	/**
	 * @param conPerson
	 *            the conPerson to set
	 */
	public void setConPerson(String conPerson) {
		this.conPerson = conPerson;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the phone1
	 */
	public String getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1
	 *            the phone1 to set
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2
	 *            the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return the postCod
	 */
	public String getPostCod() {
		return postCod;
	}

	/**
	 * @param postCod
	 *            the postCod to set
	 */
	public void setPostCod(String postCod) {
		this.postCod = postCod;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the paName
	 */
	public String getArlName() {
		return arlName;
	}

	/**
	 * @param paName
	 *            the paName to set
	 */
	public void setArlName(String arlName) {
		this.arlName = arlName;
	}

	/**
	 * @return Returns the billedDateString.
	 */
	public String getBilledDateString() {
		return billedDateString;
	}

	/**
	 * @param billedDateString
	 *            The billedDateString to set.
	 */
	public void setBilledDateString(String billedDateString) {
		this.billedDateString = billedDateString;
	}

	/**
	 * @return Returns the fromDateString.
	 */
	public String getFromDateString() {
		return fromDateString;
	}

	/**
	 * @param fromDateString
	 *            The fromDateString to set.
	 */
	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	/**
	 * @return Returns the toDateString.
	 */
	public String getToDateString() {
		return toDateString;
	}

	/**
	 * @param toDateString
	 *            The toDateString to set.
	 */
	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}

	/**
	 * @return Returns the billedDate.
	 */
	public LocalDate getBilledDate() {
		return billedDate;
	}

	/**
	 * @param billedDate
	 *            The billedDate to set.
	 */
	public void setBilledDate(LocalDate billedDate) {
		this.billedDate = billedDate;
	}

	/**
	 * @return Returns the billingCurrencyCode.
	 */
	public String getBillingCurrencyCode() {
		return billingCurrencyCode;
	}

	/**
	 * @param billingCurrencyCode
	 *            The billingCurrencyCode to set.
	 */
	public void setBillingCurrencyCode(String billingCurrencyCode) {
		this.billingCurrencyCode = billingCurrencyCode;
	}

	/**
	 * @return Returns the fromBillingPeriod.
	 */
	public LocalDate getFromBillingPeriod() {
		return fromBillingPeriod;
	}

	/**
	 * @param fromBillingPeriod
	 *            The fromBillingPeriod to set.
	 */
	public void setFromBillingPeriod(LocalDate fromBillingPeriod) {
		this.fromBillingPeriod = fromBillingPeriod;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return Returns the sector.
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector
	 *            The sector to set.
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * @return Returns the toBillingPeriod.
	 */
	public LocalDate getToBillingPeriod() {
		return toBillingPeriod;
	}

	/**
	 * @param toBillingPeriod
	 *            The toBillingPeriod to set.
	 */
	public void setToBillingPeriod(LocalDate toBillingPeriod) {
		this.toBillingPeriod = toBillingPeriod;
	}

	/**
	 * @return Returns the totalAmountinBillingCurrency.
	 */
	public double getTotalAmountinBillingCurrency() {
		return totalAmountinBillingCurrency;
	}

	/**
	 * @param totalAmountinBillingCurrency
	 *            The totalAmountinBillingCurrency to set.
	 */
	public void setTotalAmountinBillingCurrency(
			double totalAmountinBillingCurrency) {
		this.totalAmountinBillingCurrency = totalAmountinBillingCurrency;
	}

	/**
	 * @return Returns the contractCurrencyCode.
	 */
	public String getContractCurrencyCode() {
		return contractCurrencyCode;
	}

	/**
	 * @param contractCurrencyCode
	 *            The contractCurrencyCode to set.
	 */
	public void setContractCurrencyCode(String contractCurrencyCode) {
		this.contractCurrencyCode = contractCurrencyCode;
	}

	/**
	 * @return Returns the totalAmountinContractCurrency.
	 */
	public double getTotalAmountinContractCurrency() {
		return totalAmountinContractCurrency;
	}

	/**
	 * @param totalAmountinContractCurrency
	 *            The totalAmountinContractCurrency to set.
	 */
	public void setTotalAmountinContractCurrency(
			double totalAmountinContractCurrency) {
		this.totalAmountinContractCurrency = totalAmountinContractCurrency;
	}

	/**
	 * @return the totalAmountinsettlementCurrency
	 */
	public double getTotalAmountinsettlementCurrency() {
		return totalAmountinsettlementCurrency;
	}

	/**
	 * @param totalAmountinsettlementCurrency the totalAmountinsettlementCurrency to set
	 */
	public void setTotalAmountinsettlementCurrency(
			double totalAmountinsettlementCurrency) {
		this.totalAmountinsettlementCurrency = totalAmountinsettlementCurrency;
	}

}
