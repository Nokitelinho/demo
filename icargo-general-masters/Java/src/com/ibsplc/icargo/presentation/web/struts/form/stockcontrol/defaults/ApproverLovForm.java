/*
 * ApproverLovForm.java Created on Aug 30, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author kirupakaran
 *
 * This is the form class that represents the Approver Lov
 * screen
 */

public class ApproverLovForm extends ScreenModel {

	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "approverlovresources";

	private String bundle;


	private String[] approverChkBox;
	private String[] checkBox;
	private String[] stockholder;
	private String code;
	private String description;
	private String stockHolderType="";
	private String id;
	private String docType;
	private String docSubType;
    private String lastPageNum="0";
	private String displayPage="1";
	private String selectedValues="";
	private Boolean isValueSelected=false;
	private String stockApproverCode="";
	
	/**
	 * @return Returns the stockHolderType.
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}
	/**
	 * @param stockHolderType The stockHolderType to set.
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}

	/**
	 * @return Returns the selectedValues.
	 */
	public String getSelectedValues() {
		return selectedValues;
	}
	/**
	 * @param selectedValues The selectedValues to set.
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}
	/**
	 * @return Returns the approverChkBox.
	 */
	public String[] getApproverChkBox() {
		return approverChkBox;
	}
	/**
	 * @param approverChkBox The approverChkBox to set.
	 */
	public void setApproverChkBox(String[] approverChkBox) {
		this.approverChkBox = approverChkBox;
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
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return Returns the stockholder.
	 */
	public String[] getStockholder() {
		return stockholder;
	}
	/**
	 * @param stockholder The stockholder to set.
	 */
	public void setStockholder(String[] stockholder) {
		this.stockholder = stockholder;
	}
	/**
	 * getting ScreenId
	 */
	public String getScreenId() {
			return "UISKC021";
	}
	/**
	 * getProduct
	 */
	public String getProduct() {
			return "stockcontrol";
	}
	/**
	 * getSubProduct
	 */
	public String getSubProduct() {
			return "defaults";
	}
	/**
	 * @return Returns the docSubType.
	 */
	public String getDocSubType() {
		return docSubType;
	}
	/**
	 * @param docSubType The docSubType to set.
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}
	/**
	 * @return Returns the docType.
	 */
	public String getDocType() {
		return docType;
	}
	/**
	 * @param docType The docType to set.
	 */
	public void setDocType(String docType) {
		this.docType = docType;
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
	public Boolean getIsValueSelected() {
		return isValueSelected;
	}
	public void setIsValueSelected(Boolean isValueSelected) {
		this.isValueSelected = isValueSelected;
	}
	/**
	 * @param stockApproverCode the stockApproverCode to set
	 */
	public void setStockApproverCode(String stockApproverCode) {
		this.stockApproverCode = stockApproverCode;
	}
	/**
	 * @return the stockApproverCode
	 */
	public String getStockApproverCode() {
		return stockApproverCode;
	}

}

