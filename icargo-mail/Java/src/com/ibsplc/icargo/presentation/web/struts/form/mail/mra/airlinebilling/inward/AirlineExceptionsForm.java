/*
 * AirlineExceptionsForm.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward;




import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author Rani Rose John
 * Form for assign exceptions screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 13, 2007   Rani Rose John    		Initial draft
 *  
 */

public class AirlineExceptionsForm extends ScreenModel {

    private static final String BUNDLE = "airlineExceptions";

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.airlineexceptions";
	
	
	/**
	 * From Date
	 */
	private String fromDate;
	/**
	 * To Date
	 */
	private String toDate;
	/**
	 * Airline code
	 */
	private String airlineCode;
	/**
	 * Exception code
	 */
	private String exceptionCode;
	/**
	 * Exception code
	 */
	private String invoiceRefNo;
	
	/**
	 * DSN
	 */
	private String dsn;
	
	/**
	 * save status
	 */
	private String saveStatus;
	
	
	/**
	 * check box values
	 */
	private String[] selectedElements;
	/**
	 * assignee values
	 */
	private String[] assigneeCode;
	
	/**
	 * Assigned Date
	 */
	private String[] assignedDate;
		
	/**
	 * operationFlag values
	 */
	private String[] operationalFlag;
	/**
	 * fromScreenFlag
	 */
	private String fromScreenFlag;
	/**
	 * selectedRows
	 */
	private String[] selectedRows;
	/**
	 * screenStatus
	 */
	private String screenStatus;
	/**
	 * selectedRow
	 */
	private String selectedRow;
	
	private String popupRemarks;
	
	/**
	 * For ANZ CR : AirNZ1011 : Assignee LOV
	 */		
	private String mode;
	
	private String closeStatusFlag;
	private String lastPageNumber = "0";
	private String destinationOfficeOfExchange;
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getMailCategory() {
		return mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}

	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}

	public String getRegisteredIndicator() {
		return registeredIndicator;
	}

	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}

	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	private String originOfficeOfExchange;
	private String year;
	private String subClass;
	private String mailCategory;
	private String highestNumberIndicator;
	private String registeredIndicator;
	private String receptacleSerialNumber;
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

	private String displayPage = "1";
	/**
	 * @return the screenStatus
	 */
	public String getScreenStatus() {
		return screenStatus;
	}

	/**
	 * @param screenStatus the screenStatus to set
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}

	/**
	 * @return Returns the fromScreenFlag.
	 */
	public String getFromScreenFlag() {
		return fromScreenFlag;
	}

	/**
	 * @param fromScreenFlag The fromScreenFlag to set.
	 */
	public void setFromScreenFlag(String fromScreenFlag) {
		this.fromScreenFlag = fromScreenFlag;
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
	 * @return Returns the exceptionCode.
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * @param exceptionCode The exceptionCode to set.
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	
	/**
	 * @return Returns the fromDate.
	 */
	@ DateFieldId(id="ListExceptionDetailsDateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	

	/**
	 * @return Returns the toDate.
	 */
	@ DateFieldId(id="ListExceptionDetailsDateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	
	/**
	 * @return Returns the operationalFlag.
	 */
	public String[] getOperationalFlag() {
		return operationalFlag;
	}

	/**
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
	}

	/**
	 * @return Returns the selectedElements.
	 */
	public String[] getSelectedElements() {
		return selectedElements;
	}

	/**
	 * @param selectedElements The selectedElements to set.
	 */
	public void setSelectedElements(String[] selectedElements) {
		this.selectedElements = selectedElements;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the assignedDate.
	 */
	public String[] getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate The assignedDate to set.
	 */
	public void setAssignedDate(String[] assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the invoiceRefNo.
	 */
	public String getInvoiceRefNo() {
		return invoiceRefNo;
	}

	/**
	 * @param invoiceRefNo The invoiceRefNo to set.
	 */
	public void setInvoiceRefNo(String invoiceRefNo) {
		this.invoiceRefNo = invoiceRefNo;
	}

	/**
	 * @return Returns the assigneeCode.
	 */
	public String[] getAssigneeCode() {
		return assigneeCode;
	}

	/**
	 * @param assigneeCode The assigneeCode to set.
	 */
	public void setAssigneeCode(String[] assigneeCode) {
		this.assigneeCode = assigneeCode;
	}

	/**
	 * @return Returns the saveStatus.
	 */
	public String getSaveStatus() {
		return saveStatus;
	}

	/**
	 * @param saveStatus The saveStatus to set.
	 */
	public void setSaveStatus(String saveStatus) {
		this.saveStatus = saveStatus;
	}

	/**
	 * @return the selectedRows
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows the selectedRows to set
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
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
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the popupRemarks
	 */
	public String getPopupRemarks() {
		return popupRemarks;
	}

	/**
	 * @param popupRemarks the popupRemarks to set
	 */
	public void setPopupRemarks(String popupRemarks) {
		this.popupRemarks = popupRemarks;
	}

	/**
	 * @return the closeStatusFlag
	 */
	public String getCloseStatusFlag() {
		return closeStatusFlag;
	}

	/**
	 * @param closeStatusFlag the closeStatusFlag to set
	 */
	public void setCloseStatusFlag(String closeStatusFlag) {
		this.closeStatusFlag = closeStatusFlag;
	}


}
