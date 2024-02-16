/*
 * ULDAuditEnquiryForm.java Created on Apr 03,2008
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
 * @author A-2667
 * 
 */
public class ULDAuditEnquiryForm extends ScreenModel {

	private static final String BUNDLE = "uldAuditEnquiry";

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.uldauditenquiry";

	private String uldNumber;

	private String uldSuffix;

	private String location;

	private String facilityType;

	private String uldAirport;

	private String defaultComboValue;

	private String locationName;

	private String reportingStationChild;

	private String txnFromDate;
	
	private String txnToDate;
	
	public String getReportingStationChild() {
		return reportingStationChild;
	}

	public void setReportingStationChild(String reportingStationChild) {
		this.reportingStationChild = reportingStationChild;
	}

	public String getDefaultComboValue() {
		return defaultComboValue;
	}

	public void setDefaultComboValue(String defaultComboValue) {
		this.defaultComboValue = defaultComboValue;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	

	/**
	 * @return Returns the uldAirport.
	 */
	public String getUldAirport() {
		return uldAirport;
	}

	/**
	 * @param uldAirport The uldAirport to set.
	 */
	public void setUldAirport(String uldAirport) {
		this.uldAirport = uldAirport;
	}

	/**
	 */
	public String getFacilityType() {
		return facilityType;
	}

	/**
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	/**
	 * 
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * 
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * 
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * 
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * 
	 * @return
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * 
	 * @param uldNumber
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * 
	 * @return
	 */
	public String getUldSuffix() {
		return uldSuffix;
	}

	/**
	 * 
	 * @param uldSuffix
	 */
	public void setUldSuffix(String uldSuffix) {
		this.uldSuffix = uldSuffix;
	}

	/**
	 * 
	 * @return
	 */
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return Returns the txnFromDate.
	 */
	public String getTxnFromDate() {
		return txnFromDate;
	}

	/**
	 * @param txnFromDate The txnFromDate to set.
	 */
	public void setTxnFromDate(String txnFromDate) {
		this.txnFromDate = txnFromDate;
	}

	/**
	 * @return Returns the txnToDate.
	 */
	public String getTxnToDate() {
		return txnToDate;
	}

	/**
	 * @param txnToDate The txnToDate to set.
	 */
	public void setTxnToDate(String txnToDate) {
		this.txnToDate = txnToDate;
	}

}
