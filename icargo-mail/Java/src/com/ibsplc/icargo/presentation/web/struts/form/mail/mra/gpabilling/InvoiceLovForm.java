/*
 * InvoiceLovForm.java Created on Feb 08, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-2408
 *
 */
public class InvoiceLovForm extends ScreenModel {

	private static final String BUNDLE ="invoicelov";
	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.gpabilling.invoicelov";

	private String code;

	private String gpaCodeFilter;

	private Page invoiceLovPage;

	private String lovList;

	private String[] invoiceNumber;

	private String[] gpaCode;

	private String[] billingPeriod;

	private String lovaction;

	private String selectedValues="";

	private String lastPageNum="";

	private String displayPage="1";

	private String multiselect;

	private String pagination;

	private String formCount="";

	private String lovTxtFieldName;

	private String lovNameTxtFieldName;

	private String lovDescriptionTxtFieldName;

	private int index;

	private String pageURL = "";

	private String[] selectCheckBox;

	private String title;

	private String hiddenToList = "";

	private String hiddenFromList = "";






	/**
	 * @return Returns the screenid.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the product.
	 */
    public String getProduct() {
        return PRODUCT;
    }

    /**
	 * @return Returns the subproduct.
	 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    /**
	 * @return Returns the bundle.
	 */
	public static String getBUNDLE() {
		return BUNDLE;
	}
	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		//this.bundle = bundle;
	}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * @return Returns the screenid.
	 */
	public static String getSCREENID() {
		return SCREENID;
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
	 * @return Returns the formCount.
	 */
	public String getFormCount() {
		return formCount;
	}

	/**
	 * @param formCount The formCount to set.
	 */
	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}

	/**
	 * @return Returns the index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index The index to set.
	 */
	public void setIndex(int index) {
		this.index = index;
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
	 * @return Returns the lovaction.
	 */
	public String getLovaction() {
		return lovaction;
	}

	/**
	 * @param lovaction The lovaction to set.
	 */
	public void setLovaction(String lovaction) {
		this.lovaction = lovaction;
	}

	/**
	 * @return Returns the lovDescriptionTxtFieldName.
	 */
	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}

	/**
	 * @param lovDescriptionTxtFieldName The lovDescriptionTxtFieldName to set.
	 */
	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}

	/**
	 * @return Returns the lovList.
	 */
	public String getLovList() {
		return lovList;
	}

	/**
	 * @param lovList The lovList to set.
	 */
	public void setLovList(String lovList) {
		this.lovList = lovList;
	}

	/**
	 * @return Returns the lovNameTxtFieldName.
	 */
	public String getLovNameTxtFieldName() {
		return lovNameTxtFieldName;
	}

	/**
	 * @param lovNameTxtFieldName The lovNameTxtFieldName to set.
	 */
	public void setLovNameTxtFieldName(String lovNameTxtFieldName) {
		this.lovNameTxtFieldName = lovNameTxtFieldName;
	}

	/**
	 * @return Returns the lovTxtFieldName.
	 */
	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}

	/**
	 * @param lovTxtFieldName The lovTxtFieldName to set.
	 */
	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}

	/**
	 * @return Returns the multiselect.
	 */
	public String getMultiselect() {
		return multiselect;
	}

	/**
	 * @param multiselect The multiselect to set.
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}

	/**
	 * @return Returns the pagination.
	 */
	public String getPagination() {
		return pagination;
	}

	/**
	 * @param pagination The pagination to set.
	 */
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}






	/**
	 * @return Returns the billingPeriod.
	 */
	public String[] getBillingPeriod() {
		return billingPeriod;
	}

	/**
	 * @param billingPeriod The billingPeriod to set.
	 */
	public void setBillingPeriod(String[] billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String[] getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String[] gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the gpaCodeFilter.
	 */
	public String getGpaCodeFilter() {
		return gpaCodeFilter;
	}

	/**
	 * @param gpaCodeFilter The gpaCodeFilter to set.
	 */
	public void setGpaCodeFilter(String gpaCodeFilter) {
		this.gpaCodeFilter = gpaCodeFilter;
	}

	/**
	 * @return Returns the invoiceLovPage.
	 */
	public Page getInvoiceLovPage() {
		return invoiceLovPage;
	}

	/**
	 * @param invoiceLovPage The invoiceLovPage to set.
	 */
	public void setInvoiceLovPage(Page invoiceLovPage) {
		this.invoiceLovPage = invoiceLovPage;
	}

	/**
	 * @return Returns the invoiceNumber.
	 */
	public String[] getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String[] invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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
	 * @return Returns the pageURL.
	 */
	public String getPageURL() {
		return pageURL;
	}

	/**
	 * @param pageURL The pageURL to set.
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	/**
	 * @return Returns the hiddenFromList.
	 */
	public String getHiddenFromList() {
		return hiddenFromList;
	}

	/**
	 * @param hiddenFromList The hiddenFromList to set.
	 */
	public void setHiddenFromList(String hiddenFromList) {
		this.hiddenFromList = hiddenFromList;
	}

	/**
	 * @return Returns the hiddenToList.
	 */
	public String getHiddenToList() {
		return hiddenToList;
	}

	/**
	 * @param hiddenToList The hiddenToList to set.
	 */
	public void setHiddenToList(String hiddenToList) {
		this.hiddenToList = hiddenToList;
	}

	/**
	 * @return Returns the selectCheckBox.
	 */
	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}

	/**
	 * @param selectCheckBox The selectCheckBox to set.
	 */
	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	

	


}
