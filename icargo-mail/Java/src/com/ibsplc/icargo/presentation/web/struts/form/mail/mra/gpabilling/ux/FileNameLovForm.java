package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class FileNameLovForm extends ScreenModel{
	private static final String SCREEN_ID = "mail.mra.gpabilling.ux.filenamelov";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "mra";
	private static final String BUNDLE = "filenamelovResources";
	
	//FOR LOV
	private String fromDate;
	
	private String toDate;

    private String periodNumber;

    private String multiselect;

	private String pagination;

	private String lovAction;

	private String title;

	private String formCount;

	private String lovTxtFieldName;

	private String lovDescriptionTxtFieldName;

	private String selectedValues = "";

	private String[] selectCheckBox;

	//FOR PAGINATION
	private String lastPageNum = "";

	private String displayPage = "1";
	
	private String pageURL="";
	
	private String defaultPageSize="10";
	
	private Page fileNameLovPage;
	
	private int index;  
	
	@Override
	public String getProduct() {
		return PRODUCT_NAME;
	}

	@Override
	public String getScreenId() {
		return SCREEN_ID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	@Override
	public String getBundle() {
		return BUNDLE;
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

	public String getLovAction() {
		return lovAction;
	}

	public void setLovAction(String lovAction) {
		this.lovAction = lovAction;
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

	public String getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(String defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public Page getFileNameLovPage() {
		return fileNameLovPage;
	}

	public void setFileNameLovPage(Page fileNameLovPage) {
		this.fileNameLovPage = fileNameLovPage;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

}
