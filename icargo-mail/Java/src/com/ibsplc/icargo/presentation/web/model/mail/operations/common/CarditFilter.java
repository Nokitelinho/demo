package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.io.Serializable;

public class CarditFilter implements Serializable{
	  private String mailbagId;
	  private String flightNumber;
	  private String flightDate;
	  private String carrierCode;
	  private String ooe;
	  private String doe;
	  private String mailCategoryCode;
	  private String mailSubclass;
	  private String year;
	  private String despatchSerialNumber;
	  private String receptacleSerialNumber;
	  private String conDocNo;
	  private String consignmentDate;
	  private String fromDate;
	  private String toDate;
	  private String paCode;
	  private String uldNumber;
	  private String mailDestination;
	  private String mailOrigin;
	  private String mailStatus;
	  private String airportCode;
	  private String displayPage;
	  private String pol;
	  private String carditActiveTab;
	  private String rdtDate;
	  private String rdtTime;
	  private String awbAttached;
	  private String documentNumber;
	  private String nonSelectionOperation;
	  private String totalMailbagCount;
	  private String flightType;
	  private boolean isPendingResditChecked;
	  public String shipmentPrefix;
	  private String masterDocumentNumber;
	  private String reqDeliveryDate;
	  private String reqDeliveryTime;
	  private String pageSize; //Added by A-8164 for ICRD-320122
	  private String transportServWindowDate;// Added by A-8176 for IASCB-33693
	  private String transportServWindowTime;// Added by A-8176 for IASCB-33693
	  private String mailCount;
	  
	  
	  
	public String getMailCount() {
		return mailCount;
	}
	public void setMailCount(String mailCount) {
		this.mailCount = mailCount;
	}
	public String getMailbagId() {
		return mailbagId;
	}
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getOoe() {
		return ooe;
	}
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}
	public String getDoe() {
		return doe;
	}
	public void setDoe(String doe) {
		this.doe = doe;
	}
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	public String getMailSubclass() {
		return mailSubclass;
	}
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}
	public String getConDocNo() {
		return conDocNo;
	}
	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	
	public String getMailDestination() {
		return mailDestination;
	}
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	public String getMailOrigin() {
		return mailOrigin;
	}
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}
	public String getMailStatus() {
		return mailStatus;
	}
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getUldNumber() {
		return uldNumber;
	}
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	public String getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getCarditActiveTab() {
		return carditActiveTab;
	}
	public void setCarditActiveTab(String carditActiveTab) {
		this.carditActiveTab = carditActiveTab;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public boolean isPendingResditChecked() {
		return isPendingResditChecked;
	}
	public void setPendingResditChecked(boolean isPendingResditChecked) {
		this.isPendingResditChecked = isPendingResditChecked;
	}
	public String getConsignmentDate() {
		return consignmentDate;
	}
	public void setConsignmentDate(String consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	public String getRdtDate() {
		return rdtDate;
	}
	public void setRdtDate(String rdtDate) {
		this.rdtDate = rdtDate;
	}
	public String getRdtTime() {
		return rdtTime;
	}
	public void setRdtTime(String rdtTime) {
		this.rdtTime = rdtTime;
	}
	public String getAwbAttached() {
		return awbAttached;
	}
	public void setAwbAttached(String awbAttached) {
		this.awbAttached = awbAttached;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getNonSelectionOperation() {
		return nonSelectionOperation;
	}
	public void setNonSelectionOperation(String nonSelectionOperation) {
		this.nonSelectionOperation = nonSelectionOperation;
	}
	public String getTotalMailbagCount() {
		return totalMailbagCount;
	}
	public void setTotalMailbagCount(String totalMailbagCount) {
		this.totalMailbagCount = totalMailbagCount;
	}
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public String getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	public void setReqDeliveryTime(String reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	public String getReqDeliveryDate() {
		return reqDeliveryDate;
	}
	public void setReqDeliveryDate(String reqDeliveryDate) {
		this.reqDeliveryDate = reqDeliveryDate;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getTransportServWindowDate() {
		return transportServWindowDate;
	}
	public void setTransportServWindowDate(String transportServWindowDate) {
		this.transportServWindowDate = transportServWindowDate;
	}
	public String getTransportServWindowTime() {
		return transportServWindowTime;
	}
	public void setTransportServWindowTime(String transportServWindowTime) {
		this.transportServWindowTime = transportServWindowTime;
	}
	
	  
}
