/*
 * ListInvoiceForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1496
 *
 */
public class ListInvoiceForm extends ScreenModel {
    
    
	private static final String BUNDLE = "listinvoice";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.listinvoice";

	private String bundle;    
	
	private String transactionType;
	 
    private String invoiceRefNumber;
    
    private String invoiceRefNum;
    
    private String invoicedToCode;
    
    private String invoicedDateFrom = "";
    
    private String invoicedDateTo = "";

    private String displayPage = "1";
    
    private String lastPageNum = "0";
    
    private String displayLovPage = "1";
    
    private String lastLovPageNum = "0";
    
    private String[] selectedRows;
    
    private String[] selectedRowsInLov;
    
    private String[] invoiceRefNumbers;
    
    private String[] invoicedDates;
    
    private String[] invoicedToCodes;
    
    private String[] invoicedToNames;
    
    private String[] transactionTypes;
    
    private String airlineBaseCurrency;
    
    private String selectedLovRows;
    
    private String lovStatusFlag;
    
    private String partyType;
    
    private String comboFlag;
    
    private String countTotalFlag = "";//Added by A-5214 as part from the ICRD-22824

	/**
	 * @return the comboFlag
	 */
	public String getComboFlag() {
		return comboFlag;
	}

	/**
	 * @param comboFlag the comboFlag to set
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}

	/**
	 * @return the partyType
	 */
	public String getPartyType() {
		return partyType;
	}

	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	/**
	 * @return Returns the lovStatusFlag.
	 */
	public String getLovStatusFlag() {
		return lovStatusFlag;
	}

	/**
	 * @param lovStatusFlag The lovStatusFlag to set.
	 */
	public void setLovStatusFlag(String lovStatusFlag) {
		this.lovStatusFlag = lovStatusFlag;
	}

	/**
	 * @return Returns the selectedRows.
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows The selectedRows to set.
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * @return Returns the invoicedDateFrom.
	 */
	@DateFieldId(id="ListULDInvoiceDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getInvoicedDateFrom() {
		return invoicedDateFrom;
	}

	/**
	 * @param invoicedDateFrom The invoicedDateFrom to set.
	 */
	public void setInvoicedDateFrom(String invoicedDateFrom) {
		this.invoicedDateFrom = invoicedDateFrom;
	}

	/**
	 * @return Returns the invoicedDateTo.
	 */
	@DateFieldId(id="ListULDInvoiceDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getInvoicedDateTo() {
		return invoicedDateTo;
	}

	/**
	 * @param invoicedDateTo The invoicedDateTo to set.
	 */
	public void setInvoicedDateTo(String invoicedDateTo) {
		this.invoicedDateTo = invoicedDateTo;
	}

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
	 * @return Returns the invoiceRefNumber.
	 */
	public String getInvoiceRefNumber() {
		return invoiceRefNumber;
	}

	/**
	 * @param invoiceRefNumber The invoiceRefNumber to set.
	 */
	public void setInvoiceRefNumber(String invoiceRefNumber) {
		this.invoiceRefNumber = invoiceRefNumber;
	}

	/**
	 * @return Returns the transactionType.
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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
	 * @return Returns the displayLovPage.
	 */
	public String getDisplayLovPage() {
		return displayLovPage;
	}

	/**
	 * @param displayLovPage The displayLovPage to set.
	 */
	public void setDisplayLovPage(String displayLovPage) {
		this.displayLovPage = displayLovPage;
	}

	/**
	 * @return Returns the lastLovPageNum.
	 */
	public String getLastLovPageNum() {
		return lastLovPageNum;
	}

	/**
	 * @param lastLovPageNum The lastLovPageNum to set.
	 */
	public void setLastLovPageNum(String lastLovPageNum) {
		this.lastLovPageNum = lastLovPageNum;
	}

	/**
	 * @return Returns the selectedLovRows.
	 */
	public String getSelectedLovRows() {
		return selectedLovRows;
	}

	/**
	 * @param selectedLovRows The selectedLovRows to set.
	 */
	public void setSelectedLovRows(String selectedLovRows) {
		this.selectedLovRows = selectedLovRows;
	}

	/**
	 * @return Returns the selectedRowsInLov.
	 */
	public String[] getSelectedRowsInLov() {
		return selectedRowsInLov;
	}

	/**
	 * @param selectedRowsInLov The selectedRowsInLov to set.
	 */
	public void setSelectedRowsInLov(String[] selectedRowsInLov) {
		this.selectedRowsInLov = selectedRowsInLov;
	}

	/**
	 * @return Returns the invoiceRefNumbers.
	 */
	public String[] getInvoiceRefNumbers() {
		return invoiceRefNumbers;
	}

	/**
	 * @param invoiceRefNumbers The invoiceRefNumbers to set.
	 */
	public void setInvoiceRefNumbers(String[] invoiceRefNumbers) {
		this.invoiceRefNumbers = invoiceRefNumbers;
	}

	/**
	 * @return Returns the invoicedDates.
	 */
	public String[] getInvoicedDates() {
		return invoicedDates;
	}

	/**
	 * @param invoicedDates The invoicedDates to set.
	 */
	public void setInvoicedDates(String[] invoicedDates) {
		this.invoicedDates = invoicedDates;
	}

	/**
	 * @return Returns the invoicedToCodes.
	 */
	public String[] getInvoicedToCodes() {
		return invoicedToCodes;
	}

	/**
	 * @param invoicedToCodes The invoicedToCodes to set.
	 */
	public void setInvoicedToCodes(String[] invoicedToCodes) {
		this.invoicedToCodes = invoicedToCodes;
	}

	/**
	 * @return Returns the invoicedToNames.
	 */
	public String[] getInvoicedToNames() {
		return invoicedToNames;
	}

	/**
	 * @param invoicedToNames The invoicedToNames to set.
	 */
	public void setInvoicedToNames(String[] invoicedToNames) {
		this.invoicedToNames = invoicedToNames;
	}

	/**
	 * @return Returns the transactionTypes.
	 */
	public String[] getTransactionTypes() {
		return transactionTypes;
	}

	/**
	 * @param transactionTypes The transactionTypes to set.
	 */
	public void setTransactionTypes(String[] transactionTypes) {
		this.transactionTypes = transactionTypes;
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

	public String getInvoiceRefNum() {
		return invoiceRefNum;
	}

	public void setInvoiceRefNum(String invoiceRefNum) {
		this.invoiceRefNum = invoiceRefNum;
	}

	//Added by A-5214 as part from the ICRD-22824 STARTS
	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}

	public String getCountTotalFlag() {
		return countTotalFlag;
	}
	//Added by A-5214 as part from the ICRD-22824 ENDS

}
