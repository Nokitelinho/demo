/*
 * StockManagerForm.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1619
 *
 */
public class StockManagerForm extends ScreenModel {

    private static final String BUNDLE = "stockmanager";

    private String bundle;

    private static final String PRODUCT = "stockcontrol";

    private static final String SUBPRODUCT = "defaults";

    private static final String SCREEN_ID =
    	"stockcontrol.defaults.stockmanager";


    private String airline;

    private String documentSubType;

    private String documentType;

    private String airlineHidden;

    private String documentSubTypeHidden;

    private String documentTypeHidden;

    private String rangeFrom;

    private String rangeTo;

    private String count;

    private String remarks;

    private String summaryCount;

    private String addRangeFrom;

    private String addRangeTo;

    private String addDocumentType;

    private String addDocumentSubType;

    private String addRemarks;

    private String curRangeFrom;

    private String curRangeTo;

    private String remDocumentType;

    private String remDocumentSubType;

    private String toRangeFrom;

    private String toRangeTo;

    private String remRemarks;

    private String[] check;

    private String errorStatus;

    private String errorStatusRemove = "";

	/*
	 * Variables for split pagination
	 */
	private String displayPage = "1";

	private String totalRecords = "0";

	private String lastPageNum = "0";

	private String currentPageNum = "1";

	private String checkValueForLog;

	private String checkValueForAction;

	private String serialNumber;

	private String content;

	private String[] checkAction;

	private String checkForHomeAirline;

	private String forMessage;

	private String forAddOkMessage;

	private String fromStockList = "N";
	
	private String documentLength = "";


    /**
	 * @return Returns the documentLength.
	 */
	public String getDocumentLength() {
		return this.documentLength;
	}
	/**
	 * @param documentLength The documentLength to set.
	 */
	public void setDocumentLength(String documentLength) {
		this.documentLength = documentLength;
	}
	/**
	 * @return Returns the fromStockList.
	 */
	public String getFromStockList() {
		return this.fromStockList;
	}
	/**
	 * @param fromStockList The fromStockList to set.
	 */
	public void setFromStockList(String fromStockList) {
		this.fromStockList = fromStockList;
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
	 * @return Returns the addDocumentSubType.
	 */
	public String getAddDocumentSubType() {
		return addDocumentSubType;
	}
	/**
	 * @param addDocumentSubType The addDocumentSubType to set.
	 */
	public void setAddDocumentSubType(String addDocumentSubType) {
		this.addDocumentSubType = addDocumentSubType;
	}
	/**
	 * @return Returns the addDocumentType.
	 */
	public String getAddDocumentType() {
		return addDocumentType;
	}
	/**
	 * @param addDocumentType The addDocumentType to set.
	 */
	public void setAddDocumentType(String addDocumentType) {
		this.addDocumentType = addDocumentType;
	}
	/**
	 * @return Returns the addRangeFrom.
	 */
	public String getAddRangeFrom() {
		return addRangeFrom;
	}
	/**
	 * @param addRangeFrom The addRangeFrom to set.
	 */
	public void setAddRangeFrom(String addRangeFrom) {
		this.addRangeFrom = addRangeFrom;
	}
	/**
	 * @return Returns the addRangeTo.
	 */
	public String getAddRangeTo() {
		return addRangeTo;
	}
	/**
	 * @param addRangeTo The addRangeTo to set.
	 */
	public void setAddRangeTo(String addRangeTo) {
		this.addRangeTo = addRangeTo;
	}
	/**
	 * @return Returns the addRemarks.
	 */
	public String getAddRemarks() {
		return addRemarks;
	}
	/**
	 * @param addRemarks The addRemarks to set.
	 */
	public void setAddRemarks(String addRemarks) {
		this.addRemarks = addRemarks;
	}
	/**
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return airline;
	}
	/**
	 * @param airline The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	/**
	 * @return Returns the count.
	 */
	public String getCount() {
		return count;
	}
	/**
	 * @param count The count to set.
	 */
	public void setCount(String count) {
		this.count = count;
	}
	/**
	 * @return Returns the curRangeFrom.
	 */
	public String getCurRangeFrom() {
		return curRangeFrom;
	}
	/**
	 * @param curRangeFrom The curRangeFrom to set.
	 */
	public void setCurRangeFrom(String curRangeFrom) {
		this.curRangeFrom = curRangeFrom;
	}
	/**
	 * @return Returns the curRangeTo.
	 */
	public String getCurRangeTo() {
		return curRangeTo;
	}
	/**
	 * @param curRangeTo The curRangeTo to set.
	 */
	public void setCurRangeTo(String curRangeTo) {
		this.curRangeTo = curRangeTo;
	}
	/**
	 * @return Returns the documentSubType.
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}
	/**
	 * @param documentSubType The documentSubType to set.
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}
	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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
	 * @return Returns the remDocumentSubType.
	 */
	public String getRemDocumentSubType() {
		return remDocumentSubType;
	}
	/**
	 * @param remDocumentSubType The remDocumentSubType to set.
	 */
	public void setRemDocumentSubType(String remDocumentSubType) {
		this.remDocumentSubType = remDocumentSubType;
	}
	/**
	 * @return Returns the remDocumentType.
	 */
	public String getRemDocumentType() {
		return remDocumentType;
	}
	/**
	 * @param remDocumentType The remDocumentType to set.
	 */
	public void setRemDocumentType(String remDocumentType) {
		this.remDocumentType = remDocumentType;
	}
	/**
	 * @return Returns the remRemarks.
	 */
	public String getRemRemarks() {
		return remRemarks;
	}
	/**
	 * @param remRemarks The remRemarks to set.
	 */
	public void setRemRemarks(String remRemarks) {
		this.remRemarks = remRemarks;
	}
	/**
	 * @return Returns the summaryCount.
	 */
	public String getSummaryCount() {
		return summaryCount;
	}
	/**
	 * @param summaryCount The summaryCount to set.
	 */
	public void setSummaryCount(String summaryCount) {
		this.summaryCount = summaryCount;
	}
	/**
	 * @return Returns the toRangeFrom.
	 */
	public String getToRangeFrom() {
		return toRangeFrom;
	}
	/**
	 * @param toRangeFrom The toRangeFrom to set.
	 */
	public void setToRangeFrom(String toRangeFrom) {
		this.toRangeFrom = toRangeFrom;
	}
	/**
	 * @return Returns the toRangeTo.
	 */
	public String getToRangeTo() {
		return toRangeTo;
	}
	/**
	 * @param toRangeTo The toRangeTo to set.
	 */
	public void setToRangeTo(String toRangeTo) {
		this.toRangeTo = toRangeTo;
	}
	/**
	 * @return Returns the bundle.
	 */
	/**
     * @return String
     * @param 
     * */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
     */
	/**
     * @return String
     * @param 
     * */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
     */
    /**
     * @return String
     * @param 
     * */
    public String getProduct() {
        return PRODUCT;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
     */
    /**
     * @return String
     * @param 
     * */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
	/**
	 * @return Returns the errorStatus.
	 */
	public String getErrorStatus() {
		return errorStatus;
	}
	/**
	 * @param errorStatus The errorStatus to set.
	 */
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
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
	 * @return Returns the checkValueForLog.
	 */
	public String getCheckValueForLog() {
		return checkValueForLog;
	}
	/**
	 * @param checkValueForLog The checkValueForLog to set.
	 */
	public void setCheckValueForLog(String checkValueForLog) {
		this.checkValueForLog = checkValueForLog;
	}
	/**
	 * @return Returns the checkValueForAction.
	 */
	public String getCheckValueForAction() {
		return checkValueForAction;
	}
	/**
	 * @param checkValueForAction The checkValueForAction to set.
	 */
	public void setCheckValueForAction(String checkValueForAction) {
		this.checkValueForAction = checkValueForAction;
	}
	/**
	 * @return Returns the checkAction.
	 */
	public String[] getCheckAction() {
		return checkAction;
	}
	/**
	 * @param checkAction The checkAction to set.
	 */
	public void setCheckAction(String[] checkAction) {
		this.checkAction = checkAction;
	}
	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return Returns the serialNumber.
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return Returns the airlineHidden.
	 */
	public String getAirlineHidden() {
		return airlineHidden;
	}
	/**
	 * @param airlineHidden The airlineHidden to set.
	 */
	public void setAirlineHidden(String airlineHidden) {
		this.airlineHidden = airlineHidden;
	}
	/**
	 * @return Returns the documentSubTypeHidden.
	 */
	public String getDocumentSubTypeHidden() {
		return documentSubTypeHidden;
	}
	/**
	 * @param documentSubTypeHidden The documentSubTypeHidden to set.
	 */
	public void setDocumentSubTypeHidden(String documentSubTypeHidden) {
		this.documentSubTypeHidden = documentSubTypeHidden;
	}
	/**
	 * @return Returns the documentTypeHidden.
	 */
	public String getDocumentTypeHidden() {
		return documentTypeHidden;
	}
	/**
	 * @param documentTypeHidden The documentTypeHidden to set.
	 */
	public void setDocumentTypeHidden(String documentTypeHidden) {
		this.documentTypeHidden = documentTypeHidden;
	}
	/**
	 * @return Returns the errorStatusRemove.
	 */
	public String getErrorStatusRemove() {
		return errorStatusRemove;
	}
	/**
	 * @param errorStatusRemove The errorStatusRemove to set.
	 */
	public void setErrorStatusRemove(String errorStatusRemove) {
		this.errorStatusRemove = errorStatusRemove;
	}
	/**
	 * @return Returns the checkForHomeAirline.
	 */
	public String getCheckForHomeAirline() {
		return checkForHomeAirline;
	}
	/**
	 * @param checkForHomeAirline The checkForHomeAirline to set.
	 */
	public void setCheckForHomeAirline(String checkForHomeAirline) {
		this.checkForHomeAirline = checkForHomeAirline;
	}
	/**
	 * @return Returns the forMessage.
	 */
	public String getForMessage() {
		return forMessage;
	}
	/**
	 * @param forMessage The forMessage to set.
	 */
	public void setForMessage(String forMessage) {
		this.forMessage = forMessage;
	}
	/**
	 * @return Returns the forAddOkMessage.
	 */
	public String getForAddOkMessage() {
		return forAddOkMessage;
	}
	/**
	 * @param forAddOkMessage The forAddOkMessage to set.
	 */
	public void setForAddOkMessage(String forAddOkMessage) {
		this.forAddOkMessage = forAddOkMessage;
	}


}
