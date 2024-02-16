/*
 * ReassignMailbagForm.java 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3251
 *
 */
public class ReassignMailbagForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.reassignmailbag";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "reassignMailbagResources";
	private String assignToFlight;
	private String flightCarrierCode;
	private String flightNumber;
	private String depDate;
	private String carrierCode;
	private String destination;
	private String  hideRadio;
	private String  departurePort;
	private String checkAll;
	private String[] selectedRows;
	private int carrierId;
	private String duplicateFlightStatus;
	private String[] selectContainer;
	private String reDSN;
	private String selmailbags;
	
	
	
	
	private String flightNo;
	private String closeFlag;
	private String container;
	private String initialFocus;
	
	private String fromScreen;
	private String fromFlightCarrierCode;
	private String fromFlightNumber;
	private String frmFlightDate;
	private String frmassignTo;
	private String fromdestination;
	
	private int carrierIdInv;
	private String carrierCodeInv;	
	private String currentDialogOption;	
	private String currentDialogId;
	private String selectMode;
	private String scanDate;
	private String mailScanTime;
	private String reassignFocus;
	
	private int lastPageNum;
	private int displayPage;
	private String next;
	
	/**
	 * @return the selmailbags
	 */
	public String getSelmailbags() {
		return selmailbags;
	}

	/**
	 * @param selmailbags the selmailbags to set
	 */
	public void setSelmailbags(String selmailbags) {
		this.selmailbags = selmailbags;
	}

	/**
	 * @return the reDSN
	 */
	public String getReDSN() {
		return reDSN;
	}

	/**
	 * @param reDSN the reDSN to set
	 */
	public void setReDSN(String reDSN) {
		this.reDSN = reDSN;
	}

	/**
	 * @return the selectContainer
	 */
	public String[] getSelectContainer() {
		return selectContainer;
	}

	/**
	 * @param selectContainer the selectContainer to set
	 */
	public void setSelectContainer(String[] selectContainer) {
		this.selectContainer = selectContainer;
	}

	/**
	 * @return the duplicateFlightStatus
	 */
	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}

	/**
	 * @param duplicateFlightStatus the duplicateFlightStatus to set
	 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	/**
	 * @return the carrierId
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId the carrierId to set
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
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
	 * @return the departurePort
	 */
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePort the departurePort to set
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	/**
	 * @return the hideRadio
	 */
	public String getHideRadio() {
		return hideRadio;
	}

	/**
	 * @param hideRadio the hideRadio to set
	 */
	public void setHideRadio(String hideRadio) {
		this.hideRadio = hideRadio;
	}

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the depDate
	 */
	public String getDepDate() {
		return depDate;
	}

	/**
	 * @param depDate the depDate to set
	 */
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	/**
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the assignToFlight
	 */
	public String getAssignToFlight() {
		return assignToFlight;
	}

	/**
	 * @param assignToFlight the assignToFlight to set
	 */
	public void setAssignToFlight(String assignToFlight) {
		this.assignToFlight = assignToFlight;
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
	 * @return the carrierCodeInv
	 */
	public String getCarrierCodeInv() {
		return carrierCodeInv;
	}

	/**
	 * @param carrierCodeInv the carrierCodeInv to set
	 */
	public void setCarrierCodeInv(String carrierCodeInv) {
		this.carrierCodeInv = carrierCodeInv;
	}

	/**
	 * @return the carrierIdInv
	 */
	public int getCarrierIdInv() {
		return carrierIdInv;
	}

	/**
	 * @param carrierIdInv the carrierIdInv to set
	 */
	public void setCarrierIdInv(int carrierIdInv) {
		this.carrierIdInv = carrierIdInv;
	}

	/**
	 * @return the closeFlag
	 */
	public String getCloseFlag() {
		return closeFlag;
	}

	/**
	 * @param closeFlag the closeFlag to set
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return the container
	 */
	public String getContainer() {
		return container;
	}

	/**
	 * @param container the container to set
	 */
	public void setContainer(String container) {
		this.container = container;
	}

	/**
	 * @return the currentDialogId
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

	/**
	 * @param currentDialogId the currentDialogId to set
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * @return the currentDialogOption
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption the currentDialogOption to set
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * @param flightNo the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return the initialFocus
	 */
	public String getInitialFocus() {
		return initialFocus;
	}

	/**
	 * @param initialFocus the initialFocus to set
	 */
	public void setInitialFocus(String initialFocus) {
		this.initialFocus = initialFocus;
	}

	/**
	 * @return the mailScanTime
	 */
	public String getMailScanTime() {
		return mailScanTime;
	}

	/**
	 * @param mailScanTime the mailScanTime to set
	 */
	public void setMailScanTime(String mailScanTime) {
		this.mailScanTime = mailScanTime;
	}

	/**
	 * @return the reassignFocus
	 */
	public String getReassignFocus() {
		return reassignFocus;
	}

	/**
	 * @param reassignFocus the reassignFocus to set
	 */
	public void setReassignFocus(String reassignFocus) {
		this.reassignFocus = reassignFocus;
	}

	/**
	 * @return the scanDate
	 */
	public String getScanDate() {
		return scanDate;
	}

	/**
	 * @param scanDate the scanDate to set
	 */
	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	/**
	 * @return the selectMode
	 */
	public String getSelectMode() {
		return selectMode;
	}

	/**
	 * @param selectMode the selectMode to set
	 */
	public void setSelectMode(String selectMode) {
		this.selectMode = selectMode;
	}

	/**
	 * @return the displayPage
	 */
	public int getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return the lastPageNum
	 */
	public int getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum the lastPageNum to set
	 */
	public void setLastPageNum(int lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return the next
	 */
	public String getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(String next) {
		this.next = next;
	}

	public String getFrmassignTo() {
		return frmassignTo;
	}

	public void setFrmassignTo(String frmassignTo) {
		this.frmassignTo = frmassignTo;
	}

	public String getFrmFlightDate() {
		return frmFlightDate;
	}

	public void setFrmFlightDate(String frmFlightDate) {
		this.frmFlightDate = frmFlightDate;
	}

	public String getFromFlightCarrierCode() {
		return fromFlightCarrierCode;
	}

	public void setFromFlightCarrierCode(String fromFlightCarrierCode) {
		this.fromFlightCarrierCode = fromFlightCarrierCode;
	}

	public String getFromFlightNumber() {
		return fromFlightNumber;
	}

	public void setFromFlightNumber(String fromFlightNumber) {
		this.fromFlightNumber = fromFlightNumber;
	}

	public String getFromdestination() {
		return fromdestination;
	}

	public void setFromdestination(String fromdestination) {
		this.fromdestination = fromdestination;
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
