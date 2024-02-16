/*
 * LoyaltyParameterVO.java Created on Dec 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;



import java.io.Serializable;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class LoyaltyParameterVO  extends AbstractVO implements Serializable{
	
	private String companyCode;
	private String loyaltyProgrammeCode;
	private String parameterCode;
	private String sequenceNumber;
	private String parameterValue;
	private String operationFlag;
	private boolean isChanged;
	
	/**
	 * @return Returns the isChanged.
	 */
	public boolean isChanged() {
		return isChanged;
	}
	/**
	 * @param isChanged The isChanged to set.
	 */
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}
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
	 * @return Returns the loyaltyProgrammeCode.
	 */
	public String getLoyaltyProgrammeCode() {
		return loyaltyProgrammeCode;
	}
	/**
	 * @param loyaltyProgrammeCode The loyaltyProgrammeCode to set.
	 */
	public void setLoyaltyProgrammeCode(String loyaltyProgrammeCode) {
		this.loyaltyProgrammeCode = loyaltyProgrammeCode;
	}
	/**
	 * @return Returns the parameterCode.
	 */
	public String getParameterCode() {
		return parameterCode;
	}
	/**
	 * @param parameterCode The parameterCode to set.
	 */
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	/**
	 * @return Returns the parameterValue.
	 */
	public String getParameterValue() {
		return parameterValue;
	}
	/**
	 * @param parameterValue The parameterValue to set.
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	/**
	 * @return Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
	
}
