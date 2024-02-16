/*
 * MailInvoicProcessLog.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicProcessLogVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVPRSLOG")
@Staleable
@Deprecated
public class MailInvoicProcessLog {
	
	
	private MailInvoicProcessLogPK mailInvoicProcessLogPK;
	
	private String processName;
	
	private Calendar startTime;
	
	private Calendar endTime;
	
	private String processStatus;
	
	private String errorDescription;
	
	private String messageReferenceNumber;
	
	/**
	 * 
	 */
	public MailInvoicProcessLog(){
		
	}
	/**
	 * @param processVO
	 * @throws SystemException
	 */
	public MailInvoicProcessLog(MailInvoicProcessLogVO processVO)
	throws SystemException{
		MailInvoicProcessLogPK processPK= new MailInvoicProcessLogPK();
		
		processPK.setCompanyCode(processVO.getCompanyCode());
		processPK.setProcessIdentifier(processVO.getProcessIdentifier());
		
		this.setMailInvoicProcessLogPK(processPK);
		populateAttributes(processVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
	}
	/**
	 * @return Returns the endTime.
	 */
	@Column(name="ENDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime The endTime to set.
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
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
	 * @return Returns the mailInvoicProcessLogPK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="processIdentifier", column=@Column(name="PRSIDR"))}
		)
	public MailInvoicProcessLogPK getMailInvoicProcessLogPK() {
		return mailInvoicProcessLogPK;
	}

	/**
	 * @param mailInvoicProcessLogPK The mailInvoicProcessLogPK to set.
	 */
	public void setMailInvoicProcessLogPK(
			MailInvoicProcessLogPK mailInvoicProcessLogPK) {
		this.mailInvoicProcessLogPK = mailInvoicProcessLogPK;
	}

	/**
	 * @return Returns the messageReferenceNumber.
	 */
	@Column(name="MSGREF")
	public String getMessageReferenceNumber() {
		return messageReferenceNumber;
	}

	/**
	 * @param messageReferenceNumber The messageReferenceNumber to set.
	 */
	public void setMessageReferenceNumber(String messageReferenceNumber) {
		this.messageReferenceNumber = messageReferenceNumber;
	}

	/**
	 * @return Returns the processName.
	 */
	@Column(name="PRSNAM")
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName The processName to set.
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @return Returns the processStatus.
	 */
	@Column(name="PRSSTA")
	public String getProcessStatus() {
		return processStatus;
	}

	/**
	 * @param processStatus The processStatus to set.
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * @return Returns the startTime.
	 */
	@Column(name="TIMSMP")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime The startTime to set.
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	/**
	 * @param processVO
	 */
	private void populateAttributes(MailInvoicProcessLogVO processVO){
		this.setProcessName(processVO.getProcessName());
		this.setStartTime(processVO.getStartTime());
		this.setEndTime(processVO.getEndTime());
		this.setProcessStatus(processVO.getProcessStatus());
		this.setErrorDescription(processVO.getErrorDescription());
		this.setMessageReferenceNumber(processVO.getMessageReferenceNumber());
	}
}