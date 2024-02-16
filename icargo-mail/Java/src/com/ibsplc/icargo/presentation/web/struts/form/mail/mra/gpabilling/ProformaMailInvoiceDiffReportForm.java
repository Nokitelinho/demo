/*
 * ProformaMailInvoiceDiffReportForm.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3271
 *
 */

public class ProformaMailInvoiceDiffReportForm extends ScreenModel {
	private static final String BUNDLE ="proformaInvoiceDiffReport";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.gpabilling.proformainvoicediffreport";

	private String functionPoint;

	private String fromDate;

	private String toDate;

	private String country;


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
	  * @return Returns the SCREENID.
	  */
	 public String getScreenId() {
		 return SCREENID;
	 }
	/**
	 * @return Returns the BUNDLE.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the functionPoint.
	 */
	public String getFunctionPoint() {
		return functionPoint;
	}

	/**
	 * @param functionPoint The functionPoint to set.
	 */
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}

	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
