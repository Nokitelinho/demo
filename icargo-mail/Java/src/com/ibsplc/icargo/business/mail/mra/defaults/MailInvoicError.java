/*
 * MailInvoicError.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.sql.Blob;
import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicErrorVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVERR")
@Staleable
@Deprecated
public class MailInvoicError {
	
	private MailInvoicErrorPK mailInvoicErrorPK;
	
	private Blob invoicMessage;
	
	private String errorDescription;
	
	private int lineNumber;
	
	private String errorStatus;
	
	private String poaCode;
	
	private Calendar timeStamp;
	
	private String testIndicator;
	
	private String sendIndicator;
	
	private String invoiceKey;
	
	/**
	 * 
	 */
	public MailInvoicError(){
		
	}
	
	/**
	 * @param errorVO
	 * @throws SystemException
	 */
	public MailInvoicError(MailInvoicErrorVO errorVO)
	throws SystemException{
		MailInvoicErrorPK errorPK= new MailInvoicErrorPK();
		errorPK.setCompanyCode(errorVO.getCompanyCode());
		errorPK.setInvoicErrorIdentifier(errorVO.getInvoicErrorIdentifier());
		
		this.setMailInvoicErrorPK(errorPK);
		
		populateAttributes(errorVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
	}


	/**
	 * @return Returns the errorDescription.
	 */
	@Column(name="ERRDSC")
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
	@Column(name="ERRSTA")
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
	@Column(name="INVKEY")
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
	 * @return Returns the invoicMessage.
	 */
	@Lob
	@Column(name="INVMSG")
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
	@Column(name="LINNUM")
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
	 * @return Returns the mailInvoicErrorPK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="invoicErrorIdentifier", column=@Column(name="INVERRIDR"))}
		)
	public MailInvoicErrorPK getMailInvoicErrorPK() {
		return mailInvoicErrorPK;
	}

	/**
	 * @param mailInvoicErrorPK The mailInvoicErrorPK to set.
	 */
	public void setMailInvoicErrorPK(MailInvoicErrorPK mailInvoicErrorPK) {
		this.mailInvoicErrorPK = mailInvoicErrorPK;
	}

	/**
	 * @return Returns the poaCode.
	 */
	@Column(name="POACOD")
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
	@Column(name="SNDIND")
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
	@Column(name="TSTIND")
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
	@Column(name="TIMSMP")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp The timeStamp to set.
	 */
	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	/**
	 * @param errorVO
	 */
	private void populateAttributes(MailInvoicErrorVO errorVO){
		this.setInvoicMessage(errorVO.getInvoicMessage());
		this.setErrorDescription(errorVO.getErrorDescription());
		this.setLineNumber(errorVO.getLineNumber());
		this.setErrorStatus(errorVO.getErrorStatus());
		this.setPoaCode(errorVO.getPoaCode());
		this.setTimeStamp(errorVO.getTimeStamp());
		this.setTestIndicator(errorVO.getTestIndicator());
		this.setSendIndicator(errorVO.getSendIndicator());
		this.setInvoiceKey(errorVO.getInvoiceKey());
		
	}
	
}