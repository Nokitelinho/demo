/*
 * ResiditRestrictonSetUpForm.java Created on Sep 30, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3108
 * 
 */
public class ResiditRestrictonSetUpForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.residitrestrictionsetup";

	private static final String PRODUCT_NAME = "mail";

	private static final String SUBPRODUCT_NAME = "operations";

	private static final String BUNDLE = "residitRestrictionSetUpResources";

	private String airportCodeFilter;
	
	private String paCodeFilter;
	
	private String carrierCodeFilter;
	
	private String[] rowId;

	private String[] carrierCode;

	private String[] carrierName;
	
	private String[] airportCode;

	private String[] paCode;
	
	private String[] opFlag;
	
	private String disableSave;
	
	//to populate carrier name while tab out from carrier code;
	
	private String carrierNameValue;


	
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
	 * @return Returns the airportCode.
	 */
	public String[] getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String[] airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the airportCodeFilter.
	 */
	public String getAirportCodeFilter() {
		return airportCodeFilter;
	}

	/**
	 * @param airportCodeFilter The airportCodeFilter to set.
	 */
	public void setAirportCodeFilter(String airportCodeFilter) {
		this.airportCodeFilter = airportCodeFilter;
	}

	/**
	 * @return Returns the carrierCodeFilter.
	 */
	public String getCarrierCodeFilter() {
		return carrierCodeFilter;
	}

	/**
	 * @param carrierCodeFilter The carrierCodeFilter to set.
	 */
	public void setCarrierCodeFilter(String carrierCodeFilter) {
		this.carrierCodeFilter = carrierCodeFilter;
	}

	/**
	 * @return Returns the paCodeFilter.
	 */
	public String getPaCodeFilter() {
		return paCodeFilter;
	}

	/**
	 * @param paCodeFilter The paCodeFilter to set.
	 */
	public void setPaCodeFilter(String paCodeFilter) {
		this.paCodeFilter = paCodeFilter;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String[] getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String[] carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the carrierName.
	 */
	public String[] getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName The carrierName to set.
	 */
	public void setCarrierName(String[] carrierName) {
		this.carrierName = carrierName;
	}

	/**
	 * @return Returns the opFlag.
	 */
	public String[] getOpFlag() {
		return opFlag;
	}

	/**
	 * @param opFlag The opFlag to set.
	 */
	public void setOpFlag(String[] opFlag) {
		this.opFlag = opFlag;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String[] getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String[] paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return Returns the disableSave.
	 */
	public String getDisableSave() {
		return disableSave;
	}

	/**
	 * @param disableSave The disableSave to set.
	 */
	public void setDisableSave(String disableSave) {
		this.disableSave = disableSave;
	}

	/**
	 * @return Returns the carrierNameValue.
	 */
	public String getCarrierNameValue() {
		return carrierNameValue;
	}

	/**
	 * @param carrierNameValue The carrierNameValue to set.
	 */
	public void setCarrierNameValue(String carrierNameValue) {
		this.carrierNameValue = carrierNameValue;
	}
	
	

}
