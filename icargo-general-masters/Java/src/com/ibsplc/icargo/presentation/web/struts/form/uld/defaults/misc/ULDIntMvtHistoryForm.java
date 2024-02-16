/*
 * ULDIntMvtHistoryForm.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2412
 *
 */
public class ULDIntMvtHistoryForm extends ScreenModel{
	private static final String BUNDLE = "uldIntMvtResources";

	private String bundle;

	
	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.misc.uldintmvthistory";

	private String uldNumber;

	private String fromDate;

	private String toDate;

	private String ownerCode;

	private String ownerStation;

	private String currentLocation;

	private String[] contents;

	private String[] airport;

	private String[] fromStation;

	private String[] toStation;

	private String[] movementDate;

	private String[] remarks;	
		
	 private String displayPage = "1";
	 	
     private String  lastPageNum = "0";
     
     //added by a-3045 for CR AirNZ269 starts
     private String afterList;
     //added by a-3045 for CR AirNZ269 ends
	/**
	 * @return the afterList
	 */
	public String getAfterList() {
		return afterList;
	}

	/**
	 * @param afterList the afterList to set
	 */
	public void setAfterList(String afterList) {
		this.afterList = afterList;
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

	public String[] getAirport() {
		return airport;
	}

	public void setAirport(String[] airport) {
		this.airport = airport;
	}

	public String getBundle() {
		return BUNDLE;
	}

	public String[] getContents() {
		return contents;
	}

	public void setContents(String[] contents) {
		this.contents = contents;
	}

	/**
	 * @return the currentLocation
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation the currentLocation to set
	 */
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String[] getFromStation() {
		return fromStation;
	}

	public void setFromStation(String[] fromStation) {
		this.fromStation = fromStation;
	}

	public String[] getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(String[] movementDate) {
		this.movementDate = movementDate;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getOwnerStation() {
		return ownerStation;
	}

	public void setOwnerStation(String ownerStation) {
		this.ownerStation = ownerStation;
	}

	public String getProduct() {
		return PRODUCT;
	}

	public String[] getRemarks() {
		return remarks;
	}

	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	public String getScreenId() {
		return SCREENID;
	}

	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String[] getToStation() {
		return toStation;
	}

	public void setToStation(String[] toStation) {
		this.toStation = toStation;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	
}
