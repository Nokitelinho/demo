/*
 * PeakMessageVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *  
 * @author A-3109
 * 
 */

public class PeakMessageVO extends AbstractVO {

	private String companyCode;
	
	private String stationCode;
	
	private String uploadUser;
	
	private String rawMessage;
	
	private String originalMessage;
	
	private LocalDate receiptOrSentDate;
	
	private String fileName;
	
	private String operationMode;
	
	private String pol;
	
	private String pou;

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
	 * @return Returns the originalMessage.
	 */
	public String getOriginalMessage() {
		return originalMessage;
	}

	/**
	 * @param originalMessage The originalMessage to set.
	 */
	public void setOriginalMessage(String originalMessage) {
		this.originalMessage = originalMessage;
	}

	/**
	 * @return Returns the rawMessage.
	 */
	public String getRawMessage() {
		return rawMessage;
	}

	/**
	 * @param rawMessage The rawMessage to set.
	 */
	public void setRawMessage(String rawMessage) {
		this.rawMessage = rawMessage;
	}

	/**
	 * @return Returns the receiptOrSentDate.
	 */
	public LocalDate getReceiptOrSentDate() {
		return receiptOrSentDate;
	}

	/**
	 * @param receiptOrSentDate The receiptOrSentDate to set.
	 */
	public void setReceiptOrSentDate(LocalDate receiptOrSentDate) {
		this.receiptOrSentDate = receiptOrSentDate;
	}

	/**
	 * @return Returns the stationCode.
	 */
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	/**
	 * @return Returns the uploadUser.
	 */
	public String getUploadUser() {
		return uploadUser;
	}

	/**
	 * @param uploadUser The uploadUser to set.
	 */
	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return Returns the operationMode.
	 */
	public String getOperationMode() {
		return operationMode;
	}

	/**
	 * @param operationMode The operationMode to set.
	 */
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	
}
