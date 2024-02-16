/*
 * MailContractDetailsHistoryVO.java created on Mar 30, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2518
 * 
 */
public class MailContractDetailsHistoryVO extends AbstractVO {
	/**
	 * Company Code
	 */
	private String companyCode;

	/**
	 * Mail contract reference number
	 */
	private String contractReferenceNumber;

	/**
	 * Version number;
	 */
	private String versionNumber;

	/**
	 * Serial number
	 */
	private int serialNumber;

	/**
	 * Origin city code
	 */
	private String originCode;

	/**
	 * Destination city code
	 */
	private String destinationCode;

	/**
	 * Service level activity one - Acceptance to departure
	 */
	private String acceptanceToDeparture;

	/**
	 * Service level activity one - Arrival to delivery
	 */
	private String arrivalToDelivery;

	/**
	 * Remarks
	 */
	private String remarks;

	/**
	 * Operation flag
	 */
	private String operationFlag;

	/**
	 * @return Returns the acceptanceToDeparture.
	 */
	public String getAcceptanceToDeparture() {
		return acceptanceToDeparture;
	}

	/**
	 * @param acceptanceToDeparture
	 *            The acceptanceToDeparture to set.
	 */
	public void setAcceptanceToDeparture(String acceptanceToDeparture) {
		this.acceptanceToDeparture = acceptanceToDeparture;
	}

	/**
	 * @return Returns the arrivalToDelivery.
	 */
	public String getArrivalToDelivery() {
		return arrivalToDelivery;
	}

	/**
	 * @param arrivalToDelivery
	 *            The arrivalToDelivery to set.
	 */
	public void setArrivalToDelivery(String arrivalToDelivery) {
		this.arrivalToDelivery = arrivalToDelivery;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the contractReferenceNumber.
	 */
	public String getContractReferenceNumber() {
		return contractReferenceNumber;
	}

	/**
	 * @param contractReferenceNumber
	 *            The contractReferenceNumber to set.
	 */
	public void setContractReferenceNumber(String contractReferenceNumber) {
		this.contractReferenceNumber = contractReferenceNumber;
	}

	/**
	 * @return Returns the destinationCode.
	 */
	public String getDestinationCode() {
		return destinationCode;
	}

	/**
	 * @param destinationCode
	 *            The destinationCode to set.
	 */
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the originCode.
	 */
	public String getOriginCode() {
		return originCode;
	}

	/**
	 * @param originCode
	 *            The originCode to set.
	 */
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the serialNumber.
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber
	 *            The serialNumber to set.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return Returns the versionNumber.
	 */
	public String getVersionNumber() {
		return versionNumber;
	}

	/**
	 * @param versionNumber
	 *            The versionNumber to set.
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

}
