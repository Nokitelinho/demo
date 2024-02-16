/*
 * ListBlackListedStockForm.java Created on Sep 21, 2005
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
 * @author A-1927
 *
 */
public class ListBlackListedStockForm extends ScreenModel {


	private String docType;

	private String subType;

	private String rangeFrom;

	private String rangeTo;

	private String[] blacklistCheck;

	private String fromDate;

	private String toDate;

	private String lastPageNumber = "0";

	private String displayPage = "1";
	
	private String listButton = "";
	
	/*
	 * For #102543
	 */
	private String awbPrefix;
	private String airlineName;
	private boolean partnerAirline;
	
	private String countTotalFlag = "";//Added by A-5214 as part from the ICRD-20959
	/*
	 * End #102543
	 */
	/** The document range. */
	private String documentRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType	

	private String partnerPrefix;	
	public String getPartnerPrefix() {
		return partnerPrefix;
	}
	public void setPartnerPrefix(String partnerPrefix) {
		this.partnerPrefix = partnerPrefix;
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
	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "listblacklistedstockresources";

	private String bundle;


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
	 * @return Returns the rangeFrom.
	 */
	public String getRangeFrom(){
		return rangeFrom;
	}

	/**
	 * @param rangeFrom The rangeFrom to set.
	 */
	public void setRangeFrom(String rangeFrom){
		this.rangeFrom=rangeFrom;
	}

	/**
	 * @return Returns the rangeTo.
	 */
	public String getRangeTo(){
		return rangeTo;
	}

	/**
	 * @param rangeTo The rangeTo to set.
	 */
	public void setRangeTo(String rangeTo){
		this.rangeTo=rangeTo;
	}

	/**
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="ListBlackListedStocksDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDate(){
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate){
		this.fromDate=fromDate;
	}

	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="ListBlackListedStocksDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDate(){
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate){
		this.toDate=toDate;
	}

	/**
	 * @return Returns the docType.
	 */
	public String getDocType(){
		return docType;
	}

	/**
	 * @param docType The docType to set.
	 */
	public void setDocType(String docType){
		this.docType=docType;
	}

	/**
	 * @return Returns the subType.
	 */
	public String getSubType(){
		return subType;
	}

	/**
	 * @param subType The subType to set.
	 */
	public void setSubType(String subType){
		this.subType=subType;
	}

	/**
	 * @return Returns the blacklistCheck.
	 */
	public String[] getBlacklistCheck(){
		return blacklistCheck;
	}

	/**
	 * @param blacklistCheck The blacklistCheck to set.
	 */
	public void setBlacklistCheck(String[] blacklistCheck){
		 this.blacklistCheck=blacklistCheck;
	}


	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage
	 *            The displayPage to set.
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
	 * @param lastPageNumber
	 *            The lastPageNumber to set.
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return Returns the screenId.
	 */
	public String getScreenId(){
		return "stockcontrol.defaults.listblacklistedstock";
	}

	/**
	 * @return Returns the product.
	 */
	public String getProduct(){
		return "stockcontrol";
	}

	/**
	 * @return Returns the subProduct.
	 */
	public String getSubProduct(){
		return "defaults";
	}
	/**
	 * @return Returns the listButton.
	 */
	public String getListButton() {
		return listButton;
	}
	/**
	 * @param listButton The listButton to set.
	 */
	public void setListButton(String listButton) {
		this.listButton = listButton;
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
	
	//Added by A-5214 as part from the ICRD-20959 starts
	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}
	public String getCountTotalFlag() {
		return countTotalFlag;
	}
	//Added by A-5214 as part from the ICRD-20959 ends
}
