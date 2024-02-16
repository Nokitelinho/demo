/*
 * ULDHistoryForm.java Created on Oct 17, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2619
 * 
 */
public class ULDHistoryForm extends ScreenModel {

	private static final String BUNDLE = "uldHistory";

	private static final String SCREEN_ID = "uld.defaults.uldhistory";

	private static final String MODULE_NAME = "uld";

	private static final String SUBMODULE_NAME = "defaults";

	private String bundle;

	private String uldNumber;

	private String status;

	private String fromDate;

	private String toDate;

	private String carrierCode;

	private String flightNumber;

	private String flightDate;

	private String fromStation;

	private String remarks;

	private String lastPageNum;

	private String displayPage;

	/**
	 * @return Returns the bundle.
	 */

	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle
	 *            The bundle to set.
	 */

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return Returns the screenId.
	 */

	public String getScreenId() {
		return "uld.defaults.uldhistory";
	}

	/**
	 * @return Returns the product.
	 */

	public String getProduct() {
		return "uld";
	}

	/**
	 * @return Returns the subProduct.
	 */

	public String getSubProduct() {
		return "defaults";
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the fromStation.
	 */
	public String getFromStation() {
		return fromStation;
	}

	/**
	 * @param fromStation
	 *            The fromStation to set.
	 */
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage
	 *            The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum
	 *            The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

}
