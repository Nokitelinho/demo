/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5219
 *
 */
public class ForceMajeureRequestFilterVO extends AbstractVO{
	
	private String companyCode;
	
	private String source;
	
	private String orginAirport;
	
	private String destinationAirport;
	
	private String viaPoint;
	
	private String poaCode;
	
	private String affectedAirport;
	
	private int carrierID;
	
	private String flightNumber;
	
	private LocalDate flightDate;
	
	private LocalDate fromDate;
	
	private LocalDate toDate;
	
	private String filterParameters;
	
	private String reqRemarks;
	
	private String apprRemarks;
	
	private String lastUpdatedUser;
	
	private int totalRecords;
	
	private int pageNumber;
	
	private String currentAirport;
	
	private String transactionCode;
	
	private int txnSerialNumber;
	
	private int defaultPageSize;
	
	private String forceMajeureID;
	
	private String status;

	private String sortingField;
	private String sortOrder;
	
	private String scanType;
	private String scanTypeDetail;
	
	private String mailbagId;
	private String consignmentNo;
	private String airportCode;
	private String carrierCode;
	
	public String getScanTypeDetail() {
		return scanTypeDetail;
	}

	public void setScanTypeDetail(String scanTypeDetail) {
		this.scanTypeDetail = scanTypeDetail;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the orginAirport
	 */
	public String getOrginAirport() {
		return orginAirport;
	}

	/**
	 * @param orginAirport the orginAirport to set
	 */
	public void setOrginAirport(String orginAirport) {
		this.orginAirport = orginAirport;
	}

	/**
	 * @return the destinationAirport
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}

	/**
	 * @param destinationAirport the destinationAirport to set
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	/**
	 * @return the viaPoint
	 */
	public String getViaPoint() {
		return viaPoint;
	}

	/**
	 * @param viaPoint the viaPoint to set
	 */
	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
	}

	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the affectedAirport
	 */
	public String getAffectedAirport() {
		return affectedAirport;
	}

	/**
	 * @param affectedAirport the affectedAirport to set
	 */
	public void setAffectedAirport(String affectedAirport) {
		this.affectedAirport = affectedAirport;
	}

	/**
	 * @return the carrierID
	 */
	public int getCarrierID() {
		return carrierID;
	}

	/**
	 * @param carrierID the carrierID to set
	 */
	public void setCarrierID(int carrierID) {
		this.carrierID = carrierID;
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
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the filterParameters
	 */
	public String getFilterParameters() {
		return filterParameters;
	}

	/**
	 * @param filterParameters the filterParameters to set
	 */
	public void setFilterParameters(String filterParameters) {
		this.filterParameters = filterParameters;
	}

	/**
	 * @return the remarks
	 */
	public String getReqRemarks() {
		return reqRemarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setReqRemarks(String reqRemarks) {
		this.reqRemarks = reqRemarks;
	}

	/**
	 * @return the apprRemarks
	 */
	public String getApprRemarks() {
		return apprRemarks;
	}

	/**
	 * @param apprRemarks the apprRemarks to set
	 */
	public void setApprRemarks(String apprRemarks) {
		this.apprRemarks = apprRemarks;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
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
	 * @return the currentAirport
	 */
	public String getCurrentAirport() {
		return currentAirport;
	}

	/**
	 * @param currentAirport the currentAirport to set
	 */
	public void setCurrentAirport(String currentAirport) {
		this.currentAirport = currentAirport;
	}

	/**
	 * @return the transactionCode
	 */
	public String getTransactionCode() {
		return transactionCode;
	}

	/**
	 * @param transactionCode the transactionCode to set
	 */
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	/**
	 * @return the txnSerialNumber
	 */
	public int getTxnSerialNumber() {
		return txnSerialNumber;
	}

	/**
	 * @param txnSerialNumber the txnSerialNumber to set
	 */
	public void setTxnSerialNumber(int txnSerialNumber) {
		this.txnSerialNumber = txnSerialNumber;
	}

	/**
	 * @return the defaultPageSize
	 */
	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	/**
	 * @param defaultPageSize the defaultPageSize to set
	 */
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	/**
	 * @return the forceMajeureID
	 */
	public String getForceMajeureID() {
		return forceMajeureID;
	}

	/**
	 * @param forceMajeureID the forceMajeureID to set
	 */
	public void setForceMajeureID(String forceMajeureID) {
		this.forceMajeureID = forceMajeureID;
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
	public String getSortingField() {
		return sortingField;
	}
	public void setSortingField(String sortingField) {
		this.sortingField = sortingField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

}
