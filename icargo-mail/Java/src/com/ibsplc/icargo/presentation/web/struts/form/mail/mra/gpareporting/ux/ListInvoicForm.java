/**
 * DSNLovForm.java Created on May 25 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux;


import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author a-4823
 *
 */
public class ListInvoicForm extends ScreenModel{
	private static final String SCREEN_ID = "mail.mra.gpareporting.ux.listinvoic";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "mra";
	private static final String BUNDLE = "ListInvoicResourceBundle";

	private String lastPageNum = "";
	private String companycode;
	private String displayPage = "1";
	private String status;
	private int index;  
	private String totalRecords;
	private String selectedValues = "";
	private String paCode;
	private String[] selectCheckBox;	
	private String fromDate;
	private String toDate;
	private String actionFlag;
	private String defaultPageSize = "10";
	private String invoicId;
	private String fileName;
	private String invoicRefId;
	private boolean rejectFlag;
	private String remarks;


	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	
	@DateFieldId(id="listInvoicDateRange",fieldType="from") //Added by A-7929 for IASCB-27457
	public String getFromDate() {
		return fromDate;
	}


	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	@DateFieldId(id="listInvoicDateRange",fieldType="to") //Added by A-7929 for IASCB-27457
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


	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}


	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}

	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}


	public String getDefaultPageSize() {
		return defaultPageSize;
	}


	public void setDefaultPageSize(String defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalRecords() {
		return totalRecords;
	}


	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}


	public String getPaCode() {
		return paCode;
	}


	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getInvoicId() {
		return invoicId;
	}
	public void setInvoicId(String invoicId) {
		this.invoicId = invoicId;
	}

	/**
	 * 	Getter for fileName 
	 *	Added by : A-5219 on 10-Jun-2020
	 * 	Used for :
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 *  @param fileName the fileName to set
	 * 	Setter for fileName 
	 *	Added by : A-5219 on 10-Jun-2020
	 * 	Used for :
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 	Getter for invoicRefId 
	 *	Added by : A-5219 on 10-Jun-2020
	 * 	Used for :
	 */
	public String getInvoicRefId() {
		return invoicRefId;
	}

	/**
	 *  @param invoicRefId the invoicRefId to set
	 * 	Setter for invoicRefId 
	 *	Added by : A-5219 on 10-Jun-2020
	 * 	Used for :
	 */
	public void setInvoicRefId(String invoicRefId) {
		this.invoicRefId = invoicRefId;
	}
	public boolean isRejectFlag() {
		return rejectFlag;
	}
	public void setRejectFlag(boolean rejectFlag) {
		this.rejectFlag = rejectFlag;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}