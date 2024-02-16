/*
 * MaintainMailContractsForm.java Created on Mar 30, 2007
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;


import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-2408
 *
 */
public class MaintainMailContractsForm extends ScreenModel{

private static final String BUNDLE = "maintainmailcontracts";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.defaults.maintainmailcontracts";

	private String operationFlag;
	
	private String contractRefNumber;
	
	private String description;
	
	private String paCode;
	
	private String airlineCode;
	
	private String billingMatrix;
	
	private String fromDate;
	
	private String toDate;
	
	private String screenStatus;
	
	private String version;
	
	private String agreementType;
	
	private String agreementStatus;
	
	private String[] originCode;
	
	private String[] destinationCode;
	
	private String[] acceptanceToDeparture;
	
	private String[] arrivalToDelivery;
	
	private String[] remarks;
	
	private String[] check;
	
	private String agreementStatusDisplay;
	
	private String mailContractsCloseFlag="";
	
	private int airlineidentifier;

	
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
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
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
	 * @return Returns the billingMatrix.
	 */
	public String getBillingMatrix() {
		return billingMatrix;
	}

	/**
	 * @param billingMatrix The billingMatrix to set.
	 */
	public void setBillingMatrix(String billingMatrix) {
		this.billingMatrix = billingMatrix;
	}

	/**
	 * @return Returns the contractRefNumber.
	 */
	public String getContractRefNumber() {
		return contractRefNumber;
	}

	/**
	 * @param contractRefNumber The contractRefNumber to set.
	 */
	public void setContractRefNumber(String contractRefNumber) {
		this.contractRefNumber = contractRefNumber;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return Returns the screenStatus.
	 */
	public String getScreenStatus() {
		return screenStatus;
	}

	/**
	 * @param screenStatus The screenStatus to set.
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
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
	 * @return Returns the version.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version The version to set.
	 */
	public void setVersion(String version) {
		this.version = version;
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
	 * @return Returns the acceptanceToDeparture.
	 */
	public String[] getAcceptanceToDeparture() {
		return acceptanceToDeparture;
	}

	/**
	 * @param acceptanceToDeparture The acceptanceToDeparture to set.
	 */
	public void setAcceptanceToDeparture(String[] acceptanceToDeparture) {
		this.acceptanceToDeparture = acceptanceToDeparture;
	}

	/**
	 * @return Returns the arrivalToDelivery.
	 */
	public String[] getArrivalToDelivery() {
		return arrivalToDelivery;
	}

	/**
	 * @param arrivalToDelivery The arrivalToDelivery to set.
	 */
	public void setArrivalToDelivery(String[] arrivalToDelivery) {
		this.arrivalToDelivery = arrivalToDelivery;
	}

	/**
	 * @return Returns the destinationCode.
	 */
	public String[] getDestinationCode() {
		return destinationCode;
	}

	/**
	 * @param destinationCode The destinationCode to set.
	 */
	public void setDestinationCode(String[] destinationCode) {
		this.destinationCode = destinationCode;
	}

	/**
	 * @return Returns the originCode.
	 */
	public String[] getOriginCode() {
		return originCode;
	}

	/**
	 * @param originCode The originCode to set.
	 */
	public void setOriginCode(String[] originCode) {
		this.originCode = originCode;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the check.
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * @param check The check to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * @return Returns the agreementStatusDisplay.
	 */
	public String getAgreementStatusDisplay() {
		return agreementStatusDisplay;
	}

	/**
	 * @param agreementStatusDisplay The agreementStatusDisplay to set.
	 */
	public void setAgreementStatusDisplay(String agreementStatusDisplay) {
		this.agreementStatusDisplay = agreementStatusDisplay;
	}

	/**
	 * @return Returns the airlineidentifier.
	 */
	public int getAirlineidentifier() {
		return airlineidentifier;
	}

	/**
	 * @param airlineidentifier The airlineidentifier to set.
	 */
	public void setAirlineidentifier(int airlineidentifier) {
		this.airlineidentifier = airlineidentifier;
	}

	
	

}
