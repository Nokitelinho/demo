/*
 * MailHandedOverReportForm.java Created on FEB 28, 2008
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
 * 
 * @author A-3353
 *
 */
public class MailHandedOverReportForm extends ScreenModel{

	private static final String SCREEN_ID 
						= "mailtracking.defaults.mailhandedoverreport";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailHandedOverReportResources";
	
	private String fromDate;
	private String toDate;
	private String carrierCode;
	private String carrierId;
	private String flightCarrierCode;
	private String flightCarrierId;
	private String flightNumber;
	private String validFlag;
	private String status;
	/**
	 * @return the fromDate
	 */
	@DateFieldId(id="MailHandedovertoOtherAirlinesDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
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
	 * @return the toDate
	 */
	@DateFieldId(id="MailHandedovertoOtherAirlinesDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
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
	 * @return the bUNDLE
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * @return the pRODUCT_NAME
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}
	/**
	 * @return the sCREEN_ID
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}
	/**
	 * @return the sUBPRODUCT_NAME
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
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
	 * @return the carrierId
	 */
	public String getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId the carrierId to set
	 */
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return the flightCarrierId
	 */
	public String getFlightCarrierId() {
		return flightCarrierId;
	}
	/**
	 * @param flightCarrierId the flightCarrierId to set
	 */
	public void setFlightCarrierId(String flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
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

}
