/*
 * ListMailContractsForm.java Created on April 2, 2007
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1946
 *
 */
public class ListMailContractsForm extends ScreenModel{

private static final String BUNDLE = "listmailcontractsresources";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listmailcontracts";
	
	private String contractRefNo="";
	
	private String contractDate ="";	
	
	private String paCode ="";
	
	private String airlineCode ="";
	
	private String agreementType ="";
	
	private String agreementStatus ="";
	
	private String fromDate ="";
	
	private String toDate ="";
	
	private String []rowId;
	
	private String mailContractsCloseFlag="";
	
	private String[] contractReferenceNumber;
	
	private String[] versionNumber;
	
	private String versionNumberFilter="";	

	/**
	 * @return Returns the versionNumberFilter.
	 */
	public String getVersionNumberFilter() {
		return versionNumberFilter;
	}

	/**
	 * @param versionNumberFilter The versionNumberFilter to set.
	 */
	public void setVersionNumberFilter(String versionNumberFilter) {
		this.versionNumberFilter = versionNumberFilter;
	}

	/**
	 * @return Returns the versionNumber.
	 */
	public String[] getVersionNumber() {
		return versionNumber;
	}

	/**
	 * @param versionNumber The versionNumber to set.
	 */
	public void setVersionNumber(String[] versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * @return Returns the contractReferenceNumber.
	 */
	public String[] getContractReferenceNumber() {
		return contractReferenceNumber;
	}

	/**
	 * @param contractReferenceNumber The contractReferenceNumber to set.
	 */
	public void setContractReferenceNumber(String[] contractReferenceNumber) {
		this.contractReferenceNumber = contractReferenceNumber;
	}

	/**
	 * @return Returns the mailContractsCloseFlag.
	 */
	public String getMailContractsCloseFlag() {
		return mailContractsCloseFlag;
	}

	/**
	 * @param mailContractsCloseFlag The mailContractsCloseFlag to set.
	 */
	public void setMailContractsCloseFlag(String mailContractsCloseFlag) {
		this.mailContractsCloseFlag = mailContractsCloseFlag;
	}

	/**
	 * @return Returns the agreementStatus.
	 */
	public String getAgreementStatus() {
		return agreementStatus;
	}

	/**
	 * @param agreementStatus The agreementStatus to set.
	 */
	public void setAgreementStatus(String agreementStatus) {
		this.agreementStatus = agreementStatus;
	}

	/**
	 * @return Returns the agreementType.
	 */
	public String getAgreementType() {
		return agreementType;
	}

	/**
	 * @param agreementType The agreementType to set.
	 */
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the contractDate.
	 */
	public String getContractDate() {
		return contractDate;
	}

	/**
	 * @param contractDate The contractDate to set.
	 */
	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	/**
	 * @return Returns the contractRefNo.
	 */
	public String getContractRefNo() {
		return contractRefNo;
	}

	/**
	 * @param contractRefNo The contractRefNo to set.
	 */
	public void setContractRefNo(String contractRefNo) {
		this.contractRefNo = contractRefNo;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 *
	 */
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 *
	 */
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 *
	 */
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
}