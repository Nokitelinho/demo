/*
 * MailInvoicProcessLogVO.java created on Jul 19, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class MailInvoicProcessLogVO extends AbstractVO {
	
	private String companyCode;
	
	private int processIdentifier;
	
	private String processName;
	
	private LocalDate startTime;
	
	private LocalDate endTime;
	
	private String processStatus;
	
	private String errorDescription;
	
	private String messageReferenceNumber;

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
	 * @return Returns the endTime.
	 */
	public LocalDate getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime The endTime to set.
	 */
	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
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
	 * @return Returns the messageReferenceNumber.
	 */
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
	 * @return Returns the processIdentifier.
	 */
	public int getProcessIdentifier() {
		return processIdentifier;
	}

	/**
	 * @param processIdentifier The processIdentifier to set.
	 */
	public void setProcessIdentifier(int processIdentifier) {
		this.processIdentifier = processIdentifier;
	}

	/**
	 * @return Returns the processName.
	 */
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
	public LocalDate getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime The startTime to set.
	 */
	public void setStartTime(LocalDate startTime) {
		this.startTime = startTime;
	}
	
	
	
}