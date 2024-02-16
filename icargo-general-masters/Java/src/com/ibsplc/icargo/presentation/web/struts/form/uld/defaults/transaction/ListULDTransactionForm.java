/*
 * ListULDTransactionForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;

/**
 * @author A-1496
 * 
 */
public class ListULDTransactionForm extends ScreenModel {

	/**
	 * BUNDLE
	 */
	private static final String BUNDLE = "loanBorrowDetailsEnquiryResources";

	/**
	 * PRODUCT
	 */
	private static final String PRODUCT = "uld";

	/**
	 * SUBPRODUCT
	 */
	private static final String SUBPRODUCT = "defaults";

	/**
	 * SCREENID
	 */
	private static final String SCREENID = "uld.defaults.loanborrowdetailsenquiry";

	/**
	 * bundle
	 */
	private String bundle;

	/**
	 * uldNum
	 */
	private String uldNum;

	/**
	 * uldTypeCode
	 */
	private String uldTypeCode;

	/**
	 * accessoryCode
	 */
	private String accessoryCode;

	/**
	 * txnType
	 */
	private String txnType;

	/**
	 * txnStatus
	 */
	private String txnStatus;

	/**
	 * partyType
	 */
	private String partyType;

	/**
	 * partyCode
	 */
	private String fromPartyCode;

	private String toPartyCode;

	/**
	 * txnStation
	 */
	private String txnStation;

	/**
	 * returnStation
	 */
	private String returnStation;

	private String enquiryDisableStatus;

	/**
	 * txnFromDate
	 */
	private String txnFromDate;

	/**
	 * returnStation
	 */
	private String txnToDate;

	/**
	 * returnFromDate
	 */
	private String returnFromDate;

	/**
	 * returnToDate
	 */
	private String returnToDate;

	/**
	 * uldDetails
	 */
	private String[] uldDetails;

	/**
	 * accessoryQuantity
	 */
	private String[] accessoryDetails;

	/**
	 * closeFlag
	 */
	private String closeFlag;

	/**
	 * for modify txn details pop up
	 * 
	 * modULDNo
	 */
	private String modULDNo;

	/**
	 * modTxnType
	 */
	private String modTxnType;

	/**
	 * modPartyType
	 */
	private String modPartyType;

	/**
	 * modPartyCode
	 */
	private String modPartyCode;

	/**
	 * modTxnDate
	 */
	private String modTxnDate;
	private String modTxnTime;
	
	private String modLseEndDate;

	/**
	 * modDuration
	 */
	private String modDuration;

	/**
	 * modTxnRemarks
	 */
	private String modTxnRemarks;

	/**
	 * closeModifyFlag
	 */
	private String closeModifyFlag;

	/**
	 * mode
	 */
	private String mode;

	 private String displayPage="1";
	 private String lastPageNumber="0";
	 private String totalRecords;
	 private String currentPageNum;
	 private String listMode="";
	 private String showTab="";

	/**
	 * invoiceId
	 */
	private String invoiceId;
/**
 * 
 */
	private String popupflag;


	private String comboFlag;

	private String txnFrmTime;

	private String txnToTime;

	private String returnFrmTime;

	private String returnToTime;

	private String listStatus;
	
	//Added by Preet on 4th Jan for newly added fileds --CRN and ULD condition starts
	private String modUldCondition;	
	
	private String modCRN; 
	
	private String modCrnPrefix; 
	
	//Added by Preet for newly added fileds --CRN and ULD condition ends	
	private String uldNumbersSelected;
	private String txnNumbersSelected;
	
	
	// ends
	//added by nisha for CR-15 on 21-11-07...indicates whether to open the msgbroker popup or not
	private String msgFlag;
//	added by nisha ends
	
	//Added by A-7131 for ICRD-220974
	private String fromPopup;

	// Added by a-3278 for QF1015 on 03Jul08
	/**
	 * desStation
	 */
	private String desStation;

	/**
	 * modRtnDate
	 */
	private String modRtnDate;

	/**
	 * modRtnTime
	 */
	private String modRtnTime;

	/**
	 * rtnRemarks
	 */
	private String rtnRemarks;

	/**
	 * rtndemurrage
	 */
	private String rtndemurrage;

	/**
	 * rtnwaived
	 */
	private String rtnwaived;

	/**
	 * rtntaxes
	 */
	private String rtntaxes;
	
	/**
	 * totalDemmurage
	 */
	private String totalDemmurage;
	
	//a-3278 ends		 
	/**
	 * added by a-3278 for ULD771
	 * baseCurrency
	 */
	private String baseCurrency;
	
	
	private String chkTransaction;
	
	//added by a-3045 for CR QF1142 starts
	private String mucStatus;
	private String[] detailMUCStatus;
	private String mucRefNum;
	private String mucReferenceNumber;
	private String pageURL;
	//added by a-3045 for CR QF1142 ends
	
	//added by a-3045 for CR QF1015 starts
	private String modAwbNumber;
	
	private String modLoaded;
	/**
	 * returnQuantity
	 */
	private String[] returnQuantity;
	//added by a-3045 for CR QF1015 ends
	
	//added by a-3278 for bug 18372 on 18Sep08
	/**
	 * currency
	 */
	private String currency;
	//Added for pagination when mutiple ulds are selected
	private String modDisplayPage = "1";
	    
    private String modCurrentPage = "1";
    
    private String modLastPageNum = "0";
    
    private String modTotalRecords ="0";
	   
	//ends
    //added by a-3278 for bug 23062 on 29Oct08
    private String poolOwnerFlag;
       
    //added by a-3045 for bug 26528 starts,This is for Control Receipt No. filter in Loan Borrow Enquiry Screen
    private String controlReceiptNo;
    //added for same bug 26528 in 26Nov08 starts
    private String controlReceiptNoPrefix;
    private String controlReceiptNoMid;
    //added for same bug 26528 in 26Nov08 ends
    //added by a-3045 for bug 26528 ends
    
    //added by a-3278 for bug 26527 on 21Nov08
    private String dummy;
    private String dummyAirport;
    private String dummyDate;
    private String dummyTime;
    private String dummyDemmurage;
    private String loginStation;
    //a-3278 ends
    private String totalCountFlag;
    
    private String modifiedFlag;    

    /**
     * added by a-3278 for bug ULD823 on 15Dec08
	 * modFlag
	 */
	private String modFlag;

	/**
     * added by a-3278 for bug 30887 on 16Dec08
	 * dummyModFlag
	 */
	private String dummyCRN;
	
	/**
     * added by a-3278 for bug 30887 on 16Dec08
	 * dummyCRNPrefix
	 */
	private String dummyCRNPrefix;
	private String leaseOrReturnFlg;
	
	/**
	 * @return the poolOwnerFlag
	 */
	public String getPoolOwnerFlag() {
		return poolOwnerFlag;
	}

	/**
	 * @param poolOwnerFlag the poolOwnerFlag to set
	 */
	public void setPoolOwnerFlag(String poolOwnerFlag) {
		this.poolOwnerFlag = poolOwnerFlag;
	}
	//a-3278 ends

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	//a-3278 ends
	
	public String getChkTransaction() {
		return chkTransaction;
	}

	public void setChkTransaction(String chkTransaction) {
		this.chkTransaction = chkTransaction;
	}

	public String getListStatus() {
		return listStatus;
	}

	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	/**
	 * @return Returns the comboFlag.
	 */
	public String getComboFlag() {
		return comboFlag;
	}

	/**
	 * @param comboFlag
	 *            The comboFlag to set.
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
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
	 * @param bundle
	 *            The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return Returns the accessoryCode.
	 */
	public String getAccessoryCode() {
		return this.accessoryCode;
	}

	/**
	 * @param accessoryCode
	 *            The accessoryCode to set.
	 */
	public void setAccessoryCode(String accessoryCode) {
		this.accessoryCode = accessoryCode;
	}

	/**
	 * @return Returns the accessoryDetails.
	 */
	public String[] getAccessoryDetails() {
		return this.accessoryDetails;
	}

	/**
	 * @param accessoryDetails
	 *            The accessoryDetails to set.
	 */
	public void setAccessoryDetails(String[] accessoryDetails) {
		this.accessoryDetails = accessoryDetails;
	}

	/**
	 * @return Returns the partyType.
	 */
	public String getPartyType() {
		return this.partyType;
	}

	/**
	 * @param partyType
	 *            The partyType to set.
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	/**
	 * @return Returns the returnFromDate.
	 */
	@DateFieldId(id="LoanReturnDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getReturnFromDate() {
		return this.returnFromDate;
	}

	/**
	 * @param returnFromDate
	 *            The returnFromDate to set.
	 */
	public void setReturnFromDate(String returnFromDate) {
		this.returnFromDate = returnFromDate;
	}

	/**
	 * @return Returns the returnStation.
	 */
	public String getReturnStation() {
		return this.returnStation;
	}

	/**
	 * @param returnStation
	 *            The returnStation to set.
	 */
	public void setReturnStation(String returnStation) {
		this.returnStation = returnStation;
	}

	/**
	 * @return Returns the returnToDate.
	 */
	@DateFieldId(id="LoanReturnDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getReturnToDate() {
		return this.returnToDate;
	}

	/**
	 * @param returnToDate
	 *            The returnToDate to set.
	 */
	public void setReturnToDate(String returnToDate) {
		this.returnToDate = returnToDate;
	}

	/**
	 * @return Returns the txnFromDate.
	 */
	@DateFieldId(id="LoanDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getTxnFromDate() {
		return this.txnFromDate;
	}

	/**
	 * @param txnFromDate
	 *            The txnFromDate to set.
	 */
	public void setTxnFromDate(String txnFromDate) {
		this.txnFromDate = txnFromDate;
	}

	/**
	 * @return Returns the txnStation.
	 */
	public String getTxnStation() {
		return this.txnStation;
	}

	/**
	 * @param txnStation
	 *            The txnStation to set.
	 */
	public void setTxnStation(String txnStation) {
		this.txnStation = txnStation;
	}

	/**
	 * @return Returns the txnStatus.
	 */
	public String getTxnStatus() {
		return this.txnStatus;
	}

	/**
	 * @param txnStatus
	 *            The txnStatus to set.
	 */
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	/**
	 * @return Returns the txnToDate.
	 */
	@DateFieldId(id="LoanDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getTxnToDate() {
		return this.txnToDate;
	}

	/**
	 * @param txnToDate
	 *            The txnToDate to set.
	 */
	public void setTxnToDate(String txnToDate) {
		this.txnToDate = txnToDate;
	}

	/**
	 * @return Returns the txnType.
	 */
	public String getTxnType() {
		return this.txnType;
	}

	/**
	 * @param txnType
	 *            The txnType to set.
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return Returns the uldDetails.
	 */
	public String[] getUldDetails() {
		return this.uldDetails;
	}

	/**
	 * @param uldDetails
	 *            The uldDetails to set.
	 */
	public void setUldDetails(String[] uldDetails) {
		this.uldDetails = uldDetails;
	}

	/**
	 * @return Returns the uldNum.
	 */
	public String getUldNum() {
		return this.uldNum;
	}

	/**
	 * @param uldNum
	 *            The uldNum to set.
	 */
	public void setUldNum(String uldNum) {
		this.uldNum = uldNum;
	}

	/**
	 * @return Returns the uldTypeCode.
	 */
	public String getUldTypeCode() {
		return this.uldTypeCode;
	}

	/**
	 * @param uldTypeCode
	 *            The uldTypeCode to set.
	 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
	}

	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return this.closeFlag;
	}

	/**
	 * @param closeFlag
	 *            The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return Returns the closeModifyFlag.
	 */
	public String getCloseModifyFlag() {
		return this.closeModifyFlag;
	}

	/**
	 * @param closeModifyFlag
	 *            The closeModifyFlag to set.
	 */
	public void setCloseModifyFlag(String closeModifyFlag) {
		this.closeModifyFlag = closeModifyFlag;
	}

	/**
	 * @return Returns the modDuration.
	 */
	public String getModDuration() {
		return this.modDuration;
	}

	/**
	 * @param modDuration
	 *            The modDuration to set.
	 */
	public void setModDuration(String modDuration) {
		this.modDuration = modDuration;
	}

	/**
	 * @return Returns the modPartyCode.
	 */
	public String getModPartyCode() {
		return this.modPartyCode;
	}

	/**
	 * @param modPartyCode
	 *            The modPartyCode to set.
	 */
	public void setModPartyCode(String modPartyCode) {
		this.modPartyCode = modPartyCode;
	}

	/**
	 * @return Returns the modPartyType.
	 */
	public String getModPartyType() {
		return this.modPartyType;
	}

	/**
	 * @param modPartyType
	 *            The modPartyType to set.
	 */
	public void setModPartyType(String modPartyType) {
		this.modPartyType = modPartyType;
	}

	/**
	 * @return Returns the modTxnDate.
	 */
	public String getModTxnDate() {
		return this.modTxnDate;
	}

	/**
	 * @param modTxnDate
	 *            The modTxnDate to set.
	 */
	public void setModTxnDate(String modTxnDate) {
		this.modTxnDate = modTxnDate;
	}

	/**
	 * @return Returns the modTxnRemarks.
	 */
	public String getModTxnRemarks() {
		return this.modTxnRemarks;
	}

	/**
	 * @param modTxnRemarks
	 *            The modTxnRemarks to set.
	 */
	public void setModTxnRemarks(String modTxnRemarks) {
		this.modTxnRemarks = modTxnRemarks;
	}

	/**
	 * @return Returns the modTxnType.
	 */
	public String getModTxnType() {
		return this.modTxnType;
	}

	/**
	 * @param modTxnType
	 *            The modTxnType to set.
	 */
	public void setModTxnType(String modTxnType) {
		this.modTxnType = modTxnType;
	}

	/**
	 * @return Returns the modULDNo.
	 */
	public String getModULDNo() {
		return this.modULDNo;
	}

	/**
	 * @param modULDNo
	 *            The modULDNo to set.
	 */
	public void setModULDNo(String modULDNo) {
		this.modULDNo = modULDNo;
	}

	/**
	 * @return Returns the mode.
	 */
	public String getMode() {
		return this.mode;
	}

	/**
	 * @param mode
	 *            The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return this.displayPage;
	}

	/**
	 * @param displayPage
	 *            The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}



	/**
	 * @return Returns the invoiceId.
	 */
	public String getInvoiceId() {
		return this.invoiceId;
	}

	/**
	 * @param invoiceId
	 *            The invoiceId to set.
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @return Returns the popupflag.
	 */
	public String getPopupflag() {
		return popupflag;
	}

	/**
	 * @param popupflag
	 *            The popupflag to set.
	 */
	public void setPopupflag(String popupflag) {
		this.popupflag = popupflag;
	}

	/**
	 * @return Returns the enquiryDisableStatus.
	 */
	public String getEnquiryDisableStatus() {
		return enquiryDisableStatus;
	}

	/**
	 * @param enquiryDisableStatus
	 *            The enquiryDisableStatus to set.
	 */
	public void setEnquiryDisableStatus(String enquiryDisableStatus) {
		this.enquiryDisableStatus = enquiryDisableStatus;
	}

	/**
	 * @return Returns the toPartyCode.
	 */
	public String getToPartyCode() {
		return toPartyCode;
	}

	/**
	 * @param toPartyCode
	 *            The toPartyCode to set.
	 */
	public void setToPartyCode(String toPartyCode) {
		this.toPartyCode = toPartyCode;
	}

	/**
	 * @return Returns the fromPartyCode.
	 */
	public String getFromPartyCode() {
		return fromPartyCode;
	}

	/**
	 * @param fromPartyCode
	 *            The fromPartyCode to set.
	 */
	public void setFromPartyCode(String fromPartyCode) {
		this.fromPartyCode = fromPartyCode;
	}
/**
 * 
 * @return
 */
	public String getReturnFrmTime() {
		return returnFrmTime;
	}
/**
 * 
 * @param returnFrmTime
 */
	public void setReturnFrmTime(String returnFrmTime) {
		this.returnFrmTime = returnFrmTime;
	}
/**
 * 
 * @return
 */
	public String getReturnToTime() {
		return returnToTime;
	}
/**
 * 
 * @param returnToTime
 */
	public void setReturnToTime(String returnToTime) {
		this.returnToTime = returnToTime;
	}
/**
 * 
 * @return
 */
	public String getTxnFrmTime() {
		return txnFrmTime;
	}
/**
 * 
 * @param txnFrmTime
 */
	public void setTxnFrmTime(String txnFrmTime) {
		this.txnFrmTime = txnFrmTime;
	}
/**
 * 
 * @return
 */
	public String getTxnToTime() {
		return txnToTime;
	}
/**
 * 
 * @param txnToTime
 */
	public void setTxnToTime(String txnToTime) {
		this.txnToTime = txnToTime;
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
 * @return Returns the listMode.
 */
public String getListMode() {
	return listMode;
}

/**
 * @param listMode The listMode to set.
 */
public void setListMode(String listMode) {
	this.listMode = listMode;
}

public String getShowTab() {
	return showTab;
}

public void setShowTab(String showTab) {
	this.showTab = showTab;
}

public String getUldNumbersSelected() {
	return uldNumbersSelected;
}

public void setUldNumbersSelected(String uldNumbersSelected) {
	this.uldNumbersSelected = uldNumbersSelected;
}

public String getTxnNumbersSelected() {
	return txnNumbersSelected;
}

public void setTxnNumbersSelected(String txnNumbersSelected) {
	this.txnNumbersSelected = txnNumbersSelected;
}

/**
 * @return Returns the msgFlag.
 */
public String getMsgFlag() {
	return msgFlag;
}

/**
 * @param msgFlag The msgFlag to set.
 */
public void setMsgFlag(String msgFlag) {
	this.msgFlag = msgFlag;
}

public String getModCRN() {
	return modCRN;
}

public void setModCRN(String modCRN) {
	this.modCRN = modCRN;
}

public String getModCrnPrefix() {
	return modCrnPrefix;
}

public void setModCrnPrefix(String modCrnPrefix) {
	this.modCrnPrefix = modCrnPrefix;
}

public String getModTxnTime() {
	return modTxnTime;
}

public void setModTxnTime(String modTxnTime) {
	this.modTxnTime = modTxnTime;
}

public String getModUldCondition() {
	return modUldCondition;
}

public void setModUldCondition(String modUldCondition) {
	this.modUldCondition = modUldCondition;
}

/**
 * @return the desStation
 */
public String getDesStation() {
	return desStation;
}

/**
 * @param desStation the desStation to set
 */
public void setDesStation(String desStation) {
	this.desStation = desStation;
}

/**
 * @return the modRtnDate
 */
public String getModRtnDate() {
	return modRtnDate;
}

/**
 * @param modRtnDate the modRtnDate to set
 */
public void setModRtnDate(String modRtnDate) {
	this.modRtnDate = modRtnDate;
}

/**
 * @return the modRtnTime
 */
public String getModRtnTime() {
	return modRtnTime;
}

/**
 * @param modRtnTime the modRtnTime to set
 */
public void setModRtnTime(String modRtnTime) {
	this.modRtnTime = modRtnTime;
}

/**
 * @return the rtndemurrage
 */
public String getRtndemurrage() {
	return rtndemurrage;
}

/**
 * @param rtndemurrage the rtndemurrage to set
 */
public void setRtndemurrage(String rtndemurrage) {
	this.rtndemurrage = rtndemurrage;
}

/**
 * @return the rtnRemarks
 */
public String getRtnRemarks() {
	return rtnRemarks;
}

/**
 * @param rtnRemarks the rtnRemarks to set
 */
public void setRtnRemarks(String rtnRemarks) {
	this.rtnRemarks = rtnRemarks;
}

/**
 * @return the rtntaxes
 */
public String getRtntaxes() {
	return rtntaxes;
}

/**
 * @param rtntaxes the rtntaxes to set
 */
public void setRtntaxes(String rtntaxes) {
	this.rtntaxes = rtntaxes;
}

/**
 * @return the rtnwaived
 */
public String getRtnwaived() {
	return rtnwaived;
}

/**
 * @param rtnwaived the rtnwaived to set
 */
public void setRtnwaived(String rtnwaived) {
	this.rtnwaived = rtnwaived;
}

/**
 * @return the mucStatus
 */
public String getMucStatus() {
	return mucStatus;
}

/**
 * @param mucStatus the mucStatus to set
 */
public void setMucStatus(String mucStatus) {
	this.mucStatus = mucStatus;
}

/**
 * @return the detailMUCStatus
 */
public String[] getDetailMUCStatus() {
	return detailMUCStatus;
}

/**
 * @param detailMUCStatus the detailMUCStatus to set
 */
public void setDetailMUCStatus(String[] detailMUCStatus) {
	this.detailMUCStatus = detailMUCStatus;
}

/**
 * @return the returnQuantity
 */
public String[] getReturnQuantity() {
	return returnQuantity;
}

/**
 * @param returnQuantity the returnQuantity to set
 */
public void setReturnQuantity(String[] returnQuantity) {
	this.returnQuantity = returnQuantity;
}

/**
 * @return the totalDemmurage
 */
public String getTotalDemmurage() {
	return totalDemmurage;
}

/**
 * @param totalDemmurage the totalDemmurage to set
 */
public void setTotalDemmurage(String totalDemmurage) {
	this.totalDemmurage = totalDemmurage;
}

/**
 * @return the mucRefNum
 */
public String getMucRefNum() {
	return mucRefNum;
}

/**
 * @param mucRefNum the mucRefNum to set
 */
public void setMucRefNum(String mucRefNum) {
	this.mucRefNum = mucRefNum;
}

/**
 * @return the mucReferenceNumber
 */
public String getMucReferenceNumber() {
	return mucReferenceNumber;
}

/**
 * @param mucReferenceNumber the mucReferenceNumber to set
 */
public void setMucReferenceNumber(String mucReferenceNumber) {
	this.mucReferenceNumber = mucReferenceNumber;
}

/**
 * @return the pageURL
 */
public String getPageURL() {
	return pageURL;
}

/**
 * @param pageURL the pageURL to set
 */
public void setPageURL(String pageURL) {
	this.pageURL = pageURL;
}

/**
 * @return the modAwbNumber
 */
public String getModAwbNumber() {
	return modAwbNumber;
}

/**
 * @param modAwbNumber the modAwbNumber to set
 */
public void setModAwbNumber(String modAwbNumber) {
	this.modAwbNumber = modAwbNumber;
}

/**
 * @return the modLoaded
 */
public String getModLoaded() {
	return modLoaded;
}

/**
 * @param modLoaded the modLoaded to set
 */
public void setModLoaded(String modLoaded) {
	this.modLoaded = modLoaded;
}

/**
 * @return the modCurrentPage
 */
public String getModCurrentPage() {
	return modCurrentPage;
}

/**
 * @param modCurrentPage the modCurrentPage to set
 */
public void setModCurrentPage(String modCurrentPage) {
	this.modCurrentPage = modCurrentPage;
}

/**
 * @return the modDisplayPage
 */
public String getModDisplayPage() {
	return modDisplayPage;
}

/**
 * @param modDisplayPage the modDisplayPage to set
 */
public void setModDisplayPage(String modDisplayPage) {
	this.modDisplayPage = modDisplayPage;
}

/**
 * @return the modLastPageNum
 */
public String getModLastPageNum() {
	return modLastPageNum;
}

/**
 * @param modLastPageNum the modLastPageNum to set
 */
public void setModLastPageNum(String modLastPageNum) {
	this.modLastPageNum = modLastPageNum;
}

/**
 * @return the modTotalRecords
 */
public String getModTotalRecords() {
	return modTotalRecords;
}

/**
 * @param modTotalRecords the modTotalRecords to set
 */
public void setModTotalRecords(String modTotalRecords) {
	this.modTotalRecords = modTotalRecords;
}

/**
 * @return the baseCurrency
 */
public String getBaseCurrency() {
	return baseCurrency;
}

/**
 * @param baseCurrency the baseCurrency to set
 */
public void setBaseCurrency(String baseCurrency) {
	this.baseCurrency = baseCurrency;
}

/**
 * @return the controlReceiptNo
 */
public String getControlReceiptNo() {
	return controlReceiptNo;
}

/**
 * @param controlReceiptNo the controlReceiptNo to set
 */
public void setControlReceiptNo(String controlReceiptNo) {
	this.controlReceiptNo = controlReceiptNo;
}

/**
 * @return the dummy
 */
public String getDummy() {
	return dummy;
}

/**
 * @param dummy the dummy to set
 */
public void setDummy(String dummy) {
	this.dummy = dummy;
}

/**
 * @return the dummyAirport
 */
public String getDummyAirport() {
	return dummyAirport;
}

/**
 * @param dummyAirport the dummyAirport to set
 */
public void setDummyAirport(String dummyAirport) {
	this.dummyAirport = dummyAirport;
}

/**
 * @return the dummyTime
 */
public String getDummyTime() {
	return dummyTime;
}

/**
 * @param dummyTime the dummyTime to set
 */
public void setDummyTime(String dummyTime) {
	this.dummyTime = dummyTime;
}

/**
 * @return the dummyDemmurage
 */
public String getDummyDemmurage() {
	return dummyDemmurage;
}

/**
 * @param dummyDemmurage the dummyDemmurage to set
 */
public void setDummyDemmurage(String dummyDemmurage) {
	this.dummyDemmurage = dummyDemmurage;
}

/**
 * @return the dummyDate
 */
public String getDummyDate() {
	return dummyDate;
}

/**
 * @param dummyDate the dummyDate to set
 */
public void setDummyDate(String dummyDate) {
	this.dummyDate = dummyDate;
}

/**
 * @return the loginStation
 */
public String getLoginStation() {
	return loginStation;
}

/**
 * @param loginStation the loginStation to set
 */
public void setLoginStation(String loginStation) {
	this.loginStation = loginStation;
}

/**
 * @return the controlReceiptNoMid
 */
public String getControlReceiptNoMid() {
	return controlReceiptNoMid;
}

/**
 * @param controlReceiptNoMid the controlReceiptNoMid to set
 */
public void setControlReceiptNoMid(String controlReceiptNoMid) {
	this.controlReceiptNoMid = controlReceiptNoMid;
}

/**
 * @return the controlReceiptNoPrefix
 */
public String getControlReceiptNoPrefix() {
	return controlReceiptNoPrefix;
}

/**
 * @param controlReceiptNoPrefix the controlReceiptNoPrefix to set
 */
public void setControlReceiptNoPrefix(String controlReceiptNoPrefix) {
	this.controlReceiptNoPrefix = controlReceiptNoPrefix;
}

/**
 * @return the modFlag
 */
public String getModFlag() {
	return modFlag;
}

/**
 * @param modFlag the modFlag to set
 */
public void setModFlag(String modFlag) {
	this.modFlag = modFlag;
}

/**
 * @return the dummyCRN
 */
public String getDummyCRN() {
	return dummyCRN;
}

/**
 * @param dummyCRN the dummyCRN to set
 */
public void setDummyCRN(String dummyCRN) {
	this.dummyCRN = dummyCRN;
}

/**
 * @return the dummyCRNPrefix
 */
public String getDummyCRNPrefix() {
	return dummyCRNPrefix;
}

/**
 * @param dummyCRNPrefix the dummyCRNPrefix to set
 */
public void setDummyCRNPrefix(String dummyCRNPrefix) {
	this.dummyCRNPrefix = dummyCRNPrefix;
}

/**
 * @return the totalCountFlag
 */
public String getTotalCountFlag() {
	return totalCountFlag;
}

/**
 * @param totalCountFlag the totalCountFlag to set
 */
public void setTotalCountFlag(String totalCountFlag) {
	this.totalCountFlag = totalCountFlag;
}

/**
 * @return the modifiedFlag
 */
public String getModifiedFlag() {
	return modifiedFlag;
}

/**
 * @param modifiedFlag the modifiedFlag to set
 */
public void setModifiedFlag(String modifiedFlag) {
	this.modifiedFlag = modifiedFlag;
}

	/**
	 * @return the fromPopup
	 */
	public String getFromPopup() {
		return fromPopup;
	}
	
	/**
	 * @param fromPopup the fromPopup to set
	 */
	public void setFromPopup(String fromPopup) {
		this.fromPopup = fromPopup;
	}

	
	/**
	 * 
	 * 	Method		:	ListULDTransactionForm.getLeaseOrReturnFlg
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	getting the flag which shows if the filter is for lease or return
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getLeaseOrReturnFlg() {
		return leaseOrReturnFlg;
	}
	/**
	 * 
	 * 	Method		:	ListULDTransactionForm.setLeaseOrReturnFlg
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	setting the flag which shows if the filter is for lease or return
	 *	Parameters	:	@param leaseOrReturnFlg 
	 *	Return type	: 	void
	 */
	public void setLeaseOrReturnFlg(String leaseOrReturnFlg) {
		this.leaseOrReturnFlg = leaseOrReturnFlg;
	}
	/**
	 * 
	 * 	Method		:	ListULDTransactionForm.getModLseEndDate
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	getting modifcation to Lease End Date
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getModLseEndDate() {
		return modLseEndDate;
	}
	/**
	 * 
	 * 	Method		:	ListULDTransactionForm.setModLseEndDate
	 *	Added on 	:	03-Jan-2022
	 * 	Used for 	:	getting modifcation to Lease End Date
	 *	Parameters	:	@param modLseEndDate 
	 *	Return type	: 	void
	 */
	public void setModLseEndDate(String modLseEndDate) {
		this.modLseEndDate = modLseEndDate;
	}

	
	


}
