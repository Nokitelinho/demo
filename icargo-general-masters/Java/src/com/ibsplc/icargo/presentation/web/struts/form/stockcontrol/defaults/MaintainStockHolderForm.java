/*
 * MaintainStockHolderForm.java Created on Jul 7, 2005
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
 * @author A-1870
 *
 */
public class MaintainStockHolderForm extends ScreenModel {

    private String documentType;
    private String documentSubType;

    private String id;
    private String nextAction;
	private String stockHolderType;

	private String stockHolderCode;
	private String[] isRowModified;
	private String stockHolderName;

	private String controlPrivilege;

	private String[] checkBox;

	private String[] docType;

	private String[] docSubType;
	private String checkedReorder;
	private String checkedAutoRequest;
	private String mode;

	private String[] OpFlag;
	private String[] approver;
	private String[] reorderLevel;
	private String[] reorderQuantity;
	private String[] reorderAlertFlag;
	private String[] autoRequestFlag;
	private String[] remarks;
	private String[] autoprocessQuantity;

	private String contact;
	private String fromStockHolderList = "";
	private String listSuccessful;
	
	private String approverCode;
	
	private String[] awbPrefix;

	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "maintainstockholderresources";

	private String bundle;

	private String[] autoPopulateFlag;
	private String checkedAutoPopulate;

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
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return Returns the docSubType.
	 */
	public String[] getDocSubType() {
		return docSubType;
	}

	/**
	 * @param docSubType The docSubType to set.
	 */
	public void setDocSubType(String[] docSubType) {
		this.docSubType = docSubType;
	}

	/**
	 * @return Returns the docType.
	 */
	public String[] getDocType() {
		return docType;
	}

	/**
	 * @param docType The docType to set.
	 */
	public void setDocType(String[] docType) {
		this.docType = docType;
	}

	/**
	 * @return Returns the checkBox.
	 */
	public String[] getCheckBox() {
		return checkBox;
	}

	/**
	 * @param checkBox The checkBox to set.
	 */
	public void setCheckBox(String[] checkBox) {
		this.checkBox = checkBox;
	}
    /**
     *
     * @return stockHolderType
     */
	public String getStockHolderType(){
		return stockHolderType;
	}
    /**
     *
     * @param stockHolderType
     */
	public void setStockHolderType(String stockHolderType){
		this.stockHolderType=stockHolderType;
	}
    /**
     *
     * @return stockHolderCode
     */
	public String getStockHolderCode(){
		
		return stockHolderCode;
	}
   /**
    *
    * @param stockHolderCode  
    */
	public void setStockHolderCode(String stockHolderCode){ 
		
		this.stockHolderCode=stockHolderCode;
	}
    /**
     *
     * @return stockHolderName
     */
	public String getStockHolderName(){
		return stockHolderName;
	}
    /**
     *
     * @param stockHolderName
     */
	public void setStockHolderName(String stockHolderName){
		this.stockHolderName=stockHolderName;
	}
    /**
     *
     * @return controlPrivilege
     */
	public String getControlPrivilege(){
		
		return controlPrivilege;
	}
    /**
     *
     * @param controlPrivilege
     */
	public void setControlPrivilege(String controlPrivilege){
		
		this.controlPrivilege=controlPrivilege;
	}
	/**
	 *
	 */
	/**
	 * Added by A-4772 for ICRD-9882.Changed the 
	 * Screen id value as per standard for UISK009
	 */
	public String getScreenId(){ 
		return "stockcontrol.defaults.maintainstockholder";
	}
	/**
	 *
	 */
	public String getSubProduct(){
		return "defaults";
	}
	/**
	 *
	 */
	public String getProduct(){
		return "stockcontrol";
	}

	/**
	 * @return Returns the checkedAutoRequest.
	 */
	public String getCheckedAutoRequest() {
		return checkedAutoRequest;
	}

	/**
	 * @param checkedAutoRequest The checkedAutoRequest to set.
	 */
	public void setCheckedAutoRequest(String checkedAutoRequest) {
		this.checkedAutoRequest = checkedAutoRequest;
	}

	/**
	 * @return Returns the checkedReorder.
	 */
	public String getCheckedReorder() {
		return checkedReorder;
	}

	/**
	 * @param checkedReorder The checkedReorder to set.
	 */
	public void setCheckedReorder(String checkedReorder) {
		this.checkedReorder = checkedReorder;
	}

	/**
	 * @return Returns the opFlag.
	 */
	public String[] getOpFlag() {
		return OpFlag;
	}

	/**
	 * @param opFlag The opFlag to set.
	 */
	public void setOpFlag(String[] opFlag) {
		OpFlag = opFlag;
	}

	/**
	 * @return Returns the approver.
	 */
	public String[] getApprover() {
		return approver;
	}

	/**
	 * @param approver The approver to set.
	 */
	public void setApprover(String[] approver) {
		this.approver = approver;
	}

	/**
	 * @return Returns the autoRequestFlag.
	 */
	public String[] getAutoRequestFlag() {
		return autoRequestFlag;
	}

	/**
	 * @param autoRequestFlag The autoRequestFlag to set.
	 */
	public void setAutoRequestFlag(String[] autoRequestFlag) {
		this.autoRequestFlag = autoRequestFlag;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the reorderAlertFlag.
	 */
	public String[] getReorderAlertFlag() {
		return reorderAlertFlag;
	}

	/**
	 * @param reorderAlertFlag The reorderAlertFlag to set.
	 */
	public void setReorderAlertFlag(String[] reorderAlertFlag) {
		this.reorderAlertFlag = reorderAlertFlag;
	}

	/**
	 * @return Returns the reorderLevel.
	 */
	public String[] getReorderLevel() {
		return reorderLevel;
	}

	/**
	 * @param reorderLevel The reorderLevel to set.
	 */
	public void setReorderLevel(String[] reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	/**
	 * @return Returns the reorderQuantity.
	 */
	public String[] getReorderQuantity() {
		return reorderQuantity;
	}

	/**
	 * @param reorderQuantity The reorderQuantity to set.
	 */
	public void setReorderQuantity(String[] reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
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
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the nextAction.
	 */
	public String getNextAction() {
		return nextAction;
	}

	/**
	 * @param nextAction The nextAction to set.
	 */
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * @return Returns the isRowModified.
	 */
	public String[] getIsRowModified() {
		return isRowModified;
	}

	/**
	 * @param isRowModified The isRowModified to set.
	 */
	public void setIsRowModified(String[] isRowModified) {
		this.isRowModified = isRowModified;
	}

	/**
	 * @return Returns the autoprocessQuantity.
	 */
	public String[] getAutoprocessQuantity() {
		return autoprocessQuantity;
	}
	/**
	 * @param autoprocessQuantity The autoprocessQuantity to set.
	 */
	public void setAutoprocessQuantity(String[] autoprocessQuantity) {
		this.autoprocessQuantity = autoprocessQuantity;
	}

	/**
	 * @return Returns the contact.
	 */
	public String getContact(){
		return contact;
	}
	/**
	 * @param contact The contact to set.
	 */
	public void setContact(String contact) {
			this.contact = contact;
	}
	/**
	 * @return Returns the fromStockHolderList.
	 */
	public String getFromStockHolderList() {
		return fromStockHolderList;
	}
	/**
	 * @param fromStockHolderList The fromStockHolderList to set.
	 */
	public void setFromStockHolderList(String fromStockHolderList) {
		this.fromStockHolderList = fromStockHolderList;
	}
	/**
	 * @return Returns the listSuccessful.
	 */
	public String getListSuccessful() {
		return listSuccessful;
	}
	/**
	 * @param listSuccessful The listSuccessful to set.
	 */
	public void setListSuccessful(String listSuccessful) {
		this.listSuccessful = listSuccessful;
	}
	
	/**
	 * @param approverCode
	 */
	public void setApproverCode(String approverCode){
		this.approverCode=approverCode;
	}
	
	public String getApproverCode(){
		return approverCode;
	}
	/**
	 * @return the awbPrefix
	 */
	public String[] getAwbPrefix() {
		return awbPrefix;
	}
	/**
	 * @param awbPrefix the awbPrefix to set
	 */
	public void setAwbPrefix(String[] awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	public String[] getAutoPopulateFlag() {
		return autoPopulateFlag;
	}
	public void setAutoPopulateFlag(String[] autoPopulateFlag) {
		this.autoPopulateFlag = autoPopulateFlag;
	}
	public String getCheckedAutoPopulate() {
		return checkedAutoPopulate;
	}
	public void setCheckedAutoPopulate(String checkedAutoPopulate) {
		this.checkedAutoPopulate = checkedAutoPopulate;
	}
}
