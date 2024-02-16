/*
 * SearchFlightForm.java Created on Jul 09, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3817
 * 
 */
public class SearchFlightForm extends ScreenModel {
	private static final String SCREEN_ID = "mailtracking.defaults.searchflight";

	private static final String PRODUCT_NAME = "mail";

	private static final String SUBPRODUCT_NAME = "operations";

	private static final String BUNDLE = "searchflightResources";

	private String carrierCode;

	private String flightNumber;

	private String flightStatus;

	private String port;

	private String departingPort;

	private String departureDate;
	
	private String depFromDate;
	
	private String depToDate;
	
private String arrFromDate;
	
	private String arrToDate;
	

	private String arrivalPort;

	private String arrivalDate;

	private String mailStatus;

	private String status;

	private String fromScreen;

	private String listStatus;

	private String fromParentScreen;

	private String selectedRow;

	private String fromReconciliation;

	private String finaliseFlag;

	private String[] selectedElements;

	private String displayPage = "1";

	private String lastPageNum = "0";
	
	private String absoluteIndex = "0";

	private String isOutbound;

	private String isInbound;
	
	private String uldsSelectedFlag;
	
	
	

	/**
	 * @return the arrivalDate
	 */
	public String getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate
	 *            the arrivalDate to set
	 */
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return the arrivalPort
	 */
	public String getArrivalPort() {
		return arrivalPort;
	}

	/**
	 * @param arrivalPort
	 *            the arrivalPort to set
	 */
	public void setArrivalPort(String arrivalPort) {
		this.arrivalPort = arrivalPort;
	}

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the departingPort
	 */
	public String getDepartingPort() {
		return departingPort;
	}

	/**
	 * @param departingPort
	 *            the departingPort to set
	 */
	public void setDepartingPort(String departingPort) {
		this.departingPort = departingPort;
	}

	/**
	 * @return the departureDate
	 */
	public String getDepartureDate() {
		return departureDate;
	}

	/**
	 * @param departureDate
	 *            the departureDate to set
	 */
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the flightStatus
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus
	 *            the flightStatus to set
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	public String getProduct() {

		return PRODUCT_NAME;
	}

	public String getScreenId() {

		return SCREEN_ID;
	}

	public String getSubProduct() {

		return SUBPRODUCT_NAME;
	}

	public String getBundle() {
		return BUNDLE;
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
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return the listStatus
	 */
	public String getListStatus() {
		return listStatus;
	}

	/**
	 * @param listStatus the listStatus to set
	 */
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	/**
	 * @return the selectedElements
	 */
	public String[] getSelectedElements() {
		return selectedElements;
	}

	/**
	 * @param selectedElements the selectedElements to set
	 */
	public void setSelectedElements(String[] selectedElements) {
		this.selectedElements = selectedElements;
	}

	/**
	 * @return the mailStatus
	 */
	public String getMailStatus() {
		return mailStatus;
	}

	/**
	 * @param mailStatus the mailStatus to set
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	/**
	 * @return the fromParentScreen
	 */
	public String getFromParentScreen() {
		return fromParentScreen;
	}

	/**
	 * @param fromParentScreen the fromParentScreen to set
	 */
	public void setFromParentScreen(String fromParentScreen) {
		this.fromParentScreen = fromParentScreen;
	}

	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return the displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return the lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum the lastPageNum to set
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return the fromReconciliation
	 */
	public String getFromReconciliation() {
		return fromReconciliation;
	}

	/**
	 * @param fromReconciliation the fromReconciliation to set
	 */
	public void setFromReconciliation(String fromReconciliation) {
		this.fromReconciliation = fromReconciliation;
	}

	/**
	 * @return the finaliseFlag
	 */
	public String getFinaliseFlag() {
		return finaliseFlag;
	}

	/**
	 * @param finaliseFlag the finaliseFlag to set
	 */
	public void setFinaliseFlag(String finaliseFlag) {
		this.finaliseFlag = finaliseFlag;
	}

	/**
	 * @return the isInbound
	 */
	public String getIsInbound() {
		return isInbound;
	}

	/**
	 * @param isInbound the isInbound to set
	 */
	public void setIsInbound(String isInbound) {
		this.isInbound = isInbound;
	}

	/**
	 * @return the isOutbound
	 */
	public String getIsOutbound() {
		return isOutbound;
	}

	/**
	 * @param isOutbound the isOutbound to set
	 */
	public void setIsOutbound(String isOutbound) {
		this.isOutbound = isOutbound;
	}

	/**
	 * @return the uldsSelectedFlag
	 */
	public String getUldsSelectedFlag() {
		return uldsSelectedFlag;
	}

	/**
	 * @param uldsSelectedFlag the uldsSelectedFlag to set
	 */
	public void setUldsSelectedFlag(String uldsSelectedFlag) {
		this.uldsSelectedFlag = uldsSelectedFlag;
	}

	/**
	 * @return Returns the absoluteIndex.
	 */
	public String getAbsoluteIndex() {
		return absoluteIndex;
	}

	/**
	 * @param absoluteIndex The absoluteIndex to set.
	 */
	public void setAbsoluteIndex(String absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public String getDepFromDate() {
		return depFromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setDepFromDate(String depFromDate) {
		this.depFromDate = depFromDate;
	}

	/**
	 * @return Returns the toDate.
	 */
	public String getDepToDate() {
		return depToDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setDepToDate(String depToDate) {
		this.depToDate = depToDate;
	}

	/**
	 * @return Returns the arrFromDate.
	 */
	public String getArrFromDate() {
		return arrFromDate;
	}

	/**
	 * @param arrFromDate The arrFromDate to set.
	 */
	public void setArrFromDate(String arrFromDate) {
		this.arrFromDate = arrFromDate;
	}

	/**
	 * @return Returns the arrToDate.
	 */
	public String getArrToDate() {
		return arrToDate;
	}

	/**
	 * @param arrToDate The arrToDate to set.
	 */
	public void setArrToDate(String arrToDate) {
		this.arrToDate = arrToDate;
	}

}
