/*
 * ListInterlineBillingEntriesForm.java Created on Aug 9, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3434
 *
 */
public class ListInterlineBillingEntriesForm extends ScreenModel {

private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
	
private static final String PRODUCT = "mail";
	
private static final String SUBPRODUCT = "mra.airlinebilling.defaults";
	
private static final String BUNDLE = "listinterlinebillingentriesBundle";
	
private String fromDate;
	
private String toDate;
	
private String airlineCode;
	
private String billingType;
	
private String billingStatus;
	
private String sectorFrom;

private String sectorTo;

private String[] saveBillingStatus;
private String popupRemarks;
private String select;
private String remarks;
private String[] check;
private String reviewCheck;
private String showPopup;
private String screenStatus;
private String review;
private String disableButton;
private String fromScreen;
private String displayPage = "1";
private String lastPageNum= "0";
private String selectedrows;
private String parameterValue;
private String originOfficeOfExchange;

private String currentDialogOption;

private String currentDialogId;
private String isReviewEnabledFlag;
public String getOriginOfficeOfExchange() {
	return originOfficeOfExchange;
}

public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
	this.originOfficeOfExchange = originOfficeOfExchange;
}

public String getYear() {
	return year;
}

public void setYear(String year) {
	this.year = year;
}

public String getSubClass() {
	return subClass;
}

public void setSubClass(String subClass) {
	this.subClass = subClass;
}

public String getMailCategory() {
	return mailCategory;
}

public void setMailCategory(String mailCategory) {
	this.mailCategory = mailCategory;
}

public String getDestinationOfficeOfExchange() {
	return destinationOfficeOfExchange;
}

public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
	this.destinationOfficeOfExchange = destinationOfficeOfExchange;
}

public String getDsn() {
	return dsn;
}

public void setDsn(String dsn) {
	this.dsn = dsn;
}

private String year;
private String subClass;
private String mailCategory;
private String destinationOfficeOfExchange;
private String dsn;
/**
 * @return the parameterValue
 */
public String getParameterValue() {
	return parameterValue;
}

/**
 * @param parameterValue the parameterValue to set
 */
public void setParameterValue(String parameterValue) {
	this.parameterValue = parameterValue;
}

/**
 * @return the receptacleSerialNumber
 */
public String getReceptacleSerialNumber() {
	return receptacleSerialNumber;
}

/**
 * @param receptacleSerialNumber the receptacleSerialNumber to set
 */
public void setReceptacleSerialNumber(String receptacleSerialNumber) {
	this.receptacleSerialNumber = receptacleSerialNumber;
}

/**
 * @return the highestNumberIndicator
 */
public String getHighestNumberIndicator() {
	return highestNumberIndicator;
}

/**
 * @param highestNumberIndicator the highestNumberIndicator to set
 */
public void setHighestNumberIndicator(String highestNumberIndicator) {
	this.highestNumberIndicator = highestNumberIndicator;
}

/**
 * @return the registeredIndicator
 */
public String getRegisteredIndicator() {
	return registeredIndicator;
}

/**
 * @param registeredIndicator the registeredIndicator to set
 */
public void setRegisteredIndicator(String registeredIndicator) {
	this.registeredIndicator = registeredIndicator;
}

private String receptacleSerialNumber;
private String highestNumberIndicator;
private String registeredIndicator;
//added by A-5223 for ICRD-21098 starts

public static final String PAGINATION_MODE_FROM_FILTER = "LIST";

public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "LINK";

private String paginationMode;

//added by A-5223 for ICRD-21098 ends
/**
 * @return Returns the selectedrows.
 */
public String getSelectedrows() {
	return selectedrows;
}

/**
 * @param selectedrows The selectedrows to set.
 */
public void setSelectedrows(String selectedrows) {
	this.selectedrows = selectedrows;
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
 * @return Returns the disableButton.
 */
public String getDisableButton() {
	return disableButton;
}

/**
 * @param disableButton The disableButton to set.
 */
public void setDisableButton(String disableButton) {
	this.disableButton = disableButton;
}

/**
 * @return Returns the fromScreen.
 */
public String getFromScreen() {
	return fromScreen;
}

/**
 * @param fromScreen The fromScreen to set.
 */
public void setFromScreen(String fromScreen) {
	this.fromScreen = fromScreen;
}

/**
 * @return Returns the saveBillingStatus.
 */
public String[] getSaveBillingStatus() {
	return saveBillingStatus;
}

/**
 * @param saveBillingStatus The saveBillingStatus to set.
 */
public void setSaveBillingStatus(String[] saveBillingStatus) {
	this.saveBillingStatus = saveBillingStatus;
}

/**
 * @return Returns the review.
 */
public String getReview() {
	return review;
}

/**
 * @param review The review to set.
 */
public void setReview(String review) {
	this.review = review;
}

/**
 * @return Returns the reviewCheck.
 */
public String getReviewCheck() {
	return reviewCheck;
}

/**
 * @param reviewCheck The reviewCheck to set.
 */
public void setReviewCheck(String reviewCheck) {
	this.reviewCheck = reviewCheck;
}

/**
 * @return Returns the screenStatus.
 */
public String getScreenStatus() {
	return screenStatus;
}

/**
 * @param screenStatus The screenStatus to set.
 */
public void setScreenStatus(String screenStatus) {
	this.screenStatus = screenStatus;
}

/**
 * @return Returns the showPopup.
 */
public String getShowPopup() {
	return showPopup;
}

/**
 * @param showPopup The showPopup to set.
 */
public void setShowPopup(String showPopup) {
	this.showPopup = showPopup;
}

/**
 * @return Returns the popupRemarks.
 */
public String getPopupRemarks() {
	return popupRemarks;
}

/**
 * @param popupRemarks The popupRemarks to set.
 */
public void setPopupRemarks(String popupRemarks) {
	this.popupRemarks = popupRemarks;
}

/**
 * @return Returns the check.
 */
public String[] getCheck() {
	return check;
}

/**
 * @param check The check to set.
 */
public void setCheck(String[] check) {
	this.check = check;
}

/**
 * @return Returns the remarks.
 */
public String getRemarks() {
	return remarks;
}

/**
 * @param remarks The remarks to set.
 */
public void setRemarks(String remarks) {
	this.remarks = remarks;
}

/**
 * @return Returns the select.
 */
public String getSelect() {
	return select;
}

/**
 * @param select The select to set.
 */
public void setSelect(String select) {
	this.select = select;
}





/**
 * @return Returns the airlineCode.
 */
public String getAirlineCode() {
	return airlineCode;
}

/**
 * @param airlineCode The airlineCode to set.
 */
public void setAirlineCode(String airlineCode) {
	this.airlineCode = airlineCode;
}

/**
 * @return Returns the billingStatus.
 */
public String getBillingStatus() {
	return billingStatus;
}

/**
 * @param billingStatus The billingStatus to set.
 */
public void setBillingStatus(String billingStatus) {
	this.billingStatus = billingStatus;
}

/**
 * @return Returns the billingType.
 */
public String getBillingType() {
	return billingType;
}

/**
 * @param billingType The billingType to set.
 */
public void setBillingType(String billingType) {
	this.billingType = billingType;
}

/**
 * @return Returns the fromDate.
 */
@ DateFieldId(id="ListInterlineBillingEntriesDateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/
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
 * @return Returns the sectorFrom.
 */
public String getSectorFrom() {
	return sectorFrom;
}

/**
 * @param sectorFrom The sectorFrom to set.
 */
public void setSectorFrom(String sectorFrom) {
	this.sectorFrom = sectorFrom;
}

/**
 * @return Returns the sectorTo.
 */
public String getSectorTo() {
	return sectorTo;
}

/**
 * @param sectorTo The sectorTo to set.
 */
public void setSectorTo(String sectorTo) {
	this.sectorTo = sectorTo;
}

/**
 * @return Returns the toDate.
 */
@ DateFieldId(id="ListInterlineBillingEntriesDateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/
public String getToDate() {
	return toDate;
}

/**
 * @param toDate The toDate to set.
 */
public void setToDate(String toDate) {
	this.toDate = toDate;
}

/* (non-Javadoc)
 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
 */
public String getScreenId() {
	return SCREEN_ID;
}

/* (non-Javadoc)
 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
 */
public String getProduct() {
	return PRODUCT;
}

/* (non-Javadoc)
 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
 */
public String getSubProduct() {
	return SUBPRODUCT;
}

public String getBundle() {
	return BUNDLE;
}

/**
 * 	Getter for paginationMode 
 *	Added by : a-5223 on 16-Oct-2012
 * 	Used for : getting pagination mode
 */
public String getPaginationMode() {
	return paginationMode;
}

/**
 *  @param paginationMode the paginationMode to set
 * 	Setter for paginationMode 
 *	Added by : a-5223 on 16-Oct-2012
 * 	Used for : setting pagination mode
 */
public void setPaginationMode(String paginationMode) {
	this.paginationMode = paginationMode;
}

/**
 * 	Getter for currentDialogOption 
 *	Added by : A-5219 on 16-Oct-2019
 * 	Used for :
 */
public String getCurrentDialogOption() {
	return currentDialogOption;
}

/**
 *  @param currentDialogOption the currentDialogOption to set
 * 	Setter for currentDialogOption 
 *	Added by : A-5219 on 16-Oct-2019
 * 	Used for :
 */
public void setCurrentDialogOption(String currentDialogOption) {
	this.currentDialogOption = currentDialogOption;
}

/**
 * 	Getter for currentDialogId 
 *	Added by : A-5219 on 16-Oct-2019
 * 	Used for :
 */
public String getCurrentDialogId() {
	return currentDialogId;
}

/**
 *  @param currentDialogId the currentDialogId to set
 * 	Setter for currentDialogId 
 *	Added by : A-5219 on 16-Oct-2019
 * 	Used for :
 */
public void setCurrentDialogId(String currentDialogId) {
	this.currentDialogId = currentDialogId;
}

public String getIsReviewEnabledFlag() {
	return isReviewEnabledFlag;
}

public void setIsReviewEnabledFlag(String isReviewEnabledFlag) {
	this.isReviewEnabledFlag = isReviewEnabledFlag;
}

}
