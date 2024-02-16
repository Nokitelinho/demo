/**
 * StockHolderLovForm.java Created on Sep 07, 2005
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
 */
public class StockHolderLovForm extends ScreenModel {

	private String code = "";

	private String description = "";

	private String lastPageNumber = "0";

	private String displayPage = "1";

	private String[] stationChecked;

	private String selectedValues = "";

	private String nextAction;

	private String codeName;
	
	private String typeName;
	
	private String readOnly;

	private String[] stockHolder;
	
	private String stkHolderType = "";
	
	private String stockHolderType = "";
	
	private String stockHolderTypeValue = "";

	private String selectedData = "N";
	
	private String formNumber;
	
	//Added for ICRD-1304 by A-3767 on 16Jun11 	
	private String documentType;
	
	private String documentSubType;

	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "stockholderlovresources";

	private String bundle;
	
    private int index; 
    
    private String multiselect;
    
    private String pagination;
    
    private String lovTxtFieldName="";
    
    private String lovDescriptionTxtFieldName;
    
    private String lovaction="";
    
    private String title="";
    
    private String formCount="";

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
	 * @return Returns the selectedData.
	 */
	public String getSelectedData() {
		return selectedData;
	}

	/**
	 * @param selectedData
	 *            The selectedData to set.
	 */
	public void setSelectedData(String selectedData) {
		this.selectedData = selectedData;
	}

	/**
	 * @return Returns the nextAction.
	 */
	public String getNextAction() {
		return nextAction;
	}

	/**
	 * @param nextAction
	 *            The nextAction to set.
	 */
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * @return Returns the codeName.
	 */
	public String getCodeName() {
		return codeName;
	}

	/**
	 * @param codeName
	 *            The codeName to set.
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	/**
	 * @return Returns the stationChecked.
	 */
	public String[] getStationChecked() {
		return stationChecked;
	}

	/**
	 * @param stationChecked
	 *            The stationChecked to set.
	 */
	public void setStationChecked(String[] stationChecked) {
		this.stationChecked = stationChecked;
	}


	/**
	 * @return Returns the stockHolder.
	 */
	public String[] getStockHolder() {
		return stockHolder;
	}

	/**
	 * @param stockHolder
	 *            The stockHolder to set.
	 */
	public void setStockHolder(String[] stockHolder) {
		this.stockHolder = stockHolder;
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
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            The code to set.
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
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the selectedValues.
	 */
	public String getSelectedValues() {
		return selectedValues;
	}

	/**
	 * @param selectedValues
	 *            The selectedValues to set.
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	/**
	 * The overiden function to return the screen id
	 *
	 * @return String screenid
	 */
	public String getScreenId() {
		return "stockcontrol.defaults.common.stockholderlov";
	}

	/**
	 * The overriden function to return the product name
	 *
	 * @return products
	 */
	public String getProduct() {
		return "stockcontrol";
	}

	/**
	 * The overriden function to return the sub product name
	 *
	 * @return defaults
	 */
	public String getSubProduct() {
		return "defaults";
	}
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
	 * @return Returns the typeName.
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param typeName The typeName to set.
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @return Returns the readOnly.
	 */
	public String getReadOnly() {
		return readOnly;
	}
	/**
	 * @param readOnly The readOnly to set.
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
	/**
	 * @return Returns the stockHolderTypeValue.
	 */
	public String getStockHolderTypeValue() {
		return stockHolderTypeValue;
	}
	/**
	 * @param stockHolderTypeValue The stockHolderTypeValue to set.
	 */
	public void setStockHolderTypeValue(String stockHolderTypeValue) {
		this.stockHolderTypeValue = stockHolderTypeValue;
	}
	/**
	 * @return Returns the stkHolderType.
	 */
	public String getStkHolderType() {
		return stkHolderType;
	}
	/**
	 * @param stkHolderType The stkHolderType to set.
	 */
	public void setStkHolderType(String stkHolderType) {
		this.stkHolderType = stkHolderType;
	}
	/**
	 * @return Returns the formNumber.
	 */
	public String getFormNumber() {
		return formNumber;
	}
	/**
	 * @param formNumber The formNumber to set.
	 */
	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentSubType() {
		return documentSubType;
	}
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}
	
	/**
	 * @return the multiselect
	 */
	public String getMultiselect() {
		return multiselect;
	}
	/**
	 * @param multiselect the multiselect to set
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the pagination
	 */
	public String getPagination() {
		return pagination;
	}
	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	/**
	 * @return the lovTxtFieldName
	 */
	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}
	/**
	 * @param lovTxtFieldName the lovTxtFieldName to set
	 */
	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}
	/**
	 * @return the lovDescriptionTxtFieldName
	 */
	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}
	/**
	 * @param lovDescriptionTxtFieldName the lovDescriptionTxtFieldName to set
	 */
	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}
	/**
	 * @return the lovaction
	 */
	public String getLovaction() {
		return lovaction;
	}
	/**
	 * @param lovaction the lovaction to set
	 */
	public void setLovaction(String lovaction) {
		this.lovaction = lovaction;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the formCount
	 */
	public String getFormCount() {
		return formCount;
	}
	/**
	 * @param formCount the formCount to set
	 */
	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}
	
	
	
	
}
