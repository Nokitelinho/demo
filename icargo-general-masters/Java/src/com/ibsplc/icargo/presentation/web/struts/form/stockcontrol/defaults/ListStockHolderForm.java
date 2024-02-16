/*
 * ListStockHolderForm.java Created on Aug 11, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1883
 * 
 */
public class ListStockHolderForm extends ScreenModel {

	// added by A-5175 for QF CR ICRD-20959 starts
	public static final String PAGINATION_MODE_FROM_FILTER = "FILTER";

	public static final String PAGINATION_MODE_FROM_NAVIGATION = "NAVIGATION";
	// ends
	private String stockHolderCode;
	private String docType = "";
	private String docSubType = "";
	private String slectedDocType = "";
	private String[] checkStockHolder;
	private String[] reorderAlert;
	private String[] autoStockRequest;
	private String checkList;
	private String stockHolderType = "";
	private String level;
	private String fromStockHolderList = "";
	private String statusFlag = "";

	private String lastPageNum = "0";

	private String displayPage = "1";

	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "liststockholderresources";

	private String bundle;
	private String fromScreen;
	private String disableButn = "";

	private String selected = "";

	private String listFlag = "";

	private String listInt = "";
	private String fromListMonitor = "";
	private String fromMonitorStock = "";

	/*
	 * For #102543 for Base Product
	 */
	private boolean partnerAirline;
	private String awbPrefix;
	private String airlineName;
	/*
	 * End #102543
	 */
	// Added by : A-5175
	private String navigationMode;

	/**
	 * Getter for navigationMode Added by : A-5175 on 12-Oct-2012 Used for :
	 * ICRD-20959
	 */
	public String getNavigationMode() {
		return navigationMode;
	}

	/**
	 * @param navigationMode
	 *            the navigationMode to set Setter for navigationMode Added by :
	 *            A-5175 on 12-Oct-2012 Used for : ICRD-20959
	 */
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}

	public String getFromMonitorStock() {
		return fromMonitorStock;
	}

	public void setFromMonitorStock(String fromMonitorStock) {
		this.fromMonitorStock = fromMonitorStock;
	}

	public String getFromListMonitor() {
		return fromListMonitor;
	}

	public void setFromListMonitor(String fromListMonitor) {
		this.fromListMonitor = fromListMonitor;
	}

	public String getDisableButn() {
		return disableButn;
	}

	public void setDisableButn(String disableButn) {
		this.disableButn = disableButn;
	}

	public String getListFlag() {
		return listFlag;
	}

	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

	public String getListInt() {
		return listInt;
	}

	public void setListInt(String listInt) {
		this.listInt = listInt;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle
	 *            The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return Returns the checkList.
	 */
	public String getCheckList() {
		return checkList;
	}

	/**
	 * @param checkList
	 *            The checkList to set.
	 */
	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}

	/**
	 * @return Returns the docSubType.
	 */
	public String getDocSubType() {
		return docSubType;
	}

	/**
	 * @param docSubType
	 *            The docSubType to set.
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}

	/**
	 * @return Returns the docType.
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType
	 *            The docType to set.
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getScreenId() {
		return "stockcontrol.defaults.liststockholder";
	}

	public String getProduct() {
		return "stockcontrol";
	}

	public String getSubProduct() {
		return "defaults";
	}

	/**
	 * @return Returns the slectedDocType.
	 */
	public String getSlectedDocType() {
		return slectedDocType;
	}

	/**
	 * @param slectedDocType
	 *            The slectedDocType to set.
	 */
	public void setSlectedDocType(String slectedDocType) {
		this.slectedDocType = slectedDocType;
	}

	/**
	 * @return Returns the autoStockRequest.
	 */
	public String[] getAutoStockRequest() {
		return autoStockRequest;
	}

	/**
	 * @param autoStockRequest
	 *            The autoStockRequest to set.
	 */
	public void setAutoStockRequest(String[] autoStockRequest) {
		this.autoStockRequest = autoStockRequest;
	}

	/**
	 * @return Returns the checkStockHolder.
	 */
	public String[] getCheckStockHolder() {
		return checkStockHolder;
	}

	/**
	 * @param checkStockHolder
	 *            The checkStockHolder to set.
	 */
	public void setCheckStockHolder(String[] checkStockHolder) {
		this.checkStockHolder = checkStockHolder;
	}

	/**
	 * @return Returns the reorderAlert.
	 */
	public String[] getReorderAlert() {
		return reorderAlert;
	}

	/**
	 * @param reorderAlert
	 *            The reorderAlert to set.
	 */
	public void setReorderAlert(String[] reorderAlert) {
		this.reorderAlert = reorderAlert;
	}

	/**
	 * @return Returns the level.
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            The level to set.
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode
	 *            The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return Returns the stockHolderType.
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}

	/**
	 * @param stockHolderType
	 *            The stockHolderType to set.
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}

	/**
	 * @return Returns the fromStockHolderList.
	 */
	public String getFromStockHolderList() {
		return fromStockHolderList;
	}

	/**
	 * @param fromStockHolderList
	 *            The fromStockHolderList to set.
	 */
	public void setFromStockHolderList(String fromStockHolderList) {
		this.fromStockHolderList = fromStockHolderList;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
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
	 * @return the partnerAirline
	 */
	public boolean isPartnerAirline() {
		return partnerAirline;
	}

	/**
	 * @param partnerAirline
	 *            the partnerAirline to set
	 */
	public void setPartnerAirline(boolean partnerAirline) {
		this.partnerAirline = partnerAirline;
	}

	/**
	 * @return the awbPrefix
	 */
	public String getAwbPrefix() {
		return awbPrefix;
	}

	/**
	 * @param awbPrefix
	 *            the awbPrefix to set
	 */
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}

	/**
	 * @return the airlineName
	 */
	public String getAirlineName() {
		return airlineName;
	}

	/**
	 * @param airlineName
	 *            the airlineName to set
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

}
