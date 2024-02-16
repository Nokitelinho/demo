/*
 * MaintainAccessoriesStockForm.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1347
 *
 */
public class MaintainAccessoriesStockForm extends ScreenModel {

	private static final String BUNDLE = "maintainaccessoriesstockResources";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.maintainaccessoriesstock";


	private String bundle;

	private String accessoryCode;

	private String airlineCode;
	
	private int airlineIdentifier;

	private String stationCode;

	private String accessoryDescription;

	private int available;

	private int loaned;

	private String lastUpdateTime;

	private String remarks;
	
	private String statusFlag;
	
	private String detailsFlag;
	
	private String modeFlag;
	
	private String lovFlag;
	
	private String[] select;
	
	private String displayPage = "1";
    
    private String currentPage = "1";
    
    private String lastPageNum = "0";
    
    private String totalRecords = "0";
    
    private String accCodesSelected;
    
    private String airCodesSelected;
    
    private String stationsSelected;
   
    private String accessoryDisableStatus="";
    
    //added by a-3045 for CR AirNZ450 starts
	private int minimumQuantity;
    //added by a-3045 for CR AirNZ450 starts


	/**
	 * @return Returns the accessoryDisableStatus.
	 */
	public String getAccessoryDisableStatus() {
		return accessoryDisableStatus;
	}
	/**
	 * @param accessoryDisableStatus The accessoryDisableStatus to set.
	 */
	public void setAccessoryDisableStatus(String accessoryDisableStatus) {
		this.accessoryDisableStatus = accessoryDisableStatus;
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
	 * Method to return the accessoryCode the screen is associated with
	 *
     * @return String
     */

	public  String getAccessoryCode() {
		return accessoryCode;
	}


	/**
	 * @param accessoryCode The accessoryCode to set.
	 */
	public void setAccessoryCode(String accessoryCode) {
		this.accessoryCode = accessoryCode;
	}


	/**
	 * Method to return the airlineCode the screen is associated with
	 *
	 * @return String
	 */

	public  String getAirlineCode() {
		return airlineCode;
	}


    /**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
			this.airlineCode = airlineCode;
	}
	
	
	/**
	 * Method to return the airlineIdentifier the screen is associated with
	 *
	 * @return int
	 */

	public  int getAirlineIdentifier() {
		return airlineIdentifier;
	}


    /**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
			this.airlineIdentifier = airlineIdentifier;
	}


	/**
	 * Method to return the stationCode the screen is associated with
	 *
	 * @return String
	 */

	public  String getStationCode() {
		return stationCode;
	}


	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}


	/**
	 * Method to return the accessoryDescription the screen is associated with
	 *
	 * @return String
	 */

	public  String getAccessoryDescription() {
	    return accessoryDescription;
	}


	/**
	 * @param accessoryDescription The accessoryDescription to set.
	 */
	public void setAccessoryDescription(String accessoryDescription) {
		this.accessoryDescription = accessoryDescription;
	}

	/**
	 * Method to return the available the screen is associated with
	 *
	 * @return int
	 */

	public  int getAvailable() {
	    return available;
	}


	/**
	 * @param available The available to set.
	 */
	public void setAvailable(int available) {
		this.available = available;
	}


	/**
	 * Method to return  the loaned  the screen is associated with
	 *
	 * @return int
	 */

	public  int getLoaned() {
	    return loaned;
	}


	/**
	 * @param loaned The loaned to set.
	 */
	public void setLoaned(int loaned) {
		this.loaned = loaned;
	}


	/**
	 * Method to return  the lastUpdateTime  the screen is associated with
	 *
	 * @return LocalDate
	 */

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}


	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}



	/**
	 * Method to return  the remarks  the screen is associated with
	 *
	 * @return String
	 */

	public  String getRemarks() {
	    return remarks;
	}


	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	/**
	 * Method to return  the statusFlag  the screen is associated with
	 *
	 * @return String
	 */

	public  String getStatusFlag() {
	    return statusFlag;
	}


	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	
	
	/**
	 * Method to return  the detailsFlag  the screen is associated with
	 *
	 * @return String
	 */

	public  String getDetailsFlag() {
	    return detailsFlag;
	}


	/**
	 * @param detailsFlag The detailsFlag to set.
	 */
	public void setDetailsFlag(String detailsFlag) {
		this.detailsFlag = detailsFlag;
	}
	
	
	/**
	 * Method to return  the modeFlag the screen is associated with
	 *
	 * @return String
	 */

	public  String getModeFlag() {
	    return modeFlag;
	}


	/**
	 * @param modeFlag The modeFlag to set.
	 */
	public void setModeFlag(String modeFlag) {
		this.modeFlag = modeFlag;
	}
	/**
	 * @return Returns the select.
	 */
	public String[] getSelect() {
		return select;
	}

	/**
	 * @param select The select to set.
	 */
	public void setSelect(String[] select) {
		this.select = select;
	}
	
	
	/**
	 * @return Returns the currentPage.
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * 
	 * @return accCodesSelected
	 */
	public String getAccCodesSelected() {
		return accCodesSelected;
	}
	
	/**
	 * 
	 * @param accCodesSelected
	 */
	public void setAccCodesSelected(String accCodesSelected) {
		this.accCodesSelected = accCodesSelected;
	}
	/**
	 * 
	 * @return String
	 */
	public String getAirCodesSelected() {
		return airCodesSelected;
	}
	/**
	 * 
	 * @param airCodesSelected
	 */
	public void setAirCodesSelected(String airCodesSelected) {
		this.airCodesSelected = airCodesSelected;
	}
	/**
	 * 
	 * @return String
	 */
	public String getStationsSelected() {
		return stationsSelected;
	}
	/**
	 * 
	 * @param stationsSelected
	 */
	public void setStationsSelected(String stationsSelected) {
		this.stationsSelected = stationsSelected;
	}
	/**
	 * 
	 * @return lovFlag
	 */
	public String getLovFlag() {
		return lovFlag;
	}
	/**
	 * 
	 * @param lovFlag
	 */
	public void setLovFlag(String lovFlag) {
		this.lovFlag = lovFlag;
	}
	/**
	 * @return the minimumQuantity
	 */
	public int getMinimumQuantity() {
		return minimumQuantity;
	}
	/**
	 * @param minimumQuantity the minimumQuantity to set
	 */
	public void setMinimumQuantity(int minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}






 }
