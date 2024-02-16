/*
 * MRAAirlineAuditForm.java Created on Aug 13, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2391
 *
 */

public class MRAAirlineAuditForm extends ScreenModel{
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.airlineaudit";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "mra";
	private static final String BUNDLE = "airlineAuditResources";

	//private String bundle;

	private String txnFromDate;
	private String txnToDate;
	private String airlineCode;
	private String clearancePeriod;
	
	/**
	 * @return airlineCode
	 */

	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return clearancePeriod
	 */

	public String getClearancePeriod() {
		return clearancePeriod;
	}
	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	
	/**
	 * @return  txnFromDate
	 */
	public String getTxnFromDate() {
		return txnFromDate;
	}
	/**
	 * @param txnFromDate The txnFromDate to set.
	 */
	public void setTxnFromDate(String txnFromDate) {
		this.txnFromDate = txnFromDate;
	}
	/**
	 * @return txnToDate.
	 */
	public String getTxnToDate() {
		return txnToDate;
	}
	/**
	 * @param txnToDate The txnToDate to set.
	 */
	public void setTxnToDate(String txnToDate) {
		this.txnToDate = txnToDate;
	}


}
