/*
 * MissingUCMListForm.java Created on Jul 29, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;

/**
 * @author A-3459
 *
 */
public class MissingUCMListForm extends ScreenModel{
	private static final String BUNDLE = "UCMMissingResources";
    private static final String PRODUCT = "uld";
    private static final String SUBPRODUCT = "defaults";
    private static final String SCREENID = "uld.defaults.missingucmlist";
    private String fromDate;
    private String toDate;
    private String flightNumber;
    private String carrierCode;
    private String flightDate;
    private String origin;
    private String destination;
    private String ucmOut;
    private String ucmIn;
    private String fromDates[];
    private String toDates[];
    private String flightNumbers[];
    private String carrierCodes[];
    private String flightDates[];
    private String origins[];
    private String destinations[];
    private String ucmOutReceived[];
    private String ucmIntReceived[];
    private String bundle;
	
	private String lastPageNum = "0";
	private String displayPage = "1";
	
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
	 * @return the destinations
	 */
	public String[] getDestinations() {
		return destinations;
	}
	/**
	 * @param destinations the destinations to set
	 */
	public void setDestinations(String[] destinations) {
		this.destinations = destinations;
	}
	/**
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the flightDates
	 */
	public String[] getFlightDates() {
		return flightDates;
	}
	/**
	 * @param flightDates the flightDates to set
	 */
	public void setFlightDates(String[] flightDates) {
		this.flightDates = flightDates;
	}
	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return the flightNumbers
	 */
	public String[] getFlightNumbers() {
		return flightNumbers;
	}
	/**
	 * @param flightNumbers the flightNumbers to set
	 */
	public void setFlightNumbers(String[] flightNumbers) {
		this.flightNumbers = flightNumbers;
	}
	/**
	 * @return the fromDate
	 */
	@DateFieldId(id="MissingUCMsDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
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
	 * @return the fromDates
	 */
	public String[] getFromDates() {
		return fromDates;
	}
	/**
	 * @param fromDates the fromDates to set
	 */
	public void setFromDates(String[] fromDates) {
		this.fromDates = fromDates;
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
	 * @return the origins
	 */
	public String[] getOrigins() {
		return origins;
	}
	/**
	 * @param origins the origins to set
	 */
	public void setOrigins(String[] origins) {
		this.origins = origins;
	}
	/**
	 * @return the toDate
	 */
	@DateFieldId(id="MissingUCMsDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
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
	 * @return the toDates
	 */
	public String[] getToDates() {
		return toDates;
	}
	/**
	 * @param toDates the toDates to set
	 */
	public void setToDates(String[] toDates) {
		this.toDates = toDates;
	}
	
	/**
	 * @return the ucmIntReceived
	 */
	public String[] getUcmIntReceived() {
		return ucmIntReceived;
	}
	/**
	 * @param ucmIntReceived the ucmIntReceived to set
	 */
	public void setUcmIntReceived(String[] ucmIntReceived) {
		this.ucmIntReceived = ucmIntReceived;
	}
	
	/**
	 * @return the ucmOutReceived
	 */
	public String[] getUcmOutReceived() {
		return ucmOutReceived;
	}
	/**
	 * @param ucmOutReceived the ucmOutReceived to set
	 */
	public void setUcmOutReceived(String[] ucmOutReceived) {
		this.ucmOutReceived = ucmOutReceived;
	}

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the carrierCodes
	 */
	public String[] getCarrierCodes() {
		return carrierCodes;
	}

	/**
	 * @param carrierCodes the carrierCodes to set
	 */
	public void setCarrierCodes(String[] carrierCodes) {
		this.carrierCodes = carrierCodes;
	}

	/**
	 * @return the ucmIn
	 */
	public String getUcmIn() {
		return ucmIn;
	}

	/**
	 * @param ucmIn the ucmIn to set
	 */
	public void setUcmIn(String ucmIn) {
		this.ucmIn = ucmIn;
	}

	/**
	 * @return the ucmOut
	 */
	public String getUcmOut() {
		return ucmOut;
	}

	/**
	 * @param ucmOut the ucmOut to set
	 */
	public void setUcmOut(String ucmOut) {
		this.ucmOut = ucmOut;
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
}
