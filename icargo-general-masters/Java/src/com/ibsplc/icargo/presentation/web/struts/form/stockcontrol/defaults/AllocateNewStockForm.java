/**
 * AllocateNewStockForm.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1927
 *
 * This is the form class that represents the Allocate New Stock
 *
 * screen
 */

public class AllocateNewStockForm extends ScreenModel {


	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "allocatenewstockresources";

	private String bundle;

	private String availableCheckAll;

	private String allocatedCheckAll;

	private String[] availableRangeNo;

	private String[] allocatedRangeNo;

	private boolean isManual;

	private String[] stockRangeFrom;

	private String[] stockRangeTo;

	private String docType;

	private String subType;

	private String stockHolderCode;

	private String stockHolderType;

	private String level;

	private String stockControlFor;

	private String approvedStock;

	private String rangeFrom;

	private String rangeTo;

	private String numberOfDocs;

	private String[] noOfDocs;

	private String availableTotalnoofDocs;

	private String allocatedTotalnoofDocs;

	private String buttonStatusFlag = "";
	
	private String[] hiddenOpFlag;
	
	private String airlineName;
	private String awbPrefix;
	private boolean partnerAirline;
	
	
	private String reportGenerateMode; // Added as part of ICRD-46860 to decide whether report should be generated or not
	
	
	private String statusFlag = "";	

	/** The document range. */
	private String documentRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType
	
	private String partnerPrefix;	
	public String getPartnerPrefix() {
		return partnerPrefix;
	}
	public void setPartnerPrefix(String partnerPrefix) {
		this.partnerPrefix = partnerPrefix;
	}
	private String isError;
	
	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
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
	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getReportGenerateMode() {
		return reportGenerateMode;
	}

	public void setReportGenerateMode(String reportGenerateMode) {
		this.reportGenerateMode = reportGenerateMode;
	}

	/**
	 * @return Returns the stockRangeFrom.
	 */
	public String[] getStockRangeFrom(){
			return stockRangeFrom;
	}

	/**
	 * @param stockRangeFrom The stockRangeFrom to set.
	 */
	public void setStockRangeFrom(String[] stockRangeFrom){
			this.stockRangeFrom=stockRangeFrom;
	}
	/**
	 * @return Returns the stockRangeTo.
	 */
	public String[] getStockRangeTo(){
			return stockRangeTo;
	}

	/**
	 * @param stockRangeTo The stockRangeTo to set.
	 */
	public void setStockRangeTo(String[] stockRangeTo){
			this.stockRangeTo=stockRangeTo;
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
	 * @return Returns the approvedStock.
	 */
	public String getApprovedStock(){
			return approvedStock;
	}

	/**
	 * @param approvedStock The approvedStock to set.
	 */
	public void setApprovedStock(String approvedStock){
			this.approvedStock=approvedStock;
	}

	/**
	 * @return Returns the isManual.
	 */
	public boolean isManual(){
			return isManual;
	}

	/**
	 * @param isManual The isManual to set.
	 */
	public void setManual(boolean isManual){
			this.isManual=isManual;
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
	 * @return Returns the noOfDocs.
	 */
	public String[] getNoOfDocs(){
			return noOfDocs;
	}

	/**
	 * @param noOfDocs The noOfDocs to set.
	 */
	public void setNoOfDocs(String[] noOfDocs){
			this.noOfDocs=noOfDocs;
	}

	/**
	 * @return Returns the numberOfDocs.
	 */
	public String getNumberOfDocs(){
			return numberOfDocs;
	}

	/**
	 * @param numberOfDocs The numberOfDocs to set.
	 */
	public void setNumberOfDocs(String numberOfDocs){
			this.numberOfDocs=numberOfDocs;
	}

	/**
	 * @return Returns the availableTotalnoofDocs.
	 */
	public String getAvailableTotalnoofDocs(){
			return availableTotalnoofDocs;
	}

	/**
	 * @param availableTotalnoofDocs The availableTotalnoofDocs to set.
	 */
	public void setAvailableTotalnoofDocs(String availableTotalnoofDocs){
			this.availableTotalnoofDocs=availableTotalnoofDocs;
	}

	/**
	 * @return Returns the allocatedTotalnoofDocs.
	 */
	public String getAllocatedTotalnoofDocs(){
			return allocatedTotalnoofDocs;
	}

	/**
	 * @param allocatedTotalnoofDocs The allocatedTotalnoofDocs to set.
	 */
	public void setAllocatedTotalnoofDocs(String allocatedTotalnoofDocs){
			this.allocatedTotalnoofDocs=allocatedTotalnoofDocs;
	}

	/**
	 * @return Returns the availableCheckAll.
	 */
	public String getAvailableCheckAll(){
			return availableCheckAll;
	}

	/**
	 * @param availableCheckAll The availableCheckAll to set.
	 */
	public void setAvailableCheckAll(String availableCheckAll){
			this.availableCheckAll=availableCheckAll;
	}

	/**
	 * @return Returns the allocatedCheckAll.
	 */
	public String getAllocatedCheckAll(){
			return allocatedCheckAll;
	}

	/**
	 * @param allocatedCheckAll The allocatedCheckAll to set.
	 */
	public void setAllocatedCheckAll(String allocatedCheckAll){
			 this.allocatedCheckAll=allocatedCheckAll;
	}

	/**
	 * @return Returns the availableRangeNo.
	 */
	public String[] getAvailableRangeNo(){
			return availableRangeNo;
	}

	/**
	 * @param availableRangeNo The availableRangeNo to set.
	 */
	public void setAvailableRangeNo(String[] availableRangeNo){
			this.availableRangeNo=availableRangeNo;
	}

	/**
	 * @return Returns the allocatedRangeNo.
	 */
	public String[] getAllocatedRangeNo(){
			return allocatedRangeNo;
	}

	/**
	 * @param allocatedRangeNo The allocatedRangeNo to set.
	 */
	public void setAllocatedRangeNo(String[] allocatedRangeNo){
			 this.allocatedRangeNo=allocatedRangeNo;
	}

	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode(){
			return stockHolderCode;
	}

	/**
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode){
	 		 this.stockHolderCode=stockHolderCode;
	}

	/**
	 * @return Returns the stockHolderType.
	 */
	public String getStockHolderType(){
			return stockHolderType;
	}

	/**
	 * @param stockHolderType The stockHolderType to set.
	 */
	public void setStockHolderType(String stockHolderType){
	 		 this.stockHolderType=stockHolderType;
	}

	/**
	 * @return Returns the stockControlFor.
	 */
	public String getStockControlFor(){
			return stockControlFor;
	}

	/**
	 * @param stockControlFor The stockControlFor to set.
	 */
	public void setStockControlFor(String stockControlFor){
	 		 this.stockControlFor=stockControlFor;
	}

	/**
	 * @return Returns the level.
	 */
	public String getLevel(){
				return level;
	}

	/**
	 * @param level The level to set.
	 */
	public void setLevel(String level){
	 		 this.level=level;
	}

	/**
	 * @return Returns the screenId.
	 */
	public String getScreenId(){
			return "stockcontrol.defaults.allocatenewstock";
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
	 * @return Returns the buttonStatusFlag.
	 */
	public String getButtonStatusFlag() {
		return buttonStatusFlag;
	}

	/**
	 * @param buttonStatusFlag The buttonStatusFlag to set.
	 */
	public void setButtonStatusFlag(String buttonStatusFlag) {
		this.buttonStatusFlag = buttonStatusFlag;
	}

	/**
	 * @return Returns the hiddenOpFlag.
	 */
	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}

	/**
	 * @param hiddenOpFlag The hiddenOpFlag to set.
	 */
	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
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
}

