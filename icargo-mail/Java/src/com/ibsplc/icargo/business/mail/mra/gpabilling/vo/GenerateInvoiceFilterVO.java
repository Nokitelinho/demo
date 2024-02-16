/*
 * GenerateInvoiceFilterVO.java Created on Jan 20, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2049
 *
 */
public class GenerateInvoiceFilterVO extends AbstractVO {

    private String companyCode;

    private String gpaCode;

    private String gpaName;

    private String countryCode;

    private LocalDate billingPeriodFrom;

    private LocalDate billingPeriodTo;
    
    private LocalDate currentDate;
    
    private String billingFrequency;
    
    private String invoiceType;
    //Added by A-6991 for ICRD-211662
    private boolean isAddNew;
    
    private String source;
    private Collection<BillingParameterVO> paramsList;

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	private String transactionCode;
	
	private int invoiceLogSerialNumber;

	public int getInvoiceLogSerialNumber() {
		return invoiceLogSerialNumber;
	}

	public void setInvoiceLogSerialNumber(int invoiceLogSerialNumber) {
		this.invoiceLogSerialNumber = invoiceLogSerialNumber;
	}

	/**
	 * @return Returns the billingPeriodFrom.
	 */
	public LocalDate getBillingPeriodFrom() {
		return billingPeriodFrom;
	}

	/**
	 * @param billingPeriodFrom The billingPeriodFrom to set.
	 */
	public void setBillingPeriodFrom(LocalDate billingPeriodFrom) {
		this.billingPeriodFrom = billingPeriodFrom;
	}

	/**
	 * @return Returns the billingPeriodTo.
	 */
	public LocalDate getBillingPeriodTo() {
		return billingPeriodTo;
	}

	/**
	 * @param billingPeriodTo The billingPeriodTo to set.
	 */
	public void setBillingPeriodTo(LocalDate billingPeriodTo) {
		this.billingPeriodTo = billingPeriodTo;
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
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
	 * @return Returns the currentDate.
	 */
	public LocalDate getCurrentDate() {
		return currentDate;
	}

	/**
	 * @param currentDate The currentDate to set.
	 */
	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * @return the billingFrequency
	 */
	public String getBillingFrequency() {
		return billingFrequency;
	}

	/**
	 * @param billingFrequency the billingFrequency to set
	 */
	public void setBillingFrequency(String billingFrequency) {
		this.billingFrequency = billingFrequency;
	}

	/**
	 * 	Getter for isAddNew 
	 *	Added by : A-6991 on 13-Sep-2017
	 * 	Used for : ICRD-211662
	 */
	public boolean isAddNew() {
		return isAddNew;
	}

	/**
	 *  @param isAddNew the isAddNew to set
	 * 	Setter for isAddNew 
	 *	Added by : A-6991 on 13-Sep-2017
	 * 	Used for : ICRD-211662
	 */
	public void setAddNew(boolean isAddNew) {
		this.isAddNew = isAddNew;
	}

	/**
	 * 	Getter for source 
	 *	Added by : A-8061 on 10-Jun-2021
	 * 	Used for :
	 */
	public String getSource() {
		return source;
	}

	/**
	 *  @param source the source to set
	 * 	Setter for source 
	 *	Added by : A-8061 on 10-Jun-2021
	 * 	Used for :
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 	Getter for paramsList 
	 *	Added by : A-8061 on 10-Jun-2021
	 * 	Used for :
	 */
	public Collection<BillingParameterVO> getParamsList() {
		return paramsList;
	}

	/**
	 *  @param paramsList the paramsList to set
	 * 	Setter for paramsList 
	 *	Added by : A-8061 on 10-Jun-2021
	 * 	Used for :
	 */
	public void setParamsList(Collection<BillingParameterVO> paramsList) {
		this.paramsList = paramsList;
	}




}
