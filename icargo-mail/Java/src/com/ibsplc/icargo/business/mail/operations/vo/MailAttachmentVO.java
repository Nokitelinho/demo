/*
 * MailbagVO.java Created on OCT 06, 2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**@author A-5526
 * This VO can be used to store mail attachments 
 *
 */
public class MailAttachmentVO extends AbstractVO {

	private String fileName;

	private String attachmentStation;

	private LocalDate uploadDate;
	
	private String uploadUser;

	private LocalDate lastUpdateTime;

	private String lastUpdateUser;

	private String attachmentOpFlag;

	private int serialNumber;

	private byte[] fileData;

	private String companyCode;

	private long mailbagId;

	private String contentType;

	private String remarks;

	private String documentType;

	private String reference1;

	private int docSerialNumber;

	private String attachmentType;

	private long attachmentSerialNumber;
	
	private String reference2;



	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAttachmentStation() {
		return attachmentStation;
	}

	public void setAttachmentStation(String attachmentStation) {
		this.attachmentStation = attachmentStation;
	}

	public LocalDate getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}



	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	public String getAttachmentOpFlag() {
		return attachmentOpFlag;
	}

	public void setAttachmentOpFlag(String attachmentOpFlag) {
		this.attachmentOpFlag = attachmentOpFlag;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public long getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(long mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getReference1() {
		return reference1;
	}

	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}

	public int getDocSerialNumber() {
		return docSerialNumber;
	}

	public void setDocSerialNumber(int docSerialNumber) {
		this.docSerialNumber = docSerialNumber;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public long getAttachmentSerialNumber() {
		return attachmentSerialNumber;
	}

	public void setAttachmentSerialNumber(long attachmentSerialNumber) {
		this.attachmentSerialNumber = attachmentSerialNumber;
	}
	public String getReference2() {
		return reference2;
	}
	public void setReference2(String reference2) {
		this.reference2 = reference2;
	}
	
	

	
}