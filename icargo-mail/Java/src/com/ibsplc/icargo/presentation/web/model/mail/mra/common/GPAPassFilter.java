/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPAPassFilter.java
 *
 *	Created by	:	A-4809
 *	Created on	:	22-Apr-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPAPassFilter.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	22-Apr-2021	:	Draft
 */
public class GPAPassFilter {
	
	private String country;
	private String gpaCode;
	private String periodNumber;
	private String fromBillingDate;
	private String toBillingDate;
	private String fileName;
	private boolean includeNewInvoice;
	/**
	 * 	Getter for country 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public String getCountry() {
		return country;
	}
	/**
	 *  @param country the country to set
	 * 	Setter for country 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * 	Getter for gpaCode 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 *  @param gpaCode the gpaCode to set
	 * 	Setter for gpaCode 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * 	Getter for periodNumber 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}
	/**
	 *  @param periodNumber the periodNumber to set
	 * 	Setter for periodNumber 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	/**
	 * 	Getter for fromBillingDate 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public String getFromBillingDate() {
		return fromBillingDate;
	}
	/**
	 *  @param fromBillingDate the fromBillingDate to set
	 * 	Setter for fromBillingDate 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public void setFromBillingDate(String fromBillingDate) {
		this.fromBillingDate = fromBillingDate;
	}
	/**
	 * 	Getter for toBillingDate 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public String getToBillingDate() {
		return toBillingDate;
	}
	/**
	 *  @param toBillingDate the toBillingDate to set
	 * 	Setter for toBillingDate 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public void setToBillingDate(String toBillingDate) {
		this.toBillingDate = toBillingDate;
	}
	/**
	 * 	Getter for fileName 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 *  @param fileName the fileName to set
	 * 	Setter for fileName 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 	Getter for includeNewInvoice 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public boolean isIncludeNewInvoice() {
		return includeNewInvoice;
	}
	/**
	 *  @param includeNewInvoice the includeNewInvoice to set
	 * 	Setter for includeNewInvoice 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public void setIncludeNewInvoice(boolean includeNewInvoice) {
		this.includeNewInvoice = includeNewInvoice;
	}

}
