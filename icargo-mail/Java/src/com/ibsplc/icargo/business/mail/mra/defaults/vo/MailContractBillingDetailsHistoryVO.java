/*
 * MailContractBillingDetailsHistoryVO.java created on Mar 30, 2007
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
public class MailContractBillingDetailsHistoryVO extends AbstractVO {
	/**
	 * Company Code
	 */
	private String companyCode;

	/**
	 * Mail contract reference number
	 */
	private String contractReferenceNumber;

	/**
	 * Billing matrix code
	 */
	private String billingMatrixCode;

	/**
	 * Version number
	 */
	private String versionNumber;

	/**
	 * Operation flag
	 */
	private String operationFlag;

	/**
	 * @return Returns the billingMatrixCode.
	 */
	public String getBillingMatrixCode() {
		return billingMatrixCode;
	}

	/**
	 * @param billingMatrixCode
	 *            The billingMatrixCode to set.
	 */
	public void setBillingMatrixCode(String billingMatrixCode) {
		this.billingMatrixCode = billingMatrixCode;
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
