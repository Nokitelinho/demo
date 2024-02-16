/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter.java Created on	:	08-Jun-2018
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.ArrayList;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter.java
 * Version : Name : Date : Updation
 * ---------------------------------------------------
 *  0.1 : A-2257 :  08-Jun-2018 : Draft
 */
public class MailbagFilter {

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
	private String carditPresent; 
	private String damageFlag;
	private String transitFlag;
	private String consigmentNumber;
	private String upuCode;
	private int pageNumber;
	private String reqDeliveryDate;
	private String reqDeliveryTime;
	private String scanUser;
	private String scanPort;
	private String lyingListActiveTab;
	private String operationalStatus;
	private String carditStatus;
	private String rdtDate;
	private String containerNo;
	private String consignmentNo;
	private String userID;
	private String pageSize;//Added by A-8353 for ICRD-324698
	private String serviceLevel;//Added by A-8672 for ICRD-327149
	private String onTimeDelivery;//Added for ICRD-323389
	private ArrayList<String> mailBagsToList;// Added by A-8164 for mailinbound

    private String deviationListActiveTab;

	private String transportServWindowDate;// Added by A-8176 for IASCB-35785
	private String transportServWindowTime;// Added by A-8176 for IASCB-35785
	
	private String consignmentDate;
	private boolean carditAvailable; 
	private boolean damaged; 
	private String transferFromCarrier;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private boolean routingInfoAvailable;
	private boolean plt;
	private String containerAssignedOn;
	
	
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
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

	public String getCarditPresent() {
		return carditPresent;
	}

	public void setCarditPresent(String carditPresent) {
		this.carditPresent = carditPresent;
	}

	public String getDamageFlag() {
		return damageFlag;
	}

	public void setDamageFlag(String damageFlag) {
		this.damageFlag = damageFlag;
	}

	public String getTransitFlag() {
		return transitFlag;
	}

	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}

	public String getConsigmentNumber() {
		return consigmentNumber;
	}

	public void setConsigmentNumber(String consigmentNumber) {
		this.consigmentNumber = consigmentNumber;
	}

	public String getUpuCode() {
		return upuCode;
	}

	public void setUpuCode(String upuCode) {
		this.upuCode = upuCode;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getReqDeliveryDate() {
		return reqDeliveryDate;
	}

	public void setReqDeliveryDate(String reqDeliveryDate) {
		this.reqDeliveryDate = reqDeliveryDate;
	}

	public String getReqDeliveryTime() {
		return reqDeliveryTime;
	}

	public void setReqDeliveryTime(String reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}

	public String getScanUser() {
		return scanUser;
	}

	public void setScanUser(String scanUser) {
		this.scanUser = scanUser;
	}

	public String getScanPort() {
		return scanPort;
	}

	public void setScanPort(String scanPort) {
		this.scanPort = scanPort;
	}
	public String getLyingListActiveTab() {
		return lyingListActiveTab;
	}
	public void setLyingListActiveTab(String lyingListActiveTab) {
		this.lyingListActiveTab = lyingListActiveTab;
	}

	public String getOperationalStatus() {
		return operationalStatus;
	}

	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	public String getCarditStatus() {
		return carditStatus;
	}

	public void setCarditStatus(String carditStatus) {
		this.carditStatus = carditStatus;
	}

	public String getRdtDate() {
		return rdtDate;
	}

	public void setRdtDate(String rdtDate) {
		this.rdtDate = rdtDate;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	//Added by A-8672 for ICRD-327149 starts
	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getDeviationListActiveTab() {
		return deviationListActiveTab;
	}

	public void setDeviationListActiveTab(String deviationListActiveTab) {
		this.deviationListActiveTab = deviationListActiveTab;
	}
	//Added by A-8672 for ICRD-327149 ends
	/**
	 * @return the onTimeDelivery
	 */
	public String getOnTimeDelivery() {
		return onTimeDelivery;
	}
	/**
	 * @param onTimeDelivery the onTimeDelivery to set
	 */
	public void setOnTimeDelivery(String onTimeDelivery) {
		this.onTimeDelivery = onTimeDelivery;
	}

	public ArrayList<String> getMailBagsToList() {
		return mailBagsToList;
	}

	public void setMailBagsToList(ArrayList<String> mailBagsToList) {
		this.mailBagsToList = mailBagsToList;
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

	public String getConsignmentDate() {
		return consignmentDate;
	}

	public void setConsignmentDate(String consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	public boolean isCarditAvailable() {
		return carditAvailable;
	}

	public void setCarditAvailable(boolean carditAvailable) {
		this.carditAvailable = carditAvailable;
	}

	public boolean isDamaged() {
		return damaged;
	}

	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}

	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}

	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
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

	public boolean isRoutingInfoAvailable() {
		return routingInfoAvailable;
	}

	public void setRoutingInfoAvailable(boolean routingInfoAvailable) {
		this.routingInfoAvailable = routingInfoAvailable;
	}

	public boolean isPlt() {
		return plt;
	}

	public void setPlt(boolean plt) {
		this.plt = plt;
	}
	public String getContainerAssignedOn() {
		return containerAssignedOn;
	}

	public void setContainerAssignedOn(String containerAssignedOn) {
		this.containerAssignedOn = containerAssignedOn;
	}
	
	
	
}
