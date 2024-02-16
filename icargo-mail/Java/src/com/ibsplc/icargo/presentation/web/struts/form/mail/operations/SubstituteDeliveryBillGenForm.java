/*
 * SubstituteDeliveryBillGenForm.java Created on Jun 30 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-3217
 *
 */
public class SubstituteDeliveryBillGenForm extends ScreenModel{

	private static final String BUNDLE = "substituteDeliveryBillResources";
	
	private static final String SCREEN_ID = "mailtracking.defaults.substitutedeliverybill";
	
	private static final String PRODUCT = "mail";
	
	private static final String SUBPRODUCT = "operations";
		
	private String carrierCode = "";
	
	private String flightNumber = "";
	
	private String departurePort;
	
	private String departureDate;
	
	private String arrivalPort;
	
	private String arrivalDate;

	private String radioInboundOutbound;
	
	private String[] consignmentCheck;
	
	private String[] mailCheck;
	
	private String consignmentMutlireports;
	
	private String mailMutlireports;
	
	private String status;
		
	
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
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the departurePort
	 */
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePort the departurePort to set
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	/**
	 * @return the departureDate
	 */
	public String getDepartureDate() {
		return departureDate;
	}

	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * @return the arrivalPort
	 */
	public String getArrivalPort() {
		return arrivalPort;
	}

	/**
	 * @param arrivalPort the arrivalPort to set
	 */
	public void setArrivalPort(String arrivalPort) {
		this.arrivalPort = arrivalPort;
	}

	/**
	 * @return the arrivalDate
	 */
	public String getArrivalDate() {
		return arrivalDate;
	}  

	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the PRODUCT
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return the SCREEN_ID
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return the SUBPRODUCT
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return the BUNDLE
	 */
	public String getBundle(){
		return BUNDLE;
	}
	
	/**
	 * @return the BUNDLE
	 */
	public void setBundle(String BUNDLE){
	
	}

	/**
	 * @return the radioInboundOutbound
	 */
	public String getRadioInboundOutbound() {
		return radioInboundOutbound;
	}

	/**
	 * @param radioInboundOutbound the radioInboundOutbound to set
	 */
	public void setRadioInboundOutbound(String radioInboundOutbound) {
		this.radioInboundOutbound = radioInboundOutbound;
	}

	/**
	 * @return Returns the consignmentCheck.
	 */
	public String[] getConsignmentCheck() {
		return consignmentCheck;
	}

	/**
	 * @param consignmentCheck The consignmentCheck to set.
	 */
	public void setConsignmentCheck(String[] consignmentCheck) {
		this.consignmentCheck = consignmentCheck;
	}

	/**
	 * @return Returns the mailCheck.
	 */
	public String[] getMailCheck() {
		return mailCheck;
	}

	/**
	 * @param mailCheck The mailCheck to set.
	 */
	public void setMailCheck(String[] mailCheck) {
		this.mailCheck = mailCheck;
	}

	/**
	 * @return Returns the consignmentMutlireports.
	 */
	public String getConsignmentMutlireports() {
		return consignmentMutlireports;
	}

	/**
	 * @param consignmentMutlireports The consignmentMutlireports to set.
	 */
	public void setConsignmentMutlireports(String consignmentMutlireports) {
		this.consignmentMutlireports = consignmentMutlireports;
	}

	/**
	 * @return Returns the mailMutlireports.
	 */
	public String getMailMutlireports() {
		return mailMutlireports;
	}

	/**
	 * @param mailMutlireports The mailMutlireports to set.
	 */
	public void setMailMutlireports(String mailMutlireports) {
		this.mailMutlireports = mailMutlireports;
	}

	
}
