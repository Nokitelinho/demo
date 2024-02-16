/*
 * ContainerFilter.java Created on Jul 17, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.ArrayList;

/**
 * Revision History Revision Date Author Description 0.1 Jul 17, 2018 A-5437
 * First draft
 */

public class ContainerFilter {

	private String containerNo;

	private String fromDate;

	private String toDate;

	private String assignedBy;

	private String assignedTo;

	private String airport;

	private String destination;

	private String type;

	private String notClosedFlag;

	private String mailAcceptedFlag;

	private String toBeTransfered;

	private String showEmpty;

	private String flightNumber;

	private String displayPage;

	private String operationType;
	
	 private String pageSize; //Added by A-7929 as part of ICRD-320470
	 
	 private ArrayList<String> containersToList; //Added by A-8164 for mailinbound starts
	 
	 private String flightNumberFromInbound;
	 
	 private String flightDateFromInbound;
	 
	 private String flightCarrierCodeFromInbound;
	 
	 private String flightSeqNumber;
	 
	 private String legSerialNumber; //Added by A-8164 for mailinbound ends
	 
	 private String showUnreleased; //Added by A-8893
	 
	 private String subclassGroup;
	 private boolean navigation; //Added by A-8672 for mailoutbound
	 
	private String carrierCode;

	private String flightDate;
	private String uldFulIndFlag;
	private String unplannedContainers;
	private String hbaMarkedFlag; //added by 203168 

	 public String getPageSize() {
			return pageSize;
		}

		public void setPageSize(String pageSize) {
			this.pageSize = pageSize;
		}


	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNotClosedFlag() {
		return notClosedFlag;
	}

	public void setNotClosedFlag(String notClosedFlag) {
		this.notClosedFlag = notClosedFlag;
	}

	public String getMailAcceptedFlag() {
		return mailAcceptedFlag;
	}

	public void setMailAcceptedFlag(String mailAcceptedFlag) {
		this.mailAcceptedFlag = mailAcceptedFlag;
	}

	public String getToBeTransfered() {
		return toBeTransfered;
	}

	public void setToBeTransfered(String toBeTransfered) {
		this.toBeTransfered = toBeTransfered;
	}

	public String getShowEmpty() {
		return showEmpty;
	}

	public void setShowEmpty(String showEmpty) {
		this.showEmpty = showEmpty;
	}

	public ArrayList<String> getContainersToList() {
		return containersToList;
	}

	public void setContainersToList(ArrayList<String> containersToList) {
		this.containersToList = containersToList;
	}

	public String getFlightNumberFromInbound() {
		return flightNumberFromInbound;
	}

	public void setFlightNumberFromInbound(String flightNumberFromInbound) {
		this.flightNumberFromInbound = flightNumberFromInbound;
	}

	public String getFlightDateFromInbound() {
		return flightDateFromInbound;
	}

	public void setFlightDateFromInbound(String flightDateFromInbound) {
		this.flightDateFromInbound = flightDateFromInbound;
	}

	public String getFlightCarrierCodeFromInbound() {
		return flightCarrierCodeFromInbound;
	}

	public void setFlightCarrierCodeFromInbound(String flightCarrierCodeFromInbound) {
		this.flightCarrierCodeFromInbound = flightCarrierCodeFromInbound;
	}

	public String getFlightSeqNumber() {
		return flightSeqNumber;
	}

	public void setFlightSeqNumber(String flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}

	public String getLegSerialNumber() {
		return legSerialNumber;
	}

	public void setLegSerialNumber(String legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	
	/**
	 * @return the navigation
	 */
	public boolean isNavigation() {
		return navigation;
	}
	
	/**
	 * @param navigation the navigation to set
	 */
	public void setNavigation(boolean navigation) {
		this.navigation = navigation;
	}

	public String getShowUnreleased() {
		return showUnreleased;
	}
	public void setShowUnreleased(String showUnreleased) {
		this.showUnreleased = showUnreleased;
	}
	public String getSubclassGroup() {
		return subclassGroup;
	}
	public void setSubclassGroup(String subclassGroup) {
		this.subclassGroup = subclassGroup;
	}

	public String getUnplannedContainers() {
		return unplannedContainers;
	}
	public void setUnplannedContainers(String unplannedContainers) {
		this.unplannedContainers = unplannedContainers;
	}
	public String getUldFulIndFlag() {
		return uldFulIndFlag;
	}
	public void setUldFulIndFlag(String uldFulIndFlag) {
		this.uldFulIndFlag = uldFulIndFlag;
	}
	public String getHbaMarkedFlag() {
		return hbaMarkedFlag;
	}
	public void setHbaMarkedFlag(String hbaMarkedFlag) {
		this.hbaMarkedFlag = hbaMarkedFlag;
	}
}