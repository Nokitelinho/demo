/*
 * UCMErrorLogForm.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;


/**
 * @author A-1862
 *
 */
public class UCMErrorLogForm extends ScreenModel {
    
	private static final String BUNDLE = "ucmerrorlogResources";

private String bundle;    

private static final String PRODUCT = "uld";

private static final String SUBPRODUCT = "defaults";

private static final String SCREENID = "uld.defaults.ucmerrorlog";


private String displayPage="1";
private String lastPageNumber="0";
private String totalRecords;
private String currentPageNum;
private String pageURL="";
private String ucmerrorlogAirport="";
private String ucmDisableStat="";
private String[] selectedUCMErrorLog;
private String[] errorCodes;
private String[] hasULDError;
private String[] hasUCMError;
private String absIdx="";
private String carrierCode="";
private String flightDate="";

private String flightNo="";
private String duplicateStatus="";
private String msgType="";
private String rowindex="";
private String fromDate="";
private String toDate="";
private String msgStatus="";


private String reconcileStatus;
private String[] uldNumbersFirst;
private String[] uldNumbersSecond;

private String[] pouFirst;
private String[] pouSecond;


private String[] selectedUcmsFirst;
private String[] selectedUcmsSecond;
private String canClose;
private String flightValidationStatus;
private String mismatchStatus;
private String screenStatusValue;
private String listStatus;

private String listflag;

/**
	 * @return the listflag
	 */
	public String getListflag() {
		return listflag;
	}
	/**
	 * @param listflag the listflag to set
	 */
	public void setListflag(String listflag) {
		this.listflag = listflag;
	}

public String getListStatus() {
	return listStatus;
}
public void setListStatus(String listStatus) {
	this.listStatus = listStatus;
}
/**
 * @return Returns the fromDate.
 */
@DateFieldId(id="UCMLogDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
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
 * @return Returns the msgStatus.
 */
public String getMsgStatus() {
	return msgStatus;
}
/**
 * @param msgStatus The msgStatus to set.
 */
public void setMsgStatus(String msgStatus) {
	this.msgStatus = msgStatus;
}
/**
 * @return Returns the toDate.
 */
@DateFieldId(id="UCMLogDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
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
 * @return Returns the pouFirst.
 */
public String[] getPouFirst() {
	return pouFirst;
}
/**
 * @param pouFirst The pouFirst to set.
 */
public void setPouFirst(String[] pouFirst) {
	this.pouFirst = pouFirst;
}
/**
 * @return Returns the pouSecond.
 */
public String[] getPouSecond() {
	return pouSecond;
}
/**
 * @param pouSecond The pouSecond to set.
 */
public void setPouSecond(String[] pouSecond) {
	this.pouSecond = pouSecond;
}
/**
 * 
 * @return
 */
public String getMismatchStatus() {
	return mismatchStatus;
}
/**
 * 
 * @param mismatchStatus
 */
public void setMismatchStatus(String mismatchStatus) {
	this.mismatchStatus = mismatchStatus;
}
/**
 * 
 * @return
 */
public String getFlightValidationStatus() {
	return flightValidationStatus;
}
/**
 * 
 * @param flightValidationStatus
 */
public void setFlightValidationStatus(String flightValidationStatus) {
	this.flightValidationStatus = flightValidationStatus;
}
/**
 * 
 * @return
 */
public String getCanClose() {
	return canClose;
}

/**
 * 
 * @param canClose
 */
public void setCanClose(String canClose) {
	this.canClose = canClose;
}

/**
 * @return Returns the msgType.
 */
public String getMsgType() {
	return msgType;
}

/**
 * @param msgType The msgType to set.
 */
public void setMsgType(String msgType) {
	this.msgType = msgType;
}

/**
 * @return Returns the ucmerrorlogAirport.
 */
public String getUcmerrorlogAirport() {
	return ucmerrorlogAirport;
}

/**
 * @param ucmerrorlogAirport The ucmerrorlogAirport to set.
 */
public void setUcmerrorlogAirport(String ucmerrorlogAirport) {
	this.ucmerrorlogAirport = ucmerrorlogAirport;
}

/**
 * Method to return the product the screen is associated with
 * 
 * @return String
 */
public String getProduct() {
    return PRODUCT;
}

/**
 * Method to return the sub product the screen is associated with
 * 
 * @return String
 */
public String getSubProduct() {
    return SUBPRODUCT;
}

/**
 * Method to return the id the screen is associated with
 * 
 * @return String
 */
public String getScreenId() {
    return SCREENID;
}

/**
 * @return Returns the bundle.
 */
public String getBundle() {
	return BUNDLE;
}

/**
 * @param bundle The bundle to set.
 */
public void setBundle(String bundle) {
	this.bundle = bundle;
}

/**
 * @return Returns the currentPageNum.
 */
public String getCurrentPageNum() {
	return currentPageNum;
}

/**
 * @param currentPageNum The currentPageNum to set.
 */
public void setCurrentPageNum(String currentPageNum) {
	this.currentPageNum = currentPageNum;
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
 * @return Returns the lastPageNumber.
 */
public String getLastPageNumber() {
	return lastPageNumber;
}

/**
 * @param lastPageNumber The lastPageNumber to set.
 */
public void setLastPageNumber(String lastPageNumber) {
	this.lastPageNumber = lastPageNumber;
}

/**
 * @return Returns the pageURL.
 */
public String getPageURL() {
	return pageURL;
}

/**
 * @param pageURL The pageURL to set.
 */
public void setPageURL(String pageURL) {
	this.pageURL = pageURL;
}

/**
 * @return Returns the totalRecords.
 */
public String getTotalRecords() {
	return totalRecords;
}

/**
 * @param totalRecords The totalRecords to set.
 */
public void setTotalRecords(String totalRecords) {
	this.totalRecords = totalRecords;
}

/**
 * @return Returns the selectedUCMErrorLog.
 */
public String[] getSelectedUCMErrorLog() {
	return selectedUCMErrorLog;
}

/**
 * @param selectedUCMErrorLog The selectedUCMErrorLog to set.
 */
public void setSelectedUCMErrorLog(String[] selectedUCMErrorLog) {
	this.selectedUCMErrorLog = selectedUCMErrorLog;
}

/**
 * @return Returns the absIdx.
 */
public String getAbsIdx() {
	return absIdx;
}

/**
 * @param absIdx The absIdx to set.
 */
public void setAbsIdx(String absIdx) {
	this.absIdx = absIdx;
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
 * @return Returns the duplicateStatus.
 */
public String getDuplicateStatus() {
	return duplicateStatus;
}

/**
 * @param duplicateStatus The duplicateStatus to set.
 */
public void setDuplicateStatus(String duplicateStatus) {
	this.duplicateStatus = duplicateStatus;
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
 * @return Returns the flightNo.
 */
public String getFlightNo() {
	return flightNo;
}

/**
 * @param flightNo The flightNo to set.
 */
public void setFlightNo(String flightNo) {
	this.flightNo = flightNo;
}

/**
 * @return Returns the rowindex.
 */
public String getRowindex() {
	return rowindex;
}

/**
 * @param rowindex The rowindex to set.
 */
public void setRowindex(String rowindex) {
	this.rowindex = rowindex;
}
/**
 * 
 * @return
 */
public String getReconcileStatus() {
	return reconcileStatus;
}
/**
 * 
 * @param reconcileStatus
 */
public void setReconcileStatus(String reconcileStatus) {
	this.reconcileStatus = reconcileStatus;
}



/**
 * @return Returns the hasULDError.
 */
public String[] getHasULDError() {
	return hasULDError;
}

/**
 * @param hasULDError The hasULDError to set.
 */
public void setHasULDError(String[] hasULDError) {
	this.hasULDError = hasULDError;
}

/**
 * @return Returns the hasUCMError.
 */
public String[] getHasUCMError() {
	return hasUCMError;
}

/**
 * @param hasUCMError The hasUCMError to set.
 */
public void setHasUCMError(String[] hasUCMError) {
	this.hasUCMError = hasUCMError;
}

/**
 * @return Returns the ucmDisableStat.
 */
public String getUcmDisableStat() {
	return ucmDisableStat;
}

/**
 * @param ucmDisableStat The ucmDisableStat to set.
 */
public void setUcmDisableStat(String ucmDisableStat) {
	this.ucmDisableStat = ucmDisableStat;
}

/**
 * @return Returns the errorCodes.
 */
public String[] getErrorCodes() {
	return errorCodes;
}

/**
 * @param errorCodes The errorCodes to set.
 */
public void setErrorCodes(String[] errorCodes) {
	this.errorCodes = errorCodes;
}

/**
 * @return Returns the selectedUcmsFirst.
 */
public String[] getSelectedUcmsFirst() {
	return selectedUcmsFirst;
}

/**
 * @param selectedUcmsFirst The selectedUcmsFirst to set.
 */
public void setSelectedUcmsFirst(String[] selectedUcmsFirst) {
	this.selectedUcmsFirst = selectedUcmsFirst;
}

/**
 * @return Returns the selectedUcmsSecond.
 */
public String[] getSelectedUcmsSecond() {
	return selectedUcmsSecond;
}

/**
 * @param selectedUcmsSecond The selectedUcmsSecond to set.
 */
public void setSelectedUcmsSecond(String[] selectedUcmsSecond) {
	this.selectedUcmsSecond = selectedUcmsSecond;
}

/**
 * @return Returns the uldNumbersFirst.
 */
public String[] getUldNumbersFirst() {
	return uldNumbersFirst;
}

/**
 * @param uldNumbersFirst The uldNumbersFirst to set.
 */
public void setUldNumbersFirst(String[] uldNumbersFirst) {
	this.uldNumbersFirst = uldNumbersFirst;
}

/**
 * @return Returns the uldNumbersSecond.
 */
public String[] getUldNumbersSecond() {
	return uldNumbersSecond;
}

/**
 * @param uldNumbersSecond The uldNumbersSecond to set.
 */
	public void setUldNumbersSecond(String[] uldNumbersSecond) {
		this.uldNumbersSecond = uldNumbersSecond;
	}
public String getScreenStatusValue() {
	return screenStatusValue;
}
public void setScreenStatusValue(String screenStatusValue) {
	this.screenStatusValue = screenStatusValue;
}	  
    
 }
