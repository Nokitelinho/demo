/*
 * InvoicKeyLovForm.java Created on Aug 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2408
 *
 */
public class InvoicKeyLovForm extends ScreenModel {

	private static final String BUNDLE ="invoickeylov";
	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.invoickeylov";
	
	private String code;
	
	private String poaCodeFilter;
	
	private Page invoicKeyLovPage;

	private String lovList;

	private String[] companyCode;

	private String[] invoicKey;

	private String[] poaCode;

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
	 * @return Returns the companyCode.
	 */
	public String[] getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String[] companyCode) {
		this.companyCode = companyCode;
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
	 * @return Returns the invoicKey.
	 */
	public String[] getInvoicKey() {
		return invoicKey;
	}

	/**
	 * @param invoicKey The invoicKey to set.
	 */
	public void setInvoicKey(String[] invoicKey) {
		this.invoicKey = invoicKey;
	}

	/**
	 * @return Returns the invoicKeyLovPage.
	 */
	public Page getInvoicKeyLovPage() {
		return invoicKeyLovPage;
	}

	/**
	 * @param invoicKeyLovPage The invoicKeyLovPage to set.
	 */
	public void setInvoicKeyLovPage(Page invoicKeyLovPage) {
		this.invoicKeyLovPage = invoicKeyLovPage;
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
	 * @return Returns the poaCode.
	 */
	public String[] getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String[] poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the poaCodeFilter.
	 */
	public String getPoaCodeFilter() {
		return poaCodeFilter;
	}

	/**
	 * @param poaCodeFilter The poaCodeFilter to set.
	 */
	public void setPoaCodeFilter(String poaCodeFilter) {
		this.poaCodeFilter = poaCodeFilter;
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