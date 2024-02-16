/*
 * UCMFlightExceptionListForm.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-1862
 *
 */
public class UCMFlightExceptionListForm extends ScreenModel {
    
	private static final String BUNDLE = "ucmflightexceptionlistResources";

	private String bundle;    
	
	private static final String PRODUCT = "uld";
	
	private static final String SUBPRODUCT = "defaults";
	
	private static final String SCREENID = "uld.defaults.ucmflightexceptionlist";
	
	private String airportCode;
	private String[] selectedRows; 
	private String[] flightCarrier;
	private String[] flightNumber;
	private String[] flightDate;
	private String date;
	private String actionStatus;
	private String duplicateFlightStatus;
	private String listStatus;
	/**
	 * @return Returns the duplicateFlightStatus.
	 */
	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}

	/**
	 * @param duplicateFlightStatus The duplicateFlightStatus to set.
	 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	/**
	 * @return Returns the actionStatus.
	 */
	public String getActionStatus() {
		return actionStatus;
	}

	/**
	 * @param actionStatus The actionStatus to set.
	 */
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the flightCarrier.
	 */
	public String[] getFlightCarrier() {
		return flightCarrier;
	}

	/**
	 * @param flightCarrier The flightCarrier to set.
	 */
	public void setFlightCarrier(String[] flightCarrier) {
		this.flightCarrier = flightCarrier;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String[] getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String[] flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String[] getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the selectedRows.
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows The selectedRows to set.
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
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
	
	/**
	 * Method to return the id the screen is associated with
	 * 
	 * @return String
	 */
	public String getScreenId() {
	    return SCREENID;
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
	 * @return Returns the listStatus.
	 */
	public String getListStatus() {
		return listStatus;
	}

	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}

  
    
 }
