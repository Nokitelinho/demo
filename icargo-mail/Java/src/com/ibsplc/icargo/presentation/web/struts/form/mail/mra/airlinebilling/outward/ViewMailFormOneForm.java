/*
 * ViewMailFormOneForm.java Created on July 15, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3456
 * 
 */
public class ViewMailFormOneForm extends ScreenModel {
	private static final String BUNDLE = "viewForm1";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra.airlinebilling";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";

	private String clearancePeriod;

	private String airlineCode;

	private String airlineNumber;

	private String listingCurrency;

	private String exchangeRateInBillingCurrency;

	private String exchangeRateInListingCurrency;

	private String billingCurrency;

	private String tableClass;

	private String airlineCodeFilterField;

	private String airlineNumberFilterField;

	/**
	 * @author a-3447
	 */
	private String listingTotalAmt;

	private String totalBaseAmt;

	private String billingTotalAmt;

	private String invokingScreen;

	/**
	 * @author a-3447
	 */

	/**
	 * @return Returns the airlineCodeFilterField.
	 */
	public String getAirlineCodeFilterField() {
		return airlineCodeFilterField;
	}

	/**
	 * @param airlineCodeFilterField
	 *            The airlineCodeFilterField to set.
	 */

	public void setAirlineCodeFilterField(String airlineCodeFilterField) {
		this.airlineCodeFilterField = airlineCodeFilterField;
	}

	/**
	 * @return Returns the airlineNumberFilterField.
	 */
	public String getAirlineNumberFilterField() {
		return airlineNumberFilterField;
	}

	/**
	 * @param airlineNumberFilterField
	 *            The airlineNumberFilterField to set.
	 */
	public void setAirlineNumberFilterField(String airlineNumberFilterField) {
		this.airlineNumberFilterField = airlineNumberFilterField;
	}

	/**
	 * @return Returns the SCREENID.
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return Returns the PRODUCT.
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SUBPRODUCT.
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineNumber.
	 */
	public String getAirlineNumber() {
		return airlineNumber;
	}

	/**
	 * @param airlineNumber
	 *            The airlineNumber to set.
	 */
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
	}

	/**
	 * @return Returns the billingCurrency.
	 */
	public String getBillingCurrency() {
		return billingCurrency;
	}

	/**
	 * @param billingCurrency
	 *            The billingCurrency to set.
	 */
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod
	 *            The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the exchangeRateInBillingCurrency.
	 */

	public String getExchangeRateInBillingCurrency() {
		return exchangeRateInBillingCurrency;
	}

	/**
	 * @param exchangeRateInBillingCurrency
	 *            The exchangeRateInBillingCurrency to set.
	 */
	public void setExchangeRateInBillingCurrency(
			String exchangeRateInBillingCurrency) {
		this.exchangeRateInBillingCurrency = exchangeRateInBillingCurrency;
	}

	/**
	 * @return Returns the listingCurrency.
	 */
	public String getListingCurrency() {
		return listingCurrency;
	}

	/**
	 * @param listingCurrency
	 *            The listingCurrency to set.
	 */
	public void setListingCurrency(String listingCurrency) {
		this.listingCurrency = listingCurrency;
	}

	/**
	 * @return Returns the tableClass.
	 */
	public String getTableClass() {
		return tableClass;
	}

	/**
	 * @param tableClass
	 *            The tableClass to set.
	 */
	public void setTableClass(String tableClass) {
		this.tableClass = tableClass;
	}

	/**
	 * @return Returns the exchangeRateInListingCurrency.
	 */
	public String getExchangeRateInListingCurrency() {
		return exchangeRateInListingCurrency;
	}

	/**
	 * @param exchangeRateInListingCurrency
	 *            The exchangeRateInListingCurrency to set.
	 */
	public void setExchangeRateInListingCurrency(
			String exchangeRateInListingCurrency) {
		this.exchangeRateInListingCurrency = exchangeRateInListingCurrency;
	}

	/**
	 * @return the billingTotalAmt
	 */
	public String getBillingTotalAmt() {
		return billingTotalAmt;
	}

	/**
	 * @param billingTotalAmt
	 *            the billingTotalAmt to set
	 */
	public void setBillingTotalAmt(String billingTotalAmt) {
		this.billingTotalAmt = billingTotalAmt;
	}

	/**
	 * @return the invokingScreen
	 */
	public String getInvokingScreen() {
		return invokingScreen;
	}

	/**
	 * @param invokingScreen
	 *            the invokingScreen to set
	 */
	public void setInvokingScreen(String invokingScreen) {
		this.invokingScreen = invokingScreen;
	}

	/**
	 * @return the listingTotalAmt
	 */
	public String getListingTotalAmt() {
		return listingTotalAmt;
	}

	/**
	 * @param listingTotalAmt
	 *            the listingTotalAmt to set
	 */
	public void setListingTotalAmt(String listingTotalAmt) {
		this.listingTotalAmt = listingTotalAmt;
	}

	/**
	 * @return the totalBaseAmt
	 */
	public String getTotalBaseAmt() {
		return totalBaseAmt;
	}

	/**
	 * @param totalBaseAmt
	 *            the totalBaseAmt to set
	 */
	public void setTotalBaseAmt(String totalBaseAmt) {
		this.totalBaseAmt = totalBaseAmt;
	}

}
