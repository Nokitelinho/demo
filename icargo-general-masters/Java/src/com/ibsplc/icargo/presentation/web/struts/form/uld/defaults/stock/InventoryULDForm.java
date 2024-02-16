/*
 * InventoryULDForm.java Created on May 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * 
 * @author a-2883
 *
 */
public class InventoryULDForm extends ScreenModel{

	private static final String BUNDLE = "inventoryuldResources";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.stock.inventoryuld";
	private String airportCode;
	private String fromDate;
	private String toDate;
	private String uldType;
	private String date;
	private String requiredULD;
	private String remarks;
	private String bundle;
	private String displayPage = "1";
	private String lastPageNum = "0";
	private String detailULDType;
	private int detailRequiredULD;
	private String detailRemarks;
	private String parentPrimaryKey;
	private String serialNumber;
	private String childPrimaryKey;
	private String statusFlag;
	private String opFlag;
	private String absoluteIndex;
	private String flightPlanFlag;
	private String[] checkedParent;
	private String companyCode;
	private String inventoryDate;
	private String displayDate;
	private String inventoryFromScreen;
	
	

	/**
	 * @return the inventoryFromScreen
	 */
	public String getInventoryFromScreen() {
		return inventoryFromScreen;
	}


	/**
	 * @param inventoryFromScreen the inventoryFromScreen to set
	 */
	public void setInventoryFromScreen(String inventoryFromScreen) {
		this.inventoryFromScreen = inventoryFromScreen;
	}


	/**
	 * @return the displayDate
	 */
	public String getDisplayDate() {
		return displayDate;
	}


	/**
	 * @param displayDate the displayDate to set
	 */
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}


	/**
	 * @return the inventoryDate
	 */
	public String getInventoryDate() {
		return inventoryDate;
	}


	/**
	 * @param inventoryDate the inventoryDate to set
	 */
	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
	}


	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}


	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


	/**
	 * @return the checkedParent
	 */
	public String[] getCheckedParent() {
		return checkedParent;
	}


	/**
	 * @param checkedParent the checkedParent to set
	 */
	public void setCheckedParent(String[] checkedParent) {
		this.checkedParent = checkedParent;
	}


	/**
	 * @return the flightPlanFlag
	 */
	public String getFlightPlanFlag() {
		return flightPlanFlag;
	}


	/**
	 * @param flightPlanFlag the flightPlanFlag to set
	 */
	public void setFlightPlanFlag(String flightPlanFlag) {
		this.flightPlanFlag = flightPlanFlag;
	}


	/**
	 * @return the absoluteIndex
	 */
	public String getAbsoluteIndex() {
		return absoluteIndex;
	}


	/**
	 * @param absoluteIndex the absoluteIndex to set
	 */
	public void setAbsoluteIndex(String absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}


	/**
	 * @return the opFlag
	 */
	public String getOpFlag() {
		return opFlag;
	}


	/**
	 * @param opFlag the opFlag to set
	 */
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}


	/**
	 * @return the statusFlag
	 */
	public String getStatusFlag() {
		return statusFlag;
	}


	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}


	/**
	 * @return the childPrimaryKey
	 */
	public String getChildPrimaryKey() {
		return childPrimaryKey;
	}


	/**
	 * @param childPrimaryKey the childPrimaryKey to set
	 */
	public void setChildPrimaryKey(String childPrimaryKey) {
		this.childPrimaryKey = childPrimaryKey;
	}


	/**
	 * @return the parentPrimaryKey
	 */
	public String getParentPrimaryKey() {
		return parentPrimaryKey;
	}


	/**
	 * @param parentPrimaryKey the parentPrimaryKey to set
	 */
	public void setParentPrimaryKey(String parentPrimaryKey) {
		this.parentPrimaryKey = parentPrimaryKey;
	}


	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}


	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	/**
	 * @return the detailRemarks
	 */
	public String getDetailRemarks() {
		return detailRemarks;
	}


	/**
	 * @param detailRemarks the detailRemarks to set
	 */
	public void setDetailRemarks(String detailRemarks) {
		this.detailRemarks = detailRemarks;
	}


	


	/**
	 * @return the detailRequiredULD
	 */
	public int getDetailRequiredULD() {
		return detailRequiredULD;
	}


	/**
	 * @param detailRequiredULD the detailRequiredULD to set
	 */
	public void setDetailRequiredULD(int detailRequiredULD) {
		this.detailRequiredULD = detailRequiredULD;
	}


	/**
	 * @return the detailULDType
	 */
	public String getDetailULDType() {
		return detailULDType;
	}


	/**
	 * @param detailULDType the detailULDType to set
	 */
	public void setDetailULDType(String detailULDType) {
		this.detailULDType = detailULDType;
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


	/**
	 * @return the lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}


	/**
	 * @param lastPageNum the lastPageNum to set
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}


	/**
	 * @return the bundle
	 */
	public String getBundle() {
		return BUNDLE;
	}


	/**
	 * @param bundle the bundle to set
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}


	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}


	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}


	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}


	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}


	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}


	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}


	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	/**
	 * @return the requiredULD
	 */
	public String getRequiredULD() {
		return requiredULD;
	}


	/**
	 * @param requiredULD the requiredULD to set
	 */
	public void setRequiredULD(String requiredULD) {
		this.requiredULD = requiredULD;
	}


	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}


	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}


	/**
	 * @return the uldType
	 */
	public String getUldType() {
		return uldType;
	}


	/**
	 * @param uldType the uldType to set
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}


	public String getProduct() {
		return PRODUCT;
	}

	
	public String getScreenId() {
		return SCREENID;
	}

	
	public String getSubProduct() {
		return SUBPRODUCT;
	}
	
	
	

}
