/*
 * SearchContainerFilterVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.ArrayList;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 * 
 */
public class SearchContainerFilterVO extends AbstractVO {
	private String containerNumber;
	private LocalDate assignedFromDate;
	private LocalDate assignedToDate;
	private String departurePort;
	private String assignedUser;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private String finalDestination;
	private LocalDate flightDate;
	private String companyCode;
	private int absoluteIndex;
	private int destionationCarrierId;
	private int totalRecords;// Added by A-5201 as part for the ICRD-21098
	// whether inbound or outbound
	private String operationType;
	/*
	 * Added by Roopak for client purpose only
	 */
	private String strFromDate;
	private String strToDate;
	private String strFlightDate;
	private String flightCarrierCode;
	/*
	 * 
	 * Added By Karthick V Starts to include the fields that could be used for
	 * fetching the Containers that can be transferred..
	 */
	private String currentAirport;
	private String transferStatus;
	/*
	 * Added By karthick V Ends
	 */
	private String searchMode;
	private String destination;
	private String subclassGroup;
	private String notClosedFlag;
	private String mailAcceptedFlag;
	// Added by A-5945 for ICRD-96261
	private ArrayList<String> partnerCarriers;
	private String showEmptyContainer;

    private int pageSize; //Added by A-7929 as part of ICRD-320470
    
    private ArrayList<String> containersToList; //Added by A-8164 for mailinbound starts
    private boolean isBulkPresent;
    private String flightNumberFromInbound;
	private String flightCarrierCodeFromInbound;
	private String flightSeqNumber;
	private String legSerialNumber; //Added by A-8164 for mailinbound ends
	private String showUnreleasedContainer;// Added by A-8893 
	private boolean navigation;
	private String uldFulIndFlag; 
	private String segOrigin;
	private String segDestination;
	private boolean excATDCapFlights;
	private boolean unplannedContainers;
	private String containerView;
	private String source;   
    private String hbaMarkedFlag;
	 public String getContainerView() {
		return containerView;
	}
	public void setContainerView(String containerView) {
		this.containerView = containerView;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	 public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

	public ArrayList<String> getPartnerCarriers() {
		return partnerCarriers;
	}

	public void setPartnerCarriers(ArrayList<String> partnerCarriers) {
		this.partnerCarriers = partnerCarriers;
	}

	public String getShowEmptyContainer() {
		return showEmptyContainer;
	}

	public void setShowEmptyContainer(String showEmptyContainer) {
		this.showEmptyContainer = showEmptyContainer;
	}

	// Added by A-5945 for ICRD-96261 ends
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the subclassGroup
	 */
	public String getSubclassGroup() {
		return subclassGroup;
	}

	/**
	 * @param subclassGroup
	 *            the subclassGroup to set
	 */
	public void setSubclassGroup(String subclassGroup) {
		this.subclassGroup = subclassGroup;
	}

	/**
	 * @return the notClosedFlag
	 */
	public String getNotClosedFlag() {
		return notClosedFlag;
	}

	/**
	 * @param notClosedFlag
	 *            the notClosedFlag to set
	 */
	public void setNotClosedFlag(String notClosedFlag) {
		this.notClosedFlag = notClosedFlag;
	}

	/**
	 * @return the mailAcceptedFlag
	 */
	public String getMailAcceptedFlag() {
		return mailAcceptedFlag;
	}

	/**
	 * @param mailAcceptedFlag
	 *            the mailAcceptedFlag to set
	 */
	public void setMailAcceptedFlag(String mailAcceptedFlag) {
		this.mailAcceptedFlag = mailAcceptedFlag;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the assignedFromDate.
	 */
	public LocalDate getAssignedFromDate() {
		return assignedFromDate;
	}

	/**
	 * @param assignedFromDate
	 *            The assignedFromDate to set.
	 */
	public void setAssignedFromDate(LocalDate assignedFromDate) {
		this.assignedFromDate = assignedFromDate;
	}

	/**
	 * @return Returns the assignedToDate.
	 */
	public LocalDate getAssignedToDate() {
		return assignedToDate;
	}

	/**
	 * @param assignedToDate
	 *            The assignedToDate to set.
	 */
	public void setAssignedToDate(LocalDate assignedToDate) {
		this.assignedToDate = assignedToDate;
	}

	/**
	 * @return Returns the assignedUser.
	 */
	public String getAssignedUser() {
		return assignedUser;
	}

	/**
	 * @param assignedUser
	 *            The assignedUser to set.
	 */
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the departurePort.
	 */
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePort
	 *            The departurePort to set.
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	/**
	 * @return Returns the finalDestination.
	 */
	public String getFinalDestination() {
		return finalDestination;
	}

	/**
	 * @param finalDestination
	 *            The finalDestination to set.
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the absoluteIndex.
	 */
	public int getAbsoluteIndex() {
		return this.absoluteIndex;
	}

	/**
	 * @param absoluteIndex
	 *            The absoluteIndex to set.
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return this.flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode
	 *            The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the strFlightDate.
	 */
	public String getStrFlightDate() {
		return this.strFlightDate;
	}

	/**
	 * @param strFlightDate
	 *            The strFlightDate to set.
	 */
	public void setStrFlightDate(String strFlightDate) {
		this.strFlightDate = strFlightDate;
	}

	/**
	 * @return Returns the strFromDate.
	 */
	public String getStrFromDate() {
		return this.strFromDate;
	}

	/**
	 * @param strFromDate
	 *            The strFromDate to set.
	 */
	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	/**
	 * @return Returns the strToDate.
	 */
	public String getStrToDate() {
		return this.strToDate;
	}

	/**
	 * @param strToDate
	 *            The strToDate to set.
	 */
	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	/**
	 * @return Returns the destionationCarrierId.
	 */
	public int getDestionationCarrierId() {
		return destionationCarrierId;
	}

	/**
	 * @param destionationCarrierId
	 *            The destionationCarrierId to set.
	 */
	public void setDestionationCarrierId(int destionationCarrierId) {
		this.destionationCarrierId = destionationCarrierId;
	}

	/**
	 * @return Returns the operationType.
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType
	 *            The operationType to set.
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return Returns the searchMode.
	 */
	public String getSearchMode() {
		return searchMode;
	}

	/**
	 * @param searchMode
	 *            The searchMode to set.
	 */
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	/**
	 * @return Returns the transferStatus.
	 */
	public String getTransferStatus() {
		return transferStatus;
	}

	/**
	 * @param transferStatus
	 *            The transferStatus to set.
	 */
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

	/**
	 * @return Returns the currentAirport.
	 */
	public String getCurrentAirport() {
		return currentAirport;
	}

	/**
	 * @param currentAirport
	 *            The currentAirport to set.
	 */
	public void setCurrentAirport(String currentAirport) {
		this.currentAirport = currentAirport;
	}

	// Added by A-5201 as part for the ICRD-21098 starts
	/**
	 * @param setTotalRecords
	 *            to set total number of records
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the total number of records
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	// Added by A-5201 as part for the ICRD-21098 end

	public ArrayList<String> getContainersToList() {
		return containersToList;
	}

	public void setContainersToList(ArrayList<String> containersToList) {
		this.containersToList = containersToList;
	}

	public boolean isBulkPresent() {
		return isBulkPresent;
	}

	public void setBulkPresent(boolean isBulkPresent) {
		this.isBulkPresent = isBulkPresent;
	}

	public String getFlightNumberFromInbound() {
		return flightNumberFromInbound;
	}

	public void setFlightNumberFromInbound(String flightNumberFromInbound) {
		this.flightNumberFromInbound = flightNumberFromInbound;
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
	public String getShowUnreleasedContainer() {
		return showUnreleasedContainer;
	}
	public void setShowUnreleasedContainer(String showUnreleasedContainer) {
		this.showUnreleasedContainer = showUnreleasedContainer;
	}
	public String getUldFulIndFlag() {
		return uldFulIndFlag;
	}
	public void setUldFulIndFlag(String uldFulIndFlag) {
		this.uldFulIndFlag = uldFulIndFlag;
	}
	public String getSegOrigin() {
		return segOrigin;
	}
	public void setSegOrigin(String segOrigin) {
		this.segOrigin = segOrigin;
	}
	public String getSegDestination() {
		return segDestination;
	}
	public void setSegDestination(String segDestination) {
		this.segDestination = segDestination;
	}
	public boolean isExcATDCapFlights() {
		return excATDCapFlights;
	}
	public void setExcATDCapFlights(boolean excATDCapFlights) {
		this.excATDCapFlights = excATDCapFlights;
	}
	public boolean isUnplannedContainers() {
		return unplannedContainers;
	}
	public void setUnplannedContainers(boolean unplannedContainers) {
		this.unplannedContainers = unplannedContainers;
	}
	public String getHbaMarkedFlag() {
		return hbaMarkedFlag;
	}
	public void setHbaMarked(String hbaMarkedFlag) {
		this.hbaMarkedFlag = hbaMarkedFlag;
	}
	
	
	
}
