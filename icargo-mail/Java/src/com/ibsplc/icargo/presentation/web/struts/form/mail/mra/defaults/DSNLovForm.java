/**
 * DSNLovForm.java Created on May 25 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4823
 *
 */
public class DSNLovForm extends ScreenModel{
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnlov";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "mra";
	private static final String BUNDLE = "dsnlov";
	public String getBundle() {
		return BUNDLE;
	}
	
	private String bundle;
	
	private String lastPageNum = "";

	private String displayPage = "1";
	
	private String pageURL="";
	private Page dsnLovPage;
	private int index;  

	private String selectedValues = "";

	private String[] selectCheckBox;
    private String multiselect;

	private String pagination;

	private String lovaction;
	
	private String formCount;
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private String lovTxtFieldName;

	private String lovDescriptionTxtFieldName;
	private String origin;

	private String destination;

	private String category;

	private String subclass;
	private String year;

	private String condocno;
	private String fromDate;
	private String toDate;
	private String registeredIndicator;
	
	public String getRegisteredIndicator() {
		return registeredIndicator;
	}
	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}
	public String getRecepatableSerialNumber() {
		return recepatableSerialNumber;
	}
	public void setRecepatableSerialNumber(String recepatableSerialNumber) {
		this.recepatableSerialNumber = recepatableSerialNumber;
	}
	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}
	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}

	private String recepatableSerialNumber;
	private String highestNumberIndicator;
	 public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	private String parameterValue;
	//// Author A-5125(CM) For ICRD-16160 
	private String lovActionType="dsnScreenLoad";
	

	public void setLovActionType(String lovActionType) {
		this.lovActionType = lovActionType;
	}
	public String getLovActionType() {
		return lovActionType;
	}
	@DateFieldId(id="DSNLovDateRange", fieldType="from")
	public String getFromDate() {
		return fromDate;
	}


	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

   @DateFieldId(id="DSNLovDateRange", fieldType="to")
	public String getToDate() {
		return toDate;
	}


	public void setToDate(String toDate) {
		this.toDate = toDate;
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


	public Page getDsnLovPage() {
		return dsnLovPage;
	}


	public void setDsnLovPage(Page dsnLovPage) {
		this.dsnLovPage = dsnLovPage;
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


	public String getFormCount() {
		return formCount;
	}


	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}


	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}


	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}


	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}


	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
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


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getCondocno() {
		return condocno;
	}


	public void setCondocno(String condocno) {
		this.condocno = condocno;
	}


	public String getDsnNumber() {
		return dsnNumber;
	}


	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}


	private String dsnNumber;
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
