/*
 POMailStatementForm.java Created on Feb 01, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author a-4777
 *
 */

public class POMailStatementForm extends ScreenModel {
	private static String SCREEN_ID = "mailtracking.defaults.national.mailstatement";
	private static String PRODUCT_NAME = "mail";
	private static String SUBPRODUCT_NAME = "operations";
	private static String BUNDLE = "pomailStatementResources";
	
	
	private String origin;
	private String destination;
	private String category;
	private String consignmentNo;
	private String fromDate;
	private String toDate;
	private String[] selectedRow;
	private String displayPage = "1";

	private String lastPageNum = "0";
	
	private String[] remarks;
	//Added by A-4810 for icrd-15155
	private String firstList;
	
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
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getConsignmentNo() {
		return consignmentNo;
	}
	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}
	
	
	@DateFieldId(id="PostalMailOfficeServiceDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	@DateFieldId(id="PostalMailOfficeServiceDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public  String getScreenId() {
		return SCREEN_ID;
	}
	public  void setScreenId(String screenId) {
		SCREEN_ID = screenId;
	}
	public  String getProductName() {
		return PRODUCT_NAME;
	}
	public  void setProductName(String productName) {
		PRODUCT_NAME = productName;
	}
	public  String getSubproductName() {
		return SUBPRODUCT_NAME;
	}
	public  void setSubproductName(String subproductName) {
		SUBPRODUCT_NAME = subproductName;
	}
	public  String getBundle() {
		return BUNDLE;
	}
	public  void setBundle(String bundle) {
		BUNDLE = bundle;
	}
	
	public String getProduct() {
		
		return PRODUCT_NAME;
	}
	
	public String getSubProduct() {
		
		return PRODUCT_NAME;
	}
	/**
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the firstList
	 */
	public String getFirstList() {
		return firstList;
	}
	/**
	 * @param firstList the firstList to set
	 */
	public void setFirstList(String firstList) {
		this.firstList = firstList;
	}
	
	
}
