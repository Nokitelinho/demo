package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

public class MailManifestCN46Form {
	
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "cN46ManifestResources";

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

	@MeasureAnnotation(mappedValue = "stdWeightMeasure", unitType = "MWT")
	private double stdWeight;
	private Measure stdWeightMeasure;

	private String weightStandard;
	private String shipmentDesc;
	private String screenStatus;
	private String agentCode;
	private String autoAttach;
	private String selectChild;

	// Generate Manifest popup

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
	// for mailsummary link
	private String mailFlightSummary;
	private String mailFlighCarrierCode;
	private String mailFlightNumber;
	private String maildepDate;
	private String overrideContainerFlag;

	private String routingAvl;

	// FOR BUG 50584 ENHANCEMENT
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
	private String[] routeOpFlag;
	private String routingStatus;
	private String attachRouting;
	private String[] csgDocNum;
	private String[] paCod;
	private String csgDocNumForRouting;
	private String paCodeForRouting;
	// ADDED FOR BUG 82870
	private String newRoutingFlag;
	private String fromScreen;

	// Added for CR ICMN-2337
	private String[] operationalFlag;

	private String warningFlag;
	private String warningOveride;
	private String disableButtonsForTBA;

	// Added by A-7794 as part of ICRD-197439
	private String disableButtonsForAirport;

	private String autoAttachAWB;

	private String parentContainer;

	private String duplicateFlightStatus;

	private String disableSaveFlag;

	private String initialFocus;

	private String containerNo;
	private String paBuilt;
	private String onwardFlights;
	private String duplicateAndTbaTbc;

	private String selectTab;

	private String uldsSelectedFlag;

	private String uldsPopupCloseFlag;

	private String density;

	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	public String getDeparturePort() {
		return departurePort;
	}

	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	public String[] getSelectMail() {
		return selectMail;
	}

	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
	}

	public String[] getSelectDSN() {
		return selectDSN;
	}

	public void setSelectDSN(String[] selectDSN) {
		this.selectDSN = selectDSN;
	}

	public String getOpenAttachAWB() {
		return openAttachAWB;
	}

	public void setOpenAttachAWB(String openAttachAWB) {
		this.openAttachAWB = openAttachAWB;
	}

	public String getOperationalStatus() {
		return operationalStatus;
	}

	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getStdPieces() {
		return stdPieces;
	}

	public void setStdPieces(int stdPieces) {
		this.stdPieces = stdPieces;
	}

	public double getStdWeight() {
		return stdWeight;
	}

	public void setStdWeight(double stdWeight) {
		this.stdWeight = stdWeight;
	}

	public Measure getStdWeightMeasure() {
		return stdWeightMeasure;
	}

	public void setStdWeightMeasure(Measure stdWeightMeasure) {
		this.stdWeightMeasure = stdWeightMeasure;
	}

	public String getWeightStandard() {
		return weightStandard;
	}

	public void setWeightStandard(String weightStandard) {
		this.weightStandard = weightStandard;
	}

	public String getShipmentDesc() {
		return shipmentDesc;
	}

	public void setShipmentDesc(String shipmentDesc) {
		this.shipmentDesc = shipmentDesc;
	}

	public String getScreenStatus() {
		return screenStatus;
	}

	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAutoAttach() {
		return autoAttach;
	}

	public void setAutoAttach(String autoAttach) {
		this.autoAttach = autoAttach;
	}

	public String getSelectChild() {
		return selectChild;
	}

	public void setSelectChild(String selectChild) {
		this.selectChild = selectChild;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
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

	public String getBulkDisable() {
		return bulkDisable;
	}

	public void setBulkDisable(String bulkDisable) {
		this.bulkDisable = bulkDisable;
	}

	public String getMailFlightSummary() {
		return mailFlightSummary;
	}

	public void setMailFlightSummary(String mailFlightSummary) {
		this.mailFlightSummary = mailFlightSummary;
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

	public String getMaildepDate() {
		return maildepDate;
	}

	public void setMaildepDate(String maildepDate) {
		this.maildepDate = maildepDate;
	}

	public String getOverrideContainerFlag() {
		return overrideContainerFlag;
	}

	public void setOverrideContainerFlag(String overrideContainerFlag) {
		this.overrideContainerFlag = overrideContainerFlag;
	}

	public String getRoutingAvl() {
		return routingAvl;
	}

	public void setRoutingAvl(String routingAvl) {
		this.routingAvl = routingAvl;
	}

	public String getConDocNo() {
		return conDocNo;
	}

	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getConDate() {
		return conDate;
	}

	public void setConDate(String conDate) {
		this.conDate = conDate;
	}

	public String getContype() {
		return contype;
	}

	public void setContype(String contype) {
		this.contype = contype;
	}

	public String[] getSelectRoute() {
		return selectRoute;
	}

	public void setSelectRoute(String[] selectRoute) {
		this.selectRoute = selectRoute;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String[] getFlightRouteCarrierCode() {
		return flightRouteCarrierCode;
	}

	public void setFlightRouteCarrierCode(String[] flightRouteCarrierCode) {
		this.flightRouteCarrierCode = flightRouteCarrierCode;
	}

	public String[] getFlightRouteNumber() {
		return flightRouteNumber;
	}

	public void setFlightRouteNumber(String[] flightRouteNumber) {
		this.flightRouteNumber = flightRouteNumber;
	}

	public String[] getDepRouteDate() {
		return depRouteDate;
	}

	public void setDepRouteDate(String[] depRouteDate) {
		this.depRouteDate = depRouteDate;
	}

	public String[] getPouRoute() {
		return pouRoute;
	}

	public void setPouRoute(String[] pouRoute) {
		this.pouRoute = pouRoute;
	}

	public String[] getPolRoute() {
		return polRoute;
	}

	public void setPolRoute(String[] polRoute) {
		this.polRoute = polRoute;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String[] getRouteOpFlag() {
		return routeOpFlag;
	}

	public void setRouteOpFlag(String[] routeOpFlag) {
		this.routeOpFlag = routeOpFlag;
	}

	public String getRoutingStatus() {
		return routingStatus;
	}

	public void setRoutingStatus(String routingStatus) {
		this.routingStatus = routingStatus;
	}

	public String getAttachRouting() {
		return attachRouting;
	}

	public void setAttachRouting(String attachRouting) {
		this.attachRouting = attachRouting;
	}

	public String[] getCsgDocNum() {
		return csgDocNum;
	}

	public void setCsgDocNum(String[] csgDocNum) {
		this.csgDocNum = csgDocNum;
	}

	public String[] getPaCod() {
		return paCod;
	}

	public void setPaCod(String[] paCod) {
		this.paCod = paCod;
	}

	public String getCsgDocNumForRouting() {
		return csgDocNumForRouting;
	}

	public void setCsgDocNumForRouting(String csgDocNumForRouting) {
		this.csgDocNumForRouting = csgDocNumForRouting;
	}

	public String getPaCodeForRouting() {
		return paCodeForRouting;
	}

	public void setPaCodeForRouting(String paCodeForRouting) {
		this.paCodeForRouting = paCodeForRouting;
	}

	public String getNewRoutingFlag() {
		return newRoutingFlag;
	}

	public void setNewRoutingFlag(String newRoutingFlag) {
		this.newRoutingFlag = newRoutingFlag;
	}

	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	public String[] getOperationalFlag() {
		return operationalFlag;
	}

	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
	}

	public String getWarningFlag() {
		return warningFlag;
	}

	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}

	public String getWarningOveride() {
		return warningOveride;
	}

	public void setWarningOveride(String warningOveride) {
		this.warningOveride = warningOveride;
	}

	public String getDisableButtonsForTBA() {
		return disableButtonsForTBA;
	}

	public void setDisableButtonsForTBA(String disableButtonsForTBA) {
		this.disableButtonsForTBA = disableButtonsForTBA;
	}

	public String getDisableButtonsForAirport() {
		return disableButtonsForAirport;
	}

	public void setDisableButtonsForAirport(String disableButtonsForAirport) {
		this.disableButtonsForAirport = disableButtonsForAirport;
	}

	public String getAutoAttachAWB() {
		return autoAttachAWB;
	}

	public void setAutoAttachAWB(String autoAttachAWB) {
		this.autoAttachAWB = autoAttachAWB;
	}

	public String getParentContainer() {
		return parentContainer;
	}

	public void setParentContainer(String parentContainer) {
		this.parentContainer = parentContainer;
	}

	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}

	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	public String getDisableSaveFlag() {
		return disableSaveFlag;
	}

	public void setDisableSaveFlag(String disableSaveFlag) {
		this.disableSaveFlag = disableSaveFlag;
	}

	public String getInitialFocus() {
		return initialFocus;
	}

	public void setInitialFocus(String initialFocus) {
		this.initialFocus = initialFocus;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getPaBuilt() {
		return paBuilt;
	}

	public void setPaBuilt(String paBuilt) {
		this.paBuilt = paBuilt;
	}

	public String getOnwardFlights() {
		return onwardFlights;
	}

	public void setOnwardFlights(String onwardFlights) {
		this.onwardFlights = onwardFlights;
	}

	public String getDuplicateAndTbaTbc() {
		return duplicateAndTbaTbc;
	}

	public void setDuplicateAndTbaTbc(String duplicateAndTbaTbc) {
		this.duplicateAndTbaTbc = duplicateAndTbaTbc;
	}

	public String getSelectTab() {
		return selectTab;
	}

	public void setSelectTab(String selectTab) {
		this.selectTab = selectTab;
	}

	public String getUldsSelectedFlag() {
		return uldsSelectedFlag;
	}

	public void setUldsSelectedFlag(String uldsSelectedFlag) {
		this.uldsSelectedFlag = uldsSelectedFlag;
	}

	public String getUldsPopupCloseFlag() {
		return uldsPopupCloseFlag;
	}

	public void setUldsPopupCloseFlag(String uldsPopupCloseFlag) {
		this.uldsPopupCloseFlag = uldsPopupCloseFlag;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public static String getProductName() {
		return PRODUCT_NAME;
	}

	public static String getSubproductName() {
		return SUBPRODUCT_NAME;
	}

	public static String getBundle() {
		return BUNDLE;
	}

	
	
}