/*
 * AssignExceptionsForm.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown;


import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2401
 *
 */
public class AssignExceptionsForm extends ScreenModel{
	
	private static final String BUNDLE = "mraarlassignexceptions";

	//private String bundle;

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.flown.assignexceptions";
	
	private String flightNumber;
	
	private int flightCarrierId;
	
	private String flightCarrierCode;
	
	private String fromDate;
	
	private String toDate;
	
	private String segmentOrigin;
	
	private String segmentDestination;
	
	private String exceptionCode;
	
	private String assignee;
	
	private String assignedDate;
	
	private String despatchNo;
	
	private String resolvedDate;
	
	private String statusFlag;
	
	private String[] selectedElements;
	
	private String[] asigneeCodes;
	
	private String[] operationFlag;
	
	private String exceptionCodeForPrint;
	
	private String viewFlownMailFlag;
	
	/**
	 * @return Returns the fromScreen.
	 */
	public String getViewFlownMailFlag() {
		return viewFlownMailFlag;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setViewFlownMailFlag(String viewFlownMailFlag) {
		this.viewFlownMailFlag = viewFlownMailFlag;
	}

	/**
	 * @return Returns the exceptionCodeForPrint.
	 */
	public String getExceptionCodeForPrint() {
		return exceptionCodeForPrint;
	}

	/**
	 * @param exceptionCodeForPrint The exceptionCodeForPrint to set.
	 */
	public void setExceptionCodeForPrint(String exceptionCodeForPrint) {
		this.exceptionCodeForPrint = exceptionCodeForPrint;
	}

	/**
	 * @return Returns the asigneeCodes.
	 */
	public String[] getAsigneeCodes() {
		return asigneeCodes;
	}

	/**
	 * @param asigneeCodes The asigneeCodes to set.
	 */
	public void setAsigneeCodes(String[] asigneeCodes) {
		this.asigneeCodes = asigneeCodes;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
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
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
	 * @return Returns the assignedDate.
	 */
	public String getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate The assignedDate to set.
	 */
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return Returns the assignee.
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee The assignee to set.
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
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
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="AssignExceptionsDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
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
	 * @return Returns the resolvedDate.
	 */
	public String getResolvedDate() {
		return resolvedDate;
	}

	/**
	 * @param resolvedDate The resolvedDate to set.
	 */
	public void setResolvedDate(String resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="AssignExceptionsDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
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
	 * @return Returns the despatchNo.
	 */
	public String getDespatchNo() {
		return despatchNo;
	}

	/**
	 * @param despatchNo The despatchNo to set.
	 */
	public void setDespatchNo(String despatchNo) {
		this.despatchNo = despatchNo;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightCarrierId.
	 */
	public int getFlightCarrierId() {
		return flightCarrierId;
	}

	/**
	 * @param flightCarrierId The flightCarrierId to set.
	 */
	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
	}

	

	/**
	 * @return Returns the segmentOrigin.
	 */
	public String getSegmentOrigin() {
		return segmentOrigin;
	}

	/**
	 * @param segmentOrigin The segmentOrigin to set.
	 */
	public void setSegmentOrigin(String segmentOrigin) {
		this.segmentOrigin = segmentOrigin;
	}

	/**
	 * @return Returns the segmentDestination.
	 */
	public String getSegmentDestination() {
		return segmentDestination;
	}

	/**
	 * @param segmentDestination The segmentDestination to set.
	 */
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}

}
