/*
 * DsnAuditForm.java Created on July 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1876
 *
 */
public class DsnAuditForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.dsnaudit";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "dsnAuditResources";

	private String bundle;

	private String txnFromDate;
	private String txnToDate;
	private String dsn;
	private String ooe;
	private String doe;
	private String category;
	private String subclass;
	private String mailClass;
	private String year;
	

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
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	public String getOoe() {
		return ooe;
	}

	public void setOoe(String ooe) {
		this.ooe = ooe;
	}

	public String getSubclass() {
		return subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public String getDoe() {
		return doe;
	}

	public void setDoe(String doe) {
		this.doe = doe;
	}

	public String getTxnFromDate() {
		return txnFromDate;
	}

	public void setTxnFromDate(String txnFromDate) {
		this.txnFromDate = txnFromDate;
	}

	public String getTxnToDate() {
		return txnToDate;
	}

	public void setTxnToDate(String txnToDate) {
		this.txnToDate = txnToDate;
	}

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}


}
