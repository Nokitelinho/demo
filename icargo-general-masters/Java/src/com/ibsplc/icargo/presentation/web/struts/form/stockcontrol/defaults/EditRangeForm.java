/*
 * EditRangeForm.java Created on Aug 31, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

// TODO: Auto-generated Javadoc
/**
 * The Class EditRangeForm.
 *
 * @author A-1927
 * 
 * This is the form class that represents the Edit Range
 * screen
 */

public class EditRangeForm extends ScreenModel {

	/** The available check all. */
	private String availableCheckAll;

	/** The allocated check all. */
	private String allocatedCheckAll;

	/** The available range no. */
	private String[] availableRangeNo;

	/** The allocated range no. */
	private String[] allocatedRangeNo;

	/** The is manual. */
	private boolean isManual;

	/** The stock range from. */
	private String[] stockRangeFrom;

	/** The stock range to. */
	private String[] stockRangeTo;

	/** The doc type. */
	private String docType;

	/** The sub type. */
	private String subType;

	/** The stock holder code. */
	private String stockHolderCode;

	/** The stock control for. */
	private String stockControlFor;

	/** The approved stock. */
	private String approvedStock;

	/** The range from. */
	private String rangeFrom;

	/** The range to. */
	private String rangeTo;

	/** The no of docs. */
	private String[] noOfDocs;

	/** The number of docs. */
	private String numberOfDocs;

	/** The reference no. */
	private String referenceNo;
	
	/** The is valid range. */
	private String isValidRange;

	/** The available totalnoof docs. */
	private String availableTotalnoofDocs;

	/** The allocated totalnoof docs. */
	private String allocatedTotalnoofDocs;
	
	/** The hidden op flag. */
	private String[] hiddenOpFlag;

	// The key attribute specified in struts_config.xml file.
	/** The Constant BUNDLE. */
	private static final String BUNDLE = "editrangeresources";

	/** The bundle. */
	private String bundle;

	
	/** The max range. */
	private String maxRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType
	
	
	/**
	 * Gets the max range.
	 *
	 * @return the max range
	 */
	public String getMaxRange() {
		return maxRange;
	}
	
	/**
	 * Sets the max range.
	 *
	 * @param maxRange the new max range
	 */
	public void setMaxRange(String maxRange) {
		this.maxRange = maxRange;
	}
	
	/**
	 * Gets the bundle.
	 *
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	
	/**
	 * Sets the bundle.
	 *
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}


	/**
	 * Gets the stock range from.
	 *
	 * @return Returns the stockRangeFrom.
	 */
	public String[] getStockRangeFrom(){
			return stockRangeFrom;
	}

	/**
	 * Sets the stock range from.
	 *
	 * @param stockRangeFrom The stockRangeFrom to set.
	 */
	public void setStockRangeFrom(String[] stockRangeFrom){
			this.stockRangeFrom=stockRangeFrom;
	}

	/**
	 * Gets the stock range to.
	 *
	 * @return Returns the stockRangeTo.
	 */
	public String[] getStockRangeTo(){
			return stockRangeTo;
	}

	/**
	 * Sets the stock range to.
	 *
	 * @param stockRangeTo The stockRangeTo to set.
	 */
	public void setStockRangeTo(String[] stockRangeTo){
			this.stockRangeTo=stockRangeTo;
	}

	/**
	 * Gets the reference no.
	 *
	 * @return Returns the referenceNo.
	 */
	public String getReferenceNo(){
			return referenceNo;
	}

	/**
	 * Sets the reference no.
	 *
	 * @param referenceNo The referenceNo to set.
	 */
	public void setReferenceNo(String referenceNo){
			this.referenceNo=referenceNo;
	}

	/**
	 * Gets the doc type.
	 *
	 * @return Returns the docType.
	 */
	public String getDocType(){
			return docType;
	}

	/**
	 * Sets the doc type.
	 *
	 * @param docType The docType to set.
	 */
	public void setDocType(String docType){
			this.docType=docType;
	}

	/**
	 * Gets the sub type.
	 *
	 * @return Returns the subType.
	 */
	public String getSubType(){
			return subType;
	}

	/**
	 * Sets the sub type.
	 *
	 * @param subType The subType to set.
	 */
	public void setSubType(String subType){
		    this.subType=subType;
	}

	/**
	 * Gets the approved stock.
	 *
	 * @return Returns the approvedStock.
	 */
	public String getApprovedStock(){
			return approvedStock;
	}

	/**
	 * Sets the approved stock.
	 *
	 * @param approvedStock The approvedStock to set.
	 */
	public void setApprovedStock(String approvedStock){
			this.approvedStock=approvedStock;
	}

	/**
	 * Checks if is manual.
	 *
	 * @return Returns the isManual.
	 */
	public boolean isManual(){
			return isManual;
	}

	/**
	 * Sets the manual.
	 *
	 * @param isManual The isManual to set.
	 */
	public void setManual(boolean isManual){
			this.isManual=isManual;
	}

	/**
	 * Gets the range from.
	 *
	 * @return Returns the rangeFrom.
	 */
	public String getRangeFrom(){
			return rangeFrom;
	}

	/**
	 * Sets the range from.
	 *
	 * @param rangeFrom The rangeFrom to set.
	 */
	public void setRangeFrom(String rangeFrom){
			this.rangeFrom=rangeFrom;
	}

	/**
	 * Gets the range to.
	 *
	 * @return Returns the rangeTo.
	 */
	public String getRangeTo(){
			return rangeTo;
	}

	/**
	 * Sets the range to.
	 *
	 * @param rangeTo The rangeTo to set.
	 */
	public void setRangeTo(String rangeTo){
			this.rangeTo=rangeTo;
	}

	/**
	 * Gets the number of docs.
	 *
	 * @return Returns the numberOfDocs.
	 */
	public String getNumberOfDocs(){
			return numberOfDocs;
	}

	/**
	 * Sets the number of docs.
	 *
	 * @param numberOfDocs The numberOfDocs to set.
	 */
	public void setNumberOfDocs(String numberOfDocs){
			this.numberOfDocs=numberOfDocs;
	}

	/**
	 * Gets the no of docs.
	 *
	 * @return Returns the noOfDocs.
	 */
	public String[] getNoOfDocs(){
			return noOfDocs;
	}

	/**
	 * Sets the no of docs.
	 *
	 * @param noOfDocs The noOfDocs to set.
	 */
	public void setNoOfDocs(String[] noOfDocs){
			this.noOfDocs=noOfDocs;
	}

	/**
	 * Gets the available totalnoof docs.
	 *
	 * @return Returns the availableTotalnoofDocs.
	 */
	public String getAvailableTotalnoofDocs(){
			return availableTotalnoofDocs;
	}

	/**
	 * Sets the available totalnoof docs.
	 *
	 * @param availableTotalnoofDocs The availableTotalnoofDocs to set.
	 */
	public void setAvailableTotalnoofDocs(String availableTotalnoofDocs){
			this.availableTotalnoofDocs=availableTotalnoofDocs;
	}

	/**
	 * Gets the allocated totalnoof docs.
	 *
	 * @return Returns the allocatedTotalnoofDocs.
	 */
	public String getAllocatedTotalnoofDocs(){
			return allocatedTotalnoofDocs;
	}

	/**
	 * Sets the allocated totalnoof docs.
	 *
	 * @param allocatedTotalnoofDocs The allocatedTotalnoofDocs to set.
	 */
	public void setAllocatedTotalnoofDocs(String allocatedTotalnoofDocs){
			this.allocatedTotalnoofDocs=allocatedTotalnoofDocs;
	}

	/**
	 * Gets the available check all.
	 *
	 * @return Returns the availableCheckAll.
	 */
	public String getAvailableCheckAll(){
			return availableCheckAll;
	}

	/**
	 * Sets the available check all.
	 *
	 * @param availableCheckAll The availableCheckAll to set.
	 */
	public void setAvailableCheckAll(String availableCheckAll){
			this.availableCheckAll=availableCheckAll;
	}

	/**
	 * Gets the allocated check all.
	 *
	 * @return Returns the allocatedCheckAll.
	 */
	public String getAllocatedCheckAll(){
			return allocatedCheckAll;
	}

	/**
	 * Sets the allocated check all.
	 *
	 * @param allocatedCheckAll The allocatedCheckAll to set.
	 */
	public void setAllocatedCheckAll(String allocatedCheckAll){
			 this.allocatedCheckAll=allocatedCheckAll;
	}


	/**
	 * Gets the available range no.
	 *
	 * @return Returns the availableRangeNo.
	 */
	public String[] getAvailableRangeNo(){
			return availableRangeNo;
	}

	/**
	 * Sets the available range no.
	 *
	 * @param availableRangeNo The availableRangeNo to set.
	 */
	public void setAvailableRangeNo(String[] availableRangeNo){
			this.availableRangeNo=availableRangeNo;
	}

	/**
	 * Gets the allocated range no.
	 *
	 * @return Returns the allocatedRangeNo.
	 */
	public String[] getAllocatedRangeNo(){
			return allocatedRangeNo;
	}

	/**
	 * Sets the allocated range no.
	 *
	 * @param allocatedRangeNo The allocatedRangeNo to set.
	 */
	public void setAllocatedRangeNo(String[] allocatedRangeNo){
			 this.allocatedRangeNo=allocatedRangeNo;
	}

	/**
	 * Gets the stock holder code.
	 *
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode(){
			return stockHolderCode;
	}

	/**
	 * Sets the stock holder code.
	 *
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode){
			 this.stockHolderCode=stockHolderCode;
	}

	/**
	 * Gets the stock control for.
	 *
	 * @return Returns the stockControlFor.
	 */
	public String getStockControlFor(){
			return stockControlFor;
	}

	/**
	 * Sets the stock control for.
	 *
	 * @param stockControlFor The stockControlFor to set.
	 */
	public void setStockControlFor(String stockControlFor){
	 		 this.stockControlFor=stockControlFor;
	}

	/**
	 * Gets the screen id.
	 *
	 * @return Returns the screenId.
	 */
	public String getScreenId(){
			return "stockcontrol.defaults.editrange";
	}

	/**
	 * Gets the product.
	 *
	 * @return Returns the product.
	 */
	public String getProduct(){
			return "stockcontrol";
	}

	/**
	 * Gets the sub product.
	 *
	 * @return Returns the subproduct.
	 */
	public String getSubProduct(){
			return "defaults";
	}
	
	/**
	 * Gets the checks if is valid range.
	 *
	 * @return Returns the isValidRange.
	 */
	public String getIsValidRange() {
		return isValidRange;
	}
	
	/**
	 * Sets the checks if is valid range.
	 *
	 * @param isValidRange The isValidRange to set.
	 */
	public void setIsValidRange(String isValidRange) {
		this.isValidRange = isValidRange;
	}
	
	/**
	 * Gets the hidden op flag.
	 *
	 * @return Returns the hiddenOpFlag.
	 */
	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}
	
	/**
	 * Sets the hidden op flag.
	 *
	 * @param hiddenOpFlag The hiddenOpFlag to set.
	 */
	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
	}

}
