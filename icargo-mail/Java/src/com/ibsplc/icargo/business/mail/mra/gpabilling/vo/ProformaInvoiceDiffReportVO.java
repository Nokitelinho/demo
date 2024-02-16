/*
 * ProformaInvoiceDiffReportVO.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3271
 *
 */
public class ProformaInvoiceDiffReportVO extends AbstractVO{
	
	public static final String FINALIZED_INVOICE = "F";
	
	private String companyCode;
	
	private String functionPoint;
	
	private LocalDate fromDate;
	
	private LocalDate toDate;
	
	private String country;
	
	private String invoiceNumber;
	
	private String invoiceStatus;
	
	private Collection<CN51SummaryVO> cn51SummaryVOs;

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
	 * @return Returns the functionPoint.
	 */
	public String getFunctionPoint() {
		return functionPoint;
	}

	/**
	 * @param functionPoint The functionPoint to set.
	 */
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}

	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the cn51SummaryVOs.
	 */
	public Collection<CN51SummaryVO> getCn51SummaryVOs() {
		return cn51SummaryVOs;
	}

	/**
	 * @param cn51SummaryVOs The cn51SummaryVOs to set.
	 */
	public void setCn51SummaryVOs(Collection<CN51SummaryVO> cn51SummaryVOs) {
		this.cn51SummaryVOs = cn51SummaryVOs;
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
	 * @return Returns the invoiceStatus.
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus The invoiceStatus to set.
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}


}
