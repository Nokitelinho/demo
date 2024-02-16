/*
 * EmptyULDsForm.java Created on August 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2047
 *
 */
public class EmptyULDsForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.emptyulds";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "emptyULDResources";
	
	private String bundle;
	private String[] selectULD;
	private String fromScreen;
	private String status;
	private String fromFlightCarrierCode;
	private String fromFlightNumber;
	private String frmFlightDate;
	private String frmassignTo;
	private String fromdestination;
	private String toScreen;

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

	/**
	 * @return Returns the selectULD.
	 */
	public String[] getSelectULD() {
		return selectULD;
	}

	/**
	 * @param selectULD The selectULD to set.
	 */
	public void setSelectULD(String[] selectULD) {
		this.selectULD = selectULD;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the frmassignTo
	 */
	public String getFrmassignTo() {
		return frmassignTo;
	}

	/**
	 * @param frmassignTo the frmassignTo to set
	 */
	public void setFrmassignTo(String frmassignTo) {
		this.frmassignTo = frmassignTo;
	}

	/**
	 * @return the frmFlightDate
	 */
	public String getFrmFlightDate() {
		return frmFlightDate;
	}

	/**
	 * @param frmFlightDate the frmFlightDate to set
	 */
	public void setFrmFlightDate(String frmFlightDate) {
		this.frmFlightDate = frmFlightDate;
	}

	/**
	 * @return the fromdestination
	 */
	public String getFromdestination() {
		return fromdestination;
	}

	/**
	 * @param fromdestination the fromdestination to set
	 */
	public void setFromdestination(String fromdestination) {
		this.fromdestination = fromdestination;
	}

	/**
	 * @return the fromFlightCarrierCode
	 */
	public String getFromFlightCarrierCode() {
		return fromFlightCarrierCode;
	}

	/**
	 * @param fromFlightCarrierCode the fromFlightCarrierCode to set
	 */
	public void setFromFlightCarrierCode(String fromFlightCarrierCode) {
		this.fromFlightCarrierCode = fromFlightCarrierCode;
	}

	/**
	 * @return the fromFlightNumber
	 */
	public String getFromFlightNumber() {
		return fromFlightNumber;
	}

	/**
	 * @param fromFlightNumber the fromFlightNumber to set
	 */
	public void setFromFlightNumber(String fromFlightNumber) {
		this.fromFlightNumber = fromFlightNumber;
	}

	public String getToScreen() {
		return toScreen;
	}

	public void setToScreen(String toScreen) {
		this.toScreen = toScreen;
	}

}

