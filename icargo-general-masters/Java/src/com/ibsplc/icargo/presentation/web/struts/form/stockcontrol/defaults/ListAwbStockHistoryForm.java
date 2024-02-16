/*
 * ListAwbStockHistoryForm.java Created on Jan 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/** 
 *
 * @author A-3184
 *
 */
public class ListAwbStockHistoryForm extends ScreenModel {

	private String rangeFrom;

	private String rangeTo;

	private String startDate;

	private String endDate;
	
	private String awbRange;

	private String stockHolderCode;

	private String stockStatus;
	
	//Added by A-4803 for bug ICRD-14360
	private String statusFlag;;
	
	private boolean isHistory;
	
	private String accountNumber;
	
	private String awb = "";

	private String awp = "";
	
	private String stockType;
	
	private String transactionDate;
	
	private String numberOfDocuments;
	
	private String docSubType;
	
    private String timeFrom;
	
	private String timeTo;

	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "listawbstockhistoryresources";

	private String bundle;

	private String onList;

	/**
	 * Added for ICRD-3082
	 * @author A-2881
	 */
	private String userId;
	
	private boolean partnerAirline;
	
	private String awbPrefix;
	
	private String airlineName;
	/**
	 * Added for ICRD-3082 Ends
	 */
	
	private String docType;
	
	/**
	 * Added by A-2881 for ICRD-12179
	 * 
	 */
    private String displayPage="1";
    
    private String lastPageNum="0";
  //Added by A-5220 for ICRD-20959 starts 
    public static final String NAV_MOD_LIST="list";
    public static final String NAV_MOD_PAGINATION = "navigation";
    
    /** The document range. */
	private String documentRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType	

	private String partnerPrefix;	
	public String getPartnerPrefix() {
		return partnerPrefix;
	}
	public void setPartnerPrefix(String partnerPrefix) {
		this.partnerPrefix = partnerPrefix;
	}
	private String errorFlag; 
	/**
	 * @return the errorFlag
	 */
	public String getErrorFlag() {
		return errorFlag;
	}
	/**
	 * @param errorFlag the errorFlag to set
	 */
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
	/**
	 * Gets the document range.
	 *
	 * @return the document range
	 */
	public String getDocumentRange() {
		return documentRange;
	}
	
	/**
	 * Sets the document range.
	 *
	 * @param documentRange the new document range
	 */
	public void setDocumentRange(String documentRange) {
		this.documentRange = documentRange;
	}
    private String navigationMode;
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
	//Added by A-5220 for ICRD-20959 ends
	
	/**
	 * @return Returns the accountNumber.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber The accountNumber to set.
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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
	 * @return Returns the endDate.
	 */
	@DateFieldId(id="ListAWBStocHistoryDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getEndDate() {
		return endDate;
	}
 
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return Returns the rangeFrom.
	 */ 
	public String getRangeFrom() {
		return rangeFrom;
	}

	/**
	 * @param rangeFrom The rangeFrom to set.
	 */
	public void setRangeFrom(String rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	/**
	 * @return Returns the rangeTo.
	 */
	public String getRangeTo() {
		return rangeTo;
	}

	/**
	 * @param rangeTo The rangeTo to set.
	 */
	public void setRangeTo(String rangeTo) {
		this.rangeTo = rangeTo;
	}

	/**
	 * @return Returns the startDate.
	 */
	@DateFieldId(id="ListAWBStocHistoryDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	

	/**
	 * @return Returns the stockType.
	 */
	public String getStockType() {
		return stockType;
	}

	/**
	 * @param stockType The stockType to set.
	 */
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	/**
	 * @return Returns the stockStatus.
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * @param stockStatus The stockStatus to set.
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	/**
	 * @return Returns the stockcontrol.defaults.listawbstockhistory.
	 */
	public String getScreenId() {
		
		return "stockcontrol.defaults.listawbstockhistory";
	}
	/**
	 * @return Returns the stockcontrol.
	 */
	public String getProduct() {
		return "stockcontrol";
	}
	/**
	 * @return Returns the defaults.
	 */
	public String getSubProduct() {
		return "defaults";
	}


	/**
	 * @return Returns the awb.
	 */
	public String getAwb() {
		return awb;
	}

	/**
	 * @param awb The awb to set.
	 */
	public void setAwb(String awb) {
		this.awb = awb;
	}

	/**
	 * @return Returns the awp.
	 */
	public String getAwp() {
		return awp;
	}

	/**
	 * @param awp The awp to set.
	 */
	public void setAwp(String awp) {
		this.awp = awp;
	}
	
	/**
	 * @return Returns the isHistory.
	 */
	public boolean isHistory() {
		return isHistory;
	}

	/**
	 * @param isHistory
	 */
	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}

	/**
	 * @return Returns the awbRange.
	 */
	public String getAwbRange() {
		return awbRange;
	}

	/**
	 * @param awbRange The awbRange to set.
	 */
	public void setAwbRange(String awbRange) {
		this.awbRange = awbRange;
	}

	/**
	 * @return Returns the numberOfDocuments.
	 */
	public String getNumberOfDocuments() {
		return numberOfDocuments;
	}

	/**
	 * @param numberOfDocuments The numberOfDocuments to set.
	 */
	public void setNumberOfDocuments(String numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}

	/**
	 * @return Returns the transactionDate.
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDocSubType() {
		return docSubType;
	}

	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}
	/**
	 * @return Returns the timeFrom.
	 */
	public String getTimeFrom() {
		return timeFrom;
	}
	/**
	 * @param timeFrom The timeFrom to set.
	 */
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	/**
	 * @return Returns the timeTo.
	 */
	public String getTimeTo() {
		return timeTo;
	}
	/**
	 * @param timeTo The timeTo to set.
	 */
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	/**
	 * @return Returns the onList.
	 */
	public String getOnList() {
		return onList;
	}

	/**
	 * @param onList The onList to set.
	 */
	public void setOnList(String onList) {
		this.onList = onList;
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
	 * @return the airlineName
	 */
	public String getAirlineName() {
		return airlineName;
	}

	/**
	 * @param airlineName the airlineName to set
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	/**
	 * @return the awbPrefix
	 */
	public String getAwbPrefix() {
		return awbPrefix;
	}

	/**
	 * @param awbPrefix the awbPrefix to set
	 */
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}

	/**
	 * @return the partnerAirline
	 */
	public boolean isPartnerAirline() {
		return partnerAirline;
	}

	/**
	 * @param partnerAirline the partnerAirline to set
	 */
	public void setPartnerAirline(boolean partnerAirline) {
		this.partnerAirline = partnerAirline;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
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
	 * @return the statusFlag
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	
	
}
