/*
 * ListAccessoriesStockForm.java Created on Aug 1, 2005
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
public class ListAccessoriesStockForm extends ScreenModel {
    
	private static final String BUNDLE = "listaccessoriesstockResources";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.stock.listaccessoriesstock";
	private String accessoryCode;
	private String airlineCode;
	private String station;
	private String accessoryDescription;
	private int available;
	private int loaned;
	private String lastUpdatedDate;
	private String remarks;
	private boolean selectAll;
	private String[] select;
	private String[] accCodesSelected;
	private String[] airCodesSelected;
	private String[] stationsSelected;
	private String displayPage = "1";
	private String  lastPageNum = "0";
	private int selectFlag;	
	private int[] loanedAcc;
	private String bundle;   
	private String listDisableStatus="";
	
	// Added by A-5183 for < ICRD-22824 > Starts	
	public static final String PAGINATION_MODE_FROM_LIST = "LIST";
	public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "NAVIGATION";
	private String navigationMode;	
	public String getNavigationMode() {
		return navigationMode;
	}
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}		
	// Added by A-5183 for < ICRD-22824> Ends

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
     * 
     * @return String
     */
	public String getAccessoryCode() {
		return accessoryCode;
	}
	/**
	 * 
	 * @param accessoryCode
	 */
	public void setAccessoryCode(String accessoryCode) {
		this.accessoryCode = accessoryCode;
	}
    /**
     * 
     * @return String
     */
	public String getAccessoryDescription() {
		return accessoryDescription;
	}
	/**
	 * 
	 * @param accessoryDescription
	 */
	public void setAccessoryDescription(String accessoryDescription) {
		this.accessoryDescription = accessoryDescription;
	}
	/**
	 * 
	 * @return String
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
    /**
     * 
     * @param airlineCode
     */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * 
	 * @return int
	 */
	public int getAvailable() {
		return available;
	}
	/**
	 * 
	 * @param available
	 */
	public void setAvailable(int available) {
		this.available = available;
	}
	/**
	 * 
	 * @return String
	 */
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	/**
	 * 
	 * @param lastUpdatedDate
	 */
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	/**
	 * 
	 * @return int
	 */
	public int getLoaned() {
		return loaned;
	}
	/**
	 * 
	 * @param loaned
	 */
	public void setLoaned(int loaned) {
		this.loaned = loaned;
	}
	/**
	 * 
	 * @return String
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 
	 * @return String
	 */
	public String getStation() {
		return station;
	}
	/**
	 * 
	 * @param station
	 */
	public void setStation(String station) {
		this.station = station;
	}
	/**
	 * @return String
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
     * 
     * @return  String[]
     */
	public String[] getSelect() {
		return select;
	}
	/**
	 * 
	 * @param select
	 */
	public void setSelect(String[] select) {
		this.select = select;
	}
	/**
	 * 
	 * @return String
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * 
	 * @param displayPage
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * 
	 * @return String
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * 
	 * @param lastPageNum
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 * 
	 * @return boolean
	 */
	public boolean getSelectAll() {
		return selectAll;
	}
	/**
	 * 
	 * @param selectAll
	 */
	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}
	/**
	 * 
	 * @return int
	 */
	public int getSelectFlag() {
		return selectFlag;
	}
	/**
	 * @param selectFlag
	 */
	public void setSelectFlag(int selectFlag) {
		this.selectFlag = selectFlag;
	}
	
	/**
	 * 
	 * @return String[]
	 */
	public String[] getAccCodesSelected() {
		return accCodesSelected;
	}
	/**
	 * 
	 * @param accCodesSelected
	 */
	public void setAccCodesSelected(String[] accCodesSelected) {
		this.accCodesSelected = accCodesSelected;
	}
	/**
	 * 
	 * @return String[]
	 */
	public String[] getAirCodesSelected() {
		return airCodesSelected;
	}
	/**
	 * 
	 * @param airCodesSelected
	 */
	public void setAirCodesSelected(String[] airCodesSelected) {
		this.airCodesSelected = airCodesSelected;
	}
	/**
	 * 
	 * @return String[]
	 */
	public String[] getStationsSelected() {
		return stationsSelected;
	}
	/**
	 * 
	 * @param stationsSelected
	 */
	public void setStationsSelected(String[] stationsSelected) {
		this.stationsSelected = stationsSelected;
	}

	/**
	 * 
	 * @return int[]
	 */
	public int[] getLoanedAcc() {
		return loanedAcc;
	}
	/**
	 * 
	 * @param loanedAcc
	 */
	public void setLoanedAcc(int[] loanedAcc) {
		this.loanedAcc = loanedAcc;
	}

	/**
	 * @return Returns the listDisableStatus.
	 */
	public String getListDisableStatus() {
		return listDisableStatus;
	}

	/**
	 * @param listDisableStatus The listDisableStatus to set.
	 */
	public void setListDisableStatus(String listDisableStatus) {
		this.listDisableStatus = listDisableStatus;
	}

	    
 }
