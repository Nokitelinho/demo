/*
 * DsnEnquiryForm.java Created on July 05, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1861
 *
 */
public class DsnEnquiryForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "dsnEnquiryResources";

	private String dsn;
	private String consignmentNo;
	private String originCity;
	private String destnCity;
	private String category;
	private String mailClass;
	private String fromDate;
	private String toDate;
	private String flightCarrierCode;
	private String flightNumber;
	private String flightDate;
	private String operationType;
	private boolean isPlt;
	private String containerType;	
	private String uldNo;
	private String postalAuthorityCode;
	private String port;

	private String lastPageNum;
	private String displayPage;

	private String status;
	private String currentDialogOption;
	private String currentDialogId;
	
	private String reList;

	private String checkAll;
	private String[] subCheck;
	// added by Paulson for Air NA CR 410
	private String transit;
	
	private String loginAirport;
	/**
	 * Captured but not accepted
	 */
	private boolean isCapNotAcp;
	
	private String[] dsnPort;
	
	private String[] contNumber;
	//added by A-5203
	 private String countTotalFlag;
	 
	 public String getCountTotalFlag()
	    {
	        return countTotalFlag;
	    }

	 public void setCountTotalFlag(String countTotalFlag)
	    {
	        this.countTotalFlag = countTotalFlag;
	    }
	 //end
	 
	public String[] getDsnPort() {
		return dsnPort;
	}

	public void setDsnPort(String[] dsnPort) {
		this.dsnPort = dsnPort;
	}


	public String getLoginAirport() {
		return loginAirport;
	}

	public void setLoginAirport(String loginAirport) {
		this.loginAirport = loginAirport;
	}

	/**
	 * @return Returns the isPlt.
	 */
	public boolean isPlt() {
		return isPlt;
	}

	/**
	 * @param isPlt The isPlt to set.
	 */
	public void setPlt(boolean isPlt) {
		this.isPlt = isPlt;
	}

	/**
	 * @return Returns the operationType.
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType The operationType to set.
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return Returns the currentDialogId.
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

	/**
	 * @param currentDialogId The currentDialogId to set.
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * @return Returns the currentDialogOption.
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption The currentDialogOption to set.
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	/**
	 * @return Returns the checkAll.
	 */
	public String getCheckAll() {
		return checkAll;
	}

	/**
	 * @param checkAll The checkAll to set.
	 */
	public void setCheckAll(String checkAll) {
		this.checkAll = checkAll;
	}

	/**
	 * @return Returns the subCheck.
	 */
	public String[] getSubCheck() {
		return subCheck;
	}

	/**
	 * @param subCheck The subCheck to set.
	 */
	public void setSubCheck(String[] subCheck) {
		this.subCheck = subCheck;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the postalAuthorityCode.
	 */
	public String getPostalAuthorityCode() {
		return postalAuthorityCode;
	}

	/**
	 * @param postalAuthorityCode The postalAuthorityCode to set.
	 */
	public void setPostalAuthorityCode(String postalAuthorityCode) {
		this.postalAuthorityCode = postalAuthorityCode;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the uldNo.
	 */
	public String getUldNo() {
		return uldNo;
	}

	/**
	 * @param uldNo The uldNo to set.
	 */
	public void setUldNo(String uldNo) {
		this.uldNo = uldNo;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return Returns the consignmentNo.
	 */
	public String getConsignmentNo() {
		return consignmentNo;
	}

	/**
	 * @param consignmentNo The consignmentNo to set.
	 */
	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	/**
	 * @return Returns the destnCity.
	 */
	public String getDestnCity() {
		return destnCity;
	}

	/**
	 * @param destnCity The destnCity to set.
	 */
	public void setDestnCity(String destnCity) {
		this.destnCity = destnCity;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
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
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
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
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="DSNEnquiryDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return Returns the originCity.
	 */
	public String getOriginCity() {
		return originCity;
	}

	/**
	 * @param originCity The originCity to set.
	 */
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	
	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="DSNEnquiryDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	 * @return Returns the port.
	 */
	public String getPort() {
		return this.port;
	}

	/**
	 * @param port The port to set.
	 */
	public void setPort(String port) {
		this.port = port;
	}

	public String getTransit() {
		return transit;
	}

	public void setTransit(String transit) {
		this.transit = transit;
	}

	public String getReList() {
		return reList;
	}

	public void setReList(String reList) {
		this.reList = reList;
	}

	/**
	 * @return the isCapNotAcp
	 */
	public boolean isCapNotAcp() {
		return isCapNotAcp;
	}

	/**
	 * @param isCapNotAcp the isCapNotAcp to set
	 */
	public void setCapNotAcp(boolean isCapNotAcp) {
		this.isCapNotAcp = isCapNotAcp;
	}
	/**
	 * @param contNumber the contNumber to set
	 */
	public void setContNumber(String[] contNumber) {
		this.contNumber = contNumber;
	}
	/**
	 * @return the contNumber
	 */
	public String[] getContNumber() {
		return contNumber;
	}

}
