/**
 * MCALovForm.java Created on May 25 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4823
 *
 */
public class MCALovForm extends ScreenModel{
	private static final String SCREEN_ID = "mailtracking.mra.defaults.mcalov";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "mra";
	private static final String BUNDLE = "mcalov";
	private String bundle;
	
	private String lastPageNum = "";

	private String displayPage = "1";
	
	private String pageURL="";
	
	private Page mcaLovPage;
	
	public Page getMcaLovPage() {
		return mcaLovPage;
	}

	public void setMcaLovPage(Page mcaLovPage) {
		this.mcaLovPage = mcaLovPage;
	}


	private int index;  

	private String selectedValues = "";

	private String[] selectCheckBox;
    private String multiselect;

	private String pagination;

	private String lovaction;
	
	private String formCount;
	//Added to populate value on condocNum when InvoiceNumber is selected
	private String lovNameTxtFieldName;

	private String lovDescriptionTxtFieldName;
	private String lovTxtFieldName;
	private String year;

	private String ccaNum;

	private String dsnNumber;
	private String origin;

	private String destination;

	private String category;

	private String subclass;
	private String title;

	

	
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
	 * @return the lovNameTxtFieldName
	 */
	public String getLovNameTxtFieldName() {
		return lovNameTxtFieldName;
	}
	/**
	 * @param lovNameTxtFieldName the lovNameTxtFieldName to set
	 */
	public void setLovNameTxtFieldName(String lovNameTxtFieldName) {
		this.lovNameTxtFieldName = lovNameTxtFieldName;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormCount() {
		return formCount;
	}

	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}



	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}

	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}

	public String getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSelectedValues() {
		return selectedValues;
	}

	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}

	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}

	public String getMultiselect() {
		return multiselect;
	}

	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String getLovaction() {
		return lovaction;
	}

	public void setLovaction(String lovaction) {
		this.lovaction = lovaction;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCcaNum() {
		return ccaNum;
	}

	public void setCcaNum(String ccaNum) {
		this.ccaNum = ccaNum;
	}

	public String getDsnNumber() {
		return dsnNumber;
	}

	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubclass() {
		return subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public String getBundle() {
		return BUNDLE;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT_NAME;
	}

	
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREEN_ID;
	}

	
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT_NAME;
	}

}
