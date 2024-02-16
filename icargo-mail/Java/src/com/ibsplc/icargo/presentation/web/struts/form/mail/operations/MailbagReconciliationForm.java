/*
 * MailbagReconciliationForm.java Created on Oct 12, 2010
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;
/**
 * 
 * @author A-3270
 *
 */
public class MailbagReconciliationForm extends ScreenModel {

	private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "operations";

	private static final String SCREENID = "mailtracking.defaults.MailbagReconciliation";
	
	private static final String BUNDLE = "MailbagReconciliationResources";
	
	private String orginOE;
	
	private String destinationOE;
	
	private String category;
	
	private String subClass;
	
    private String year;
	
	private String dsn;
	
	private String rsn;
		
	private String residit;
	
	private String messageMissing;
	
	private String consignmentId;
	
	private String paCode;
	
	private String carditOrgin;
	
	private String carditDestination;
	
	private String airport;
	
	private String fromDate;
	
	private String toDate;
	
	private boolean delayPeriod;
	
	private String controlReferenceNumber;
	
	private String[] rowID;
	
    private String lastPageNum="0";
    
    private String displayPage="1";
    @MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
    private String weight;
    private Measure weightMeasure;
    
    private String selectedMailBagId;
    
    private String selectedRow;
    
    private String status;
	
    private String carditStatus;
    
	private String countFlag;
	/**
	 * 
	 * @return weightMeasure
	 */
	public Measure getWeightMeasure() {
		return weightMeasure;
	}
/**
 * 
 * @param weightMeasure
 */
	public void setWeightMeasure(Measure weightMeasure) {
		this.weightMeasure = weightMeasure;
	}

	private String fromScreen;
	/**
	 * @return the countFlag
	 */
	public String getCountFlag() {
		return countFlag;
	}

	/**
	 * @param countFlag the countFlag to set
	 */
	public void setCountFlag(String countFlag) {
		this.countFlag = countFlag;
	}

	/**
	 * @return the carditStatus
	 */
	public String getCarditStatus() {
		return carditStatus;
	}

	/**
	 * @param carditStatus the carditStatus to set
	 */
	public void setCarditStatus(String carditStatus) {
		this.carditStatus = carditStatus;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the selectedMailBagId
	 */
	public String getSelectedMailBagId() {
		return selectedMailBagId;
	}

	/**
	 * @param selectedMailBagId the selectedMailBagId to set
	 */
	public void setSelectedMailBagId(String selectedMailBagId) {
		this.selectedMailBagId = selectedMailBagId;
	}

	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
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
	 * @return the rowID
	 */
	public String[] getRowID() {
		return rowID;
	}

	/**
	 * @param rowID the rowID to set
	 */
	public void setRowID(String[] rowID) {
		this.rowID = rowID;
	}

	/**
	 * @return the controlReferenceNumber
	 */
	public String getControlReferenceNumber() {
		return controlReferenceNumber;
	}

	/**
	 * @param controlReferenceNumber the controlReferenceNumber to set
	 */
	public void setControlReferenceNumber(String controlReferenceNumber) {
		this.controlReferenceNumber = controlReferenceNumber;
	}

	/**
	 * @return screenId
	 */
	public String getScreenId() {


		return SCREENID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {


		return PRODUCT;
	}

	/**
	 * @return subProduct
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
	 * @return the orginOE
	 */
	public String getOrginOE() {
		return orginOE;
	}

	/**
	 * @param orginOE the orginOE to set
	 */
	public void setOrginOE(String orginOE) {
		this.orginOE = orginOE;
	}

	/**
	 * @return the destinationOE
	 */
	public String getDestinationOE() {
		return destinationOE;
	}

	/**
	 * @param destinationOE the destinationOE to set
	 */
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the subClass
	 */
	public String getSubClass() {
		return subClass;
	}

	/**
	 * @param subClass the subClass to set
	 */
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return the rsn
	 */
	public String getRsn() {
		return rsn;
	}

	/**
	 * @param rsn the rsn to set
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	/**
	 * @return the residit
	 */
	public String getResidit() {
		return residit;
	}

	/**
	 * @param residit the residit to set
	 */
	public void setResidit(String residit) {
		this.residit = residit;
	}

	/**
	 * @return the messageMissing
	 */
	public String getMessageMissing() {
		return messageMissing;
	}

	/**
	 * @param messageMissing the messageMissing to set
	 */
	public void setMessageMissing(String messageMissing) {
		this.messageMissing = messageMissing;
	}

	/**
	 * @return the consignmentId
	 */
	public String getConsignmentId() {
		return consignmentId;
	}

	/**
	 * @param consignmentId the consignmentId to set
	 */
	public void setConsignmentId(String consignmentId) {
		this.consignmentId = consignmentId;
	}

	/**
	 * @return the paCode
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode the paCode to set
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return the carditOrgin
	 */
	public String getCarditOrgin() {
		return carditOrgin;
	}

	/**
	 * @param carditOrgin the carditOrgin to set
	 */
	public void setCarditOrgin(String carditOrgin) {
		this.carditOrgin = carditOrgin;
	}

	/**
	 * @return the carditDestination
	 */
	public String getCarditDestination() {
		return carditDestination;
	}

	/**
	 * @param carditDestination the carditDestination to set
	 */
	public void setCarditDestination(String carditDestination) {
		this.carditDestination = carditDestination;
	}

	/**
	 * @return the airport
	 */
	public String getAirport() {
		return airport;
	}

	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
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
	 * @return the delayPeriod
	 */
	public boolean isDelayPeriod() {
		return delayPeriod;
	}

	/**
	 * @param delayPeriod the delayPeriod to set
	 */
	public void setDelayPeriod(boolean delayPeriod) {
		this.delayPeriod = delayPeriod;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	
	
	}
