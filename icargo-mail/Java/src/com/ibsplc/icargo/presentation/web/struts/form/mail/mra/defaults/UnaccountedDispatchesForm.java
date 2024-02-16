/*
 * UnaccountedDispatchesForm.java Created on Aug 14, 2008
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2107
 *
 */
public class UnaccountedDispatchesForm extends ScreenModel{

private static final String BUNDLE = "unaccountedDispatches";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID =
		"mailtracking.mra.defaults.unaccounteddispatches";

	private String fromDate;
	
	private String toDate;
	
	private String origin;
	
	private String destination;
	
	private String flightNo;
	
	private String flightCode;
	
	private String reasonCode;
	
	private String effectiveDate;
	
	private String noOfDispatches;
	
	private String proRatedAmt;
	
	private String currency;
	
	private String lastPageNum = "0";
	
	private int currentIndex;
	
	private String displayPage= "1";
	
	private String fromScreen;
	
	private String selectedDispatch;
	
	private String closeFlag;
	
	private String[] selectedRows;
	

		
	/**
	 * @return the selectedRows
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows the selectedRows to set
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	public String getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
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
	 *
	 */
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 *
	 */
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 *
	 */
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}

	/**
	 * @param flightCode the flightCode to set
	 */
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * @param flightNo the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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
	 * @return the noOfDispatches
	 */
	public String getNoOfDispatches() {
		return noOfDispatches;
	}

	/**
	 * @param noOfDispatches the noOfDispatches to set
	 */
	public void setNoOfDispatches(String noOfDispatches) {
		this.noOfDispatches = noOfDispatches;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the proRatedAmt
	 */
	public String getProRatedAmt() {
		return proRatedAmt;
	}

	/**
	 * @param proRatedAmt the proRatedAmt to set
	 */
	public void setProRatedAmt(String proRatedAmt) {
		this.proRatedAmt = proRatedAmt;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
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
	 * @return the currentIndex
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * @param currentIndex the currentIndex to set
	 */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
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

	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	public String getSelectedDispatch() {
		return selectedDispatch;
	}

	public void setSelectedDispatch(String selectedDispatch) {
		this.selectedDispatch = selectedDispatch;
	}



	
}
