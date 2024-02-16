/*
 * ReassignDSNForm.java Created on Apr 01, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * @author RENO K ABRAHAM
 * Command class for List FlightDetails.
 *
 * Revision History
 *
 * Version      	Date      	    Author        		Description
 *
 *  0.1         APR 01, 2008  	 RENO K ABRAHAM         Coding
 */
public class ReassignDSNForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.reassigndsn";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "reassignDSNResources";

	
	private String assignToFlight;
	private String flightNo;
	private String flightCarrierCode;
	private String flightNumber;
	private String depDate;
	private String carrierCode;
	private String destination;
	private String departurePort;
	private String[] selectContainer;
	private String closeFlag;
	private String duplicateFlightStatus;
	private String container;
	private String initialFocus;
	private String fromScreen;
	private int carrierId;
	private int carrierIdInv;
	private String carrierCodeInv;
	
	private String currentDialogOption;
	private String currentDialogId;


	private String hideRadio;
	private String selectMode;
	
	private String scanDate;
	private String mailScanTime;
	private String reassignFocus;

	private String seldsn;
	private String seldsninfo;
	private String[] reAssignedPcs;
	@MeasureAnnotation(mappedValue="reAssignedWtMeasure",unitType="MWT")
	private String[] reAssignedWt;
	private Measure[] reAssignedWtMeasure;
	private String status;
	private String screenStatus;
	
	private String lastPageNum="0";
	private String displayPage="1";

	private String checkAll;
	private String[] selectedRows;
	private String selectedConsignment;

	private String fromFlightCarrierCode;
	private String fromFlightNumber;
	private String frmFlightDate;
	private String frmassignTo;
	private String fromdestination;
	/**
	 * 
	 * @return reAssignedWtMeasure
	 */
	public Measure[] getReAssignedWtMeasure() {
		return reAssignedWtMeasure;
	}
/**
 * 
 * @param reAssignedWtMeasure
 */
	public void setReAssignedWtMeasure(Measure[] reAssignedWtMeasure) {
		this.reAssignedWtMeasure = reAssignedWtMeasure;
	}

	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
     */
    public String getSubProduct() {
        return SUBPRODUCT_NAME;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the currentDialogId.
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

	/**
	 * @param currentDialogId The currentDialogId to set.
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * @return Returns the currentDialogOption.
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption The currentDialogOption to set.
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}
	
	/**
	 * @return Returns the departurePort.
	 */
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePort The departurePort to set.
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return this.closeFlag;
	}

	/**
	 * @param closeFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}
	
	/**
	 * @return Returns the flightNo.
	 */
	public String getFlightNo() {
		return this.flightNo;
	}

	/**
	 * @param flightNo The flightNo to set.
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return Returns the assignToFlight.
	 */
	public String getAssignToFlight() {
		return this.assignToFlight;
	}

	/**
	 * @param assignToFlight The assignToFlight to set.
	 */
	public void setAssignToFlight(String assignToFlight) {
		this.assignToFlight = assignToFlight;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return this.carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the depDate.
	 */
	public String getDepDate() {
		return this.depDate;
	}

	/**
	 * @param depDate The depDate to set.
	 */
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	/**
	 * @return Returns the selectContainer.
	 */
	public String[] getSelectContainer() {
		return this.selectContainer;
	}

	/**
	 * @param selectContainer The selectContainer to set.
	 */
	public void setSelectContainer(String[] selectContainer) {
		this.selectContainer = selectContainer;
	}

	/**
	 * @return Returns the duplicateFlightStatus.
	 */
	public String getDuplicateFlightStatus() {
		return this.duplicateFlightStatus;
	}

	/**
	 * @param duplicateFlightStatus The duplicateFlightStatus to set.
	 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	/**
	 * @return Returns the initialFocus.
	 */
	public String getInitialFocus() {
		return this.initialFocus;
	}

	/**
	 * @param initialFocus The initialFocus to set.
	 */
	public void setInitialFocus(String initialFocus) {
		this.initialFocus = initialFocus;
	}
	
	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return this.fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the container.
	 */
	public String getContainer() {
		return this.container;
	}

	/**
	 * @param container The container to set.
	 */
	public void setContainer(String container) {
		this.container = container;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return this.carrierId;
	}

	/**
	 * @param carrierId The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return Returns the carrierIdInv.
	 */
	public int getCarrierIdInv() {
		return carrierIdInv;
	}

	/**
	 * @param carrierIdInv The carrierIdInv to set.
	 */
	public void setCarrierIdInv(int carrierIdInv) {
		this.carrierIdInv = carrierIdInv;
	}

	/**
	 * @return Returns the carrierCodeInv.
	 */
	public String getCarrierCodeInv() {
		return carrierCodeInv;
	}

	/**
	 * @param carrierCodeInv The carrierCodeInv to set.
	 */
	public void setCarrierCodeInv(String carrierCodeInv) {
		this.carrierCodeInv = carrierCodeInv;
	}

	public String getHideRadio() {
		return hideRadio;
	}

	public void setHideRadio(String hideRadio) {
		this.hideRadio = hideRadio;
	}

	public String getSelectMode() {
		return selectMode;
	}

	public void setSelectMode(String selectMode) {
		this.selectMode = selectMode;
	}

	public String getMailScanTime() {
		return mailScanTime;
	}

	public void setMailScanTime(String mailScanTime) {
		this.mailScanTime = mailScanTime;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getReassignFocus() {
		return reassignFocus;
	}

	public void setReassignFocus(String reassignFocus) {
		this.reassignFocus = reassignFocus;
	}

	/**
	 * @return the seldsninfo
	 */
	public String getSeldsninfo() {
		return seldsninfo;
	}

	/**
	 * @param seldsninfo the seldsninfo to set
	 */
	public void setSeldsninfo(String seldsninfo) {
		this.seldsninfo = seldsninfo;
	}

	/**
	 * @return the seldsn
	 */
	public String getSeldsn() {
		return seldsn;
	}

	/**
	 * @param seldsn the seldsn to set
	 */
	public void setSeldsn(String seldsn) {
		this.seldsn = seldsn;
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
	 * @return the checkAll
	 */
	public String getCheckAll() {
		return checkAll;
	}

	/**
	 * @param checkAll the checkAll to set
	 */
	public void setCheckAll(String checkAll) {
		this.checkAll = checkAll;
	}

	/**
	 * @return the selectedConsignment
	 */
	public String getSelectedConsignment() {
		return selectedConsignment;
	}

	/**
	 * @param selectedConsignment the selectedConsignment to set
	 */
	public void setSelectedConsignment(String selectedConsignment) {
		this.selectedConsignment = selectedConsignment;
	}

	/**
	 * @return the reAssignedPcs
	 */
	public String[] getReAssignedPcs() {
		return reAssignedPcs;
	}

	/**
	 * @param reAssignedPcs the reAssignedPcs to set
	 */
	public void setReAssignedPcs(String[] reAssignedPcs) {
		this.reAssignedPcs = reAssignedPcs;
	}

	/**
	 * @return the reAssignedWt
	 */
	public String[] getReAssignedWt() {
		return reAssignedWt;
	}

	/**
	 * @param reAssignedWt the reAssignedWt to set
	 */
	public void setReAssignedWt(String[] reAssignedWt) {
		this.reAssignedWt = reAssignedWt;
	}

	/**
	 * @return the frmassignTo
	 */
	public String getFrmassignTo() {
		return frmassignTo;
	}

	/**
	 * @param frmassignTo the frmassignTo to set
	 */
	public void setFrmassignTo(String frmassignTo) {
		this.frmassignTo = frmassignTo;
	}

	/**
	 * @return the frmFlightDate
	 */
	public String getFrmFlightDate() {
		return frmFlightDate;
	}

	/**
	 * @param frmFlightDate the frmFlightDate to set
	 */
	public void setFrmFlightDate(String frmFlightDate) {
		this.frmFlightDate = frmFlightDate;
	}

	/**
	 * @return the fromdestination
	 */
	public String getFromdestination() {
		return fromdestination;
	}

	/**
	 * @param fromdestination the fromdestination to set
	 */
	public void setFromdestination(String fromdestination) {
		this.fromdestination = fromdestination;
	}

	/**
	 * @return the fromFlightCarrierCode
	 */
	public String getFromFlightCarrierCode() {
		return fromFlightCarrierCode;
	}

	/**
	 * @param fromFlightCarrierCode the fromFlightCarrierCode to set
	 */
	public void setFromFlightCarrierCode(String fromFlightCarrierCode) {
		this.fromFlightCarrierCode = fromFlightCarrierCode;
	}

	/**
	 * @return the fromFlightNumber
	 */
	public String getFromFlightNumber() {
		return fromFlightNumber;
	}

	/**
	 * @param fromFlightNumber the fromFlightNumber to set
	 */
	public void setFromFlightNumber(String fromFlightNumber) {
		this.fromFlightNumber = fromFlightNumber;
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


}
