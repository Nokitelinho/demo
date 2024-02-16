/*
 * SCMULDErrorLogForm.java Created on Aug 01, 2006
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
 * @author A-1496
 * 
 */
public class SCMULDErrorLogForm extends ScreenModel {
	private static final String BUNDLE = "scmUldReconcileResources";

	private String bundle;

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.scmulderrorlog";

	private String displayPage = "1";

	private String lastPageNum = "0";

	private String totRecords;

	private String currentPageNumber;

	private String stockCheckdate;

	private String absoluteIndexNum;

	private String selectedUldErrorLog;
	
	private String airline;

	private String pageUrl;

	private String rowIndex;

	private String scmUldAirport;

	private String uldNumber;

	private String returnTxn;

	private String scmStockCheckTime;

	private String scmSeqNo;
	
	private String scmULDDisable;

	private String[] selectedUlds;
	
	private String listStatus;
	
	private String errorDescription;
	
	private String navigationMode;
	
	public String getNavigationMode() {
		return navigationMode;
	}
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getListStatus() {
		return listStatus;
	}
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
/**
 * 
 * @return
 */
	public String[] getSelectedUlds() {
		return selectedUlds;
	}
/**
 * 
 * @param selectedUlds
 */
	public void setSelectedUlds(String[] selectedUlds) {
		this.selectedUlds = selectedUlds;
	}
/**
 * 
 * @return
 */
	public String getScmSeqNo() {
		return scmSeqNo;
	}
/**
 * 
 * @param scmSeqNo
 */
	public void setScmSeqNo(String scmSeqNo) {
		this.scmSeqNo = scmSeqNo;
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
	public String getReturnTxn() {
		return returnTxn;
	}
/**
 * 
 * @param returnTxn
 */
	public void setReturnTxn(String returnTxn) {
		this.returnTxn = returnTxn;
	}
/**
 * 
 * @return
 */
	public String getScmUldAirport() {
		return scmUldAirport;
	}
/**
 * 
 * @param scmUldAirport
 */
	public void setScmUldAirport(String scmUldAirport) {
		this.scmUldAirport = scmUldAirport;
	}
/**
 * 
 * @return
 */
	public String getPageUrl() {
		return pageUrl;
	}
/**
 * 
 * @param pageUrl
 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
/**
 * 
 * @return
 */
	public String getRowIndex() {
		return rowIndex;
	}
/**
 * 
 * @param rowIndex
 */
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
/**
 * 
 * @return
 */
	public String getSelectedUldErrorLog() {
		return selectedUldErrorLog;
	}
/**
 * 
 * @param selectedUldErrorLog
 */
	public void setSelectedUldErrorLog(String selectedUldErrorLog) {
		this.selectedUldErrorLog = selectedUldErrorLog;
	}
/**
 * 
 * @return
 */
	public static String getBUNDLE() {
		return BUNDLE;
	}
/**
 * 
 * @return
 */
	public String getAbsoluteIndexNum() {
		return absoluteIndexNum;
	}
/**
 * 
 * @param absoluteIndexNum
 */
	public void setAbsoluteIndexNum(String absoluteIndexNum) {
		this.absoluteIndexNum = absoluteIndexNum;
	}
/**
 * 
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
 * @return
 */
	public String getCurrentPageNumber() {
		return currentPageNumber;
	}
/**
 * 
 * @param currentPageNumber
 */
	public void setCurrentPageNumber(String currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
/**
 * 
 * @return
 */
//	Modified as part of ICRD-206002  by A-7558
	public String getDisplayPage() {
		return displayPage;
	}
	
/**
 * 
 * @param displayPage
 */
//	Modified as part of ICRD-206002  by A-7558
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
/**
 * 
 * @return
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
 * @return
 */
	public String getPRODUCT() {
		return PRODUCT;
	}
/**
 * 
 * @return
 */
	public String getSCREENID() {
		return SCREENID;
	}
/**
 * 
 * @return
 */
	public String getStockCheckdate() {
		return stockCheckdate;
	}
/**
 * 
 * @param stockCheckdate
 */
	public void setStockCheckdate(String stockCheckdate) {
		this.stockCheckdate = stockCheckdate;
	}
/**
 * 
 * @return
 */
	public String getSUBPRODUCT() {
		return SUBPRODUCT;
	}
/**
 * 
 * @return
 */
	public String getTotRecords() {
		return totRecords;
	}
/**
 * 
 * @param totRecords
 */
	public void setTotRecords(String totRecords) {
		this.totRecords = totRecords;
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
	 * @return Returns the scmULDDisable.
	 */
	public String getScmULDDisable() {
		return scmULDDisable;
	}

	/**
	 * @param scmULDDisable The scmULDDisable to set.
	 */
	public void setScmULDDisable(String scmULDDisable) {
		this.scmULDDisable = scmULDDisable;
	}
/**
 * 
 * @return
 */
	public String getAirline() {
		return airline;
	}
/**
 * 
 * @param airline
 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

}
