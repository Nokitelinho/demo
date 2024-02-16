/*
 * ExceptionInInvoiceFilterVO.java Created on Feb20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-2391 
 */
public class ExceptionInInvoiceFilterVO extends AbstractVO{
	private String companyCode;
	private String airlineCode;
	private String clearancePeriod;
	private String exceptionStatus;
	private String invoiceNumber;
	private String memoCode;
	private String contractCurrency;
	private String memoStatus;
	private LocalDate fromdate;
	private LocalDate todate;
	private int pageNumber;

	
	
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return Returns the fromdate.
	 */
	public LocalDate getFromdate() {
		return fromdate;
	}

	/**
	 * @param fromdate The fromdate to set.
	 */
	public void setFromdate(LocalDate fromdate) {
		this.fromdate = fromdate;
	}

	/**
	 * @return Returns the todate.
	 */
	public LocalDate getTodate() {
		return todate;
	}

	/**
	 * @param todate The todate to set.
	 */
	public void setTodate(LocalDate todate) {
		this.todate = todate;
	}

	/**
    *
    */
   public ExceptionInInvoiceFilterVO() {

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
	 * @return Returns the exceptionStatus.
	 */
	public String getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * @param exceptionStatus The exceptionStatus to set.
	 */
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
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
	 * @return Returns the memoStatus.
	 */
	public String getMemoStatus() {
		return memoStatus;
	}

	/**
	 * @param memoStatus The memoStatus to set.
	 */
	public void setMemoStatus(String memoStatus) {
		this.memoStatus = memoStatus;
	}

	

	/**
	 * @return Returns the contractCurrency.
	 */
	public String getContractCurrency() {
		return contractCurrency;
	}

	/**
	 * @param contractCurrency The contractCurrency to set.
	 */
	public void setContractCurrency(String contractCurrency) {
		this.contractCurrency = contractCurrency;
	}

	/**
	 * @return Returns the memoCode.
	 */
	public String getMemoCode() {
		return memoCode;
	}

	/**
	 * @param memoCode The memoCode to set.
	 */
	public void setMemoCode(String memoCode) {
		this.memoCode = memoCode;
	}

}
