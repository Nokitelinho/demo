/**
 * DispatchEnquiryForm Created on February 17, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author a-4823
 *
 */
public class DispatchEnquiryForm extends ScreenModel{
	private static final String SCREEN_ID = "mailtracking.defaults.national.dispatchEnquiry";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "nationalDispatchEnquiryResources";
	private String origin;
	private String destination;
	private String category;
	private String consignmentNo;
	private String status;
	private String airport;
	private String flightCarrierCode;
	private String flightNo;
	private String flightDate;
	private String userId;
	private String fromDate;
	private String toDate;	
	private String operationType;
	private String displayPage;
	private String lastPageNum;
	private String[] rowId; 	
	private String popupFlag;
	private String countTotalFlag = "";//Added by A-5214 as part from the ICRD-21098
	//Added by A-4810 as part of bug-fix icrd-15125
	private String transferFlag;
	

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}


	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
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
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}


	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}


	/**
	 * @return the consignmentNo
	 */
	public String getConsignmentNo() {
		return consignmentNo;
	}


	/**
	 * @param consignmentNo the consignmentNo to set
	 */
	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
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
	 * @return the airport
	 */
	public String getAirport() {
		return airport;
	}


	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
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
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}


	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the fromDate
	 */
	@DateFieldId(id="DomesticMailEnquiryDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
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
	 * @return the toDate
	 */
	@DateFieldId(id="DomesticMailEnquiryDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return toDate;
	}


	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getProduct() {

		return PRODUCT_NAME;
	}


	public String getScreenId() {

		return SCREEN_ID;
	}


	public String getSubProduct() {

		return SUBPRODUCT_NAME;
	}


	/**
	 * @return the bundle
	 */
	public  String getBundle() {
		return BUNDLE;
	}


	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}


	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}


	/**
	 * @return the displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}


	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}


	/**
	 * @return the lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}


	/**
	 * @param lastPageNum the lastPageNum to set
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}


	/**
	 * @return the rowId
	 */
	public String[] getRowId() {
		return rowId;
	}


	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}


	/**
	 * @return the popupFlag
	 */
	public String getPopupFlag() {
		return popupFlag;
	}


	/**
	 * @param popupFlag the popupFlag to set
	 */
	public void setPopupFlag(String popupFlag) {
		this.popupFlag = popupFlag;
	}


	/**
	 * @return the transferFlag
	 */
	public String getTransferFlag() {
		return transferFlag;
	}


	/**
	 * @param transferFlag the transferFlag to set
	 */
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	//Added by A-5214 as part from the ICRD-21098 starts
	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}


	public String getCountTotalFlag() {
		return countTotalFlag;
	}
	//Added by A-5214 as part from the ICRD-21098 ends


}
