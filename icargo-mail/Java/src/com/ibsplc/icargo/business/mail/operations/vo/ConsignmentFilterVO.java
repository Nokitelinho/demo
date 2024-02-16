/*
 * ConsignmentFilterVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 * 
 */
public class ConsignmentFilterVO extends AbstractVO {

	private String companyCode;

	private String consignmentNumber;

	private String paCode;
	
	private int pageNumber;
	
	private int totalRecords;
	
	private String scannedOnline;
	
	private String conDate;
	
	private String conType;
	
	private String routingInfo;

	private String carrierCode;
	
	private String fltNumber;
	
	private String scannedPort;
	
	private String fltDate;
	
	private String operationMode;
	
	private String originOfficeOfExchange;
	
	private String destinationOfficeOfExchange;
	
	private String mailCategory;
	
	private String mailSubClass;
	
	private int consignmentSequenceNumber;
	
	private String airportCode;
	
	private String consigmentOrigin;
	
	private String consignmentDestination;
	
	private boolean listflag;
	
	private String subType; //Added by A-6991 for ICRD-196641
	
	private int pageSize;
	
	private boolean bulkDownload;
	
	private String containerNumber;
	private String containerJourneyId;
	private String bellyCartId;
	private LocalDate consignmentFromDate;
	private LocalDate consignmentToDate;
	private String transferManifestId;

	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	public String getMailCategory() {
		return mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	public String getMailSubClass() {
		return mailSubClass;
	}

	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
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
	 * @return Returns the conType.
	 */
	public String getConType() {
		return conType;
	}

	/**
	 * @param conType The conType to set.
	 */
	public void setConType(String conType) {
		this.conType = conType;
	}

	/**
	 * @return Returns the routingInfo.
	 */
	public String getRoutingInfo() {
		return routingInfo;
	}

	/**
	 * @param routingInfo The routingInfo to set.
	 */
	public void setRoutingInfo(String routingInfo) {
		this.routingInfo = routingInfo;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
	 * @return Returns the consignmentNumber.
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber
	 *            The consignmentNumber to set.
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode
	 *            The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return the scannedOnline
	 */
	public String getScannedOnline() {
		return scannedOnline;
	}

	/**
	 * @param scannedOnline the scannedOnline to set
	 */
	public void setScannedOnline(String scannedOnline) {
		this.scannedOnline = scannedOnline;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the fltDate.
	 */
	public String getFltDate() {
		return fltDate;
	}

	/**
	 * @param fltDate The fltDate to set.
	 */
	public void setFltDate(String fltDate) {
		this.fltDate = fltDate;
	}

	/**
	 * @return Returns the fltNumber.
	 */
	public String getFltNumber() {
		return fltNumber;
	}

	/**
	 * @param fltNumber The fltNumber to set.
	 */
	public void setFltNumber(String fltNumber) {
		this.fltNumber = fltNumber;
	}

	/**
	 * @return Returns the operationMode.
	 */
	public String getOperationMode() {
		return operationMode;
	}

	/**
	 * @param operationMode The operationMode to set.
	 */
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

	/**
	 * @return Returns the scannedPort.
	 */
	public String getScannedPort() {
		return scannedPort;
	}

	/**
	 * @param scannedPort The scannedPort to set.
	 */
	public void setScannedPort(String scannedPort) {
		this.scannedPort = scannedPort;
	}

	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}

	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getConsigmentOrigin() {
		return consigmentOrigin;
	}

	public void setConsigmentOrigin(String consigmentOrigin) {
		this.consigmentOrigin = consigmentOrigin;
	}

	public String getConsignmentDestination() {
		return consignmentDestination;
	}

	public void setConsignmentDestination(String consignmentDestination) {
		this.consignmentDestination = consignmentDestination;
	}
	/**
	 * @return the listflag
	 */
	public boolean isListflag() {
		return listflag;
	}
	/**
	 * @param listflag the listflag to set
	 */
	public void setListflag(boolean listflag) {
		this.listflag = listflag;
	}

	/**
	 * @return the subType
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * @param subType the subType to set
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isBulkDownload() {
		return bulkDownload;
	}

	public void setBulkDownload(boolean bulkDownload) {
		this.bulkDownload = bulkDownload;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getContainerJourneyId() {
		return containerJourneyId;
	}

	public void setContainerJourneyId(String containerJourneyId) {
		this.containerJourneyId = containerJourneyId;
	}

	public String getBellyCartId() {
		return bellyCartId;
	}

	public void setBellyCartId(String bellyCartId) {
		this.bellyCartId = bellyCartId;
	}

	public LocalDate getConsignmentFromDate() {
		return consignmentFromDate;
	}

	public void setConsignmentFromDate(LocalDate consignmentFromDate) {
		this.consignmentFromDate = consignmentFromDate;
	}

	public LocalDate getConsignmentToDate() {
		return consignmentToDate;
	}

	public void setConsignmentToDate(LocalDate consignmentToDate) {
		this.consignmentToDate = consignmentToDate;
	}
	public String getTransferManifestId() {
		return transferManifestId;
	}
	public void setTransferManifestId(String transferManifestId) {
		this.transferManifestId = transferManifestId;
	}

}
