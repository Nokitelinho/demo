/*
 * DeleteStockRangeForm.java Created on July 12, 2017
 *
 * Copyright  IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

public class DeleteStockRangeForm extends ScreenModel{

	private static final String SCREENID="stockcontrol.defaults.deletestockrange";
	private static final String PRODUCT="stockcontrol";
	private static final String SUBPRODUCT="defaults";
	private static final String BUNDLE="deletestockresources";
	
	/** The total available stock. */
	private String totalAvailableStock;
	
	/** The delete from. */
	private String deleteFrom;

	/** The delete to. */
	private String deleteTo;

	/** The total no of docs. */
	private String totalNoOfDocs;
	
	/** The  no of docs. */
	private String noOfDocs;
	
	/** The stock holder. */
	private String stockHolder;
	
	/** The checkall. */
	private String[] checkall;
	
	/** The check. */
	private String[] check;
	
	private String selectedRowIds;
	
	private String mode="N";
	
private String lastPageNumber = "0";
	
	private String displayPage = "1";
	
	private int totalDocCount = 0;
	
	/**
	 * @return the totalDocCount
	 */
	public int getTotalDocCount() {
		return totalDocCount;
	}

	/**
	 * @param totalDocCount the totalDocCount to set
	 */
	public void setTotalDocCount(int totalDocCount) {
		this.totalDocCount = totalDocCount;
	}

	/**
	 * @return the lastPageNumber
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @param lastPageNumber the lastPageNumber to set
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return the displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	
	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public String getBundle(){
		return BUNDLE;
	}

	public String getTotalAvailableStock() {
		return totalAvailableStock;
	}

	public void setTotalAvailableStock(String totalAvailableStock) {
		this.totalAvailableStock = totalAvailableStock;
	}

	public String getDeleteFrom() {
		return deleteFrom;
	}

	public void setDeleteFrom(String deleteFrom) {
		this.deleteFrom = deleteFrom;
	}

	public String getDeleteTo() {
		return deleteTo;
	}

	public void setDeleteTo(String deleteTo) {
		this.deleteTo = deleteTo;
	}

	public String getTotalNoOfDocs() {
		return totalNoOfDocs;
	}

	public void setTotalNoOfDocs(String totalNoOfDocs) {
		this.totalNoOfDocs = totalNoOfDocs;
	}

	public String getStockHolder() {
		return stockHolder;
	}

	public void setStockHolder(String stockHolder) {
		this.stockHolder = stockHolder;
	}

	public String getNoOfDocs() {
		return noOfDocs;
	}

	public void setNoOfDocs(String noOfDocs) {
		this.noOfDocs = noOfDocs;
	}

	public String[] getCheckall() {
		return checkall;
	}

	public void setCheckall(String[] checkall) {
		this.checkall = checkall;
	}

	public String[] getCheck() {
		return check;
	}

	public void setCheck(String[] check) {
		this.check = check;
	}

	public String getSelectedRowIds() {
		return selectedRowIds;
	}

	public void setSelectedRowIds(String selectedRowIds) {
		this.selectedRowIds = selectedRowIds;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}	
	
}
