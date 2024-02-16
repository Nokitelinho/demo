/*
 * ReassignMailForm.java Created on Jul 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1876
 *
 */
public class ReassignMailForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.reassignmail";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "reassignMailResources";

	
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
	private String existingMailbagFlag;
	private String currentDialogOption;
	private String currentDialogId;


	private String hideRadio;
	private String selectMode;
	
	private String scanDate;
	private String mailScanTime;
	private String reassignFocus;
	
	private String preassignFlag;

	private String duplicateFlag;
	public String getPreassignFlag() {
		return preassignFlag;
	}

	public void setPreassignFlag(String preassignFlag) {
		this.preassignFlag = preassignFlag;
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
	 * @return the existingMailbagFlag
	 */
	public String getExistingMailbagFlag() {
		return existingMailbagFlag;
	}

	/**
	 * @param existingMailbagFlag the existingMailbagFlag to set
	 */
	public void setExistingMailbagFlag(String existingMailbagFlag) {
		this.existingMailbagFlag = existingMailbagFlag;
	}
	public String getDuplicateFlag() {
		return duplicateFlag;
	}
	public void setDuplicateFlag(String duplicateFlag) {
		this.duplicateFlag = duplicateFlag;
	}

	
}
