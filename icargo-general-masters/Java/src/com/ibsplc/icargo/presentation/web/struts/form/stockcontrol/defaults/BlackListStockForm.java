/*
 * BlackListStockForm.java Created on Aug 30, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1952
 *
 * This is the form class that represents the Blacklist Stock screen
 */

public class BlackListStockForm extends ScreenModel {

	private String docType = "";

	private String subType = "";

	private String rangeFrom;

	private String rangeTo;
	
	private String stockHolderCode;

	private String remarks;

	private String stockHolder;
	//Added by A-4803 for CR ICRD-3205
	private String voidNeeded;
	
	/*
	 * For #102543
	 */
	private String awbPrefix;
	private String airlineName;
	private boolean partnerAirline;
	/*
	 * End #102543
	 */
	//added by a-4443 for icrd-3024
	private String fromScreen;
	
	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "blackliststockresources";
	private String bundle;
	/** The document range. */
	private String documentRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType
	
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
	 *
	 * @return docType
	 */
	public String getDocType() {

		return docType;

	}

	/**
	 *
	 * @return subType
	 */
	public String getSubType() {

		return subType;

	}

	/**
	 *
	 * @return rangeFrom
	 */
	public String getRangeFrom() {

		return rangeFrom;

	}

	/**
	 *
	 * @return rangeTo
	 */
	public String getRangeTo() {

		return rangeTo;

	}

	/**
	 *
	 * @return remarks
	 */
	public String getRemarks() {

		return remarks;

	}

	/**
	 *
	 * @return stockHolder
	 */
	public String getStockHolder() {
		return stockHolder;
	}

	/**
	 *
	 * @param stockHolder
	 */
	public void setStockHolder(String stockHolder) {
		this.stockHolder = stockHolder;
	}

	/**
	 *
	 * @param rangeFrom
	 */
	public void setRangeFrom(String rangeFrom) {

		this.rangeFrom = rangeFrom;

	}

	/**
	 *
	 * @param docType
	 */
	public void setDocType(String docType) {

		this.docType = docType;

	}

	/**
	 *
	 * @param subType
	 */
	public void setSubType(String subType) {

		this.subType = subType;

	}

	/**
	 *
	 * @param rangeTo
	 */
	public void setRangeTo(String rangeTo) {

		this.rangeTo = rangeTo;

	}

	/**
	 *
	 * @param remarks
	 */
	public void setRemarks(String remarks) {

		this.remarks = remarks;
	}

	/**
	 * @return
	 */
	public String getScreenId() {

		return "stockcontrol.defaults.blackliststock";
	}

	/**
	 * @return
	 */
	public String getProduct() {

		return "stockcontrol";
	}

	/**
	 * @return
	 */
	public String getSubProduct() {

		return "defaults";
	}
	
	/**
	 * @author a-2850
	 * @return
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	
	/**
	 * @author a-2850
	 * @param stockHolderCode
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
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
	 * @return the airlineIdentifier
	 */
	public String getAirlineName() {
		return airlineName;
	}
	/**
	 * @param airlineIdentifier the airlineIdentifier to set
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
	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}
	
	/**
	 * 
	 * @return voidNeeded
	 */
	public String getVoidNeeded() {
		return voidNeeded;
	}
	/**
	 * 
	 * @param voidNeeded
	 */
	public void setVoidNeeded(String voidNeeded) {
		this.voidNeeded = voidNeeded;
	}
	
	
}
