/*
 * CustomerEnquiryForm.java Created on Jul 02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * 
 * @author a-2883
 *
 */

public class CustomerEnquiryForm extends ScreenModel{

	private static final String BUNDLE = "customerenquiry";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.customerenquiry";
	private String bundle;
	private String customerCode;
	private String fromDate;
	private String toDate;
	private String customerName;
	private String station;
	private String status;
	private String customerGroup;
	private String globalCustGroup;
	private String primaryContact;
	private String enquiryType;
	private String orgin;
	private String destination;
	private String commodity;
	private String approverCode;
	private String approverName;
	private String stockHolderCode;
	private String stockHolderName;
	private String requiredReceived;
	private String requiredPlaced;
	private String stockReceived;
	private String stockUsed;
	private String lastPageNumber = "0";
	private String displayPageNum = "1";
	private String absoluteIndex;
	private String companyCode;
	private String stationCode;
	private String agentCode;
	private String airportCode;
	private String spotRateID;
	private String ubrNo;
	private String awbNo;
	private String enquiryScreen;
	private String enquiryBookingList;
	private String enquirySpotList;
	private String fromPage;
	
	
	
	
	/**
	 * @return the fromPage
	 */
	public String getFromPage() {
		return fromPage;
	}

	/**
	 * @param fromPage the fromPage to set
	 */
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	
	/**
	 * @return the absoluteIndex
	 */
	public String getAbsoluteIndex() {
		return absoluteIndex;
	}

	/**
	 * @param absoluteIndex the absoluteIndex to set
	 */
	public void setAbsoluteIndex(String absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	/**
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return the awbNo
	 */
	public String getAwbNo() {
		return awbNo;
	}

	/**
	 * @param awbNo the awbNo to set
	 */
	public void setAwbNo(String awbNo) {
		this.awbNo = awbNo;
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
	 * @return the enquiryBookingList
	 */
	public String getEnquiryBookingList() {
		return enquiryBookingList;
	}

	/**
	 * @param enquiryBookingList the enquiryBookingList to set
	 */
	public void setEnquiryBookingList(String enquiryBookingList) {
		this.enquiryBookingList = enquiryBookingList;
	}

	/**
	 * @return the enquiryScreen
	 */
	public String getEnquiryScreen() {
		return enquiryScreen;
	}

	/**
	 * @param enquiryScreen the enquiryScreen to set
	 */
	public void setEnquiryScreen(String enquiryScreen) {
		this.enquiryScreen = enquiryScreen;
	}

	/**
	 * @return the enquirySpotList
	 */
	public String getEnquirySpotList() {
		return enquirySpotList;
	}

	/**
	 * @param enquirySpotList the enquirySpotList to set
	 */
	public void setEnquirySpotList(String enquirySpotList) {
		this.enquirySpotList = enquirySpotList;
	}

	/**
	 * @return the spotRateID
	 */
	public String getSpotRateID() {
		return spotRateID;
	}

	/**
	 * @param spotRateID the spotRateID to set
	 */
	public void setSpotRateID(String spotRateID) {
		this.spotRateID = spotRateID;
	}

	/**
	 * @return the stationCode
	 */
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode the stationCode to set
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	/**
	 * @return the ubrNo
	 */
	public String getUbrNo() {
		return ubrNo;
	}

	/**
	 * @param ubrNo the ubrNo to set
	 */
	public void setUbrNo(String ubrNo) {
		this.ubrNo = ubrNo;
	}

	/**
	 * @return the displayPageNum
	 */
	public String getDisplayPageNum() {
		return displayPageNum;
	}

	/**
	 * @param displayPageNum the displayPageNum to set
	 */
	public void setDisplayPageNum(String displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	/**
	 * @return the lastPageNumber
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @param lastPageNumber the lastPageNumber to set
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return the approverCode
	 */
	public String getApproverCode() {
		return approverCode;
	}

	/**
	 * @param approverCode the approverCode to set
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}

	/**
	 * @return the approverName
	 */
	public String getApproverName() {
		return approverName;
	}

	/**
	 * @param approverName the approverName to set
	 */
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	/**
	 * @return the requiredPlaced
	 */
	public String getRequiredPlaced() {
		return requiredPlaced;
	}

	/**
	 * @param requiredPlaced the requiredPlaced to set
	 */
	public void setRequiredPlaced(String requiredPlaced) {
		this.requiredPlaced = requiredPlaced;
	}

	/**
	 * @return the requiredReceived
	 */
	public String getRequiredReceived() {
		return requiredReceived;
	}

	/**
	 * @param requiredReceived the requiredReceived to set
	 */
	public void setRequiredReceived(String requiredReceived) {
		this.requiredReceived = requiredReceived;
	}

	/**
	 * @return the stockHolderCode
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode the stockHolderCode to set
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return the stockHolderName
	 */
	public String getStockHolderName() {
		return stockHolderName;
	}

	/**
	 * @param stockHolderName the stockHolderName to set
	 */
	public void setStockHolderName(String stockHolderName) {
		this.stockHolderName = stockHolderName;
	}

	/**
	 * @return the stockReceived
	 */
	public String getStockReceived() {
		return stockReceived;
	}

	/**
	 * @param stockReceived the stockReceived to set
	 */
	public void setStockReceived(String stockReceived) {
		this.stockReceived = stockReceived;
	}

	/**
	 * @return the stockUsed
	 */
	public String getStockUsed() {
		return stockUsed;
	}

	/**
	 * @param stockUsed the stockUsed to set
	 */
	public void setStockUsed(String stockUsed) {
		this.stockUsed = stockUsed;
	}

	/**
	 * @return the commodity
	 */
	public String getCommodity() {
		return commodity;
	}

	/**
	 * @param commodity the commodity to set
	 */
	public void setCommodity(String commodity) {
		this.commodity = commodity;
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
	 * @return the orgin
	 */
	public String getOrgin() {
		return orgin;
	}

	/**
	 * @param orgin the orgin to set
	 */
	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}

	/**
	 * @return the bundle
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle the bundle to set
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * 
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * 
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * 
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the customerGroup
	 */
	public String getCustomerGroup() {
		return customerGroup;
	}

	/**
	 * @param customerGroup the customerGroup to set
	 */
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the fromDate
	 */
	@ DateFieldId(id="CustomerEnquiryDateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the globalCustGroup
	 */
	public String getGlobalCustGroup() {
		return globalCustGroup;
	}

	/**
	 * @param globalCustGroup the globalCustGroup to set
	 */
	public void setGlobalCustGroup(String globalCustGroup) {
		this.globalCustGroup = globalCustGroup;
	}

	/**
	 * @return the primaryContact
	 */
	public String getPrimaryContact() {
		return primaryContact;
	}

	/**
	 * @param primaryContact the primaryContact to set
	 */
	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	/**
	 * @return the station
	 */
	public String getStation() {
		return station;
	}

	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.station = station;
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
	 * @return the toDate
	 */
	@ DateFieldId(id="CustomerEnquiryDateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getEnquiryType() {
		return enquiryType;
	}

	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}

	


	
	
	

}
