/*
 * MailManifestForm.java Created on Jul 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * @author A-1876
 *
 */
public class MailManifestForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailManifestResources";
	
	private String flightCarrierCode;
	private String flightNumber;
	private String depDate;
	private String departurePort;
	private String[] selectMail;
	private String[] selectDSN;
	private String openAttachAWB;
	private String operationalStatus;
	private String pou;
	private String pol;
	
	// Fields for AttachAWB Popup
	
	private String shipmentPrefix;
	private String documentNumber;
	private String origin;
	private String destination;
	private int stdPieces;
	
	@MeasureAnnotation(mappedValue="stdWeightMeasure",unitType="MWT")
	private double stdWeight;
	private Measure stdWeightMeasure;
	
	private String weightStandard;
	private String shipmentDesc;
	private String screenStatus;
	private String agentCode;
	private String autoAttach;
	private String selectChild;
	
	//Generate Manifest popup
	
	private String printType;
	
//	Move Manifestbag popup
	private String uld;
	private String containerType;
	private String selctDSN;
	private String moveMailFlag;
	private String compoCheck;
	private String selectMod;
	private String type;
	private String bulkDisable;
	//for mailsummary link
	private String mailFlightSummary;
	private String mailFlighCarrierCode;
	private String mailFlightNumber;
	private String maildepDate;
	private String overrideContainerFlag;
	
	private String routingAvl;
	
	
	//FOR BUG 50584 ENHANCEMENT
	private String conDocNo;
	private String paCode;
	private String direction;
	private String conDate;
	private String contype;
	
	private String[] selectRoute;
	private String flightNo;
	private String[] flightRouteCarrierCode;
	private String[] flightRouteNumber;
	private String[] depRouteDate;
	private String[] pouRoute;
	private String[] polRoute;
	
	private String remarks;
	private String routeOpFlag[];
	private String routingStatus;
	private String attachRouting;
	private String[] csgDocNum;
	private String[] paCod;
	private String csgDocNumForRouting;
	private String paCodeForRouting;
	// ADDED FOR BUG 82870 
	private String newRoutingFlag;
	private String fromScreen;
	
	//Added for CR ICMN-2337
	private String[] operationalFlag;
	
	private String warningFlag;
	private String warningOveride;
	private String disableButtonsForTBA;
	
	//Added by A-7794 as part of ICRD-197439
	private String disableButtonsForAirport;
	
	/**
	 * @return the operationalFlag
	 */
	public String[] getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * @param operationalFlag the operationalFlag to set
	 */
	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 * autoAttachAWB is to know whether the AutoAttachAWB process
	 * is success or not,and hence to show an INFO saying,
	 * the process was success 
	 */
	private String autoAttachAWB;
	
	/**
	 * parentContainer is to know in js, the "container" of which,
	 *  DSNs are selected on click of view mail details
	 */
	private String parentContainer;
	
	/**
	 * duplicateFlightStatus in js, is to get Duplicate flight pop up
	 */
	private String duplicateFlightStatus;
	
	/**
	 * disableSaveFlag in js, is to Diasable save btn, when no results found
	 */
	private String disableSaveFlag;
	
	/**
	 * initialFocus in js, is to set focus to First txt fld of jsp onload.
	 */
	private String initialFocus;
	
   /**
    * For View Mail Details popup
    */
	private String containerNo;
	private String paBuilt;
	private String onwardFlights;
	 private String duplicateAndTbaTbc;
	
	/**
	 * selestTab in js, is to identify which tab to be shown.
	 */
	private String selectTab;
		
	/**
	 * uldsSelectedFlag in js, is to get NotAcceptedULDs pop up
	 */
	private String uldsSelectedFlag;
	/**
	 * uldsPopupCloseFlag in js, is to show info msg for closed flight 
	 * while relisting.
	 */
	private String uldsPopupCloseFlag;
	
	/**
	 * Density For Mail 
	 */
	private String density;
	
	
	/**
	 * @return Returns the csgDocNum.
	 */
	public String[] getCsgDocNum() {
		return csgDocNum;
	}

	/**
	 * @param csgDocNum The csgDocNum to set.
	 */
	public void setCsgDocNum(String[] csgDocNum) {
		this.csgDocNum = csgDocNum;
	}

	/**
	 * @return the stdWeightMeasure
	 */
	public Measure getStdWeightMeasure() {
		return stdWeightMeasure;
	}
	/**
	 * @param stdWeightMeasure the stdWeightMeasure to set
	 */
	public void setStdWeightMeasure(Measure stdWeightMeasure) {
		this.stdWeightMeasure = stdWeightMeasure;
	}
	/**
	 * @return Returns the pa.
	 */
	public String[] getPaCod() {
		return paCod;
	}

	/**
	 * @param pa The pa to set.
	 */
	public void setPaCod(String[] paCod) {
		this.paCod = paCod;
	}

	/**
	 * @return Returns the routeOpFlag.
	 */
	public String[] getRouteOpFlag() {
		return routeOpFlag;
	}

	/**
	 * @param routeOpFlag The routeOpFlag to set.
	 */
	public void setRouteOpFlag(String[] routeOpFlag) {
		this.routeOpFlag = routeOpFlag;
	}

	/**
	 * @return Returns the conDate.
	 */
	public String getConDate() {
		return conDate;
	}

	/**
	 * @param conDate The conDate to set.
	 */
	public void setConDate(String conDate) {
		this.conDate = conDate;
	}

	/**
	 * @return Returns the conDocNo.
	 */
	public String getConDocNo() {
		return conDocNo;
	}

	/**
	 * @param conDocNo The conDocNo to set.
	 */
	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}

	/**
	 * @return Returns the contype.
	 */
	public String getContype() {
		return contype;
	}

	/**
	 * @param contype The contype to set.
	 */
	public void setContype(String contype) {
		this.contype = contype;
	}

	/**
	 * @return Returns the depRouteDate.
	 */
	public String[] getDepRouteDate() {
		return depRouteDate;
	}

	/**
	 * @param depRouteDate The depRouteDate to set.
	 */
	public void setDepRouteDate(String[] depRouteDate) {
		this.depRouteDate = depRouteDate;
	}

	/**
	 * @return Returns the direction.
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction The direction to set.
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return Returns the flightNo.
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * @param flightNo The flightNo to set.
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return Returns the flightRouteCarrierCode.
	 */
	public String[] getFlightRouteCarrierCode() {
		return flightRouteCarrierCode;
	}

	/**
	 * @param flightRouteCarrierCode The flightRouteCarrierCode to set.
	 */
	public void setFlightRouteCarrierCode(String[] flightRouteCarrierCode) {
		this.flightRouteCarrierCode = flightRouteCarrierCode;
	}

	/**
	 * @return Returns the flightRouteNumber.
	 */
	public String[] getFlightRouteNumber() {
		return flightRouteNumber;
	}

	/**
	 * @param flightRouteNumber The flightRouteNumber to set.
	 */
	public void setFlightRouteNumber(String[] flightRouteNumber) {
		this.flightRouteNumber = flightRouteNumber;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the polRoute.
	 */
	public String[] getPolRoute() {
		return polRoute;
	}

	/**
	 * @param polRoute The polRoute to set.
	 */
	public void setPolRoute(String[] polRoute) {
		this.polRoute = polRoute;
	}

	/**
	 * @return Returns the pouRoute.
	 */
	public String[] getPouRoute() {
		return pouRoute;
	}

	/**
	 * @param pouRoute The pouRoute to set.
	 */
	public void setPouRoute(String[] pouRoute) {
		this.pouRoute = pouRoute;
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
	 * @return Returns the selectRoute.
	 */
	public String[] getSelectRoute() {
		return selectRoute;
	}

	/**
	 * @param selectRoute The selectRoute to set.
	 */
	public void setSelectRoute(String[] selectRoute) {
		this.selectRoute = selectRoute;
	}

	/**
	 * @return Returns the agentCode.
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode The agentCode to set.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
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
	 * @return Returns the selectMail.
	 */
	public String[] getSelectMail() {
		return this.selectMail;
	}

	/**
	 * @param selectMail The selectMail to set.
	 */
	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
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
	 * @return Returns the containerNo.
	 */
	public String getContainerNo() {
		return this.containerNo;
	}

	/**
	 * @param containerNo The containerNo to set.
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * @return Returns the onwardFlights.
	 */
	public String getOnwardFlights() {
		return this.onwardFlights;
	}

	/**
	 * @param onwardFlights The onwardFlights to set.
	 */
	public void setOnwardFlights(String onwardFlights) {
		this.onwardFlights = onwardFlights;
	}

	/**
	 * @return Returns the paBuilt.
	 */
	public String getPaBuilt() {
		return this.paBuilt;
	}

	/**
	 * @param paBuilt The paBuilt to set.
	 */
	public void setPaBuilt(String paBuilt) {
		this.paBuilt = paBuilt;
	}

	/**
	 * @return Returns the disableSaveFlag.
	 */
	public String getDisableSaveFlag() {
		return this.disableSaveFlag;
	}

	/**
	 * @param disableSaveFlag The disableSaveFlag to set.
	 */
	public void setDisableSaveFlag(String disableSaveFlag) {
		this.disableSaveFlag = disableSaveFlag;
	}

	/**
	 * @return Returns the uldsSelectedFlag.
	 */
	public String getUldsSelectedFlag() {
		return uldsSelectedFlag;
	}

	/**
	 * @param uldsSelectedFlag The uldsSelectedFlag to set.
	 */
	public void setUldsSelectedFlag(String uldsSelectedFlag) {
		this.uldsSelectedFlag = uldsSelectedFlag;
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
	 * @return Returns the uldsPopupCloseFlag.
	 */
	public String getUldsPopupCloseFlag() {
		return uldsPopupCloseFlag;
	}

	/**
	 * @param uldsPopupCloseFlag The uldsPopupCloseFlag to set.
	 */
	public void setUldsPopupCloseFlag(String uldsPopupCloseFlag) {
		this.uldsPopupCloseFlag = uldsPopupCloseFlag;
	}

	/**
	 * @return Returns the selectDSN.
	 */
	public String[] getSelectDSN() {
		return this.selectDSN;
	}

	/**
	 * @param selectDSN The selectDSN to set.
	 */
	public void setSelectDSN(String[] selectDSN) {
		this.selectDSN = selectDSN;
	}

	/**
	 * @return Returns the parentContainer.
	 */
	public String getParentContainer() {
		return this.parentContainer;
	}

	/**
	 * @param parentContainer The parentContainer to set.
	 */
	public void setParentContainer(String parentContainer) {
		this.parentContainer = parentContainer;
	}

	/**
	 * @return Returns the selectTab.
	 */
	public String getSelectTab() {
		return this.selectTab;
	}

	/**
	 * @param selectTab The selectTab to set.
	 */
	public void setSelectTab(String selectTab) {
		this.selectTab = selectTab;
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
	 * @return Returns the documentNumber.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber The documentNumber to set.
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the shipmentDesc.
	 */
	public String getShipmentDesc() {
		return shipmentDesc;
	}

	/**
	 * @param shipmentDesc The shipmentDesc to set.
	 */
	public void setShipmentDesc(String shipmentDesc) {
		this.shipmentDesc = shipmentDesc;
	}

	/**
	 * @return Returns the shipmentPrefix.
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	/**
	 * @param shipmentPrefix The shipmentPrefix to set.
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}

	/**
	 * @return Returns the stdPieces.
	 */
	public int getStdPieces() {
		return stdPieces;
	}

	/**
	 * @param stdPieces The stdPieces to set.
	 */
	public void setStdPieces(int stdPieces) {
		this.stdPieces = stdPieces;
	}

	/**
	 * @return Returns the stdWeight.
	 */
	public double getStdWeight() {
		return stdWeight;
	}

	/**
	 * @param stdWeight The stdWeight to set.
	 */
	public void setStdWeight(double stdWeight) {
		this.stdWeight = stdWeight;
	}

	/**
	 * @return Returns the weightStandard.
	 */
	public String getWeightStandard() {
		return weightStandard;
	}

	/**
	 * @param weightStandard The weightStandard to set.
	 */
	public void setWeightStandard(String weightStandard) {
		this.weightStandard = weightStandard;
	}

	/**
	 * @return Returns the openAttachAWB.
	 */
	public String getOpenAttachAWB() {
		return this.openAttachAWB;
	}

	/**
	 * @param openAttachAWB The openAttachAWB to set.
	 */
	public void setOpenAttachAWB(String openAttachAWB) {
		this.openAttachAWB = openAttachAWB;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return this.pou;
	}

	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the screenStatus.
	 */
	public String getScreenStatus() {
		return screenStatus;
	}

	/**
	 * @param screenStatus The screenStatus to set.
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}

	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return this.pol;
	}

	/**
	 * @param pol The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the operationalStatus.
	 */
	public String getOperationalStatus() {
		return operationalStatus;
	}

	/**
	 * @param operationalStatus The operationalStatus to set.
	 */
	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	/**
	 * @return Returns the printType.
	 */
	public String getPrintType() {
		return this.printType;
	}

	/**
	 * @param printType The printType to set.
	 */
	public void setPrintType(String printType) {
		this.printType = printType;
	}

	/**
	 * @return Returns the autoAttach.
	 */
	public String getAutoAttach() {
		return autoAttach;
	}

	/**
	 * @param autoAttach The autoAttach to set.
	 */
	public void setAutoAttach(String autoAttach) {
		this.autoAttach = autoAttach;
	}

	/**
	 * @return Returns the selectChild.
	 */
	public String getSelectChild() {
		return selectChild;
	}

	/**
	 * @param selectChild The selectChild to set.
	 */
	public void setSelectChild(String selectChild) {
		this.selectChild = selectChild;
	}
	public String getUld() {
		return uld;
	}

	public void setUld(String uld) {
		this.uld = uld;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getSelctDSN() {
		return selctDSN;
	}

	public void setSelctDSN(String selctDSN) {
		this.selctDSN = selctDSN;
	}

	public String getMoveMailFlag() {
		return moveMailFlag;
	}

	public void setMoveMailFlag(String moveMailFlag) {
		this.moveMailFlag = moveMailFlag;
	}

	public String getMaildepDate() {
		return maildepDate;
	}

	public void setMaildepDate(String maildepDate) {
		this.maildepDate = maildepDate;
	}

	public String getMailFlighCarrierCode() {
		return mailFlighCarrierCode;
	}

	public void setMailFlighCarrierCode(String mailFlighCarrierCode) {
		this.mailFlighCarrierCode = mailFlighCarrierCode;
	}

	public String getMailFlightNumber() {
		return mailFlightNumber;
	}

	public void setMailFlightNumber(String mailFlightNumber) {
		this.mailFlightNumber = mailFlightNumber;
	}

	public String getMailFlightSummary() {
		return mailFlightSummary;
	}

	public void setMailFlightSummary(String mailFlightSummary) {
		this.mailFlightSummary = mailFlightSummary;
	}

	public String getCompoCheck() {
		return compoCheck;
	}

	public void setCompoCheck(String compoCheck) {
		this.compoCheck = compoCheck;
	}

	public String getSelectMod() {
		return selectMod;
	}

	public void setSelectMod(String selectMod) {
		this.selectMod = selectMod;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOverrideContainerFlag() {
		return overrideContainerFlag;
	}

	public void setOverrideContainerFlag(String overrideContainerFlag) {
		this.overrideContainerFlag = overrideContainerFlag;
	}

	/**
	 * @return Returns the autoAttachAWB.
	 */
	public String getAutoAttachAWB() {
		return autoAttachAWB;
	}

	/**
	 * @param autoAttachAWB The autoAttachAWB to set.
	 */
	public void setAutoAttachAWB(String autoAttachAWB) {
		this.autoAttachAWB = autoAttachAWB;
	}

	public String getBulkDisable() {
		return bulkDisable;
	}

	public void setBulkDisable(String bulkDisable) {
		this.bulkDisable = bulkDisable;
	}

	/**
	 * @return the routingAvl
	 */
	public String getRoutingAvl() {
		return routingAvl;
	}

	/**
	 * @param routingAvl the routingAvl to set
	 */
	public void setRoutingAvl(String routingAvl) {
		this.routingAvl = routingAvl;
	}

	/**
	 * @return the density
	 */
	public String getDensity() {
		return density;
	}

	/**
	 * @param density the density to set
	 */
	public void setDensity(String density) {
		this.density = density;
	}

	/**
	 * @return Returns the routingStatus.
	 */
	public String getRoutingStatus() {
		return routingStatus;
	}

	/**
	 * @param routingStatus The routingStatus to set.
	 */
	public void setRoutingStatus(String routingStatus) {
		this.routingStatus = routingStatus;
	}

	/**
	 * @return Returns the attachRouting.
	 */
	public String getAttachRouting() {
		return attachRouting;
	}

	/**
	 * @param attachRouting The attachRouting to set.
	 */
	public void setAttachRouting(String attachRouting) {
		this.attachRouting = attachRouting;
	}

	/**
	 * @return Returns the csgDocNumForRouting.
	 */
	public String getCsgDocNumForRouting() {
		return csgDocNumForRouting;
	}

	/**
	 * @param csgDocNumForRouting The csgDocNumForRouting to set.
	 */
	public void setCsgDocNumForRouting(String csgDocNumForRouting) {
		this.csgDocNumForRouting = csgDocNumForRouting;
	}

	/**
	 * @return Returns the paCodeForRouting.
	 */
	public String getPaCodeForRouting() {
		return paCodeForRouting;
	}

	/**
	 * @param paCodeForRouting The paCodeForRouting to set.
	 */
	public void setPaCodeForRouting(String paCodeForRouting) {
		this.paCodeForRouting = paCodeForRouting;
	}

	/**
	 * @return the newRoutingFlag
	 */
	public String getNewRoutingFlag() {
		return newRoutingFlag;
	}

	/**
	 * @param newRoutingFlag the newRoutingFlag to set
	 */
	public void setNewRoutingFlag(String newRoutingFlag) {
		this.newRoutingFlag = newRoutingFlag;
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

}
