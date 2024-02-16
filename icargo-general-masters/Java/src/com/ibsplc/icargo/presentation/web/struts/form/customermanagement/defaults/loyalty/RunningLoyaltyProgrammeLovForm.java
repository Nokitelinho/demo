/*
 * RunningLoyaltyProgrammeLovForm.java Created on May 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2105
 *
 */
public class RunningLoyaltyProgrammeLovForm extends ScreenModel {
	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID =
		"customermanagement.defaults.listrunningloyaltylov";
	/**
	 * PRODUCT_NAME
	 */
	private static final String PRODUCT_NAME = "customermanagement";
	/**
	 * SUBPRODUCT_NAME
	 */
	private static final String SUBPRODUCT_NAME = "defaults";
	/**
	 * BUNDLE
	 */
	private static final String BUNDLE =
		"listRunningLoyaltyLOVResources";

	private String companyCode;
	private String formNumber;
	private String textfiledObj;
	private String textfiledDesc;
	private String textfiledFromDate;
	private String textfiledToDate;
	private String rowCount;
	private String displayPage="1";
	private String lastPageNum="0";	
	

	/**
	 * bundle
	 */
	private String bundle;
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
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


	/**
	 * @return Returns the formNumber.
	 */
	public String getFormNumber() {
		return formNumber;
	}
	/**
	 * @param formNumber The formNumber to set.
	 */
	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}
	/**
	 * @return Returns the textfiledObj.
	 */
	public String getTextfiledObj() {
		return textfiledObj;
	}
	/**
	 * @param textfiledObj The textfiledObj to set.
	 */
	public void setTextfiledObj(String textfiledObj) {
		this.textfiledObj = textfiledObj;
	}
	/**
	 * @return Returns the textfiledDesc.
	 */
	public String getTextfiledDesc() {
		return textfiledDesc;
	}
	/**
	 * @param textfiledDesc The textfiledDesc to set.
	 */
	public void setTextfiledDesc(String textfiledDesc) {
		this.textfiledDesc = textfiledDesc;
	}

	/**
	 * @return Returns the rowCount.
	 */
	public String getRowCount() {
		return rowCount;
	}
	/**
	 * @param rowCount The rowCount to set.
	 */
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}
	/**
	 * @return Returns the textfiledToDate.
	 */
	public String getTextfiledToDate() {
		return textfiledToDate;
	}
	/**
	 * @param textfiledToDate The textfiledToDate to set.
	 */
	public void setTextfiledToDate(String textfiledToDate) {
		this.textfiledToDate = textfiledToDate;
	}
	/**
	 * @return Returns the textfiledFromDate.
	 */
	public String getTextfiledFromDate() {
		return textfiledFromDate;
	}
	/**
	 * @param textfiledFromDate The textfiledFromDate to set.
	 */
	public void setTextfiledFromDate(String textfiledFromDate) {
		this.textfiledFromDate = textfiledFromDate;
	}
	


}
