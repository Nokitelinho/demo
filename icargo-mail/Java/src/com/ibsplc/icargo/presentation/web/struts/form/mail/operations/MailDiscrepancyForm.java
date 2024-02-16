/*
 * MailDiscrepancyForm.java Created on MAR 27,2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2391
 *
 */
public class MailDiscrepancyForm extends ScreenModel{

	private static final String SCREEN_ID = "mailtracking.defaults.maildiscrepancyreport";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailDiscrepancyResources";
	
	private String flightCarrierCode;
	private String flightCarrierIdr;
	private String flightNumber; 
	private String fromDate;
	private String toDate;
	private String airport;
	private String discType;
	private String dummyFlightDate;
	private String validFlag;
	private String status;
	
	
	/**
	 * @return Returns the discType.
	 */
	public String getDiscType() {
		return discType;
	}

	/**
	 * @param discType The discType to set.
	 */
	public void setDiscType(String discType) {
		this.discType = discType;
	}



	/**
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	

	/**
	 * @return the pol
	 */
	public String getAirport() {
		return airport;
	}

	/**
	 * @param pol the pol to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}

	
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
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
	 * @return the flightCarrierIdr
	 */
	public String getFlightCarrierIdr() {
		return flightCarrierIdr;
	}

	/**
	 * @param flightCarrierIdr the flightCarrierIdr to set
	 */
	public void setFlightCarrierIdr(String flightCarrierIdr) {
		this.flightCarrierIdr = flightCarrierIdr;
	}

	/**
	 * @return the dummyFlightDate
	 */
	public String getDummyFlightDate() {
		return dummyFlightDate;
	}

	/**
	 * @param dummyFlightDate the dummyFlightDate to set
	 */
	public void setDummyFlightDate(String dummyFlightDate) {
		this.dummyFlightDate = dummyFlightDate;
	}

	/**
	 * @return the validFlag
	 */
	public String getValidFlag() {
		return validFlag;
	}

	/**
	 * @param validFlag the validFlag to set
	 */
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
