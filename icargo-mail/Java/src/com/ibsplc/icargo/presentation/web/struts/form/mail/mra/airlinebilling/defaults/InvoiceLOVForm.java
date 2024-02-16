/*
 * InvoiceLOVForm.java Created on Feb 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;


import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceLovVO;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2135
 *
 */
public class InvoiceLOVForm extends ScreenModel {

   private static final String BUNDLE = "MRAArlBlgInvoiceLovResource";

   private static final String PRODUCT = "mra";

   private static final String SUBPRODUCT = "airlinebilling";

   private static final String SCREEN_ID ="mra.airlinebilling.defaults.invoicelov";

   private String code;

   private String invoiceNumber;
 
   private String airlineCode;

   private String clearancePeriod;
   
   private String invoiceDate;
   private String strInvoiceNumber;

   private String strAirlineCode;

   private String strClearancePeriod;
   
   private String strBillingType;
   
   private String strDateField;

   private String billingType;
	
	
    private Page<AirlineInvoiceLovVO>invoiceLovVos;
	
    
    //  FOR LOV
	private String multiselect;

	private String pagination;

	private String lovaction;

	private String title;

	private String formCount;

	private String lovTxtFieldName;

	private String lovDescriptionTxtFieldName;
	
	private String lovNameDateFieldName;
	
	private String selectedValues;

	private String[] selectCheckBox;
	
	private String fromScreen ;
    
	//  FOR PAGINATION
	private String lastPageNum;

	private String displayPage;

	private int index;
	
	private String lovNameTxtFieldName="";
	
	    

	
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

	 * Constructor

	 */

	public InvoiceLOVForm() {
		super();
		lastPageNum = "0";
		displayPage = "1";
		selectedValues = "";
		selectCheckBox = null;
		
		
	}


/**
 * 
 */
	public String getScreenId() {

        return SCREEN_ID;

    }
  
/**
 * 
 */
    public String getProduct() {

        return PRODUCT;

    }


/**
 * 
 */
    public String getSubProduct() {

        return SUBPRODUCT;

    }



	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}



	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}



	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}


	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}



	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
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
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}



	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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
	 * @return Returns the strAirlineCode.
	 */
	public String getStrAirlineCode() {
		return strAirlineCode;
	}



	/**
	 * @param strAirlineCode The strAirlineCode to set.
	 */
	public void setStrAirlineCode(String strAirlineCode) {
		this.strAirlineCode = strAirlineCode;
	}



	/**
	 * @return Returns the strClearancePeriod.
	 */
	public String getStrClearancePeriod() {
		return strClearancePeriod;
	}



	/**
	 * @param strClearancePeriod The strClearancePeriod to set.
	 */
	public void setStrClearancePeriod(String strClearancePeriod) {
		this.strClearancePeriod = strClearancePeriod;
	}



	/**
	 * @return Returns the strInvoiceNumber.
	 */
	public String getStrInvoiceNumber() {
		return strInvoiceNumber;
	}



	/**
	 * @param strInvoiceNumber The strInvoiceNumber to set.
	 */
	public void setStrInvoiceNumber(String strInvoiceNumber) {
		this.strInvoiceNumber = strInvoiceNumber;
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


	public String getBillingType() {
		return billingType;
	}


	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}


	public Page<AirlineInvoiceLovVO> getInvoiceLovVos() {
		return invoiceLovVos;
	}


	public void setInvoiceLovVos(Page<AirlineInvoiceLovVO> invoiceLovVos) {
		this.invoiceLovVos = invoiceLovVos;
	}


	public String getStrBillingType() {
		return strBillingType;
	}


	public void setStrBillingType(String strBillingType) {
		this.strBillingType = strBillingType;
	}


	public String getLovNameDateFieldName() {
		return lovNameDateFieldName;
	}


	public void setLovNameDateFieldName(String lovNameDateFieldName) {
		this.lovNameDateFieldName = lovNameDateFieldName;
	}


	public String getFromScreen() {
		return fromScreen;
	}


	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}


	public String getStrDateField() {
		return strDateField;
	}


	public void setStrDateField(String strDateField) {
		this.strDateField = strDateField;
	}


	public String getInvoiceDate() {
		return invoiceDate;
	}


	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	

	/**
	 * @return Returns the invoiceLovVos.
	 */
	/*public Page<InvoiceLovVO> getInvoiceLovVos() {
		return invoiceLovVos;
	}*/


	/**
	 * @param invoiceLovVos The invoiceLovVos to set.
	 */
	/*public void setInvoiceLovVos(Page<InvoiceLovVO> invoiceLovVos) {
		this.invoiceLovVos = invoiceLovVos;
	}*/


	


}
