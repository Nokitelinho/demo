/*
 * AccessoryStockLovForm.java Created on May 14, 2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3278
 * 
 */
public class AccessoryStockLovForm extends ScreenModel {

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.stock.accessorystocklov";

	/**
	 * The key attribute specified in struts_config.xml file.
	 */
	private static final String BUNDLE = "accessorystocklov";

	private String bundle;

	private String accessoryStkCode = "";

	private String airline;

	private String accessoryStkName;

	private Page<AccessoriesStockConfigVO> pageAccessoryLov = null;

	private String lastPageNumber = "0";

	private String displayPage = "1";

	private String[] numChecked;

	private String formNumber;

	private String textfiledObj;

	private String textfiledDesc;

	private String rowCount;

	private String statusValue;

	private String airlineFromOperations;
	
	private String index;

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
	 * @param bundle
	 *            The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * 
	 * @return displayPage
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
	 * @return lastPageNumber
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
	 * @return productChecked
	 */
	public String[] getNumChecked() {
		return numChecked;
	}

	/**
	 * 
	 * @param numChecked
	 */
	public void setNumChecked(String[] numChecked) {
		this.numChecked = numChecked;
	}

	/**
	 * @return the accessoryStkCode
	 */
	public final String getAccessoryStkCode() {
		return accessoryStkCode;
	}

	/**
	 * @param accessoryStkCode
	 *            the accessoryStkCode to set
	 */
	public final void setAccessoryStkCode(String accessoryStkCode) {
		this.accessoryStkCode = accessoryStkCode;
	}

	/**
	 * @return the accessoryStkName
	 */
	public final String getAccessoryStkName() {
		return accessoryStkName;
	}

	/**
	 * @param accessoryStkName
	 *            the accessoryStkName to set
	 */
	public final void setAccessoryStkName(String accessoryStkName) {
		this.accessoryStkName = accessoryStkName;
	}

	/**
	 * @return the airline
	 */
	public final String getAirline() {
		return airline;
	}

	/**
	 * @param airline
	 *            the airline to set
	 */
	public final void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * @return the pageAccessoryLov
	 */
	public final Page<AccessoriesStockConfigVO> getPageAccessoryLov() {
		return pageAccessoryLov;
	}

	/**
	 * @param pageAccessoryLov
	 *            the pageAccessoryLov to set
	 */
	public final void setPageAccessoryLov(
			Page<AccessoriesStockConfigVO> pageAccessoryLov) {
		this.pageAccessoryLov = pageAccessoryLov;
	}

	/**
	 * @return the formNumber
	 */
	public final String getFormNumber() {
		return formNumber;
	}

	/**
	 * @param formNumber the formNumber to set
	 */
	public final void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}

	/**
	 * @return the rowCount
	 */
	public final String getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount the rowCount to set
	 */
	public final void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the textfiledDesc
	 */
	public final String getTextfiledDesc() {
		return textfiledDesc;
	}

	/**
	 * @param textfiledDesc the textfiledDesc to set
	 */
	public final void setTextfiledDesc(String textfiledDesc) {
		this.textfiledDesc = textfiledDesc;
	}

	/**
	 * @return the textfiledObj
	 */
	public final String getTextfiledObj() {
		return textfiledObj;
	}

	/**
	 * @param textfiledObj the textfiledObj to set
	 */
	public final void setTextfiledObj(String textfiledObj) {
		this.textfiledObj = textfiledObj;
	}

	/**
	 * @return the statusValue
	 */
	public final String getStatusValue() {
		return statusValue;
	}

	/**
	 * @param statusValue the statusValue to set
	 */
	public final void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	/**
	 * @return the airlineFromOperations
	 */
	public final String getAirlineFromOperations() {
		return airlineFromOperations;
	}

	/**
	 * @param airlineFromOperations the airlineFromOperations to set
	 */
	public final void setAirlineFromOperations(String airlineFromOperations) {
		this.airlineFromOperations = airlineFromOperations;
	}

	/**
	 * @return the index
	 */
	public final String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public final void setIndex(String index) {
		this.index = index;
	}

}
