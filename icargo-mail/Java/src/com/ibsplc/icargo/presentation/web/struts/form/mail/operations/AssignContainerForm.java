/*
 * AssignContainerForm.java Created on Dec 14, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-1556
 *
 */
public class AssignContainerForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "assignContainerResources";

	private String assignedto;
	private String flightCarrierCode;
	private String flightNumber;
	private String flightDate;
	private String carrier;
	private String destn;
	private String departurePort;

	private String checkAll;
	private String[] subCheck;

	private String status;
	private String flightStatus;
	
	// FIELDS FOR ADD-UPDATE POPUP
	
	private String containerType;
	private String containerNumber;
	private String pou;
	private String PaBuilt;
	private String containerDestination;
	private String remarks;
	private String contCheckAll;
	private String[] contSubCheck;
	
	private String[] fltCarrier;
	private String[] fltNo;
	private String[] depDate;
	private String[] pointOfUnlading;
	private String[] opFlag;
	
	
	private String currentAction;
	private int currentIndex;
	private String currentDialogOption;
	private String currentDialogId;
		
    //flag for override
    private String overrideFlag;
    private String warningCode;
    private String fromScreen;
    
    private String warningFlag;
    private String disableButtonsForTBA;
    private String duplicateAndTbaTbc;
	
	//Added for ICRD-128804
	private boolean barrowCheck;
	public String getWarningFlag() {
		return warningFlag;
	}

	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
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
	 * @return Returns the currentIndex.
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * @param currentIndex The currentIndex to set.
	 */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	/**
	 * @return Returns the fltCarrier.
	 */
	public String[] getFltCarrier() {
		return fltCarrier;
	}

	/**
	 * @param fltCarrier The fltCarrier to set.
	 */
	public void setFltCarrier(String[] fltCarrier) {
		this.fltCarrier = fltCarrier;
	}

	/**
	 * @return Returns the currentAction.
	 */
	public String getCurrentAction() {
		return currentAction;
	}

	/**
	 * @param currentAction The currentAction to set.
	 */
	public void setCurrentAction(String currentAction) {
		this.currentAction = currentAction;
	}

	/**
	 * @return Returns the containerDestination.
	 */
	public String getContainerDestination() {
		return containerDestination;
	}

	/**
	 * @param containerDestination The containerDestination to set.
	 */
	public void setContainerDestination(String containerDestination) {
		this.containerDestination = containerDestination;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the contCheckAll.
	 */
	public String getContCheckAll() {
		return contCheckAll;
	}

	/**
	 * @param contCheckAll The contCheckAll to set.
	 */
	public void setContCheckAll(String contCheckAll) {
		this.contCheckAll = contCheckAll;
	}

	/**
	 * @return Returns the contSubCheck.
	 */
	public String[] getContSubCheck() {
		return contSubCheck;
	}

	/**
	 * @param contSubCheck The contSubCheck to set.
	 */
	public void setContSubCheck(String[] contSubCheck) {
		this.contSubCheck = contSubCheck;
	}
	
	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the flightStatus.
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus The flightStatus to set.
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the assignedto.
	 */
	public String getAssignedto() {
		return assignedto;
	}

	/**
	 * @param assignedto The assignedto to set.
	 */
	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}

	/**
	 * @return Returns the carrier.
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier The carrier to set.
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return Returns the checkAll.
	 */
	public String getCheckAll() {
		return checkAll;
	}

	/**
	 * @param checkAll The checkAll to set.
	 */
	public void setCheckAll(String checkAll) {
		this.checkAll = checkAll;
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
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
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
	 * @return Returns the subCheck.
	 */
	public String[] getSubCheck() {
		return subCheck;
	}

	/**
	 * @param subCheck The subCheck to set.
	 */
	public void setSubCheck(String[] subCheck) {
		this.subCheck = subCheck;
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
	 * @return Returns the depDate.
	 */
	public String[] getDepDate() {
		return depDate;
	}

	/**
	 * @param depDate The depDate to set.
	 */
	public void setDepDate(String[] depDate) {
		this.depDate = depDate;
	}

	/**
	 * @return Returns the fltNo.
	 */
	public String[] getFltNo() {
		return fltNo;
	}

	/**
	 * @param fltNo The fltNo to set.
	 */
	public void setFltNo(String[] fltNo) {
		this.fltNo = fltNo;
	}

	/**
	 * @return Returns the pointOfUnlading.
	 */
	public String[] getPointOfUnlading() {
		return pointOfUnlading;
	}

	/**
	 * @param pointOfUnlading The pointOfUnlading to set.
	 */
	public void setPointOfUnlading(String[] pointOfUnlading) {
		this.pointOfUnlading = pointOfUnlading;
	}

    /**
     * @return Returns the overrideFlag.
     */
    public String getOverrideFlag() {
        return overrideFlag;
    }

    /**
     * @param overrideFlag The overrideFlag to set.
     */
    public void setOverrideFlag(String overrideFlag) {
        this.overrideFlag = overrideFlag;
    }

    /**
     * @param warningCode
     */
    public void setWarningCode(String warningCode) {
        this.warningCode = warningCode;        
    }

    /**
     * @return Returns the warningCode.
     */
    public String getWarningCode() {
        return warningCode;
    }

	/**
	 * @return Returns the opFlag.
	 */
	public String[] getOpFlag() {
		return opFlag;
	}

	/**
	 * @param opFlag The opFlag to set.
	 */
	public void setOpFlag(String[] opFlag) {
		this.opFlag = opFlag;
	}

	/**
	 * @return Returns the paBuilt.
	 */
	public String getPaBuilt() {
		return PaBuilt;
	}

	/**
	 * @param paBuilt The paBuilt to set.
	 */
	public void setPaBuilt(String paBuilt) {
		PaBuilt = paBuilt;
	}

	public String getDestn() {
		return destn;
	}

	public void setDestn(String destn) {
		this.destn = destn;
	}

	/**
	 * @return the disableButtonsForTBA
	 */
	public String getDisableButtonsForTBA() {
		return disableButtonsForTBA;
	}

	/**
	 * @param disableButtonsForTBA the disableButtonsForTBA to set
	 */
	public void setDisableButtonsForTBA(String disableButtonsForTBA) {
		this.disableButtonsForTBA = disableButtonsForTBA;
	}
	public String getDuplicateAndTbaTbc() {
		return duplicateAndTbaTbc;
	}
	public void setDuplicateAndTbaTbc(String duplicateAndTbaTbc) {
		this.duplicateAndTbaTbc = duplicateAndTbaTbc;
	}
	/**
	 * @return the barrowCheck
	 */
	public boolean isBarrowCheck() {
		return barrowCheck;
	}
	/**
	 * @param barrowCheck the barrowCheck to set
	 */
	public void setBarrowCheck(boolean barrowCheck) {
		this.barrowCheck = barrowCheck;
	}

}
