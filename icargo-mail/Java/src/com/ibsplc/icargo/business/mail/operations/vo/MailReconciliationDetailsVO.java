/*
 * MailReconciliationDetailsVO.java Created on Jun 30, 2016
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-3109
 * 
 */
public class MailReconciliationDetailsVO extends AbstractVO {

	private String companyCode;
	private String mailbagId;
	private String consignmentDocumentNumber;
	private String carditFlight;
	private String originalFlight; 
	private String eventPortCode; 
	private String eventCode; 
	private LocalDate operationTime;
	private LocalDate resditGenerationTime;
	private String controlReferenceNumber;
	private boolean isCarditExist; 
	private String carditOrigin; 
	private String carditDestination; 
	private String resditFileName;
	private String msgMissing;
	private String msgType;
	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return msgType;
	}
	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
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
	 * @return the mailbagId
	 */
	public String getMailbagId() {
		return mailbagId;
	}
	/**
	 * @param mailbagId the mailbagId to set
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	/**
	 * @return the consignmentDocumentNumber
	 */
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}
	/**
	 * @param consignmentDocumentNumber the consignmentDocumentNumber to set
	 */
	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}
	/**
	 * @return the carditFlight
	 */
	public String getCarditFlight() {
		return carditFlight;
	}
	/**
	 * @param carditFlight the carditFlight to set
	 */
	public void setCarditFlight(String carditFlight) {
		this.carditFlight = carditFlight;
	}
	/**
	 * @return the originalFlight
	 */
	public String getOriginalFlight() {
		return originalFlight;
	}
	/**
	 * @param originalFlight the originalFlight to set
	 */
	public void setOriginalFlight(String originalFlight) {
		this.originalFlight = originalFlight;
	}
	/**
	 * @return the eventPortCode
	 */
	public String getEventPortCode() {
		return eventPortCode;
	}
	/**
	 * @param eventPortCode the eventPortCode to set
	 */
	public void setEventPortCode(String eventPortCode) {
		this.eventPortCode = eventPortCode;
	}
	/**
	 * @return the eventCode
	 */
	public String getEventCode() {
		return eventCode;
	}
	/**
	 * @param eventCode the eventCode to set
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	/**
	 * @return the operationTime
	 */
	public LocalDate getOperationTime() {
		return operationTime;
	}
	/**
	 * @param operationTime the operationTime to set
	 */
	public void setOperationTime(LocalDate operationTime) {
		this.operationTime = operationTime;
	}
	/**
	 * @return the resditGenerationTime
	 */
	public LocalDate getResditGenerationTime() {
		return resditGenerationTime;
	}
	/**
	 * @param resditGenerationTime the resditGenerationTime to set
	 */
	public void setResditGenerationTime(LocalDate resditGenerationTime) {
		this.resditGenerationTime = resditGenerationTime;
	}
	/**
	 * @return the controlReferenceNumber
	 */
	public String getControlReferenceNumber() {
		return controlReferenceNumber;
	}
	/**
	 * @param controlReferenceNumber the controlReferenceNumber to set
	 */
	public void setControlReferenceNumber(String controlReferenceNumber) {
		this.controlReferenceNumber = controlReferenceNumber;
	}
	/**
	 * @return the isCarditExist
	 */
	public boolean isCarditExist() {
		return isCarditExist;
	}
	/**
	 * @param isCarditExist the isCarditExist to set
	 */
	public void setCarditExist(boolean isCarditExist) {
		this.isCarditExist = isCarditExist;
	}
	/**
	 * @return the carditOrigin
	 */
	public String getCarditOrigin() {
		return carditOrigin;
	}
	/**
	 * @param carditOrigin the carditOrigin to set
	 */
	public void setCarditOrigin(String carditOrigin) {
		this.carditOrigin = carditOrigin;
	}
	/**
	 * @return the carditDestination
	 */
	public String getCarditDestination() {
		return carditDestination;
	}
	/**
	 * @param carditDestination the carditDestination to set
	 */
	public void setCarditDestination(String carditDestination) {
		this.carditDestination = carditDestination;
	}
	/**
	 * @return the resditFileName
	 */
	public String getResditFileName() {
		return resditFileName;
	}
	/**
	 * @param resditFileName the resditFileName to set
	 */
	public void setResditFileName(String resditFileName) {
		this.resditFileName = resditFileName;
	}
	/**
	 * @return the msgMissing
	 */
	public String getMsgMissing() {
		return msgMissing;
	}
	/**
	 * @param msgMissing the msgMissing to set
	 */
	public void setMsgMissing(String msgMissing) {
		this.msgMissing = msgMissing;
	}
	
	
}
