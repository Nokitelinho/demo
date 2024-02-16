/*
 * AirportFacilityMasterForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2052
 *
 */
public class AirportFacilityMasterForm extends ScreenModel {


	private static final String BUNDLE = "airportfacilitymaster";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.airportfacilitymaster";

	private String bundle;
	private String airportCode;
	private String facilityType;
	// added by a-3278 for QF1006 on 04Aug08
	private String content[];
	private String contentVal[];
	// a-3278 ends
	private String[] facilityCode; 
	private String[] description;
	
	private String beforeSave="";
	private String[] selectedRows;	
	private String afterList="";	
	private String[] rowId;
	private String[] defaultFlag;
	private String[] operationFlag;
	private String[] sequenceNumber;
	private String chkBoxFlag;
	private String wareHouseFlag;
	private String screenName;
	private String selectedRowsLOV;
	private String selectedRow_Type;		
	
	private String facility[];	
	private String whsFacilityCode[];
	private String[] facilityCodeVal; 	
	
	/**
	 * @return the selectedRow_Type
	 */
	public String getSelectedRow_Type() {
		return selectedRow_Type;
	}

	/**
	 * @param selectedRow_Type the selectedRow_Type to set
	 */
	public void setSelectedRow_Type(String selectedRow_Type) {
		this.selectedRow_Type = selectedRow_Type;
	}

	/**
	 * @return the selectedRowsLOV
	 */
	public String getSelectedRowsLOV() {
		return selectedRowsLOV;
	}

	/**
	 * @param selectedRowsLOV the selectedRowsLOV to set
	 */
	public void setSelectedRowsLOV(String selectedRowsLOV) {
		this.selectedRowsLOV = selectedRowsLOV;
	}

	/**
	 * @return Returns the wareHouseFlag.
	 */
	public String getWareHouseFlag() {
		return wareHouseFlag;
	}

	/**
	 * @param wareHouseFlag The wareHouseFlag to set.
	 */
	public void setWareHouseFlag(String wareHouseFlag) {
		this.wareHouseFlag = wareHouseFlag;
	}

	/**
	 * @return Returns the defaultFlag.
	 */
	public String[] getDefaultFlag() {
		return defaultFlag;
	}

	/**
	 * @param defaultFlag The defaultFlag to set.
	 */
	public void setDefaultFlag(String[] defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return Returns the afterList.
	 */
	public String getAfterList() {
		return afterList;
	}

	/**
	 * @param afterList The afterList to set.
	 */
	public void setAfterList(String afterList) {
		this.afterList = afterList;
	}

	/**
	 * @return Returns the beforeSave.
	 */
	public String getBeforeSave() {
		return beforeSave;
	}

	/**
	 * @param beforeSave The beforeSave to set.
	 */
	public void setBeforeSave(String beforeSave) {
		this.beforeSave = beforeSave;
	}

	/**
	 * @return Returns the bUNDLE.
	 */
	public static String getBUNDLE() {
		return BUNDLE;
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
 * 
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
	 * @return Returns the description.
	 */
	public String[] getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String[] description) {
		this.description = description;
	}

	/**
	 * @return Returns the facilityCode.
	 */
	public String[] getFacilityCode() {
		return facilityCode;
	}

	/**
	 * @param facilityCode The facilityCode to set.
	 */
	public void setFacilityCode(String[] facilityCode) {
		this.facilityCode = facilityCode;
	}

	/**
	 * @return Returns the facilityType.
	 */
	public String getFacilityType() {
		return facilityType;
	}

	/**
	 * @param facilityType The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	/**
	 * @return Returns the chkBoxFlag.
	 */
	public String getChkBoxFlag() {
		return chkBoxFlag;
	}

	/**
	 * @param chkBoxFlag The chkBoxFlag to set.
	 */
	public void setChkBoxFlag(String chkBoxFlag) {
		this.chkBoxFlag = chkBoxFlag;
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
	 * 
	 * @return
	 */
	public String[] getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * 
	 * @param sequenceNumber
	 */
	public void setSequenceNumber(String[] sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	/**
	 * @return the content
	 */
	public String[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String[] content) {
		this.content = content;
	}

	/**
	 * @return the facility
	 */
	public String[] getFacility() {
		return facility;
	}

	/**
	 * @param facility the facility to set
	 */
	public void setFacility(String[] facility) {
		this.facility = facility;
	}	

	/**
	 * @return the whsFacilityCode
	 */
	public String[] getWhsFacilityCode() {
		return whsFacilityCode;
	}

	/**
	 * @param whsFacilityCode the whsFacilityCode to set
	 */
	public void setWhsFacilityCode(String[] whsFacilityCode) {
		this.whsFacilityCode = whsFacilityCode;
	}

	/**
	 * @return the facilityCodeVal
	 */
	public String[] getFacilityCodeVal() {
		return facilityCodeVal;
	}

	/**
	 * @param facilityCodeVal the facilityCodeVal to set
	 */
	public void setFacilityCodeVal(String[] facilityCodeVal) {
		this.facilityCodeVal = facilityCodeVal;
	}

	/**
	 * @return the contentVal
	 */
	public String[] getContentVal() {
		return contentVal;
	}

	/**
	 * @param contentVal the contentVal to set
	 */
	public void setContentVal(String[] contentVal) {
		this.contentVal = contentVal;
	}
	
	
}
