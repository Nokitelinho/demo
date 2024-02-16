/*
 * ViewFlownMailForm.java Created on Feb 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author a-2449
 *
 */

public class ViewFlownMailForm extends ScreenModel{
	
	
	private static final String BUNDLE = "flownmailresources";
	
	private static final String PRODUCT = "mra";
	
	private static final String SUBPRODUCT = "flown";
	
	private static final String SCREENID ="mra.flown.viewflownmail";
	
	private String carrierCode;
	
	private String flightNumber;
	
	private String flightDate;
	
	private String flightStatus;
	
	private String segment;
	
	private String flightSegmentStatus;
	
	private String accountingMonth;
	
	private String listSegmentsFlag;
	
	private String duplicateFlightFlag;
	
	private String oneSegmentFlag;
	
	private String processFlag;
	
	private String listFlag;
	
	private String dsnSegmentStatus;
	
	private String fromExceptionScreenFlag;
	
	private String segmentStatus;
	
	private String fromScreen;
	
	private String[] rowCount;
	
	private String accEntryFlag;
	
	private String[] selectedRow;
	
	// added for IASCB-22920 by A-9002		 
    private double displayWgt;	 
    private String displayWgtUnit;
    //Ends
    public double getDisplayWgt() {
		return displayWgt;
	}

	public void setDisplayWgt(double displayWgt) {
		this.displayWgt = displayWgt;
	}

	public String getDisplayWgtUnit() {
		return displayWgtUnit;
	}

	public void setDisplayWgtUnit(String displayWgtUnit) {
		this.displayWgtUnit = displayWgtUnit;
	}

	
	
	
	/**
	 * @return Returns the rowCount.
	 */
	public String[] getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount The rowCount to set.
	 */
	public void setRowCount(String[] rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return Return the fronExceptionScreenFlag
	 */
	public String getFromExceptionScreenFlag() {
		return fromExceptionScreenFlag;
	}

	/**
	 * @param fromExceptionScreenFlag
	 */
	public void setFromExceptionScreenFlag(String fromExceptionScreenFlag) {
		this.fromExceptionScreenFlag = fromExceptionScreenFlag;
	}

	/**
	 * @return Return the listFlag
	 */
	public String getListFlag() {
		return listFlag;
	}

	/**
	 * @param listFlag
	 */
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

	/**
	 * @return Return the processFlag
	 */
	public String getProcessFlag() {
		return processFlag;
	}

	/**
	 * @param processFlag
	 */
	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	/**
	 * @return Return the oneSegmentFlag
	 */
	public String getOneSegmentFlag() {
		return oneSegmentFlag;
	}

	/**
	 * @param oneSegmentFlag
	 */
	public void setOneSegmentFlag(String oneSegmentFlag) {
		this.oneSegmentFlag = oneSegmentFlag;
	}

	/**
	 * @return Returns the DuplicateFlightFlag
	 */
	public String getDuplicateFlightFlag() {
		return duplicateFlightFlag;
	}

	/**
	 * @param duplicateFlightFlag
	 */
	public void setDuplicateFlightFlag(String duplicateFlightFlag) {
		this.duplicateFlightFlag = duplicateFlightFlag;
	}

	/**
	 * @return Returns the ListFlag
	 */
	public String getListSegmentsFlag() {
		return listSegmentsFlag;
	}

	/**
	 * @param listFlag
	 */
	public void setListSegmentsFlag(String listFlag) {
		this.listSegmentsFlag = listFlag;
	}

	/**
	 * @return Returns the AccountingMonth
	 */
	public String getAccountingMonth() {
		return accountingMonth;
	}

	
	/**
	 * @param accountingMonth
	 */
	public void setAccountingMonth(String accountingMonth) {
		this.accountingMonth = accountingMonth;
	}

	/**
	 * @return Returns the CarrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the FlightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the FlightSegment Status
	 */
	public String getFlightSegmentStatus() {
		return flightSegmentStatus;
	}

	/**
	 * @param flightSegmentStatus
	 */
	public void setFlightSegmentStatus(String flightSegmentStatus) {
		this.flightSegmentStatus = flightSegmentStatus;
	}

	/**
	 * @return Returns the Flight Status
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return 
	 * returns the Segment
	 */
	public String getSegment() {
		return segment;
	}

	/**
	 * @param segment
	 */
	public void setSegment(String segment) {
		this.segment = segment;
	}

	
	/**
	 * @return Returns the bundle
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the ScreenId
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return Returns the Product 
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SubProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the segmentStatus.
	 */
	public String getSegmentStatus() {
		return segmentStatus;
	}

	/**
	 * @param segmentStatus The segmentStatus to set.
	 */
	public void setSegmentStatus(String segmentStatus) {
		this.segmentStatus = segmentStatus;
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

	public String getAccEntryFlag() {
		return accEntryFlag;
	}

	public void setAccEntryFlag(String accEntryFlag) {
		this.accEntryFlag = accEntryFlag;
	}

	/**
	 * @return the selectedRow
	 */
	public String[] getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}

	public String getDsnSegmentStatus() {
		return dsnSegmentStatus;
	}

	public void setDsnSegmentStatus(String dsnSegmentStatus) {
		this.dsnSegmentStatus = dsnSegmentStatus;
	}
	
}
