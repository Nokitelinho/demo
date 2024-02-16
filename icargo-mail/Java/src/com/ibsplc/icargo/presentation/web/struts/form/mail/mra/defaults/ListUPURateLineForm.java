/*
 * ListUPURateLineForm.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1556
 *
 */
public class ListUPURateLineForm extends ScreenModel {
	
private static final String BUNDLE ="viewupurate";

//private String bundle;

private final static String PRODUCT = "mail";

private final static String SUBPRODUCT = "mra";

private final static String SCREENID = "mailtracking.mra.defaults.viewupurate";

private String companyCode;

private String rateCardID;

private String origin;

private String destination;

private String fromDate;

private String toDate;

private String status;

private String[] rateLineStatus;

private String operationFlag[];

private String changeStatusFlag;

private String  lastPageNum = "0";
private String  displayPage="1";

private String absoluteIndex = "0";
private String[] rowId;

private String newStatus;

/** added for integrating with ListUPURateCard screen */
private String invokingScreen;

/** added for change status */

private String selectedIndexes;

private String orgDstLevel;

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
 * @return Returns the rowId.
 */
public String[] getRowId() {
	return rowId;
}

/**
 * @param rowId The rowId to set.
 */
public void setRowId(String[] rowId) {
	this.rowId = rowId;
}

/**
 * @return Returns the destination.
 */
public String getDestination() {
	return destination;
}

/**
 * @param destination The destination to set.
 */
public void setDestination(String destination) {
	this.destination = destination;
}

/**
 * @return Returns the fromDate.
 */
@DateFieldId(id="ViewRateLineDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
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
 * @return Returns the origin.
 */
public String getOrigin() {
	return origin;
}

/**
 * @param origin The origin to set.
 */
public void setOrigin(String origin) {
	this.origin = origin;
}

/**
 * @return Returns the rateCardID.
 */
public String getRateCardID() {
	return rateCardID;
}

/**
 * @param rateCardID The rateCardID to set.
 */
public void setRateCardID(String rateCardID) {
	this.rateCardID = rateCardID;
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
 * @return Returns the toDate.
 */
@DateFieldId(id="ViewRateLineDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
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
 * @return Returns the screenId.
 */
public String getScreenId() {
    return SCREENID;
}

/**
 * @return Returns the product.
 */
public String getProduct() {
    return PRODUCT;
}

/**
 * @return Returns the subProduct.
 */
public String getSubProduct() {
    return SUBPRODUCT;
}

/**
 * @return Returns the bundle.
 */
public String getBundle() {
	return BUNDLE;
}


///**
// * @param bundle The bundle to set.
// */
//public void setBundle(String bundle) {
//	this.bundle = bundle;
//}


/**
 * @return Returns the absoluteIndex.
 */
public String getAbsoluteIndex() {
	return absoluteIndex;
}

/**
 * @param absoluteIndex The absoluteIndex to set.
 */
public void setAbsoluteIndex(String absoluteIndex) {
	this.absoluteIndex = absoluteIndex;
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
 * @return Returns the operationFlag.
 */
public String[] getOperationFlag() {
	return operationFlag;
}

/**
 * @param operationFlag The operationFlag to set.
 */
public void setOperationFlag(String[] operationFlag) {
	this.operationFlag = operationFlag;
}

/**
 * @return Returns the changeStatusFlag.
 */
public String getChangeStatusFlag() {
	return changeStatusFlag;
}

/**
 * @param changeStatusFlag The changeStatusFlag to set.
 */
public void setChangeStatusFlag(String changeStatusFlag) {
	this.changeStatusFlag = changeStatusFlag;
}

/**
 * @return Returns the newStatus.
 */
public String getNewStatus() {
	return newStatus;
}

/**
 * @param newStatus The newStatus to set.
 */
public void setNewStatus(String newStatus) {
	this.newStatus = newStatus;
}

/**
 * @return Returns the invokingScreen.
 */
public String getInvokingScreen() {
	return invokingScreen;
}

/**
 * @param invokingScreen The invokingScreen to set.
 */
public void setInvokingScreen(String invokingScreen) {
	this.invokingScreen = invokingScreen;
}

/**
 * @return Returns the companyCode.
 */
public String getCompanyCode() {
	return companyCode;
}

/**
 * @param companyCode The companyCode to set.
 */
public void setCompanyCode(String companyCode) {
	this.companyCode = companyCode;
}

/**
 * @return Returns the selectedIndexes.
 */
public String getSelectedIndexes() {
	return selectedIndexes;
}

/**
 * @param selectedIndexes The selectedIndexes to set.
 */
public void setSelectedIndexes(String selectedIndexes) {
	this.selectedIndexes = selectedIndexes;
}

/**
 * @return Returns the rateLineStatus.
 */
public String[] getRateLineStatus() {
	return rateLineStatus;
}

/**
 * @param rateLineStatus The rateLineStatus to set.
 */
public void setRateLineStatus(String[] rateLineStatus) {
	this.rateLineStatus = rateLineStatus;
}

/**
 * 	Getter for orgDstLevel 
 *	Added by : A-5219 on 23-Oct-2020
 * 	Used for :
 */
public String getOrgDstLevel() {
	return orgDstLevel;
}

/**
 *  @param orgDstLevel the orgDstLevel to set
 * 	Setter for orgDstLevel 
 *	Added by : A-5219 on 23-Oct-2020
 * 	Used for :
 */
public void setOrgDstLevel(String orgDstLevel) {
	this.orgDstLevel = orgDstLevel;
}


}
