/*
 * BillingSummaryDetailsVO.java Created on Mar 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class BillingSummaryDetailsVO extends AbstractVO {
	
	private String companyCode;
	
	private String billingPeriod;
	
	private String gpaCode;
	
	private String gpaName;
	
	private String invoiceNumber;
	
	private LocalDate invoiceDate;
	
	private String currency;
	
	private Double billedValue;

	private Double grandTotal;//Added for ICRD-105572
	
	private Double sumGrandTotal;//Added by A-7929 for ICRD-257574 to display grandtotal in base currency  in gpabillingperiod wise report
	
	private String billedAmtForReport;//Added by A-7929 for ICRD-259231 
	 private String overrideRounding;
	public String getOverrideRounding() {
		return overrideRounding;
	}

	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
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
	 * @return Returns the billedValue.
	 */
	public Double getBilledValue() {
		return billedValue;
	}

	/**
	 * @param billedValue The billedValue to set.
	 */
	public void setBilledValue(Double billedValue) {
		this.billedValue = billedValue;
	}

	/**
	 * @return Returns the billingPeriod.
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}

	/**
	 * @param billingPeriod The billingPeriod to set.
	 */
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	/**
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return Returns the invoiceDate.
	 */
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate The invoiceDate to set.
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
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
	 * @return the grandTotal
	 */
	public Double getGrandTotal() {
		return grandTotal;
	}
	/**
	 * @param grandTotal the grandTotal to set
	 */
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Double getSumGrandTotal() {
		return sumGrandTotal;
	}

	public void setSumGrandTotal(Double sumGrandTotal) {
		this.sumGrandTotal = sumGrandTotal;
	}
   /**
    * @return billedAmtForReport
    */
	public String getBilledAmtForReport() {
		return billedAmtForReport;
	}
	/**
 	* 
 	* @param billedAmtForReport the billedAmtForReport to set
	*/
 
	public void setBilledAmtForReport(String billedAmtForReport) {
		this.billedAmtForReport = billedAmtForReport;
	}

	
	
	
}