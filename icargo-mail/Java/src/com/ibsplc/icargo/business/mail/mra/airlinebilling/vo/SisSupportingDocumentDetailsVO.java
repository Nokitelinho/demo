/*
 * SisSupportingDocumentDetailsVO created on October 29, 2018 
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * Business VO class for SIS-Supporting documents.
 * 
 * @author a-8061
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  October 29, 2018				A-8061 		 Created
 */
public class SisSupportingDocumentDetailsVO  extends AbstractVO {
	
		public static final String STATUS_BILLABLE = "B";		
		public static final String STATUS_BILLED = "D";
		public static final String ATTACHMENTS_EXISTS = "Y";
		public static final String ATTACHMENTS_NOT_EXISTS = "N";
		public static final String ATTACHMENT_INDICATOR_CHECKED = "Y";
		public static final String ATTACHMENT_INDICATOR_UNCHECKED = "N";
		
		public static final String BILLING_TYPE_PREPAID = "P";
		public static final String BILLING_TYPE_COLLECT = "C";
		public static final String BILLING_TYPE_REJECTION_MEMO = "R";
		public static final String BILLING_TYPE_BILLING_MEMO = "B";
		public static final String BILLING_TYPE_CREDIT_MEMO = "T";
		
	
		private String companyCode;
		private int billedAirline;
		private String interlineBillingType;
		private String clearancePeriod;
		private String billingType;
		private String invoiceNumber;
		private int invoiceSerialNumber;		
		private int duplicateNumber;
		private int sequenceNumber;
		private int docOwnerId;
		private String masterDocumentNumber;
		private String memoNumber;
		private int documentSerialNumber;
		private String filename;
		private byte[] fileData;
		private String carierOrigin;
		private String carierDestination;
		private String lastUpdatedUser;
		private LocalDate lastUpdatedTime;
		private String operationFlag;
		private String attachmentIndicator;//status of attachment indicator
		private String attachmentStatus;//set Y always and set N for the last attachment in memo
		private String status;//memo status
		private String billingMode;//set for billed memo only
		private boolean fromAttachment;
		private int serialNumber;//for invawb tables only	
		
		private int awbSerialNumber;
		
		private long mailSequenceNumber;
		
		
		
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
		 * @return the billedAirline
		 */
		public int getBilledAirline() {
			return billedAirline;
		}
		/**
		 * @param billedAirline the billedAirline to set
		 */
		public void setBilledAirline(int billedAirline) {
			this.billedAirline = billedAirline;
		}
		/**
		 * @return the interlineBillingType
		 */
		public String getInterlineBillingType() {
			return interlineBillingType;
		}
		/**
		 * @param interlineBillingType the interlineBillingType to set
		 */
		public void setInterlineBillingType(String interlineBillingType) {
			this.interlineBillingType = interlineBillingType;
		}
		/**
		 * @return the clearancePeriod
		 */
		public String getClearancePeriod() {
			return clearancePeriod;
		}
		/**
		 * @param clearancePeriod the clearancePeriod to set
		 */
		public void setClearancePeriod(String clearancePeriod) {
			this.clearancePeriod = clearancePeriod;
		}
		/**
		 * @return the billingType
		 */
		public String getBillingType() {
			return billingType;
		}
		/**
		 * @param billingType the billingType to set
		 */
		public void setBillingType(String billingType) {
			this.billingType = billingType;
		}
		/**
		 * @return the invoiceNumber
		 */
		public String getInvoiceNumber() {
			return invoiceNumber;
		}
		/**
		 * @param invoiceNumber the invoiceNumber to set
		 */
		public void setInvoiceNumber(String invoiceNumber) {
			this.invoiceNumber = invoiceNumber;
		}
		/**
		 * @return the invoiceSerialNumber
		 */
		public int getInvoiceSerialNumber() {
			return invoiceSerialNumber;
		}
		/**
		 * @param invoiceSerialNumber the invoiceSerialNumber to set
		 */
		public void setInvoiceSerialNumber(int invoiceSerialNumber) {
			this.invoiceSerialNumber = invoiceSerialNumber;
		}
		/**
		 * @return the duplicateNumber
		 */
		public int getDuplicateNumber() {
			return duplicateNumber;
		}
		/**
		 * @param duplicateNumber the duplicateNumber to set
		 */
		public void setDuplicateNumber(int duplicateNumber) {
			this.duplicateNumber = duplicateNumber;
		}
		/**
		 * @return the sequenceNumber
		 */
		public int getSequenceNumber() {
			return sequenceNumber;
		}
		/**
		 * @param sequenceNumber the sequenceNumber to set
		 */
		public void setSequenceNumber(int sequenceNumber) {
			this.sequenceNumber = sequenceNumber;
		}
		/**
		 * @return the docOwnerId
		 */
		public int getDocOwnerId() {
			return docOwnerId;
		}
		/**
		 * @param docOwnerId the docOwnerId to set
		 */
		public void setDocOwnerId(int docOwnerId) {
			this.docOwnerId = docOwnerId;
		}
		/**
		 * @return the masterDocumentNumber
		 */
		public String getMasterDocumentNumber() {
			return masterDocumentNumber;
		}
		/**
		 * @param masterDocumentNumber the masterDocumentNumber to set
		 */
		public void setMasterDocumentNumber(String masterDocumentNumber) {
			this.masterDocumentNumber = masterDocumentNumber;
		}
		/**
		 * @return the memoNumber
		 */
		public String getMemoNumber() {
			return memoNumber;
		}
		/**
		 * @param memoNumber the memoNumber to set
		 */
		public void setMemoNumber(String memoNumber) {
			this.memoNumber = memoNumber;
		}
		/**
		 * @return the documentSerialNumber
		 */
		public int getDocumentSerialNumber() {
			return documentSerialNumber;
		}
		/**
		 * @param documentSerialNumber the documentSerialNumber to set
		 */
		public void setDocumentSerialNumber(int documentSerialNumber) {
			this.documentSerialNumber = documentSerialNumber;
		}
		/**
		 * @return the filename
		 */
		public String getFilename() {
			return filename;
		}
		/**
		 * @param filename the filename to set
		 */
		public void setFilename(String filename) {
			this.filename = filename;
		}
		/**
		 * @return the fileData
		 */
		public byte[] getFileData() {
			return fileData;
		}
		/**
		 * @param fileData the fileData to set
		 */
		public void setFileData(byte[] fileData) {
			this.fileData = fileData;
		}
		/**
		 * @return the carierOrigin
		 */
		public String getCarierOrigin() {
			return carierOrigin;
		}
		/**
		 * @param carierOrigin the carierOrigin to set
		 */
		public void setCarierOrigin(String carierOrigin) {
			this.carierOrigin = carierOrigin;
		}
		/**
		 * @return the carierDestination
		 */
		public String getCarierDestination() {
			return carierDestination;
		}
		/**
		 * @param carierDestination the carierDestination to set
		 */
		public void setCarierDestination(String carierDestination) {
			this.carierDestination = carierDestination;
		}
		/**
		 * @return the lastUpdatedUser
		 */
		public String getLastUpdatedUser() {
			return lastUpdatedUser;
		}
		/**
		 * @param lastUpdatedUser the lastUpdatedUser to set
		 */
		public void setLastUpdatedUser(String lastUpdatedUser) {
			this.lastUpdatedUser = lastUpdatedUser;
		}
		/**
		 * @return the lastUpdatedTime
		 */
		public LocalDate getLastUpdatedTime() {
			return lastUpdatedTime;
		}
		/**
		 * @param lastUpdatedTime the lastUpdatedTime to set
		 */
		public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
			this.lastUpdatedTime = lastUpdatedTime;
		}
		/**
		 * @return the operationFlag
		 */
		public String getOperationFlag() {
			return operationFlag;
		}
		/**
		 * @param operationFlag the operationFlag to set
		 */
		public void setOperationFlag(String operationFlag) {
			this.operationFlag = operationFlag;
		}
		/**
		 * @return the attachmentIndicator
		 */
		public String getAttachmentIndicator() {
			return attachmentIndicator;
		}
		/**
		 * @param attachmentIndicator the attachmentIndicator to set
		 */
		public void setAttachmentIndicator(String attachmentIndicator) {
			this.attachmentIndicator = attachmentIndicator;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		
		/**
		 * @return the fromAttachment
		 */
		public boolean isFromAttachment() {
			return fromAttachment;
		}
		/**
		 * @param fromAttachment the fromAttachment to set
		 */
		public void setFromAttachment(boolean fromAttachment) {
			this.fromAttachment = fromAttachment;
		}
		/**
		 * @return the attachmentStatus
		 */
		public String getAttachmentStatus() {
			return attachmentStatus;
		}
		/**
		 * @param attachmentStatus the attachmentStatus to set
		 */
		public void setAttachmentStatus(String attachmentStatus) {
			this.attachmentStatus = attachmentStatus;
		}
		/**
		 * @return the billingMode
		 */
		public String getBillingMode() {
			return billingMode;
		}
		/**
		 * @param billingMode the billingMode to set
		 */
		public void setBillingMode(String billingMode) {
			this.billingMode = billingMode;
		}
		/**
		 * @return the serialNumber
		 */
		public int getSerialNumber() {
			return serialNumber;
		}
		/**
		 * @param serialNumber the serialNumber to set
		 */
		public void setSerialNumber(int serialNumber) {
			this.serialNumber = serialNumber;
		}
		/**
		 * @return the awbSerialNumber
		 */
		public int getAwbSerialNumber() {
			return awbSerialNumber;
		}
		/**
		 * @param awbSerialNumber the awbSerialNumber to set
		 */
		public void setAwbSerialNumber(int awbSerialNumber) {
			this.awbSerialNumber = awbSerialNumber;
		}
		/**
		 * 	Getter for mailSequenceNumber 
		 *	Added by : a-8061 on 01-Nov-2018
		 * 	Used for :
		 */
		public long getMailSequenceNumber() {
			return mailSequenceNumber;
		}
		/**
		 *  @param mailSequenceNumber the mailSequenceNumber to set
		 * 	Setter for mailSequenceNumber 
		 *	Added by : a-8061 on 01-Nov-2018
		 * 	Used for :
		 */
		public void setMailSequenceNumber(long mailSequenceNumber) {
			this.mailSequenceNumber = mailSequenceNumber;
		}
		

		


}
