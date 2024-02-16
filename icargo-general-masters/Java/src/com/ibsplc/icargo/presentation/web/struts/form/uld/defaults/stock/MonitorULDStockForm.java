/*
 * MonitorULDStockForm.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1347
 *
 */
public class MonitorULDStockForm extends ScreenModel {
    
	private static final String BUNDLE = "monitoruldstock";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.monitoruldstock";

	
	private String bundle;    
/**
 * 
 */
	private String airlineCode;
	/**
	 * 
	 */
	private String stationCode;
	/**
	 * 
	 */
	private String uldGroupCode;
	/**
	 * 
	 */
	private String uldTypeCode;
	/**
	 * 
	 */
	private String closeStatus;
    /**
     * 
     */
	private String selectedRows[];
	/**
     * 
     */
	private String selectedChildRows[];
    /**
     * 
     */
	private String available[];
    /**
     * 
     */
	private String total[];    
    /**
     * 
     */
	private String maxQty[];
    /**
     * 
     */
	private String minQty[];
    /**
     * 
     */
	private String airlineCodes[];
    /**
     * 
     */
	private String stationCodes[];
    
    private String airlineBaseCurrency;
    /**
     * 
     */
    private String uldNature;
    
    private boolean viewByNatureFlag;
    
    private String displayPage = "1";
    
    private String lastPageNum = "0";
    /**
     * 
     */
    private String stockDisableStatus="";
    
    //added by nisha for CRAirNZ315
    private String agentCode;
    
    //added by a-3045 for CR QF1012 starts
    private String levelType;
    private String levelValue;
    private String comboFlag;
    //added by a-3045 for CR QF1012 ends  
    
    // added by a-3278 for ULD756
    private String isPreview;
    private String loginStation;
    
 // added by a-3313 for ICRD-2796
    private String screenStatus;
    /**
	 * Added as part of ICRD-334152
	 */
	private String ownerAirline;
    
    private String  sort;
    
    private String navigationMode;//a-5505 for bug ICRD-123103 
    
    private String fromScreen; //Added by A-8399 as part of IASCB-13164 
    public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return Returns the airlineBaseCurrency.
	 */
	public String getAirlineBaseCurrency() {
		return airlineBaseCurrency;
	}

	/**
	 * @param airlineBaseCurrency The airlineBaseCurrency to set.
	 */
	public void setAirlineBaseCurrency(String airlineBaseCurrency) {
		this.airlineBaseCurrency = airlineBaseCurrency;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the closeStatus.
	 */
	public String getCloseStatus() {
		return closeStatus;
	}

	/**
	 * @param closeStatus The closeStatus to set.
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
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
	 * @return Returns the uldGroupCode.
	 */
	public String getUldGroupCode() {
		return uldGroupCode;
	}

	/**
	 * @param uldGroupCode The uldGroupCode to set.
	 */
	public void setUldGroupCode(String uldGroupCode) {
		this.uldGroupCode = uldGroupCode;
	}

	/**
	 * @return Returns the uldTypeCode.
	 */
	public String getUldTypeCode() {
		return uldTypeCode;
	}

	/**
	 * @param uldTypeCode The uldTypeCode to set.
	 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
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
	 * @return Returns the stationCode.
	 */
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
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
	 * @return Returns the airlineCodes.
	 */
	public String[] getAirlineCodes() {
		return airlineCodes;
	}

	/**
	 * @param airlineCodes The airlineCodes to set.
	 */
	public void setAirlineCodes(String[] airlineCodes) {
		this.airlineCodes = airlineCodes;
	}

	/**
	 * @return Returns the stationCodes.
	 */
	public String[] getStationCodes() {
		return stationCodes;
	}

	/**
	 * @param stationCodes The stationCodes to set.
	 */
	public void setStationCodes(String[] stationCodes) {
		this.stationCodes = stationCodes;
	}

	/**
	 * @return Returns the stockDisableStatus.
	 */
	public String getStockDisableStatus() {
		return stockDisableStatus;
	}

	/**
	 * @param stockDisableStatus The stockDisableStatus to set.
	 */
	public void setStockDisableStatus(String stockDisableStatus) {
		this.stockDisableStatus = stockDisableStatus;
	}

	/**
	 * @return Returns the available.
	 */
	public String[] getAvailable() {
		return available;
	}

	/**
	 * @param available The available to set.
	 */
	public void setAvailable(String[] available) {
		this.available = available;
	}

	/**
	 * @return Returns the total.
	 */
	public String[] getTotal() {
		return total;
	}

	/**
	 * @param total The total to set.
	 */
	public void setTotal(String[] total) {
		this.total = total;
	}

	/**
	 * @return Returns the maxQty.
	 */
	public String[] getMaxQty() {
		return maxQty;
	}

	/**
	 * @param maxQty The maxQty to set.
	 */
	public void setMaxQty(String[] maxQty) {
		this.maxQty = maxQty;
	}

	/**
	 * @return Returns the minQty.
	 */
	public String[] getMinQty() {
		return minQty;
	}

	/**
	 * @param minQty The minQty to set.
	 */
	public void setMinQty(String[] minQty) {
		this.minQty = minQty;
	}

	/**
	 * @return Returns the uldNature.
	 */
	public String getUldNature() {
		return uldNature;
	}

	/**
	 * @param uldNature The uldNature to set.
	 */
	public void setUldNature(String uldNature) {
		this.uldNature = uldNature;
	}

	/**
	 * @return Returns the viewByNatureFlag.
	 */
	public boolean getViewByNatureFlag() {
		return viewByNatureFlag;
	}

	/**
	 * @param viewByNatureFlag The viewByNatureFlag to set.
	 */
	public void setViewByNatureFlag(boolean viewByNatureFlag) {
		this.viewByNatureFlag = viewByNatureFlag;
	}

	/**
	 * @return Returns the agentCode.
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode The agentCode to set.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return the levelType
	 */
	public String getLevelType() {
		return levelType;
	}

	/**
	 * @param levelType the levelType to set
	 */
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	/**
	 * @return the levelValue
	 */
	public String getLevelValue() {
		return levelValue;
	}

	/**
	 * @param levelValue the levelValue to set
	 */
	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}

	/**
	 * @return the comboFlag
	 */
	public String getComboFlag() {
		return comboFlag;
	}

	/**
	 * @param comboFlag the comboFlag to set
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}

	/**
	 * @return the isPreview
	 */
	public String getIsPreview() {
		return isPreview;
	}

	/**
	 * @param isPreview the isPreview to set
	 */
	public void setIsPreview(String isPreview) {
		this.isPreview = isPreview;
	}

	/**
	 * @return the selectedChildRows
	 */
	public String[] getSelectedChildRows() {
		return selectedChildRows;
	}

	/**
	 * @param selectedChildRows the selectedChildRows to set
	 */
	public void setSelectedChildRows(String[] selectedChildRows) {
		this.selectedChildRows = selectedChildRows;
	}

	/**
	 * @return the loginStation
	 */
	public String getLoginStation() {
		return loginStation;
	}

	/**
	 * @param loginStation the loginStation to set
	 */
	public void setLoginStation(String loginStation) {
		this.loginStation = loginStation;
	}

	/**
	 * @return
	 */
	public String getScreenStatus() {
		return screenStatus;
	}

	/**
	 * @param screenStatus
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}
	public String getNavigationMode() {
		return navigationMode;
	}
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
	public String getOwnerAirline() {
		return ownerAirline;
	}
	public void setOwnerAirline(String ownerAirline) {
		this.ownerAirline = ownerAirline;
	}
    
	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
 }
