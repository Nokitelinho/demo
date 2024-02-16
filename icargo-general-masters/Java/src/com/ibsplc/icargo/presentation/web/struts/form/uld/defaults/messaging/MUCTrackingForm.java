/*
 * MUCTrackingForm.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author a-3045
 *
 */
public class MUCTrackingForm extends ScreenModel{
	
private static final String BUNDLE = "MUCTrackingResources";
	
	private String bundle;    

	private static final String PRODUCT = "uld";
	
	private static final String SUBPRODUCT = "defaults";
	
	private static final String SCREENID = "uld.defaults.messaging.muctracking";
	
	private String mucReferenceNumber;
	private String mucFilterDate;
	private String iataFilterStatus;
	
	private String[] selectedrow; 
    private String[] uldNumber; 
    private String[] mucDate;
    private String[] mucTime;
    private String[] fromCarrier;
    private String[] toCarrier;
    private String[] txntype;
    private String[] txnAirport;
    private String[] CRN;
    private String[] destAirport;
    private String[] condition;
    private String[] dmgcode;
    private String[] iataStatus;
    private String mucReferenceNum;
    private String mucLovFilterDate;
    private String displayLovPage = "1";   
    private String lastLovPageNum = "0";  
    private String[] selectedRowsInLov;
    private String[] mucRefNumbers;
    private String lovStatusFlag;
    private String crnNumber;
    private String uldNum;
    private String[] crnCheck;
	//added by a-3045 for bug 18654 starts
    private String mucResentFlag;
    private String enableFlag;//A-5202
	//added by a-3045 for bug 18654 ends
	//added by a-3045 for bug 19990 starts
    private int recordSize;
	//added by a-3045 for bug 19990 ends

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 */
	public String getProduct() {
		// To be reviewed Auto-generated method stub
		return PRODUCT;
	}
	/**
	 * @return the enableFlag
	 */
	public String getEnableFlag() {
		return enableFlag;
	}
	/**
	 * @param enableFlag the enableFlag to set
	 */
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 */
	public String getScreenId() {
		// To be reviewed Auto-generated method stub
		return SCREENID;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 */
	public String getSubProduct() {
		// To be reviewed Auto-generated method stub
		return SUBPRODUCT;
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
	 * @return the condition
	 */
	public String[] getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String[] condition) {
		this.condition = condition;
	}

	/**
	 * @return the cRN
	 */
	public String[] getCRN() {
		return CRN;
	}

	/**
	 * @param crn the cRN to set
	 */
	public void setCRN(String[] crn) {
		CRN = crn;
	}

	/**
	 * @return the destAirport
	 */
	public String[] getDestAirport() {
		return destAirport;
	}

	/**
	 * @param destAirport the destAirport to set
	 */
	public void setDestAirport(String[] destAirport) {
		this.destAirport = destAirport;
	}

	/**
	 * @return the dmgcode
	 */
	public String[] getDmgcode() {
		return dmgcode;
	}

	/**
	 * @param dmgcode the dmgcode to set
	 */
	public void setDmgcode(String[] dmgcode) {
		this.dmgcode = dmgcode;
	}

	/**
	 * @return the fromCarrier
	 */
	public String[] getFromCarrier() {
		return fromCarrier;
	}

	/**
	 * @param fromCarrier the fromCarrier to set
	 */
	public void setFromCarrier(String[] fromCarrier) {
		this.fromCarrier = fromCarrier;
	}

	/**
	 * @return the iataFilterStatus
	 */
	public String getIataFilterStatus() {
		return iataFilterStatus;
	}

	/**
	 * @param iataFilterStatus the iataFilterStatus to set
	 */
	public void setIataFilterStatus(String iataFilterStatus) {
		this.iataFilterStatus = iataFilterStatus;
	}

	/**
	 * @return the iataStatus
	 */
	public String[] getIataStatus() {
		return iataStatus;
	}

	/**
	 * @param iataStatus the iataStatus to set
	 */
	public void setIataStatus(String[] iataStatus) {
		this.iataStatus = iataStatus;
	}

	/**
	 * @return the mucDate
	 */
	public String[] getMucDate() {
		return mucDate;
	}

	/**
	 * @param mucDate the mucDate to set
	 */
	public void setMucDate(String[] mucDate) {
		this.mucDate = mucDate;
	}

	/**
	 * @return the mucFilterDate
	 */
	public String getMucFilterDate() {
		return mucFilterDate;
	}

	/**
	 * @param mucFilterDate the mucFilterDate to set
	 */
	public void setMucFilterDate(String mucFilterDate) {
		this.mucFilterDate = mucFilterDate;
	}

	/**
	 * @return the mucTime
	 */
	public String[] getMucTime() {
		return mucTime;
	}

	/**
	 * @param mucTime the mucTime to set
	 */
	public void setMucTime(String[] mucTime) {
		this.mucTime = mucTime;
	}

	/**
	 * @return the toCarrier
	 */
	public String[] getToCarrier() {
		return toCarrier;
	}

	/**
	 * @param toCarrier the toCarrier to set
	 */
	public void setToCarrier(String[] toCarrier) {
		this.toCarrier = toCarrier;
	}

	/**
	 * @return the txnAirport
	 */
	public String[] getTxnAirport() {
		return txnAirport;
	}

	/**
	 * @param txnAirport the txnAirport to set
	 */
	public void setTxnAirport(String[] txnAirport) {
		this.txnAirport = txnAirport;
	}

	/**
	 * @return the txntype
	 */
	public String[] getTxntype() {
		return txntype;
	}

	/**
	 * @param txntype the txntype to set
	 */
	public void setTxntype(String[] txntype) {
		this.txntype = txntype;
	}

	/**
	 * @return the uldNumber
	 */
	public String[] getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String[] uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return the displayLovPage
	 */
	public String getDisplayLovPage() {
		return displayLovPage;
	}

	/**
	 * @param displayLovPage the displayLovPage to set
	 */
	public void setDisplayLovPage(String displayLovPage) {
		this.displayLovPage = displayLovPage;
	}

	/**
	 * @return the lastLovPageNum
	 */
	public String getLastLovPageNum() {
		return lastLovPageNum;
	}

	/**
	 * @param lastLovPageNum the lastLovPageNum to set
	 */
	public void setLastLovPageNum(String lastLovPageNum) {
		this.lastLovPageNum = lastLovPageNum;
	}

	/**
	 * @return the lovStatusFlag
	 */
	public String getLovStatusFlag() {
		return lovStatusFlag;
	}

	/**
	 * @param lovStatusFlag the lovStatusFlag to set
	 */
	public void setLovStatusFlag(String lovStatusFlag) {
		this.lovStatusFlag = lovStatusFlag;
	}

	/**
	 * @return the mucRefNumbers
	 */
	public String[] getMucRefNumbers() {
		return mucRefNumbers;
	}

	/**
	 * @param mucRefNumbers the mucRefNumbers to set
	 */
	public void setMucRefNumbers(String[] mucRefNumbers) {
		this.mucRefNumbers = mucRefNumbers;
	}

	/**
	 * @return the selectedRowsInLov
	 */
	public String[] getSelectedRowsInLov() {
		return selectedRowsInLov;
	}

	/**
	 * @param selectedRowsInLov the selectedRowsInLov to set
	 */
	public void setSelectedRowsInLov(String[] selectedRowsInLov) {
		this.selectedRowsInLov = selectedRowsInLov;
	}

	/**
	 * @return the mucReferenceNum
	 */
	public String getMucReferenceNum() {
		return mucReferenceNum;
	}

	/**
	 * @param mucReferenceNum the mucReferenceNum to set
	 */
	public void setMucReferenceNum(String mucReferenceNum) {
		this.mucReferenceNum = mucReferenceNum;
	}

	/**
	 * @return the mucReferenceNumber
	 */
	public String getMucReferenceNumber() {
		return mucReferenceNumber;
	}

	/**
	 * @param mucReferenceNumber the mucReferenceNumber to set
	 */
	public void setMucReferenceNumber(String mucReferenceNumber) {
		this.mucReferenceNumber = mucReferenceNumber;
	}

	/**
	 * @return the selectedrow
	 */
	public String[] getSelectedrow() {
		return selectedrow;
	}

	/**
	 * @param selectedrow the selectedrow to set
	 */
	public void setSelectedrow(String[] selectedrow) {
		this.selectedrow = selectedrow;
	}

	/**
	 * @return the crnNumber
	 */
	public String getCrnNumber() {
		return crnNumber;
	}

	/**
	 * @param crnNumber the crnNumber to set
	 */
	public void setCrnNumber(String crnNumber) {
		this.crnNumber = crnNumber;
	}

	/**
	 * @return the mucLovFilterDate
	 */
	public String getMucLovFilterDate() {
		return mucLovFilterDate;
	}

	/**
	 * @param mucLovFilterDate the mucLovFilterDate to set
	 */
	public void setMucLovFilterDate(String mucLovFilterDate) {
		this.mucLovFilterDate = mucLovFilterDate;
	}

	/**
	 * @return the uldNum
	 */
	public String getUldNum() {
		return uldNum;
	}

	/**
	 * @param uldNum the uldNum to set
	 */
	public void setUldNum(String uldNum) {
		this.uldNum = uldNum;
	}

	/**
	 * @return the crnCheck
	 */
	public String[] getCrnCheck() {
		return crnCheck;
	}

	/**
	 * @param crnCheck the crnCheck to set
	 */
	public void setCrnCheck(String[] crnCheck) {
		this.crnCheck = crnCheck;
	}

	/**
	 * @return the mucResentFlag
	 */
	public String getMucResentFlag() {
		return mucResentFlag;
	}

	/**
	 * @param mucResentFlag the mucResentFlag to set
	 */
	public void setMucResentFlag(String mucResentFlag) {
		this.mucResentFlag = mucResentFlag;
	}

	/**
	 * @return the recordSize
	 */
	public int getRecordSize() {
		return recordSize;
	}

	/**
	 * @param recordSize the recordSize to set
	 */
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	
	

}
