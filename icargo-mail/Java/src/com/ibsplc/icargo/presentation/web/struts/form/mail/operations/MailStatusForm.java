/*
 * MailStatusForm.java Created on FEB 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class MailStatusForm extends ScreenModel{

	private static final String SCREEN_ID = "mailtracking.defaults.mailstatus";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailStatusResources";
	
	private String flightCarrierCode;
	private String flightCarrierIdr;
	private String flightNumber; 
	private String fromDate;
	private String toDate;
	private String carrierCode;
	private String carrierIdr;
	private String pol;
	private String pou;
	private String currentStatus;
	private String paCode;
	private String dummyFlightDate;
	private String validFlag;
	private String status;
	
	
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
	@DateFieldId(id="MailStatusReportDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
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
	 * @return the paCode
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode the paCode to set
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return the pol
	 */
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol the pol to set
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return the pou
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou the pou to set
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	
	/**
	 * @return the currentStatus
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}

	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**
	 * @return the toDate
	 */
	@DateFieldId(id="MailStatusReportDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
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
	 * @return the carrierIdr
	 */
	public String getCarrierIdr() {
		return carrierIdr;
	}

	/**
	 * @param carrierIdr the carrierIdr to set
	 */
	public void setCarrierIdr(String carrierIdr) {
		this.carrierIdr = carrierIdr;
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
