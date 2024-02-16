/*
 * SCMValidationForm.java Created on Jan 3, 2009
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
 * @author A-3459
 * 
 */
public class SCMValidationForm extends ScreenModel{
	private static final String BUNDLE = "SCMValidationResources";
    private static final String PRODUCT = "uld";
    private static final String SUBPRODUCT = "defaults";
    private static final String SCREENID = "uld.defaults.scmvalidation";
    
    private String uldTypeCode;
    private String airport;
    private String facilityType;
    private String location;
    private String scmStatus;
    private String origin;
    private String destination;
    private String[] uldNumber; 
    private String[] facilityTypes; 
    private String[] locations; 
    private String[] sighted; 
    private String[] scmFlag; 
    private String[] scmDate; 
    private String total; 
    private String notSighted; 
    private String missing;
    private String bundle;
    private String statusFlag;
    private String lastPageNum = "0";
	private String displayPage = "1";
	
	
	/**
	 * @return the bundle
	 */
	public String getBundle() {
		return BUNDLE;
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
	 * @param bundle the bundle to set
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
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
	 * @return the facilityType
	 */
	public String getFacilityType() {
		return facilityType;
	}
	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return the facilityTypes
	 */
	public String[] getFacilityTypes() {
		return facilityTypes;
	}
	/**
	 * @param facilityTypes the facilityTypes to set
	 */
	public void setFacilityTypes(String[] facilityTypes) {
		this.facilityTypes = facilityTypes;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the locations
	 */
	public String[] getLocations() {
		return locations;
	}
	/**
	 * @param locations the locations to set
	 */
	public void setLocations(String[] locations) {
		this.locations = locations;
	}
	/**
	 * @return the missing
	 */
	public String getMissing() {
		return missing;
	}
	/**
	 * @param missing the missing to set
	 */
	public void setMissing(String missing) {
		this.missing = missing;
	}
	/**
	 * @return the notSighted
	 */
	public String getNotSighted() {
		return notSighted;
	}
	/**
	 * @param notSighted the notSighted to set
	 */
	public void setNotSighted(String notSighted) {
		this.notSighted = notSighted;
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
	 * @return the scmDate
	 */
	public String[] getScmDate() {
		return scmDate;
	}
	/**
	 * @param scmDate the scmDate to set
	 */
	public void setScmDate(String[] scmDate) {
		this.scmDate = scmDate;
	}
	/**
	 * @return the scmFlag
	 */
	public String[] getScmFlag() {
		return scmFlag;
	}
	/**
	 * @param scmFlag the scmFlag to set
	 */
	public void setScmFlag(String[] scmFlag) {
		this.scmFlag = scmFlag;
	}
	/**
	 * @return the scmStatus
	 */
	public String getScmStatus() {
		return scmStatus;
	}
	/**
	 * @param scmStatus the scmStatus to set
	 */
	public void setScmStatus(String scmStatus) {
		this.scmStatus = scmStatus;
	}
	/**
	 * @return the sighted
	 */
	public String[] getSighted() {
		return sighted;
	}
	/**
	 * @param sighted the sighted to set
	 */
	public void setSighted(String[] sighted) {
		this.sighted = sighted;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the uldNumber
	 */
	public String[] getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String[] uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return the uldTypeCode
	 */
	public String getUldTypeCode() {
		return uldTypeCode;
	}
	/**
	 * @param uldTypeCode the uldTypeCode to set
	 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
	}
	
	/**
	 * @return the statusFlag
	 */
	public String getStatusFlag() {
		return statusFlag;
	}
	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	} 

}
