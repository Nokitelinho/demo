/*
 * ManufacturerLovForm.java Created on Merch 01, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults;

import java.util.Collection;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2052
 *
 */
public class ManufacturerLovForm extends ScreenModel {

	private String hiddenToList = "";

	private String hiddenFromList = "";
	
	private static final String BUNDLE="lovResources";

	
	//FOR LOV
	private String code;

	private String description;

	private String multiselect;

	private String pagination;

	private String lovaction;

	private String title;

	private String formCount;

	private String lovTxtFieldName;

	private String lovDescriptionTxtFieldName;

	private String selectedValues = "";

	private String[] selectCheckBox;
	
	private String bundle;

	//FOR PAGINATION
	private String lastPageNum = "";

	private String displayPage = "1";
	
	private Collection<String> manufacturer;
	
	private int index;
	/**
	 * Get method for formCount
	 * @return formCount
	 */
	public String getFormCount() {
		return formCount;
	}
    /**
	 * Set method for formCount
	 * @param formCount
	 */
	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}

    /**
	 * Get method for lovTxtFieldName
	 * @return lovTxtFieldName
	 */
	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}

    /**
	 * Set method for lovTxtFieldName
	 * @param lovTxtFieldName
	 */
	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}
    /**
	 * Get method for code
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set method for code
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

    /**
	 * Get method for description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set method for description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Get method for lovaction
	 * @return lovaction
	 */
	public String getLovaction() {
		return lovaction;
	}

 	/**
	 * Set method for lovaction
	 * @param lovaction
	 */
	public void setLovaction(String lovaction) {
		this.lovaction = lovaction;
	}
    /**
	 * Get method for multiselect
	 * @return multiselect
	 */
	public String getMultiselect() {
		return multiselect;
	}
    /**
	 * Set method for multiselect
	 * @param multiselect
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}
    /**
	 * Get method for pagination
	 * @return pagination
	 */
	public String getPagination() {
		return pagination;
	}
    /**
	 * Set method for pagination
	 * @param pagination
	 */
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
    /**
	 * Get method for title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
    /**
	 * Set method for title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
    /**
	 * Constructor
	 */
	public ManufacturerLovForm() {

	}
	/**
	 * Get method for hiddenFromList
	 * @return hiddenFromList
	 */
	public String getHiddenFromList() {
		return hiddenFromList;
	}
    /**
	 * Set method for hiddenFromList
	 * @param hiddenFromList
	 */
	public void setHiddenFromList(String hiddenFromList) {
		this.hiddenFromList = hiddenFromList;
	}
    /**
	 * Get Method for hiddenToList
	 * @return hiddenToList
	 */
	public String getHiddenToList() {
		return hiddenToList;
	}
    /**
	 * Set Method for hiddenToList
	 * @param hiddenToList
	 */
	public void setHiddenToList(String hiddenToList) {
		this.hiddenToList = hiddenToList;
	}
    /**
	 * Get Method for displayPage
	 * @return displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}
    /**
	 * Set Method for displayPage
	 * @param displayPage
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
    /**
	 * Get Method for lastPageNum
	 * @return lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
    /**
	 * Set Method for lastPageNum
	 * @param lastPageNum
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
    /**
	 * Get Method for lovDescriptionTxtFieldName
	 * @return lovDescriptionTxtFieldName
	 */
	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}
    /**
	 * Set Method for lovDescriptionTxtFieldName
	 * @param lovDescriptionTxtFieldName
	 */
	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}
    /**
	 * Get Method for selectedValues
	 * @return selectedValues
	 */
	public String getSelectedValues() {
		return selectedValues;
	}
   	/**
	 * Set Method for selectedValues
	 * @param selectedValues
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}
    /**
	 * Get Method for selectCheckBox
	 * @return selectCheckBox.
	 */
	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}
    /**
	 * Set Method for selectCheckBox
	 * @param selectCheckBox
	 */
	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}
    /**
     * Get method for ScreenId
     * @return ScreenId
     */	
	public String getScreenId() {
		// 
		return "uld.defaults.manufacturerlov";
	}

    /**
     * Get method for Product
     * @return shared
     */
	public String getProduct() {
		// To be reviewed Auto-generated method stub
		return "uld";
	}

    /**
     * Get method for subProduct
     * @return airline
     */
	public String getSubProduct() {
		// To be reviewed Auto-generated method stub
		return "defaults";
	}
	
	/**
     * Get method for index
	 * @return index The index to set.
	 */
	public int getIndex() {
		return index;
	}
	/**
     * Set method for index
	 * @param index The index to set.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return Returns the manufacturer.
	 */
	public Collection<String> getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer The manufacturer to set.
	 */
	public void setManufacturer(Collection<String> manufacturer) {
		this.manufacturer = manufacturer;
	}	
	

}
