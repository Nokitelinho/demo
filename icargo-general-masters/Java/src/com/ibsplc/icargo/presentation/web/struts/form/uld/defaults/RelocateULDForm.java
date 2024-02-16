/*
 * RelocateULDForm.java Created on Jun 6, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-2122
 *
 */
public class RelocateULDForm extends ScreenModel {
    
	private static final String BUNDLE = "relocateuldResources";
	
	private String bundle;    
	
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.relocateuld";

	private String fromLocation;
	
	private String uldNumber;
	
	private String currentStation;
    
    private String locationType;
    
    private String toLocation;
    
    private String remarks;
    
    private String checkFlag;
    
    private String saveStatusPopup;
    
    private String fromScreen;
    
 
	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	public String getSaveStatusPopup() {
		return saveStatusPopup;
	}

	public void setSaveStatusPopup(String saveStatusPopup) {
		this.saveStatusPopup = saveStatusPopup;
	}

	/**
	 * @return Returns the fromLocation.
	 */
	public String getFromLocation() {
		return fromLocation;
	}

	/**
	 * @param fromLocation The fromLocation to set.
	 */
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	
	/**
	 * @return Returns the locationType.
	 */
	public String getLocationType() {
		return locationType;
	}

	/**
	 * @param locationType The locationType to set.
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the toLocation.
	 */
	public String getToLocation() {
		return toLocation;
	}

	/**
	 * @param toLocation The toLocation to set.
	 */
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
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
	 * @return Returns the currentStation.
	 */
	public String getCurrentStation() {
		return currentStation;
	}

	/**
	 * @param currentStation The currentStation to set.
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the checkFlag.
	 */
	public String getCheckFlag() {
		return checkFlag;
	}

	/**
	 * @param checkFlag The checkFlag to set.
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	
    
 }
