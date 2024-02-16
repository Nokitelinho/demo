/*
 * InvoicEnquiryForm.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2270
 *
 */
public class InvoicEnquiryForm extends ScreenModel {
	
private static final String BUNDLE ="invoicEnquiry";

//private String bundle;

private final static String PRODUCT = "mail";

private final static String SUBPRODUCT = "mra";

private final static String SCREENID = "mailtracking.mra.defaults.invoicenquiry";
//Added by A-5218 to enable Last Link in Pagination to start
public static final String PAGINATION_MODE_FROM_FILTER="YES";
//Added by A-5218 to enable Last Link in Pagination to end

private String companyCode;

private String poaCode;

private String invoiceKey;

private String[] receptacleIdr;

private String[] sectorOrigin;

private String[] sectorDestination;

private String schInvoiceDate;

private String paymentType;

private String reconcilStatus;

private String carrierCode;

private String carrierName;

private String controlValue;

private String totalAdjAmt;

private String rateTypeIndicator;

private String consignmentDocNum;

private String operationFlag[];

private String  lastPageNum = "0";
private String  displayPage="1";

private String absoluteIndex = "0";
private String[] rowId;

/** added for integrating with ListUPURateCard screen */
private String invokingScreen;
/** added by A-5218 to enable last link in Pagination */
private String paginationMode;


/**
 * @return the paginationMode
 */
public String getPaginationMode() {
	return paginationMode;
}

/**
 * @param paginationMode the paginationMode to set
 */
public void setPaginationMode(String paginationMode) {
	this.paginationMode = paginationMode;
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
 * @return the carrierCode
 */
public String getCarrierCode() {
	return carrierCode;
}

/**
 * @param carrierCode the carrierCode to set
 */
public void setCarrierCode(String carrierCode) {
	this.carrierCode = carrierCode;
}

/**
 * @return the carrierName
 */
public String getCarrierName() {
	return carrierName;
}

/**
 * @param carrierName the carrierName to set
 */
public void setCarrierName(String carrierName) {
	this.carrierName = carrierName;
}

/**
 * @return the consignmentDocNum
 */
public String getConsignmentDocNum() {
	return consignmentDocNum;
}

/**
 * @param consignmentDocNum the consignmentDocNum to set
 */
public void setConsignmentDocNum(String consignmentDocNum) {
	this.consignmentDocNum = consignmentDocNum;
}

/**
 * @return the controlValue
 */
public String getControlValue() {
	return controlValue;
}

/**
 * @param controlValue the controlValue to set
 */
public void setControlValue(String controlValue) {
	this.controlValue = controlValue;
}

/**
 * @return the invoiceKey
 */
public String getInvoiceKey() {
	return invoiceKey;
}

/**
 * @param invoiceKey the invoiceKey to set
 */
public void setInvoiceKey(String invoiceKey) {
	this.invoiceKey = invoiceKey;
}

/**
 * @return the paymentType
 */
public String getPaymentType() {
	return paymentType;
}

/**
 * @param paymentType the paymentType to set
 */
public void setPaymentType(String paymentType) {
	this.paymentType = paymentType;
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
 * @return the rateTypeIndicator
 */
public String getRateTypeIndicator() {
	return rateTypeIndicator;
}

/**
 * @param rateTypeIndicator the rateTypeIndicator to set
 */
public void setRateTypeIndicator(String rateTypeIndicator) {
	this.rateTypeIndicator = rateTypeIndicator;
}

/**
 * @return the receptacleIdr
 */
public String[] getReceptacleIdr() {
	return receptacleIdr;
}

/**
 * @param receptacleIdr the receptacleIdr to set
 */
public void setReceptacleIdr(String[] receptacleIdr) {
	this.receptacleIdr = receptacleIdr;
}

/**
 * @return the reconcilStatus
 */
public String getReconcilStatus() {
	return reconcilStatus;
}

/**
 * @param reconcilStatus the reconcilStatus to set
 */
public void setReconcilStatus(String reconcilStatus) {
	this.reconcilStatus = reconcilStatus;
}

/**
 * @return the schInvoiceDate
 */
public String getSchInvoiceDate() {
	return schInvoiceDate;
}

/**
 * @param schInvoiceDate the schInvoiceDate to set
 */
public void setSchInvoiceDate(String schInvoiceDate) {
	this.schInvoiceDate = schInvoiceDate;
}

/**
 * @return the sectorDestination
 */
public String[] getSectorDestination() {
	return sectorDestination;
}

/**
 * @param sectorDestination the sectorDestination to set
 */
public void setSectorDestination(String[] sectorDestination) {
	this.sectorDestination = sectorDestination;
}

/**
 * @return the sectorOrigin
 */
public String[] getSectorOrigin() {
	return sectorOrigin;
}

/**
 * @param sectorOrigin the sectorOrigin to set
 */
public void setSectorOrigin(String[] sectorOrigin) {
	this.sectorOrigin = sectorOrigin;
}

/**
 * @return the totalAdjAmt
 */
public String getTotalAdjAmt() {
	return totalAdjAmt;
}

/**
 * @param totalAdjAmt the totalAdjAmt to set
 */
public void setTotalAdjAmt(String totalAdjAmt) {
	this.totalAdjAmt = totalAdjAmt;
}


}
