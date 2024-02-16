
/*
 * MailGPABillingVO.java Created on Mar 16, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3229
 *
 */
public class MailGPABillingVO extends AbstractVO{	
	
	
	private String companyCode;
	private String gpaCode;
	private String gpaName;
	private String billingCurrency;
	private String despatchNumber;
	private String billingStatus;
	private String billingFromDate;
	private String billingToDate;
	private String invoiceNumber;
	private LocalDate invoiceDate;
	private Money applicableRate;
	private double weight;
	

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the applicableRate
	 */
	public Money getApplicableRate() {
		return applicableRate;
	}
	/**
	 * @param applicableRate the applicableRate to set
	 */
	public void setApplicableRate(Money applicableRate) {
		this.applicableRate = applicableRate;
	}
	/**
	 * @return the billingCurrency
	 */
	public String getBillingCurrency() {
		return billingCurrency;
	}
	/**
	 * @param billingCurrency the billingCurrency to set
	 */
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}
	/**
	 * @return the billingFromDate
	 */
	public String getBillingFromDate() {
		return billingFromDate;
	}
	/**
	 * @param billingFromDate the billingFromDate to set
	 */
	public void setBillingFromDate(String billingFromDate) {
		this.billingFromDate = billingFromDate;
	}
	/**
	 * @return the billingStatus
	 */
	public String getBillingStatus() {
		return billingStatus;
	}
	/**
	 * @param billingStatus the billingStatus to set
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	/**
	 * @return the billingToDate
	 */
	public String getBillingToDate() {
		return billingToDate;
	}
	/**
	 * @param billingToDate the billingToDate to set
	 */
	public void setBillingToDate(String billingToDate) {
		this.billingToDate = billingToDate;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the despatchNumber
	 */
	public String getDespatchNumber() {
		return despatchNumber;
	}
	/**
	 * @param despatchNumber the despatchNumber to set
	 */
	public void setDespatchNumber(String despatchNumber) {
		this.despatchNumber = despatchNumber;
	}
	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the gpaName
	 */
	public String getGpaName() {
		return gpaName;
	}
	/**
	 * @param gpaName the gpaName to set
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}
	/**
	 * @return the invoiceDate
	 */
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

}
