/*
 * SegmentExceptionsForm.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * 
 * @author A-3429
 * 
 */

public class SegmentExceptionsForm extends ScreenModel {

	private static final String BUNDLE = "uldPoolOwnersResources";

	private String bundle;

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.segmentexceptions";

	private String[] origin;

	private String[] destination;

	private String selectedRow;
	
	private String selectedRows;
	
	private String airlineOne;
	
	private String airlineTwo;
	
	private String airport;
	
	private String remarks; 

	private String[] operationFlag;

	private String[] hiddenOperationFlag;

	
	private String errorFlag;
	/**
	 * @return the errorFlag
	 */
	public String getErrorFlag() {
		return errorFlag;
	}

	/**
	 * @param errorFlag the errorFlag to set
	 */
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
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
	 * Method to return the id the screen is associated with
	 * 
	 * @return String
	 */
	public String getScreenId() {
		return SCREENID;
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
	 * @return the destination
	 */
	public String[] getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(String[] destination) {
		this.destination = destination;
	}

	/**
	 * @return the hiddenOperationFlag
	 */
	public String[] getHiddenOperationFlag() {
		return hiddenOperationFlag;
	}

	/**
	 * @param hiddenOperationFlag
	 *            the hiddenOperationFlag to set
	 */
	public void setHiddenOperationFlag(String[] hiddenOperationFlag) {
		this.hiddenOperationFlag = hiddenOperationFlag;
	}

	/**
	 * @return the operationFlag
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            the operationFlag to set
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return the origin
	 */
	public String[] getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(String[] origin) {
		this.origin = origin;
	}

	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow
	 *            the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return the selectedRows
	 */
	public String getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows the selectedRows to set
	 */
	public void setSelectedRows(String selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * @return the airlineOne
	 */
	public String getAirlineOne() {
		return airlineOne;
	}

	/**
	 * @param airlineOne the airlineOne to set
	 */
	public void setAirlineOne(String airlineOne) {
		this.airlineOne = airlineOne;
	}

	/**
	 * @return the airlineTwo
	 */
	public String getAirlineTwo() {
		return airlineTwo;
	}

	/**
	 * @param airlineTwo the airlineTwo to set
	 */
	public void setAirlineTwo(String airlineTwo) {
		this.airlineTwo = airlineTwo;
	}

	/**
	 * @return the airport
	 */
	public String getAirport() {
		return airport;
	}

	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
