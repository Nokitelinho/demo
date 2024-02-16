/*
 * MaintainProductForm.java Created on Sep 2, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 *
 * @author A-1927
 *
 */
public class MaintainStockRequestForm extends ScreenModel {


	private String reqRefNo;

	private String dateOfReq;

	private String level;

	private String code;

	private String reqStock;

	private String allocatedStock;

	private String remarks;

	private String appRejRemarks;

	private String docType;

	private String subType;

	private String status;

	private boolean isManual;

	private String mode;

	private String region;

	private String station;

	private String agent;

	private String stockHolderType;

	private String[] stockHolder;
	
	private String fromStockRequestList = "";
	
	private String buttonStatusFlag = "";

	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "maintainstockrequestresources";

	private String bundle;
	
	/*
	 * Added by A-2589 for #102543
	 */
	private String awbPrefix;
	private String airlineName;
	private boolean partnerAirline;
	private String partnerPrefix;	
	/*
	 * End - #102543
	 */


	public String getPartnerPrefix() {
		return partnerPrefix;
	}
	public void setPartnerPrefix(String partnerPrefix) {
		this.partnerPrefix = partnerPrefix;
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
	 * @return Returns the reqRefNo.
	 */
	public String getReqRefNo(){
		return reqRefNo;
	}

	/**
	 * @param reqRefNo The reqRefNo to set.
	 */
	public void setReqRefNo(String reqRefNo){
		this.reqRefNo=reqRefNo;
	}

	/**
	 * @return Returns the dateOfReq.
	 */
	public String getDateOfReq(){
		return dateOfReq;
	}

	/**
	 * @param dateOfReq The dateOfReq to set.
	 */
	public void setDateOfReq(String dateOfReq){
		this.dateOfReq=dateOfReq;
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
		this.stockHolderType = stockHolderType;

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
	 * @return Returns the code.
	 */
	public String getCode(){
		return code;
	}

	/**
	 * @param code The code to set.
	 */
	public void setCode(String code){
		this.code=code;
	}

	/**
	 * @return Returns the reqStock.
	 */
	public String getReqStock(){
		return reqStock;
	}

	/**
	 * @param reqStock The reqStock to set.
	 */
	public void setReqStock(String reqStock){
		this.reqStock=reqStock;
	}

	/**
	 * @return Returns the allocatedStock.
	 */
	public String getAllocatedStock(){
		return allocatedStock;
	}

	/**
	 * @param allocatedStock The allocatedStock to set.
	 */
	public void setAllocatedStock(String allocatedStock){
		this.allocatedStock=allocatedStock;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks(){
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks){
		this.remarks=remarks;
	}

	/**
	 * @return Returns the appRejRemarks.
	 */
	public String getAppRejRemarks(){
		return appRejRemarks;
	}

	/**
	 * @param appRejRemarks The appRejRemarks to set.
	 */
	public void setAppRejRemarks(String appRejRemarks){
		this.appRejRemarks=appRejRemarks;
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
	 * @return Returns the status.
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status){
		this.status=status;
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
	 * @return Returns the mode.
	 */
	public String getMode(){
		return mode;
	}

	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode){
		this.mode=mode;
	}

	/**
	 * @return Returns the region.
	 */
	public String getRegion(){
		return region;
	}

	/**
	 * @param region The region to set.
	 */
	public void setRegion(String region){
		this.region=region;
	}

	/**
	 * @return Returns the station.
	 */
	public String getStation(){
		return station;
	}

	/**
	 * @param station The station to set.
	 */
	public void setStation(String station){
		this.station=station;
	}

	/**
	 * @return Returns the agent.
	 */
	public String getAgent(){
		return agent;
	}

	/**
	 * @param agent The agent to set.
	 */
	public void setAgent(String agent){
		this.agent=agent;
	}

	/**
	 * @return Returns the stockHolder.
	 */
	public String[] getStockHolder(){
		return stockHolder;
	}

	/**
	 * @param stockHolder The stockHolder to set.
	 */
	public void setStockHolder(String[] stockHolder){
		this.stockHolder=stockHolder;
	}

	/**
	 * @return Returns the screenId.
	 */
	/**
	 * Added by A-4772 for ICRD-9882.Changed the 
	 * Screen id value as per standard for UISKC002
	 */
	public String getScreenId(){
		return "stockcontrol.defaults.maintainstockrequest";
	}

	/**
	 * @return Returns the product.
	 */
	public String getSubProduct(){
		return "defaults";
	}

	/**
	 * @return Returns the subProduct.
	 */
	public String getProduct(){
		return "stockcontrol";
	}
	/**
	 * @return Returns the fromStockRequestList.
	 */
	public String getFromStockRequestList() {
		return fromStockRequestList;
	}
	/**
	 * @param fromStockRequestList The fromStockRequestList to set.
	 */
	public void setFromStockRequestList(String fromStockRequestList) {
		this.fromStockRequestList = fromStockRequestList;
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
}
