/*
 * PartnerCarriersForm.java Created on August 11, 2006
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
public class PartnerCarriersForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.partnercarriers";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "partnerCarrierResources";

	private String bundle;
	
	private String airport;
	
	private String[] rowId;
	private String[] partnerCarrierCode;
	private String[] partnerCarrierName;
	private String[] operationFlag;
	private String disableSave;
	private String hiddenpartnerCarrierCode;
	private String  hiddenpartnerCarrierName;
	
	//Added By RENO K ABRAHAM FOR AirNZ CR 491
	private String viewBillingLine;

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
	 * @return Returns the airport.
	 */
	public String getAirport() {
		return airport;
	}

	/**
	 * @param airport The airport to set.
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the partnerCarrierCode.
	 */
	public String[] getPartnerCarrierCode() {
		return partnerCarrierCode;
	}

	/**
	 * @param partnerCarrierCode The partnerCarrierCode to set.
	 */
	public void setPartnerCarrierCode(String[] partnerCarrierCode) {
		this.partnerCarrierCode = partnerCarrierCode;
	}

	/**
	 * @return Returns the partnerCarrierName.
	 */
	public String[] getPartnerCarrierName() {
		return partnerCarrierName;
	}

	/**
	 * @param partnerCarrierName The partnerCarrierName to set.
	 */
	public void setPartnerCarrierName(String[] partnerCarrierName) {
		this.partnerCarrierName = partnerCarrierName;
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
		return this.disableSave;
	}

	/**
	 * @param disableSave The disableSave to set.
	 */
	public void setDisableSave(String disableSave) {
		this.disableSave = disableSave;
	}

	/**
	 * @return the viewBillingLine
	 */
	public String getViewBillingLine() {
		return viewBillingLine;
	}

	/**
	 * @param viewBillingLine the viewBillingLine to set
	 */
	public void setViewBillingLine(String viewBillingLine) {
		this.viewBillingLine = viewBillingLine;
	}

	public String getHiddenpartnerCarrierCode() {
		return hiddenpartnerCarrierCode;
	}

	public void setHiddenpartnerCarrierCode(String hiddenpartnerCarrierCode) {
		this.hiddenpartnerCarrierCode = hiddenpartnerCarrierCode;
	}

	public String getHiddenpartnerCarrierName() {
		return hiddenpartnerCarrierName;
	}

	public void setHiddenpartnerCarrierName(String hiddenpartnerCarrierName) {
		this.hiddenpartnerCarrierName = hiddenpartnerCarrierName;
	}



}
