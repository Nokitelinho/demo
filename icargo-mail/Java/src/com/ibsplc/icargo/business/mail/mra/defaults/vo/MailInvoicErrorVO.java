/*
 * MailInvoicErrorVO.java created on Jul 19, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.sql.Blob;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class MailInvoicErrorVO extends AbstractVO {
	private String companyCode;
	
	private int invoicErrorIdentifier;
	
	private Blob invoicMessage;
	
	private String errorDescription;
	
	private int lineNumber;
	
	private String errorStatus;
	
	private String poaCode;
	
	private LocalDate timeStamp;
	
	private String testIndicator;
	
	private String sendIndicator;
	
	private String invoiceKey;

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
	 * @return Returns the errorDescription.
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription The errorDescription to set.
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @return Returns the errorStatus.
	 */
	public String getErrorStatus() {
		return errorStatus;
	}

	/**
	 * @param errorStatus The errorStatus to set.
	 */
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}

	/**
	 * @return Returns the invoiceKey.
	 */
	public String getInvoiceKey() {
		return invoiceKey;
	}

	/**
	 * @param invoiceKey The invoiceKey to set.
	 */
	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}

	/**
	 * @return Returns the invoicErrorIdentifier.
	 */
	public int getInvoicErrorIdentifier() {
		return invoicErrorIdentifier;
	}

	/**
	 * @param invoicErrorIdentifier The invoicErrorIdentifier to set.
	 */
	public void setInvoicErrorIdentifier(int invoicErrorIdentifier) {
		this.invoicErrorIdentifier = invoicErrorIdentifier;
	}

	/**
	 * @return Returns the invoicMessage.
	 */
	public Blob getInvoicMessage() {
		return invoicMessage;
	}

	/**
	 * @param invoicMessage The invoicMessage to set.
	 */
	public void setInvoicMessage(Blob invoicMessage) {
		this.invoicMessage = invoicMessage;
	}

	/**
	 * @return Returns the lineNumber.
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber The lineNumber to set.
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the sendIndicator.
	 */
	public String getSendIndicator() {
		return sendIndicator;
	}

	/**
	 * @param sendIndicator The sendIndicator to set.
	 */
	public void setSendIndicator(String sendIndicator) {
		this.sendIndicator = sendIndicator;
	}

	/**
	 * @return Returns the testIndicator.
	 */
	public String getTestIndicator() {
		return testIndicator;
	}

	/**
	 * @param testIndicator The testIndicator to set.
	 */
	public void setTestIndicator(String testIndicator) {
		this.testIndicator = testIndicator;
	}

	/**
	 * @return Returns the timeStamp.
	 */
	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp The timeStamp to set.
	 */
	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}