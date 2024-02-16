/*
 * DamageRefNoLovForm.java Created on Feb 19,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-2052
 *
 */

public class DamageRefNoLovForm extends ScreenModel {

	/*
	 * The constant variable for product capacity
	 *
	 */
	private static final String PRODUCT = "uld";
	/*
	 * The constant for sub product routing 
	 */
	private static final String SUBPRODUCT = "defaults";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "uld.defaults.damagerefnolov";

    /**
	 * Constructor
	 */
	private static final String BUNDLE = "damageRefNoLovResources";
	
	 private String uldNo;
	 private String bundle;
	 
	 private String[] masterRowId;
	 private String[] rowId;
	 private String[] operationalFlag;
	 private String[] damageRefNoTable;
	 
	 private String displayPage="1";
	 private String lastPageNum="0";
	 private String totalRecords;
	 private String currentPageNum;
    
     private String pageURL="";
     private Page listPage;
     
     //Added by Sreekumar S
     private String allChecked;
     
     public String getAllChecked() {
		return allChecked;
	}
	public void setAllChecked(String allChecked) {
		this.allChecked = allChecked;
	}
	/**
      * 
      */
	public String getScreenId() {
		// To be reviewed Auto-generated method stub
		return SCREENID;
	}
	/**
	 * 
	 */
	public String getProduct() {
		// To be reviewed Auto-generated method stub
		return PRODUCT;
	}
	/**
	 * 
	 */
	public String getSubProduct() {
		// To be reviewed Auto-generated method stub
		return SUBPRODUCT;
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
	 * @return Returns the currentPageNum.
	 */
	public String getCurrentPageNum() {
		return currentPageNum;
	}
	/**
	 * @param currentPageNum The currentPageNum to set.
	 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	/**
	 * @return Returns the damageRefNoTable.
	 */
	public String[] getDamageRefNoTable() {
		return damageRefNoTable;
	}
	/**
	 * @param damageRefNoTable The damageRefNoTable to set.
	 */
	public void setDamageRefNoTable(String[] damageRefNoTable) {
		this.damageRefNoTable = damageRefNoTable;
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
	 * @return Returns the listPage.
	 */
	public Page getListPage() {
		return listPage;
	}
	/**
	 * @param listPage The listPage to set.
	 */
	public void setListPage(Page listPage) {
		this.listPage = listPage;
	}
	/**
	 * @return Returns the masterRowId.
	 */
	public String[] getMasterRowId() {
		return masterRowId;
	}
	/**
	 * @param masterRowId The masterRowId to set.
	 */
	public void setMasterRowId(String[] masterRowId) {
		this.masterRowId = masterRowId;
	}
	/**
	 * @return Returns the operationalFlag.
	 */
	public String[] getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
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
	 * @return Returns the pRODUCT.
	 */
	public String getPRODUCT() {
		return PRODUCT;
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
	 * @return Returns the sCREENID.
	 */
	public String getSCREENID() {
		return SCREENID;
	}
	/**
	 * @return Returns the sUBPRODUCT.
	 */
	public String getSUBPRODUCT() {
		return SUBPRODUCT;
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
	 * @return Returns the uldNo.
	 */
	public String getUldNo() {
		return uldNo;
	}
	/**
	 * @param uldNo The uldNo to set.
	 */
	public void setUldNo(String uldNo) {
		this.uldNo = uldNo;
	}
	
	
	 
}
