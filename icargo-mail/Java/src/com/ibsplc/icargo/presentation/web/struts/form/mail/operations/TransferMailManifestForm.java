/*
 * TransferMailManifestForm.java Created on Apr02, 2008
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
 * 
 * @author A-3353
 *
 */
public class TransferMailManifestForm extends ScreenModel{

	private static final String SCREEN_ID 
	= "mailtracking.defaults.transfermailmanifest";
private static final String PRODUCT_NAME = "mail";
private static final String SUBPRODUCT_NAME = "operations";
private static final String BUNDLE = "transferMailManifestResources";

private String referenceNumber;
private String fromDate;
private String toDate;
private String inCarrierCode;
private String inFlightNumber;
private String inFlightDate;
private String outCarrierCode;
private String outFlightNumber;
private String outFlightDate;
private String displayPage;
private String selectMail;
private String disableBtn="";

private String airportCode;
private String transferStatus;

private String lastPageNum;

//Added by A-5220 for ICRD-21098 starts 
public static final String NAV_MOD_LIST = "list";
public static final String NAV_MOD_PAGINATION = "navigation";
private String navigationMode;
private String cnPrintType;
private String cnReportType;

/**
 * @return the navigationMode
 */
public String getNavigationMode() {
	return navigationMode;
}
/**
 * @param navigationMode the navigationMode to set
 */
public void setNavigationMode(String navigationMode) {
	this.navigationMode = navigationMode;
}
//Added by A-5220 for ICRD-21098 ends

public String getDisplayPage() {
	return displayPage;
}
public void setDisplayPage(String displayPage) {
	this.displayPage = displayPage;
}
/**
 * @return PRODUCT_NAME - String
 */
public String getProduct() {
	
	return PRODUCT_NAME;
}
/**
 * @return SCREEN_ID - String
 */
public String getScreenId() {
	
	return SCREEN_ID;
}
/**
 * @return SUBPRODUCT_NAME - String
 */
public String getSubProduct() {
	
	return SUBPRODUCT_NAME;
}

/**
 * @return the BUNDLE
 */
public String getBundle() {
	return BUNDLE;
}

/**
 * @return the referenceNumber
 */
public String getReferenceNumber() {
	return referenceNumber;
}

/**
 * @param referenceNumber the referenceNumber to set
 */
public void setReferenceNumber(String referenceNumber) {
	this.referenceNumber = referenceNumber;
}

/**
 * @return the fromDate
 */
@DateFieldId(id="ListMailTransferManifestDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
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
 * @return the inCarrierCode
 */
public String getInCarrierCode() {
	return inCarrierCode;
}

/**
 * @param inCarrierCode the inCarrierCode to set
 */
public void setInCarrierCode(String inCarrierCode) {
	this.inCarrierCode = inCarrierCode;
}

/**
 * @return the inFlightDate
 */
public String getInFlightDate() {
	return inFlightDate;
}

/**
 * @param inFlightDate the inFlightDate to set
 */
public void setInFlightDate(String inFlightDate) {
	this.inFlightDate = inFlightDate;
}

/**
 * @return the inFlightNumber
 */
public String getInFlightNumber() {
	return inFlightNumber;
}

/**
 * @param inFlightNumber the inFlightNumber to set
 */
public void setInFlightNumber(String inFlightNumber) {
	this.inFlightNumber = inFlightNumber;
}

/**
 * @return the outCarrierCode
 */
public String getOutCarrierCode() {
	return outCarrierCode;
}

/**
 * @param outCarrierCode the outCarrierCode to set
 */
public void setOutCarrierCode(String outCarrierCode) {
	this.outCarrierCode = outCarrierCode;
}

/**
 * @return the outFlightDate
 */
public String getOutFlightDate() {
	return outFlightDate;
}

/**
 * @param outFlightDate the outFlightDate to set
 */
public void setOutFlightDate(String outFlightDate) {
	this.outFlightDate = outFlightDate;
}

/**
 * @return the outFlightNumber
 */
public String getOutFlightNumber() {
	return outFlightNumber;
}

/**
 * @param outFlightNumber the outFlightNumber to set
 */
public void setOutFlightNumber(String outFlightNumber) {
	this.outFlightNumber = outFlightNumber;
}

/**
 * @return the toDate
 */
@DateFieldId(id="ListMailTransferManifestDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
public String getToDate() {
	return toDate;
}

/**
 * @param toDate the toDate to set
 */
public void setToDate(String toDate) {
	this.toDate = toDate;
}
public String getLastPageNum() {
	return lastPageNum;
}
public void setLastPageNum(String lastPageNum) {
	this.lastPageNum = lastPageNum;
}
public String getSelectMail() {
	return selectMail;
}
public void setSelectMail(String selectMail) {
	this.selectMail = selectMail;
}
public String getDisableBtn() {
	return disableBtn;
}
public void setDisableBtn(String disableBtn) {
	this.disableBtn = disableBtn;
}
public String getCnPrintType() {
	return cnPrintType;
}
public void setCnPrintType(String cnPrintType) {
	this.cnPrintType = cnPrintType;
}

public String getCnReportType() {
	return cnReportType;
}
public void setCnReportType(String cnReportType) {
	this.cnReportType = cnReportType;
}
public String getAirportCode() {
	return airportCode;
}
public void setAirportCode(String airportCode) {
	this.airportCode = airportCode;
}
public String getTransferStatus() {
	return transferStatus;
}
public void setTransferStatus(String transferStatus) {
	this.transferStatus = transferStatus;
}
private boolean transferParameter;

public boolean isTransferParameter() {
	return transferParameter;
}
public void setTransferParameter(boolean transferParameter) {
	this.transferParameter = transferParameter;
}

}
