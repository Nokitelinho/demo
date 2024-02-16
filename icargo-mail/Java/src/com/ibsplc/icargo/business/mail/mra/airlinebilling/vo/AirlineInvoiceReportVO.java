/*
 * AirlineInvoiceReportVO.java Created on Mar 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd.
 * All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2270
 *
 */
public class AirlineInvoiceReportVO extends AbstractVO{
	
	private String gpaCode;
	
	private String sector;
	
	private String fromBillingPeriod;
	 
	private String toBillingPeriod;

   private String invoiceNumber;
   
   private String billedDate;
   
   private String billingCurrencyCode;
   
   private String contractCurrencyCode;
   
   private double totalAmountinBillingCurrency;
   
   private double totalAmountinContractCurrency;

/**
 * @return Returns the contractCurrencyCode.
 */
public String getContractCurrencyCode() {
	return contractCurrencyCode;
}

/**
 * @param contractCurrencyCode The contractCurrencyCode to set.
 */
public void setContractCurrencyCode(String contractCurrencyCode) {
	this.contractCurrencyCode = contractCurrencyCode;
}

/**
 * @return Returns the totalAmountinContractCurrency.
 */
public double getTotalAmountinContractCurrency() {
	return totalAmountinContractCurrency;
}

/**
 * @param totalAmountinContractCurrency The totalAmountinContractCurrency to set.
 */
public void setTotalAmountinContractCurrency(
		double totalAmountinContractCurrency) {
	this.totalAmountinContractCurrency = totalAmountinContractCurrency;
}

/**
 * @return Returns the billedDate.
 */
public String getBilledDate() {
	return billedDate;
}

/**
 * @param billedDate The billedDate to set.
 */
public void setBilledDate(String billedDate) {
	this.billedDate = billedDate;
}

/**
 * @return Returns the billingCurrencyCode.
 */
public String getBillingCurrencyCode() {
	return billingCurrencyCode;
}

/**
 * @param billingCurrencyCode The billingCurrencyCode to set.
 */
public void setBillingCurrencyCode(String billingCurrencyCode) {
	this.billingCurrencyCode = billingCurrencyCode;
}

/**
 * @return Returns the fromBillingPeriod.
 */
public String getFromBillingPeriod() {
	return fromBillingPeriod;
}

/**
 * @param fromBillingPeriod The fromBillingPeriod to set.
 */
public void setFromBillingPeriod(String fromBillingPeriod) {
	this.fromBillingPeriod = fromBillingPeriod;
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
 * @return Returns the sector.
 */
public String getSector() {
	return sector;
}

/**
 * @param sector The sector to set.
 */
public void setSector(String sector) {
	this.sector = sector;
}

/**
 * @return Returns the toBillingPeriod.
 */
public String getToBillingPeriod() {
	return toBillingPeriod;
}

/**
 * @param toBillingPeriod The toBillingPeriod to set.
 */
public void setToBillingPeriod(String toBillingPeriod) {
	this.toBillingPeriod = toBillingPeriod;
}

/**
 * @return Returns the totalAmountinBillingCurrency.
 */
public double getTotalAmountinBillingCurrency() {
	return totalAmountinBillingCurrency;
}

/**
 * @param totalAmountinBillingCurrency The totalAmountinBillingCurrency to set.
 */
	public void setTotalAmountinBillingCurrency(
		double totalAmountinBillingCurrency) {
	this.totalAmountinBillingCurrency = totalAmountinBillingCurrency;
}
   
  
	
	

}
