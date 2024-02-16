/*
 * StockDetailsForm.java Created on May18,2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;

/**
 * @author A-1952
 *
 */
public class StockDetailsForm extends ScreenModel {

	private static final String BUNDLE = "stockdetailsresources";
	private static final String SCREEN_ID = "stockcontrol.defaults.stockdetails";
	private static final String PRODUCT = "stockcontrol";
	private static final String SUBPRODUCT = "defaults";
	//private String bundle;
	
	private String stockHolderType;

	private String level;

	private String docType;

	private String subType;

	private String stockHolderCode; 

	private String fromDate="";
	
	private String toDate="";

	private String displayPage = "1";	
	private String lastPageNum =  "0";
	private String absoluteIndex = "0";
	
	public String getAbsoluteIndex() {
		return absoluteIndex;
	}
	public void setAbsoluteIndex(String absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
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
	public String getScreenId() {
		return SCREEN_ID;
	}
	/**
	 * @return
	 */
	public String getProduct() {
		return PRODUCT;
	}
	/**
	 * @return
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}
	/**
	 * @return
	 */
	public String getBundle() {
		return BUNDLE;
	}
	public String getStockHolderType() {
		return stockHolderType;
	}

	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getStockHolderCode() {
		return stockHolderCode;
	}

	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	@DateFieldId(id="StockDetailsDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	@DateFieldId(id="StockDetailsDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
}
