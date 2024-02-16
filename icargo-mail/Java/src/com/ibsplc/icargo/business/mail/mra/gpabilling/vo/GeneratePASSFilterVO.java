/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO.java
 *
 *	Created by	:	A-4809
 *	Created on	:	12-Mar-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	12-Mar-2021	:	Draft
 */
public class GeneratePASSFilterVO extends AbstractVO{
	
	private String companyCode;
	private String periodNumber;
    private LocalDate billingPeriodFrom;
    private LocalDate billingPeriodTo;
    private String gpaCode;
    private String country;
    private String fileName;
    private boolean isAddNew;
	private String transactionCode;
	private int invoiceLogSerialNumber;
	private Collection<BillingParameterVO> paramsList;
	
	/**
	 * 	Getter for companyCode 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for periodNumber 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}
	/**
	 *  @param periodNumber the periodNumber to set
	 * 	Setter for periodNumber 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	/**
	 * 	Getter for billingPeriodFrom 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public LocalDate getBillingPeriodFrom() {
		return billingPeriodFrom;
	}
	/**
	 *  @param billingPeriodFrom the billingPeriodFrom to set
	 * 	Setter for billingPeriodFrom 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setBillingPeriodFrom(LocalDate billingPeriodFrom) {
		this.billingPeriodFrom = billingPeriodFrom;
	}
	/**
	 * 	Getter for billingPeriodTo 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public LocalDate getBillingPeriodTo() {
		return billingPeriodTo;
	}
	/**
	 *  @param billingPeriodTo the billingPeriodTo to set
	 * 	Setter for billingPeriodTo 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setBillingPeriodTo(LocalDate billingPeriodTo) {
		this.billingPeriodTo = billingPeriodTo;
	}
	/**
	 * 	Getter for gpaCode 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 *  @param gpaCode the gpaCode to set
	 * 	Setter for gpaCode 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * 	Getter for country 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public String getCountry() {
		return country;
	}
	/**
	 *  @param country the country to set
	 * 	Setter for country 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * 	Getter for fileName 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 *  @param fileName the fileName to set
	 * 	Setter for fileName 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 	Getter for isAddNew 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public boolean isAddNew() {
		return isAddNew;
	}
	/**
	 *  @param isAddNew the isAddNew to set
	 * 	Setter for isAddNew 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setAddNew(boolean isAddNew) {
		this.isAddNew = isAddNew;
	}
	/**
	 * 	Getter for transactionCode 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public String getTransactionCode() {
		return transactionCode;
	}
	/**
	 *  @param transactionCode the transactionCode to set
	 * 	Setter for transactionCode 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	/**
	 * 	Getter for invoiceLogSerialNumber 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public int getInvoiceLogSerialNumber() {
		return invoiceLogSerialNumber;
	}
	/**
	 *  @param invoiceLogSerialNumber the invoiceLogSerialNumber to set
	 * 	Setter for invoiceLogSerialNumber 
	 *	Added by : A-4809 on 12-Mar-2021
	 * 	Used for :
	 */
	public void setInvoiceLogSerialNumber(int invoiceLogSerialNumber) {
		this.invoiceLogSerialNumber = invoiceLogSerialNumber;
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
