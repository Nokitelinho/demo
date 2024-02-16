/*
 * ULDPoolOwnersForm.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

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
	
	private String firstAirline;
	
	private String secondAirline;
	
	private String polAirport;

	private String[] airlineOne;
	
	private String[] airlineTwo;
	
	private String[] airport;
	
	private String[] remarks;

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
	
	private String flag;
	
	private String popupFlag;
	
	private String selectedRow;
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
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
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

	/**
	 * @return the airlineOne
	 */
	public String[] getAirlineOne() {
		return airlineOne;
	}
	/**
	 * @param airlineOne the airlineOne to set
	 */
	public void setAirlineOne(String[] airlineOne) {
		this.airlineOne = airlineOne;
	}
	/**
	 * @return the airlineTwo
	 */
	public String[] getAirlineTwo() {
		return airlineTwo;
	}
	/**
	 * @param airlineTwo the airlineTwo to set
	 */
	public void setAirlineTwo(String[] airlineTwo) {
		this.airlineTwo = airlineTwo;
	}
	/**
	 * @return the airport
	 */
	public String[] getAirport() {
		return airport;
	}
	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String[] airport) {
		this.airport = airport;
	}
	/**
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the firstAirline
	 */
	public String getFirstAirline() {
		return firstAirline;
	}
	/**
	 * @param firstAirline the firstAirline to set
	 */
	public void setFirstAirline(String firstAirline) {
		this.firstAirline = firstAirline;
	}
	/**
	 * @return the polairport
	 */
	public String getPolAirport() {
		return polAirport;
	}
	/**
	 * @param polairport the polairport to set
	 */
	public void setPolAirport(String polairport) {
		this.polAirport = polairport;
	}
	/**
	 * @return the secondAirline
	 */
	public String getSecondAirline() {
		return secondAirline;
	}
	/**
	 * @param secondAirline the secondAirline to set
	 */
	public void setSecondAirline(String secondAirline) {
		this.secondAirline = secondAirline;
	}
	/**
	 * @return the popupFlag
	 */
	public String getPopupFlag() {
		return popupFlag;
	}
	/**
	 * @param popupFlag the popupFlag to set
	 */
	public void setPopupFlag(String popupFlag) {
		this.popupFlag = popupFlag;
	}
	
}
