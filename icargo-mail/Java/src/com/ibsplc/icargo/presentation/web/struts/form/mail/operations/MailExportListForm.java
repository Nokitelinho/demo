/*
 * MailExportListForm.java Created on MAR 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class MailExportListForm  extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.mailexportlist";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailexportListResources";

	private String assignToFlight;
	private String assignedto;
	private String flightNo;
	private String flightCarrierCode;
	private String flightNumber;
	private String depDate;
	private String carrierCode;
	private String destination;
	private String departurePort;
	private String[] selectMail;
	private String closeFlag;
	private String duplicateFlightStatus;
	private String disableDestnFlag;
	private String disableSaveFlag;
	private String initialFocus;
	private String fromScreen;
	private String preassignFlag;
	private String selCont;
	private String selDSN;
	private String currentDialogOption;
	private String currentDialogId;
	private String warningFlag;
	private String status;
	
	private String[] selectDSN;
	
	private String reCON;
	private String reDSN;
	private String operationalStatus;
	//Added by A-5249 for the ICRD-84046 starts
	private String warningOveride;
	private String disableButtonsForTBA;
	//Added by A-5160 for ICRD-92105
	 private String tbaTbcWarningFlag;
	 private String duplicateAndTbaTbc;
	//Added by A-7794 as part of ICRD-197439
	  private String disableButtonsForAirport;
	// Added by A-7371 for ICRD-133987
	 private String transferContainerFlag;
	 private String selectedContainer;
	private String[] uldType; /*added by A-8149 for ICRD-270524*/

	 
	
	/**
	 * @return the warningOveride
	 */
	public String getWarningOveride() {
		return warningOveride;
	}

	/**
	 * @param warningOveride the warningOveride to set
	 */
	public void setWarningOveride(String warningOveride) {
		this.warningOveride = warningOveride;
	}

	public String getTbaTbcWarningFlag() {
		return tbaTbcWarningFlag;
	}

	public void setTbaTbcWarningFlag(String tbaTbcWarningFlag) {
		this.tbaTbcWarningFlag = tbaTbcWarningFlag;
	}


	//Added by A-5249 for the ICRD-84046 ends
	/**
	 * @return the reCON
	 */
	public String getReCON() {
		return reCON;
	}

	/**
	 * @param reCON the reCON to set
	 */
	public void setReCON(String reCON) {
		this.reCON = reCON;
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
	 * @return the disableDestnFlag
	 */
	public String getDisableDestnFlag() {
		return disableDestnFlag;
	}

	/**
	 * @param disableDestnFlag the disableDestnFlag to set
	 */
	public void setDisableDestnFlag(String disableDestnFlag) {
		this.disableDestnFlag = disableDestnFlag;
	}

	/**
	 * @return the disableSaveFlag
	 */
	public String getDisableSaveFlag() {
		return disableSaveFlag;
	}

	/**
	 * @param disableSaveFlag the disableSaveFlag to set
	 */
	public void setDisableSaveFlag(String disableSaveFlag) {
		this.disableSaveFlag = disableSaveFlag;
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
	 * @return the preassignFlag
	 */
	public String getPreassignFlag() {
		return preassignFlag;
	}

	/**
	 * @param preassignFlag the preassignFlag to set
	 */
	public void setPreassignFlag(String preassignFlag) {
		this.preassignFlag = preassignFlag;
	}

	/**
	 * @return the selCont
	 */
	public String getSelCont() {
		return selCont;
	}

	/**
	 * @param selCont the selCont to set
	 */
	public void setSelCont(String selCont) {
		this.selCont = selCont;
	}

	/**
	 * @return the selectMail
	 */
	public String[] getSelectMail() {
		return selectMail;
	}

	/**
	 * @param selectMail the selectMail to set
	 */
	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
	}

	/**
	 * @return the warningFlag
	 */
	public String getWarningFlag() {
		return warningFlag;
	}

	/**
	 * @param warningFlag the warningFlag to set
	 */
	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}

	/**
	 * @return the selectDSN
	 */
	public String[] getSelectDSN() {
		return selectDSN;
	}

	/**
	 * @param selectDSN the selectDSN to set
	 */
	public void setSelectDSN(String[] selectDSN) {
		this.selectDSN = selectDSN;
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
	 * @return the assignedto
	 */
	public String getAssignedto() {
		return assignedto;
	}

	/**
	 * @param assignedto the assignedto to set
	 */
	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}

	/**
	 * @return the selDSN
	 */
	public String getSelDSN() {
		return selDSN;
	}

	/**
	 * @param selDSN the selDSN to set
	 */
	public void setSelDSN(String selDSN) {
		this.selDSN = selDSN;
	}

	/**
	 * @return the operationalStatus
	 */
	public String getOperationalStatus() {
		return operationalStatus;
	}

	/**
	 * @param operationalStatus the operationalStatus to set
	 */
	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
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
	 * @return the disableButtonsForAirport
	 * Added by A-7794 as part of ICRD-197439
	 */
	public String getDisableButtonsForAirport() {
		return disableButtonsForAirport;
	}

	/**
	 * @param disableButtonsForAirport the disableButtonsForAirport to set
	 * Added by A-7794 as part of ICRD-197439
	 */
	public void setDisableButtonsForAirport(String disableButtonsForAirport) {
		this.disableButtonsForAirport = disableButtonsForAirport;
	}
	/**
	 * 
	 * @return transferContainerFlag
	 * Added by A-7371 for ICRD-133987
	 */
	public String getTransferContainerFlag() {
		return transferContainerFlag;
	}
	/**
	 * 
	 * @param transferContainerFlag the transferContainerFlag to set
	 * Added by A-7371 for ICRD-133987
	 */
	public void setTransferContainerFlag(String transferContainerFlag) {
		this.transferContainerFlag = transferContainerFlag;
	}
	/**
	 * 
	 * @return selectedContainer
	 * Added by A-7371 for ICRD-133987
	 */
	public String getSelectedContainer() {
		return selectedContainer;
	}
	/**
	 * 
	 * @param selectedContainer the selectedContainer to set
	 * Added by A-7371 for ICRD-133987
	 */
	public void setSelectedContainer(String selectedContainer) {
		this.selectedContainer = selectedContainer;
	}
	
	/**
	 * @author A-8149
	 * @return uldType
	 */
	 public String[] getUldType() {
			return uldType;
		}

		public void setUldType(String[] uldType) {
			this.uldType = uldType;
		}

}
