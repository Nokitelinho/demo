/*
 * ULDPoolOwnersForm.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * 
 * @author A-2046
 * 
 */
public class ULDPoolOwnersForm extends ScreenModel {

	private static final String BUNDLE = "uldPoolOwnersResources";

	private String bundle;

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.uldpoolowners";

	private String airline;

	private String flightNumber;

	private String origin;

	private String destination;

	private String[] airlineOwn;

	private String[] flightOwn;

	private String[] fromDate;

	private String[] toDate;
	
	private String[] operationFlag;
	
	private String[] hiddenOperationFlag;

	private String duplicateFlightStatus;
	
	private String[] selectedRows;
	
	private String linkStatus;
/**
 * 
 * @return
 */
	public String getLinkStatus() {
		return linkStatus;
	}
/**
 * 
 * @param linkStatus
 */
	public void setLinkStatus(String linkStatus) {
		this.linkStatus = linkStatus;
	}
/**
 * 
 * @return
 */
	public String[] getSelectedRows() {
		return selectedRows;
	}
/**
 * 
 * @param selectedRows
 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}
/**
 * 
 * @return
 */
	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}
/**
 * 
 * @param duplicateFlightStatus
 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}
/**
 * 
 * @return
 */
	public String getAirline() {
		return airline;
	}
/**
 * 
 * @param airline
 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
/**
 * 
 * @return
 */
	public String[] getAirlineOwn() {
		return airlineOwn;
	}
/**
 * 
 * @param airlineOwn
 */
	public void setAirlineOwn(String[] airlineOwn) {
		this.airlineOwn = airlineOwn;
	}
/**
 * 
 */
	public String getBundle() {
		return BUNDLE;
	}
/**
 * 
 * @param bundle
 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
/**
 * 
 * @return
 */
	public String getDestination() {
		return destination;
	}
	/**
	 * 
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
/**
 * 
 * @return
 */
	public String getFlightNumber() {
		return flightNumber;
	}
/**
 * 
 * @param flightNumber
 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
/**
 * 
 * @return
 */
	public String[] getFlightOwn() {
		return flightOwn;
	}
/**
 * 
 * @param flightOwn
 */
	public void setFlightOwn(String[] flightOwn) {
		this.flightOwn = flightOwn;
	}
/**
 * 
 * @return
 */
	public String[] getFromDate() {
		return fromDate;
	}
/**
 * 
 * @param fromDate
 */
	public void setFromDate(String[] fromDate) {
		this.fromDate = fromDate;
	}
/**
 * 
 * @return
 */
	public String getOrigin() {
		return origin;
	}
/**
 * 
 * @param origin
 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
/**
 * 
 * @return
 */
	public String getPRODUCT() {
		return PRODUCT;
	}
/**
 * 
 * @return
 */
	public String getSCREENID() {
		return SCREENID;
	}
/**
 * 
 * @return
 */
	public String getSUBPRODUCT() {
		return SUBPRODUCT;
	}
/**
 * 
 * @return
 */
	public String[] getToDate() {
		return toDate;
	}
/**
 * 
 * @param toDate
 */
	public void setToDate(String[] toDate) {
		this.toDate = toDate;
	}

	/**
	 * Method to return the product the screen is associated with
	 * 
	 * @return String
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * Method to return the sub product the screen is associated with
	 * 
	 * @return String
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public String[] getHiddenOperationFlag() {
		return hiddenOperationFlag;
	}
	public void setHiddenOperationFlag(String[] hiddenOperationFlag) {
		this.hiddenOperationFlag = hiddenOperationFlag;
	}
	/**
	 * Method to return the id the screen is associated with
	 * 
	 * @return String
	 */
	public String getScreenId() {
		return SCREENID;
	}
	/**
	 * 
	 * @return
	 */
		public String[] getOperationFlag() {
			return operationFlag;
		}
	/**
	 * 
	 * @param operationFlag
	 */
		public void setOperationFlag(String[] operationFlag) {
			this.operationFlag = operationFlag;
		}
		
}
