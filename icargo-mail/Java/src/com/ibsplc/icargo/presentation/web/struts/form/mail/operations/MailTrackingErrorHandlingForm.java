/*
 * MailTrackingErrorHandlingForm.java 
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-4781
 *
 */
public class MailTrackingErrorHandlingForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.errorhandling";
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "operations";
	private static final String BUNDLE = "mailtrackingerrorhandlingresources";

	private String module;
	private String subModule;
	private String airportcode;
	private String mailbag;
	private String function;
	private String container;
	private String flightCarrierCode;
	

	private String flightNumber;
	private String flightdate;
	private String errorCode;
	private String transactionName;
	
	private String parameterCodeValue;
	 
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getAirportcode() {
		return airportcode;
	}

	public void setAirportcode(String airportcode) {
		this.airportcode = airportcode;
	}

	public String getMailbag() {
		return mailbag;
	}

	public void setMailbag(String mailbag) {
		this.mailbag = mailbag;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
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

	public String getFlightdate() {
		return flightdate;
	}

	public void setFlightdate(String flightdate) {
		this.flightdate = flightdate;
	}

	/**
	 * @return Returns the ScreenId.
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return Returns the Product.
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SubProduct.
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
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the subModule
	 */
	public String getSubModule() {
		return subModule;
	}

	/**
	 * @param subModule
	 *            the subModule to set
	 */
	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}
	
	/**
	 * 	Getter for transactionName 
	 *	Added by : A-8061 on 23-Mar-2020
	 * 	Used for :IASCB-38721
	 */
	public String getTransactionName() {
		return transactionName;
	}

	/**
	 *  @param transactionName the transactionName to set
	 * 	Setter for transactionName 
	 *	Added by : A-8061 on 23-Mar-2020
	 * 	Used for :IASCB-38721
	 */
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	/**
	 * 	Getter for parameterCodeValue 
	 *	Added by : A-8061 on 25-Mar-2020
	 * 	Used for :
	 */
	public String getParameterCodeValue() {
		return parameterCodeValue;
	}

	/**
	 *  @param parameterCodeValue the parameterCodeValue to set
	 * 	Setter for parameterCodeValue 
	 *	Added by : A-8061 on 25-Mar-2020
	 * 	Used for :
	 */
	public void setParameterCodeValue(String parameterCodeValue) {
		this.parameterCodeValue = parameterCodeValue;
	}
	
}
