/*
 * ChargingInvoiceFilterVO.java Created on Jan 10, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ChargingInvoiceFilterVO extends AbstractVO 
		implements Serializable {
    /**
     * Repair Transaction
     */
	public static final String TRANSACTION_TYPE_REPAIR = "P";
	public static final String TRANSACTION_TYPE_ALL = "ALL";
    private String companyCode;
    private String transactionType;
    private String invoiceRefNumber;
    private String invoicedToCode;
    private LocalDate invoicedDateFrom;
    private LocalDate invoicedDateTo;
    private int totalRecords;//Added By A-5214
    private String currencyCode;
    public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
    //Added by Deepthi on for 
    private String partyType;
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
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the invoicedDateFrom.
	 */
	public LocalDate getInvoicedDateFrom() {
		return invoicedDateFrom;
	}
	/**
	 * @param invoicedDateFrom The invoicedDateFrom to set.
	 */
	public void setInvoicedDateFrom(LocalDate invoicedDateFrom) {
		this.invoicedDateFrom = invoicedDateFrom;
	}
	/**
	 * @return Returns the invoicedDateTo.
	 */
	public LocalDate getInvoicedDateTo() {
		return invoicedDateTo;
	}
	/**
	 * @param invoicedDateTo The invoicedDateTo to set.
	 */
	public void setInvoicedDateTo(LocalDate invoicedDateTo) {
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
	//Added by A-5214 as part from the ICRD-22824 STARTS
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	//Added by A-5214 as part from the ICRD-22824 ENDS
 
}
