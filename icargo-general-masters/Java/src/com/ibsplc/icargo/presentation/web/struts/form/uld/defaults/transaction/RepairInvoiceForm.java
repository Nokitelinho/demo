/*
 * RepairInvoiceForm.java Created on Dec 19, 2005
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
public class RepairInvoiceForm extends ScreenModel {
    
										  
	private static final String BUNDLE = "repairinvoice";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.repairinvoice";

	
private String bundle;    
	
	private String invoiceRefNo;
	
    private String invoicedToCode;
    
    private String name;
    
    private String invoicedDate = "";
    
    private String totalAmount;
    
    private String totalWaived;
    
    private String totalInvoiced;
    
    private String[] waivedAmounts;
    
    private String[] invoicedAmounts;
    
    private String[] actualAmounts;
    
    private String[] remarks;
    /**
     * 
     */
    private String closeStatus;
    
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
	 * @return Returns the waivedAmounts.
	 */
	public String[] getWaivedAmounts() {
		return waivedAmounts;
	}

	/**
	 * @param waivedAmounts The waivedAmounts to set.
	 */
	public void setWaivedAmounts(String[] waivedAmounts) {
		this.waivedAmounts = waivedAmounts;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the totalAmount.
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount The totalAmount to set.
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return Returns the totalInvoiced.
	 */
	public String getTotalInvoiced() {
		return totalInvoiced;
	}

	/**
	 * @param totalInvoiced The totalInvoiced to set.
	 */
	public void setTotalInvoiced(String totalInvoiced) {
		this.totalInvoiced = totalInvoiced;
	}

	/**
	 * @return Returns the totalWaived.
	 */
	public String getTotalWaived() {
		return totalWaived;
	}

	/**
	 * @param totalWaived The totalWaived to set.
	 */
	public void setTotalWaived(String totalWaived) {
		this.totalWaived = totalWaived;
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

	/**
	 * @return Returns the invoicedAmounts.
	 */
	public String[] getInvoicedAmounts() {
		return invoicedAmounts;
	}

	/**
	 * @param invoicedAmounts The invoicedAmounts to set.
	 */
	public void setInvoicedAmounts(String[] invoicedAmounts) {
		this.invoicedAmounts = invoicedAmounts;
	}

	/**
	 * @return Returns the actualAmounts.
	 */
	public String[] getActualAmounts() {
		return actualAmounts;
	}

	/**
	 * @param actualAmounts The actualAmounts to set.
	 */
	public void setActualAmounts(String[] actualAmounts) {
		this.actualAmounts = actualAmounts;
	}

	/**
	 * @return Returns the closeStatus.
	 */
	public String getCloseStatus() {
		return closeStatus;
	}

	/**
	 * @param closeStatus The closeStatus to set.
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}


}
