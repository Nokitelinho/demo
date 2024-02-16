/*
 * ListEstimatedULDStockForm.java Created on Oct 22, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2934
 *
 */
public class ListEstimatedULDStockForm extends ScreenModel {
    
	private static final String BUNDLE = "listestimateduldstock";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.stock.listestimateduldstock";
	
	private String airport;
	private String uldType;
	
	private String airlinecode;	
	private String lastUpdatedDate;
	
	private String uldTypeCodes[];
	private String currentAvailability[];
	private String ucmUldIn[];
	private String ucmUldOut[];
	private String projectedULDCount[];
	private String minimumQuantity[];
	private String stockDeviation[];
	
	private String stockdisplayPage = "1";

	public String getStockdisplayPage() {
		return stockdisplayPage;
	}
	public void setStockdisplayPage(String stockdisplayPage) {
		this.stockdisplayPage = stockdisplayPage;
	}
	public String getStockLastPageNum() {
		return stockLastPageNum;
	}
	public void setStockLastPageNum(String stockLastPageNum) {
		this.stockLastPageNum = stockLastPageNum;
	}
	private String  stockLastPageNum = "0";
	private String bundle;  
	private int selectFlag;	
	private boolean selectAll;
	private String[] select;
	private String listStatus;
	
	public String getListStatus() {
		return listStatus;
	}
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
	
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	public String getUldType() {
		return uldType;
	}
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}
	public String getAirlinecode() {
		return airlinecode;
	}
	public void setAirlinecode(String airlinecode) {
		this.airlinecode = airlinecode;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String[] getUldTypeCodes() {
		return uldTypeCodes;
	}
	public void setUldTypeCodes(String[] uldTypeCode) {
		this.uldTypeCodes = uldTypeCode;
	}
	public String[] getCurrentAvailability() {
		return currentAvailability;
	}
	public void setCurrentAvailability(String[] currentAvailability) {
		this.currentAvailability = currentAvailability;
	}
	public String[] getUcmUldIn() {
		return ucmUldIn;
	}
	public void setUcmUldIn(String[] ucmUldIn) {
		this.ucmUldIn = ucmUldIn;
	}
	public String[] getUcmUldOut() {
		return ucmUldOut;
	}
	public void setUcmUldOut(String[] ucmUldOut) {
		this.ucmUldOut = ucmUldOut;
	}
	public String[] getProjectedULDCount() {
		return projectedULDCount;
	}
	public void setProjectedULDCount(String[] projectedULDCount) {
		this.projectedULDCount = projectedULDCount;
	}
	public String[] getMinimumQuantity() {
		return minimumQuantity;
	}
	public void setMinimumQuantity(String[] minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
	public String[] getStockDeviation() {
		return stockDeviation;
	}
	public void setStockDeviation(String[] stockDeviation) {
		this.stockDeviation = stockDeviation;
	}
	
	
	public int getSelectFlag() {
		return selectFlag;
	}
	public void setSelectFlag(int selectFlag) {
		this.selectFlag = selectFlag;
	}
	public String getBundle() {
		return BUNDLE;
	}
	public String getProduct() {
		return PRODUCT;
	}

	public String getScreenId() {
		return SCREENID;
	}
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	@Override
	public String getSubProduct() {
		// subproductttt
		return SUBPRODUCT;
	}
	/**
	 * 
	 * @return boolean
	 */
	public boolean getSelectAll() {
		return selectAll;
	}
	/**
	 * 
	 * @param selectAll
	 */
	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

    /**
     * 
     * @return  String[]
     */
	public String[] getSelect() {
		return select;
	}
	/**
	 * 
	 * @param select
	 */
	public void setSelect(String[] select) {
		this.select = select;
	}
 }
