/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPAInvoiceVO.java
 *
 *	Created by	:	A-4809
 *	Created on	:	10-Apr-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPAInvoiceVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	10-Apr-2021	:	Draft
 */
public class GPAInvoiceVO extends AbstractVO{
	
	private String companyCode;
	private String periodNumber;
    private LocalDate billingPeriodFrom;
    private LocalDate billingPeriodTo;
    private String gpaCode;
    private String invoiceNumber;
    private String branchOffice;
    private String sequenceNumber;
    private String fileFormat;
    private String interfacedFileName;
    private int sequenceNumberIncrBy;
	/**
	 * 	Getter for companyCode 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for periodNumber 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}
	/**
	 *  @param periodNumber the periodNumber to set
	 * 	Setter for periodNumber 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	/**
	 * 	Getter for billingPeriodFrom 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public LocalDate getBillingPeriodFrom() {
		return billingPeriodFrom;
	}
	/**
	 *  @param billingPeriodFrom the billingPeriodFrom to set
	 * 	Setter for billingPeriodFrom 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public void setBillingPeriodFrom(LocalDate billingPeriodFrom) {
		this.billingPeriodFrom = billingPeriodFrom;
	}
	/**
	 * 	Getter for billingPeriodTo 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public LocalDate getBillingPeriodTo() {
		return billingPeriodTo;
	}
	/**
	 *  @param billingPeriodTo the billingPeriodTo to set
	 * 	Setter for billingPeriodTo 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public void setBillingPeriodTo(LocalDate billingPeriodTo) {
		this.billingPeriodTo = billingPeriodTo;
	}
	/**
	 * 	Getter for gpaCode 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 *  @param gpaCode the gpaCode to set
	 * 	Setter for gpaCode 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * 	Getter for invoiceNumber 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 *  @param invoiceNumber the invoiceNumber to set
	 * 	Setter for invoiceNumber 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * 	Getter for branchOffice 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public String getBranchOffice() {
		return branchOffice;
	}
	/**
	 *  @param branchOffice the branchOffice to set
	 * 	Setter for branchOffice 
	 *	Added by : A-4809 on 10-Apr-2021
	 * 	Used for :
	 */
	public void setBranchOffice(String branchOffice) {
		this.branchOffice = branchOffice;
	}
	
	/**
	 * 	Getter for sequenceNumber 
	 *	Added by : A-4809 on 16-Apr-2021
	 * 	Used for :
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 *  @param sequenceNumber the sequenceNumber to set
	 * 	Setter for sequenceNumber 
	 *	Added by : A-4809 on 16-Apr-2021
	 * 	Used for :
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * 	Getter for fileFormat 
	 *	Added by : A-4809 on 15-Apr-2021
	 * 	Used for :
	 */
	public String getFileFormat() {
		return fileFormat;
	}
	/**
	 *  @param fileFormat the fileFormat to set
	 * 	Setter for fileFormat 
	 *	Added by : A-4809 on 15-Apr-2021
	 * 	Used for :
	 */
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	/**
	 * 	Getter for interfacedFileName 
	 *	Added by : A-8061 on 16-Jun-2021
	 * 	Used for :
	 */
	public String getInterfacedFileName() {
		return interfacedFileName;
	}
	/**
	 *  @param interfacedFileName the interfacedFileName to set
	 * 	Setter for interfacedFileName 
	 *	Added by : A-8061 on 16-Jun-2021
	 * 	Used for :
	 */
	public void setInterfacedFileName(String interfacedFileName) {
		this.interfacedFileName = interfacedFileName;
	}
	/**
	 * 	Getter for sequenceNumberIncrBy 
	 *	Added by : A-8061 on 17-Jun-2021
	 * 	Used for :
	 */
	public int getSequenceNumberIncrBy() {
		return sequenceNumberIncrBy;
	}
	/**
	 *  @param sequenceNumberIncrBy the sequenceNumberIncrBy to set
	 * 	Setter for sequenceNumberIncrBy 
	 *	Added by : A-8061 on 17-Jun-2021
	 * 	Used for :
	 */
	public void setSequenceNumberIncrBy(int sequenceNumberIncrBy) {
		this.sequenceNumberIncrBy = sequenceNumberIncrBy;
	}

    
}
