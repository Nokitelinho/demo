/*
 * GenerateSCMForm.java Created on Aug 01, 2006
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
public class GenerateSCMForm extends ScreenModel {
    
	private static final String BUNDLE = "generatescmResources";
	
	private String bundle;    

	private static final String PRODUCT = "uld";
	
	private static final String SUBPRODUCT = "defaults";
	
	private static final String SCREENID = "uld.defaults.generatescm";
	
	
    private String pageURL="";
    private String scmAirline="";    
    private String scmAirport="";       
    private String scmStockCheckDate="";  
    private String scmStockCheckTime;
    private String airportDisable;
    private String[] systemstock;   
    private String[] extrauld; 
    private String[] selectedSysStock;  
    private String[] selectedExtraULD; 
    private String[] scmStatusFlags;
    private String missingFlag;
    private String listStatus;
    private boolean changeStatusFlag;
    private String[] operationFlag; 
	private String lastPageNum = "0";
	private String displayPage = "1";
    private String[] rowId;
    private String[] facilityType;
    private String[] stockCheckDate;
    private String[] locations;
    public String[] getStockCheckDate() {
		return stockCheckDate;
	}
	public void setStockCheckDate(String[] stockCheckDate) {
		this.stockCheckDate = stockCheckDate;
	}
    private String defaultComboValue;
    //added by A-2408 for SCM CR
    private String msgFlag;
    
    private String[] tempFacilityType;
    private String[] tempLocations;
    private String[] tempExtrauld; 
    private String[] tempScmStatusFlags;
    private String[] tempOperationFlag; 
    private String locationName;
    private int totalRecords;
    private String scmMessageSendingFlag;
	private String listedDynamicQuery;
   
    
    /**
	 * @return the scmMessageSendingFlag
	 */
	public String getScmMessageSendingFlag() {
		return scmMessageSendingFlag;
	}
	/**
	 * @param scmMessageSendingFlag the scmMessageSendingFlag to set
	 */
	public void setScmMessageSendingFlag(String scmMessageSendingFlag) {
		this.scmMessageSendingFlag = scmMessageSendingFlag;
	}
	//Added by nisha for QF1019 on 16Jun08
    private String remarks;
       
    private String facilityFilter;
    
    private String locationFilter;
    
    private String[] newuld; 
    private String[] newFacilityType;
    private String[] newLocations;
    private String[] newOperationFlag;
    
    //added by A-6344 for ICRD-55460 start
    private String uldStatus;
    private String uldNumberStock;
    private String listFromStock;
    private String fromFinalized;
    //added by A-6344 for ICRD-55460 end
    
    
    /**
	 * @return the newOperationFlag
	 */
	public String[] getNewOperationFlag() {
		return newOperationFlag;
	}
	/**
	 * @return the fromFinalized
	 */
	public String getFromFinalized() {
		return fromFinalized;
	}
	/**
	 * @param fromFinalized the fromFinalized to set
	 */
	public void setFromFinalized(String fromFinalized) {
		this.fromFinalized = fromFinalized;
	}
	/**
	 * @return the listFromStock
	 */
	public String getListFromStock() {
		return listFromStock;
	}
	/**
	 * @param listFromStock the listFromStock to set
	 */
	public void setListFromStock(String listFromStock) {
		this.listFromStock = listFromStock;
	}
	/**
	 * @return the uldStatus
	 */
	public String getUldStatus() {
		return uldStatus;
	}
	/**
	 * @param uldStatus the uldStatus to set
	 */
	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
	}
	/**
	 * @return the uldNumber
	 */
	public String getUldNumberStock() {
		return uldNumberStock;
	}
	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumberStock(String uldNumber) {
		this.uldNumberStock = uldNumber;
	}
	/**
	 * @param newOperationFlag the newOperationFlag to set
	 */
	public void setNewOperationFlag(String[] newOperationFlag) {
		this.newOperationFlag = newOperationFlag;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String[] getTempExtrauld() {
		return tempExtrauld;
	}
	public void setTempExtrauld(String[] tempExtrauld) {
		this.tempExtrauld = tempExtrauld;
	}
	public String[] getTempFacilityType() {
		return tempFacilityType;
	}
	public void setTempFacilityType(String[] tempFacilityType) {
		this.tempFacilityType = tempFacilityType;
	}
	public String[] getTempLocations() {
		return tempLocations;
	}
	public void setTempLocations(String[] tempLocations) {
		this.tempLocations = tempLocations;
	}
	public String[] getTempOperationFlag() {
		return tempOperationFlag;
	}
	public void setTempOperationFlag(String[] tempOperationFlag) {
		this.tempOperationFlag = tempOperationFlag;
	}
	public String[] getTempScmStatusFlags() {
		return tempScmStatusFlags;
	}
	public void setTempScmStatusFlags(String[] tempScmStatusFlags) {
		this.tempScmStatusFlags = tempScmStatusFlags;
	}
	public String getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	public String getLastPageNum() {
		return lastPageNum;
	}
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
/**
 * 
 * @return
 */
	public String getListStatus() {
		return listStatus;
	}
/**
 * 
 * @param listStatus
 */
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
/**
 * 
 * @return
 */
	public String getMissingFlag() {
		return missingFlag;
	}
/**
 * 
 * @param missingFlag
 */
	public void setMissingFlag(String missingFlag) {
		this.missingFlag = missingFlag;
	}

	/**
	 * @return Returns the scmAirline.
	 */
	public String getScmAirline() {
		return scmAirline;
	}

	/**
	 * @param scmAirline The scmAirline to set.
	 */
	public void setScmAirline(String scmAirline) {
		this.scmAirline = scmAirline;
	}

	/**
	 * @return Returns the scmAirport.
	 */
	public String getScmAirport() {
		return scmAirport;
	}

	/**
	 * @param scmAirport The scmAirport to set.
	 */
	public void setScmAirport(String scmAirport) {
		this.scmAirport = scmAirport;
	}

	/**
	 * @return Returns the scmStockCheckDate.
	 */
	public String getScmStockCheckDate() {
		return scmStockCheckDate;
	}

	/**
	 * @param scmStockCheckDate The scmStockCheckDate to set.
	 */
	public void setScmStockCheckDate(String scmStockCheckDate) {
		this.scmStockCheckDate = scmStockCheckDate;
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
	 * @return Returns the pageURL.
	 */
	public String getPageURL() {
		return pageURL;
	}

	/**
	 * @param pageURL The pageURL to set.
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	/**
	 * @return Returns the extrauld.
	 */
	public String[] getExtrauld() {
		return extrauld;
	}

	/**
	 * @param extrauld The extrauld to set.
	 */
	public void setExtrauld(String[] extrauld) {
		this.extrauld = extrauld;
	}



	/**
	 * @return Returns the systemstock.
	 */
	public String[] getSystemstock() {
		return systemstock;
	}

	/**
	 * @param systemstock The systemstock to set.
	 */
	public void setSystemstock(String[] systemstock) {
		this.systemstock = systemstock;
	}

	/**
	 * @return Returns the selectedExtraULD.
	 */
	public String[] getSelectedExtraULD() {
		return selectedExtraULD;
	}

	/**
	 * @param selectedExtraULD The selectedExtraULD to set.
	 */
	public void setSelectedExtraULD(String[] selectedExtraULD) {
		this.selectedExtraULD = selectedExtraULD;
	}

	/**
	 * @return Returns the selectedSysStock.
	 */
	public String[] getSelectedSysStock() {
		return selectedSysStock;
	}

	/**
	 * @param selectedSysStock The selectedSysStock to set.
	 */
	public void setSelectedSysStock(String[] selectedSysStock) {
		this.selectedSysStock = selectedSysStock;
	}
/**
 * 
 * @return
 */
	public String getScmStockCheckTime() {
		return scmStockCheckTime;
	}
/**
 * 
 * @param scmStockCheckTime
 */
	public void setScmStockCheckTime(String scmStockCheckTime) {
		this.scmStockCheckTime = scmStockCheckTime;
	}
/**
 * 
 * @return
 */
	public String[] getScmStatusFlags() {
		return scmStatusFlags;
	}
/**
 * 
 * @param scmStatusFlags
 */
	public void setScmStatusFlags(String[] scmStatusFlags) {
		this.scmStatusFlags = scmStatusFlags;
	}

	/**
	 * @return Returns the airportDisable.
	 */
	public String getAirportDisable() {
		return airportDisable;
	}

	/**
	 * @param airportDisable The airportDisable to set.
	 */
	public void setAirportDisable(String airportDisable) {
		this.airportDisable = airportDisable;
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
	public String[] getRowId() {
		return rowId;
	}
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
	/**
	 * @return Returns the msgFlag.
	 */
	public String getMsgFlag() {
		return msgFlag;
	}
	/**
	 * @param msgFlag The msgFlag to set.
	 */
	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}

	public String getDefaultComboValue() {
		return defaultComboValue;
	}
	public void setDefaultComboValue(String defaultComboValue) {
		this.defaultComboValue = defaultComboValue;
	}

	public String[] getFacilityType() {
		return facilityType;
	}
	public void setFacilityType(String[] facilityType) {
		this.facilityType = facilityType;
	}
	public String[] getLocations() {
		return locations;
	}
	public void setLocations(String[] locations) {
		this.locations = locations;
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
	 * @return the facilityFilter
	 */
	public String getFacilityFilter() {
		return facilityFilter;
	}
	/**
	 * @param facilityFilter the facilityFilter to set
	 */
	public void setFacilityFilter(String facilityFilter) {
		this.facilityFilter = facilityFilter;
	}
	/**
	 * @return the locationFilter
	 */
	public String getLocationFilter() {
		return locationFilter;
	}
	/**
	 * @param locationFilter the locationFilter to set
	 */
	public void setLocationFilter(String locationFilter) {
		this.locationFilter = locationFilter;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public boolean isChangeStatusFlag() {
		return changeStatusFlag;
	}
	public void setChangeStatusFlag(boolean changeStatusFlag) {
		this.changeStatusFlag = changeStatusFlag;
	}
	/**
	 * @return the newuld
	 */
	public String[] getNewuld() {
		return newuld;
	}
	/**
	 * @param newuld the newuld to set
	 */
	public void setNewuld(String[] newuld) {
		this.newuld = newuld;
	}
	/**
	 * @return the newFacilityType
	 */
	public String[] getNewFacilityType() {
		return newFacilityType;
	}
	/**
	 * @param newFacilityType the newFacilityType to set
	 */
	public void setNewFacilityType(String[] newFacilityType) {
		this.newFacilityType = newFacilityType;
	}
	/**
	 * @return the newLocations
	 */
	public String[] getNewLocations() {
		return newLocations;
	}
	/**
	 * @param newLocations the newLocations to set
	 */
	public void setNewLocations(String[] newLocations) {
		this.newLocations = newLocations;
	}
	/**
	 * @return the listedDynamicQuery
	 */
	public String getListedDynamicQuery() {
		return listedDynamicQuery;
	}
	/**
	 * @param listedDynamicQuery the listedDynamicQuery to set
	 */
	public void setListedDynamicQuery(String listedDynamicQuery) {
		this.listedDynamicQuery = listedDynamicQuery;
	}

  
    
 }
