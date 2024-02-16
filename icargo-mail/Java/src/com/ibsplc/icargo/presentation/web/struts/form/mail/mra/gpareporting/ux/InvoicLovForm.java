/*
 * InvoicLovForm.java Created on Dec 13, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */



package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 *  
 * @author A-8464
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date           		Author          	Description
 * 
 *  0.1        Dec 13, 2018  		Sivapriya			Initial draft
 *  
 *  
 */


public class InvoicLovForm extends ScreenModel{
	
		private static final String SCREEN_ID = "mail.mra.gpareporting.ux";
		private static final String PRODUCT_NAME = "mail";
		private static final String SUBPRODUCT_NAME = "mra";
		private static final String BUNDLE = "invoiclov";
		
		private String bundle;
		private String lastPageNum = "";
		private String displayPage = "1";
		private String pageURL="";
		private Page invoicLovPage;
		private int index;  
		private String selectedValues = "";
		private String[] selectCheckBox;
	    private String multiselect;
		private String pagination;
		
		private String lovaction;
		private String formCount;
		private String title;
		private String lovTxtFieldName;
		private String lovDescriptionTxtFieldName;
		private String lovNameTxtFieldName;
		private String defaultPageSize = "10";
		private String parameterValue;
		private String lovActionType="invoicLovScreenLoad";
		
		private String gpaCodeFilter="";
		private String fromDateFilter="";
		private String toDateFilter="";
		private String invoicRefId;
		private String fromDate;
		private String toDate;
		private String gpaCode;

		private String code;
		
		public String getGpaCodeFilter() {
			return gpaCodeFilter;
		}
		public void setGpaCodeFilter(String gpaCodeFilter) {
			this.gpaCodeFilter = gpaCodeFilter;
		}
		public String getFromDateFilter() {
			return fromDateFilter;
		}
		public void setFromDateFilter(String fromDateFilter) {
			this.fromDateFilter = fromDateFilter;
		}
		public String getToDateFilter() {
			return toDateFilter;
		}
		public void setToDateFilter(String toDateFilter) {
			this.toDateFilter = toDateFilter;
		}
		public String getInvoicRefId() {
			return invoicRefId;
		}
		public void setInvoicRefId(String invoicRefId) {
			this.invoicRefId = invoicRefId;
		}
		public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getToDate() {
			return toDate;
		}
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}

		 public String getParameterValue() {
			return parameterValue;
		}
		public void setParameterValue(String parameterValue) {
			this.parameterValue = parameterValue;
		}

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
		public void setLovActionType(String lovActionType) {
			this.lovActionType = lovActionType;
		}
		public String getLovActionType() {
			return lovActionType;
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

		public Page getInvoicLovPage() {
			return invoicLovPage;
		}

		public void setInvoicLovPage(Page invoicLovPage) {
			this.invoicLovPage = invoicLovPage;
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
	
		public String getLovNameTxtFieldName() {
			return lovNameTxtFieldName;
		}

		public void setLovNameTxtFieldName(String lovNameTxtFieldName) {
			this.lovNameTxtFieldName = lovNameTxtFieldName;
		}
		public String getDefaultPageSize() {
			return defaultPageSize;
		}

		public void setDefaultPageSize(String defaultPageSize) {
			this.defaultPageSize = defaultPageSize;
		}
		
		public String getBundle() {
			return BUNDLE;
		}
		
		public String getProduct() {
			
			return PRODUCT_NAME;
		}
		
		public String getScreenId() {
		
			return SCREEN_ID;
		}
		
		public String getSubProduct() {
		
			return SUBPRODUCT_NAME;
		}
		public String getGpaCode() {
			return gpaCode;
		}
		public void setGpaCode(String gpaCode) {
			this.gpaCode = gpaCode;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}

}
