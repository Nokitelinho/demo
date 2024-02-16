/*
 * SCMReconcileForm.java Created on Aug 01, 2006
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
public class SCMReconcileForm extends ScreenModel {

	private static final String BUNDLE = "scmReconcileResources";

	private String bundle;

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.scmreconcile";

	private String displayPage = "1";

	private String lastPageNumber = "0";

	private String totalRecords;

	private String currentPageNum;

	private String airport;

	private String stockChkdate;

	private String scmStockCheckTime;

	private String absoluteIndex;
	
	private String scmDisable;

	private String[] selectedSCMErrorLog;

	private String rowIndex;
	private String seqNo;
	private String airline;
	private String listStatus;
	//ADDED FOR SCM CR BY A-2408
	private String msgFlag;
	private String listflag;
	
    private String navigationMode;
	public String getNavigationMode() {
		return navigationMode;
	}
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
/**
	 * @return the listflag
	 */
	public String getListflag() {
		return listflag;
	}
	/**
	 * @param listflag the listflag to set
	 */
	public void setListflag(String listflag) {
		this.listflag = listflag;
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
	public String getAbsoluteIndex() {
		return absoluteIndex;
	}
/**
 * 
 * @param absoluteIndex
 */
	public void setAbsoluteIndex(String absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
/**
 * 
 * @return
 */
	public String getStockChkdate() {
		return stockChkdate;
	}
/**
 * 
 * @param stockChkdate
 */
	public void setStockChkdate(String stockChkdate) {
		this.stockChkdate = stockChkdate;
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
	public String getAirport() {
		return airport;
	}
/**
 * 
 * @param airport
 */
	public void setAirport(String airport) {
		this.airport = airport;
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
	public String getCurrentPageNum() {
		return currentPageNum;
	}
/**
 * 
 * @param currentPageNum
 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
/**
 * 
 * @return
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
 * @return
 */
	public String getTotalRecords() {
		return totalRecords;
	}
/**
 * 
 * @param totalRecords
 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
/**
 * 
 * @return
 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}
/**
 * 
 * @param lastPageNumber
 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
/**
 * 
 * @return
 */
	public String[] getSelectedSCMErrorLog() {
		return selectedSCMErrorLog;
	}
/**
 * 
 * @param selectedSCMErrorLog
 */
	public void setSelectedSCMErrorLog(String[] selectedSCMErrorLog) {
		this.selectedSCMErrorLog = selectedSCMErrorLog;
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
	 * @return Returns the scmDisable.
	 */
	public String getScmDisable() {
		return scmDisable;
	}

	/**
	 * @param scmDisable The scmDisable to set.
	 */
	public void setScmDisable(String scmDisable) {
		this.scmDisable = scmDisable;
	}
	/**
	 * @return Returns the seqNo.
	 */
	public String getSeqNo() {
		return seqNo;
	}
	/**
	 * @param seqNo The seqNo to set.
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

}
