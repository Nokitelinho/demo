/*
 * SupportingDocumentFilterVO created on October 29, 2018 
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * Business vo class for Generate Supporting Documents filter.
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
public class SupportingDocumentFilterVO extends AbstractVO {
	
	public static final String ATTACHMENT_STATUS_ATTACHMENT_PRESENT = "AP";
	public static final String ATTACHMENT_STATUS_MISSING_ATTACHMENT = "MA";
	public static final String ATTACHMENT_STATUS_NO_ATTACHMENT = "NA";
	private String companyCode;
	private String classType;
	private String clearancePeriod;
	private boolean areNewInvoicesOnlyRequired;
	
	private int billedAirline;
	private String billingType;
	private String invoiceNumber;
	private int invoiceSerialNumber;		
	private int duplicateNumber;
	private int sequenceNumber;
	private int docOwnerId;
	private String masterDocumentNumber;
	private String memoNumber;
	private int documentSerialNumber;
	private String fileName;

	private String shipmentPrefix;
	
	private boolean isTDFRequired;
	
	
	/**
	 * @return the isTDFRequired
	 */
	public boolean isTDFRequired() {
		return isTDFRequired;
	}
	/**
	 * @param isTDFRequired the isTDFRequired to set
	 */
	public void setTDFRequired(boolean isTDFRequired) {
		this.isTDFRequired = isTDFRequired;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	 * @return the classType
	 */
	public String getClassType() {
		return classType;
	}
	/**
	 * @param classType the classType to set
	 */
	public void setClassType(String classType) {
		this.classType = classType;
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
	 * @return the areNewInvoicesOnlyRequired
	 */
	public boolean isAreNewInvoicesOnlyRequired() {
		return areNewInvoicesOnlyRequired;
	}
	/**
	 * @param areNewInvoicesOnlyRequired the areNewInvoicesOnlyRequired to set
	 */
	public void setAreNewInvoicesOnlyRequired(boolean areNewInvoicesOnlyRequired) {
		this.areNewInvoicesOnlyRequired = areNewInvoicesOnlyRequired;
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
	/***
	 * @author A-8061
	 * @return shipmentPrefix
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 *  @author A-5491
	 * @param shipmentPrefix
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	
	
		


}
