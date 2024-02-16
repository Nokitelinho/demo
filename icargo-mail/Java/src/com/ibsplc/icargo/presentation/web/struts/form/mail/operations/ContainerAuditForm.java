/*
 * ContainerAuditForm.java Created on July 18, 2007
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
public class ContainerAuditForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.containeraudit";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "containerAuditResources";

	private String bundle;
	
	private String txnFromDate;
	private String txnToDate;
	private String flightCarrierCode;
	private String flightNumber;
	private String flightDate;
	private String assignPort;
	private String containerNumber;

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

	public String getAssignPort() {
		return assignPort;
	}

	public void setAssignPort(String assignPort) {
		this.assignPort = assignPort;
	}
	
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
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

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	
}
