/*
 * MaintainMraProrateFactorsForm.java Created on Mar 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author Rani Rose John
 * Form for Capture CN 51 screen.
 *
 * Revision History
 *
 * Version        Date           		  Author          		    Description
 *
 *  0.1        Mar 20, 2007    			Rani Rose John 	   Initial draft
 */

public class MaintainMraProrateFactorsForm extends ScreenModel {

	private static final String BUNDLE = "mramaintainproratefactors";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainproratefactors";

	/**
	 * Filter fields
	 */
	private String originCityCode;

	private String originCityName;

	private String destCityCode;

	private String destCityName;

	private String statusFilter;

	private String sourceFilter;

	private String effFromDate;

	private String effToDate;

	/**
	 * Details Fields
	 */

	private String[] destinationCityCode;

	private String[] destinationCityName;

	private String[] selectedElements;

	private String[] operationFlag;

	private double[] prorationFactor;

	private String[] prorationFactorStatus;

	private String[] prorationFactorSource;

	private String[] fromDate;

	private String[] toDate;

	private String displayPage = "1";

	private String lastPageNum = "0";

	/**
	 * flags
	 */
	private String linkStatusFlag;

	private String focusFlag;
	
	private String errorStatusFlag;
	
	private String dateStatusFlag;
	
	private String selectedRow;
	// for populating city name using ajax
	private String cityCode;
	
	private String cityName;
	
	private String noResultFlag;
	
	private int[] sequenceNumber;
	
	/**
	 * @return Returns the cityName.
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName The cityName to set.
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return Returns the SCREENID.
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return Returns the PRODUCT.
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SUBPRODUCT.
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
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
	//	this.bundle = bundle;
	}

	/**
	 * @return Returns the destCityCode.
	 */
	public String getDestCityCode() {
		return destCityCode;
	}

	/**
	 * @param destCityCode
	 *            The destCityCode to set.
	 */
	public void setDestCityCode(String destCityCode) {
		this.destCityCode = destCityCode;
	}

	/**
	 * @return Returns the destCityName.
	 */
	public String getDestCityName() {
		return destCityName;
	}

	/**
	 * @param destCityName
	 *            The destCityName to set.
	 */
	public void setDestCityName(String destCityName) {
		this.destCityName = destCityName;
	}

	/**
	 * @return Returns the destinationCityCode.
	 */
	public String[] getDestinationCityCode() {
		return destinationCityCode;
	}

	/**
	 * @param destinationCityCode
	 *            The destinationCityCode to set.
	 */
	public void setDestinationCityCode(String[] destinationCityCode) {
		this.destinationCityCode = destinationCityCode;
	}

	/**
	 * @return Returns the destinationCityName.
	 */
	public String[] getDestinationCityName() {
		return destinationCityName;
	}

	/**
	 * @param destinationCityName
	 *            The destinationCityName to set.
	 */
	public void setDestinationCityName(String[] destinationCityName) {
		this.destinationCityName = destinationCityName;
	}

	/**
	 * @return Returns the prorationFactor.
	 */
	public double[] getProrationFactor() {
		return prorationFactor;
	}

	/**
	 * @param prorationFactor The prorationFactor to set.
	 */
	public void setProrationFactor(double[] prorationFactor) {
		this.prorationFactor = prorationFactor;
	}

	/**
	 * @return Returns the prorationFactorSource.
	 */
	public String[] getProrationFactorSource() {
		return prorationFactorSource;
	}

	/**
	 * @param prorationFactorSource The prorationFactorSource to set.
	 */
	public void setProrationFactorSource(String[] prorationFactorSource) {
		this.prorationFactorSource = prorationFactorSource;
	}

	/**
	 * @return Returns the prorationFactorStatus.
	 */
	public String[] getProrationFactorStatus() {
		return prorationFactorStatus;
	}

	/**
	 * @param prorationFactorStatus The prorationFactorStatus to set.
	 */
	public void setProrationFactorStatus(String[] prorationFactorStatus) {
		this.prorationFactorStatus = prorationFactorStatus;
	}

	/**
	 * @return Returns the effFromDate.
	 */
	public String getEffFromDate() {
		return effFromDate;
	}

	/**
	 * @param effFromDate
	 *            The effFromDate to set.
	 */
	public void setEffFromDate(String effFromDate) {
		this.effFromDate = effFromDate;
	}

	/**
	 * @return Returns the effToDate.
	 */
	public String getEffToDate() {
		return effToDate;
	}

	/**
	 * @param effToDate
	 *            The effToDate to set.
	 */
	public void setEffToDate(String effToDate) {
		this.effToDate = effToDate;
	}

	

	/**
	 * @return Returns the fromDate.
	 */
	public String[] getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String[] fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the originCityCode.
	 */
	public String getOriginCityCode() {
		return originCityCode;
	}

	/**
	 * @param originCityCode
	 *            The originCityCode to set.
	 */
	public void setOriginCityCode(String originCityCode) {
		this.originCityCode = originCityCode;
	}

	/**
	 * @return Returns the originCityName.
	 */
	public String getOriginCityName() {
		return originCityName;
	}

	/**
	 * @param originCityName
	 *            The originCityName to set.
	 */
	public void setOriginCityName(String originCityName) {
		this.originCityName = originCityName;
	}

	/**
	 * @return Returns the sourceFilter.
	 */
	public String getSourceFilter() {
		return sourceFilter;
	}

	/**
	 * @param sourceFilter
	 *            The sourceFilter to set.
	 */
	public void setSourceFilter(String sourceFilter) {
		this.sourceFilter = sourceFilter;
	}

	/**
	 * @return Returns the toDate.
	 */
	public String[] getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(String[] toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the linkStatusFlag.
	 */
	public String getLinkStatusFlag() {
		return linkStatusFlag;
	}

	/**
	 * @param linkStatusFlag
	 *            The linkStatusFlag to set.
	 */
	public void setLinkStatusFlag(String linkStatusFlag) {
		this.linkStatusFlag = linkStatusFlag;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage
	 *            The displayPage to set.
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
	 * @param lastPageNum
	 *            The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the selectedElements.
	 */
	public String[] getSelectedElements() {
		return selectedElements;
	}

	/**
	 * @param selectedElements
	 *            The selectedElements to set.
	 */
	public void setSelectedElements(String[] selectedElements) {
		this.selectedElements = selectedElements;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}
	
	/**
	 * @return Returns the statusFilter.
	 */
	public String getStatusFilter() {
		return statusFilter;
	}

	/**
	 * @param statusFilter The statusFilter to set.
	 */
	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}

	/**
	 * @return Returns the focusFlag.
	 */
	public String getFocusFlag() {
		return focusFlag;
	}

	/**
	 * @param focusFlag The focusFlag to set.
	 */
	public void setFocusFlag(String focusFlag) {
		this.focusFlag = focusFlag;
	}

	/**
	 * @return Returns the selectedRow.
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow The selectedRow to set.
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return Returns the cityCode.
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode The cityCode to set.
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return Returns the errorStatusFlag.
	 */
	public String getErrorStatusFlag() {
		return errorStatusFlag;
	}

	/**
	 * @param errorStatusFlag The errorStatusFlag to set.
	 */
	public void setErrorStatusFlag(String errorStatusFlag) {
		this.errorStatusFlag = errorStatusFlag;
	}

	/**
	 * @return Returns the dateStatusFlag.
	 */
	public String getDateStatusFlag() {
		return dateStatusFlag;
	}

	/**
	 * @param dateStatusFlag The dateStatusFlag to set.
	 */
	public void setDateStatusFlag(String dateStatusFlag) {
		this.dateStatusFlag = dateStatusFlag;
	}

	/**
	 * @return Returns the noResultFlag.
	 */
	public String getNoResultFlag() {
		return noResultFlag;
	}

	/**
	 * @param noResultFlag The noResultFlag to set.
	 */
	public void setNoResultFlag(String noResultFlag) {
		this.noResultFlag = noResultFlag;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int[] getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(int[] sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	
	
}