/*
 * MailFlightSummaryForm.java Created on Jan 25,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1862
 *
 */
public class MailFlightSummaryForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.mailflightsummary";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailFlightSummaryResources";

	private String flightCarrierCode;
	private String flightNumber;
	private String flightDate;
	private String port;
	private String statusFlag;
	private String selectedMails;
	private String screenStatFlag;
	private String status;
	private String controlNum;
	private String popupStatus;
	private String[] selectUBR;
	private String[] selectMail;
	private String[] childContainer;
	private String selectBooking;
	
	private String manifest;
	
	private String printDocType;
	private String printKey;
	
	private int carrierID;
	private long flightSequenceNumber;
	

	public String getManifest() {
		return manifest;
	}

	public void setManifest(String manifest) {
		this.manifest = manifest;
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
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	

	/**
	 * @return Returns the selectedMails.
	 */
	public String getSelectedMails() {
		return selectedMails;
	}

	/**
	 * @param selectedMails The selectedMails to set.
	 */
	public void setSelectedMails(String selectedMails) {
		this.selectedMails = selectedMails;
	}

	/**
	 * @return Returns the childContainer.
	 */
	public String[] getChildContainer() {
		return childContainer;
	}

	/**
	 * @param childContainer The childContainer to set.
	 */
	public void setChildContainer(String[] childContainer) {
		this.childContainer = childContainer;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
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
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the port.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port The port to set.
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return Returns the selectMail.
	 */
	public String[] getSelectMail() {
		return selectMail;
	}

	/**
	 * @param selectMail The selectMail to set.
	 */
	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
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
	 * @return Returns the controlNum.
	 */
	public String getControlNum() {
		return controlNum;
	}

	/**
	 * @param controlNum The controlNum to set.
	 */
	public void setControlNum(String controlNum) {
		this.controlNum = controlNum;
	}

	/**
	 * @return Returns the popupStatus.
	 */
	public String getPopupStatus() {
		return popupStatus;
	}

	/**
	 * @param popupStatus The popupStatus to set.
	 */
	public void setPopupStatus(String popupStatus) {
		this.popupStatus = popupStatus;
	}

	/**
	 * @return Returns the selectUBR.
	 */
	public String[] getSelectUBR() {
		return selectUBR;
	}

	/**
	 * @param selectUBR The selectUBR to set.
	 */
	public void setSelectUBR(String[] selectUBR) {
		this.selectUBR = selectUBR;
	}

	/**
	 * @return Returns the screenStatFlag.
	 */
	public String getScreenStatFlag() {
		return screenStatFlag;
	}

	/**
	 * @param screenStatFlag The screenStatFlag to set.
	 */
	public void setScreenStatFlag(String screenStatFlag) {
		this.screenStatFlag = screenStatFlag;
	}

	/**
	 * @return Returns the selectBooking.
	 */
	public String getSelectBooking() {
		return this.selectBooking;
	}

	/**
	 * @param selectBooking The selectBooking to set.
	 */
	public void setSelectBooking(String selectBooking) {
		this.selectBooking = selectBooking;
	}

	public String getPrintKey() {
		return printKey;
	}

	public void setPrintKey(String printKey) {
		this.printKey = printKey;
	}

	public String getPrintDocType() {
		return printDocType;
	}

	public void setPrintDocType(String printDocType) {
		this.printDocType = printDocType;
	}

	public int getCarrierID() {
		return carrierID;
	}

	public void setCarrierID(int carrierID) {
		this.carrierID = carrierID;
	}

	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	


}
