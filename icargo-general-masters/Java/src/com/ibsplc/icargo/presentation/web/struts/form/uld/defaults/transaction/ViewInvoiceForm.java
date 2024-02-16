/*
 * ViewInvoiceForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2001
 *
 */
public class ViewInvoiceForm extends ScreenModel {
    
    
	private static final String BUNDLE = "viewinvoice";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.viewinvoice";

	
private String bundle;    
	
	private String invoiceRefNo;
	
    private String invoicedToCode;
    
    private String name;
    
    private String invoicedDate = "";
    
    private String demerageAccured;
    
    private String waivedAmount;
    
    private String demerageAmount;
    
    private String airlineBaseCurrency;
    
    private String displayPage = "1";
    
    private String currentPage = "1";
    
    private String lastPageNum = "0";
    
    private String totalRecords = "0";

	/**
	 * @return Returns the invoicedToCode.
	 */
	public String getInvoicedToCode() {
		return invoicedToCode;
	}

	/**
	 * @param invoicedToCode The invoicedToCode to set.
	 */
	public void setInvoicedToCode(String invoicedToCode) {
		this.invoicedToCode = invoicedToCode;
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
	 * 
	 */
    public String getScreenId() {
        return SCREENID;
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
	 * @return Returns the invoicedDate.
	 */
	public String getInvoicedDate() {
		return invoicedDate;
	}

	/**
	 * @param invoicedDate The invoicedDate to set.
	 */
	public void setInvoicedDate(String invoicedDate) {
		this.invoicedDate = invoicedDate;
	}

	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * @return Returns the demerageAccured.
	 */
	public String getDemerageAccured() {
		return demerageAccured;
	}

	/**
	 * @param demerageAccured The demerageAccured to set.
	 */
	public void setDemerageAccured(String demerageAccured) {
		this.demerageAccured = demerageAccured;
	}

	/**
	 * @return Returns the demerageAmount.
	 */
	public String getDemerageAmount() {
		return demerageAmount;
	}

	/**
	 * @param demerageAmount The demerageAmount to set.
	 */
	public void setDemerageAmount(String demerageAmount) {
		this.demerageAmount = demerageAmount;
	}

	/**
	 * @return Returns the invoiceRefNo.
	 */
	public String getInvoiceRefNo() {
		return invoiceRefNo;
	}

	/**
	 * @param invoiceRefNo The invoiceRefNo to set.
	 */
	public void setInvoiceRefNo(String invoiceRefNo) {
		this.invoiceRefNo = invoiceRefNo;
	}

	/**
	 * @return Returns the waivedAmount.
	 */
	public String getWaivedAmount() {
		return waivedAmount;
	}

	/**
	 * @param waivedAmount The waivedAmount to set.
	 */
	public void setWaivedAmount(String waivedAmount) {
		this.waivedAmount = waivedAmount;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
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
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return Returns the airlineBaseCurrency.
	 */
	public String getAirlineBaseCurrency() {
		return airlineBaseCurrency;
	}

	/**
	 * @param airlineBaseCurrency The airlineBaseCurrency to set.
	 */
	public void setAirlineBaseCurrency(String airlineBaseCurrency) {
		this.airlineBaseCurrency = airlineBaseCurrency;
	}


}
