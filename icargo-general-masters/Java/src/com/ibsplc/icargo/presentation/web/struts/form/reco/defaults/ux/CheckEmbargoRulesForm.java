/*
 * CheckEmbargoRulesForm.java Created on Aug 11, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ux;

import java.util.Collection;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1358 This is the form class that represents the Check Embargo
 *         screen
 */
public class CheckEmbargoRulesForm extends ScreenModel {

	private static final String BUNDLE 	= "uxembargorulesresources";

	private String bundle;
	
	private Collection embargoPage;

	private String code[];

	private String destination;

	private String origin;

	private String companyCode;

	private String val[];

	private String shipDate;

	private String pagination;

	private String continueAction;

	private String title;

	private String formCount;

	private String lovTxtFieldName;

	private String lovDescriptionTxtFieldName;

	private String selectedValues = "";

	private String[] selectCheckBox = null;

	// FOR PAGINATION
	private String lastPageNum = "";

	private String displayPage = "1";

	private int index;


	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}


	public String getFormCount() {
		return formCount;
	}

	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}



	public String getContinueAction() {
		return continueAction;
	}

	public void setContinueAction(String continueAction) {
		this.continueAction = continueAction;
	}

	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}

	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}

	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}

	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}

	public void setEmbargoPage(Collection page) {
		this.embargoPage = page;
	}

	public Collection getEmbargoPage(){
		return embargoPage;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}

	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}

	public String getSelectedValues() {
		return selectedValues;
	}

	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getScreenId() {

		return "reco.defaults.ux.checkembargo";

	}

	public String getProduct() {

		return "reco";

	}

	public String getSubProduct() {

		return "defaults";
	}

	public String[] getCode() {
		return code;
	}

	public void setCode(String[] code) {
		this.code = code;
	}

	public String[] getVal() {
		return val;
	}

	public void setVal(String[] val) {
		this.val = val;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String date) {
		this.shipDate = date;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
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

}
