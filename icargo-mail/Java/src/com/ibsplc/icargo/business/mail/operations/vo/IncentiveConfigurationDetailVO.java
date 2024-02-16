/*
 * IncentiveConfigurationDetailVO.java Created on SEP 10, 2018
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
 * @author A-6986
 *
 */
public class IncentiveConfigurationDetailVO extends AbstractVO{

	private String companyCode;
	private int incentiveSerialNumber;
	private String sequenceNumber;
	/*private String parameterType;
	private String parameterCode;
	private String parameterValue;*/
	private String disIncParameterType;
	private String disIncParameterCode;
	private String disIncParameterValue;
	private String incParameterType;
	private String incParameterCode;
	private String incParameterValue;
	private String excludeFlag;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	
	private String disIncSrvParameterType;
	private String disIncSrvParameterCode;
	private String disIncSrvParameterValue;
	private String srvExcludeFlag;
	
	private String disIncNonSrvParameterType;
	private String disIncNonSrvParameterCode;
	private String disIncNonSrvParameterValue;
	private String nonSrvExcludeFlag;
	
	private String disIncBothSrvParameterType;
	private String disIncBothSrvParameterCode;
	private String disIncBothSrvParameterValue;
	private String bothSrvExcludeFlag;
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
	 * @return the incentiveSerialNumber
	 */
	public int getIncentiveSerialNumber() {
		return incentiveSerialNumber;
	}
	/**
	 * @param incentiveSerialNumber the incentiveSerialNumber to set
	 */
	public void setIncentiveSerialNumber(int incentiveSerialNumber) {
		this.incentiveSerialNumber = incentiveSerialNumber;
	}
	/**
	 * @return the sequenceNumber
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return the disIncparameterType
	 */
	public String getDisIncParameterType() {
		return disIncParameterType;
	}
	/**
	 * @param disIncparameterType the disIncparameterType to set
	 */
	public void setDisIncParameterType(String disIncParameterType) {
		this.disIncParameterType = disIncParameterType;
	}
	/**
	 * @return the disIncParameterCode
	 */
	public String getDisIncParameterCode() {
		return disIncParameterCode;
	}
	/**
	 * @param disIncParameterCode the disIncParameterCode to set
	 */
	public void setDisIncParameterCode(String disIncParameterCode) {
		this.disIncParameterCode = disIncParameterCode;
	}
	/**
	 * @return the disIncParameterValue
	 */
	public String getDisIncParameterValue() {
		return disIncParameterValue;
	}
	/**
	 * @param disIncParameterValue the disIncParameterValue to set
	 */
	public void setDisIncParameterValue(String disIncParameterValue) {
		this.disIncParameterValue = disIncParameterValue;
	}
	/**
	 * @return the incParameterType
	 */
	public String getIncParameterType() {
		return incParameterType;
	}
	/**
	 * @param incParameterType the incParameterType to set
	 */
	public void setIncParameterType(String incParameterType) {
		this.incParameterType = incParameterType;
	}
	/**
	 * @return the incParameterCode
	 */
	public String getIncParameterCode() {
		return incParameterCode;
	}
	/**
	 * @param incParameterCode the incParameterCode to set
	 */
	public void setIncParameterCode(String incParameterCode) {
		this.incParameterCode = incParameterCode;
	}
	/**
	 * @return the incParameterValue
	 */
	public String getIncParameterValue() {
		return incParameterValue;
	}
	/**
	 * @param incParameterValue the incParameterValue to set
	 */
	public void setIncParameterValue(String incParameterValue) {
		this.incParameterValue = incParameterValue;
	}
	/**
	 * @return the disIncSrvParameterType
	 */
	public String getDisIncSrvParameterType() {
		return disIncSrvParameterType;
	}
	/**
	 * @param disIncSrvParameterType the disIncSrvParameterType to set
	 */
	public void setDisIncSrvParameterType(String disIncSrvParameterType) {
		this.disIncSrvParameterType = disIncSrvParameterType;
	}
	/**
	 * @return the disIncSrvParameterCode
	 */
	public String getDisIncSrvParameterCode() {
		return disIncSrvParameterCode;
	}
	/**
	 * @param disIncSrvParameterCode the disIncSrvParameterCode to set
	 */
	public void setDisIncSrvParameterCode(String disIncSrvParameterCode) {
		this.disIncSrvParameterCode = disIncSrvParameterCode;
	}
	/**
	 * @return the disIncSrvParameterValue
	 */
	public String getDisIncSrvParameterValue() {
		return disIncSrvParameterValue;
	}
	/**
	 * @param disIncSrvParameterValue the disIncSrvParameterValue to set
	 */
	public void setDisIncSrvParameterValue(String disIncSrvParameterValue) {
		this.disIncSrvParameterValue = disIncSrvParameterValue;
	}
	/**
	 * @return the disIncNonSrvParameterType
	 */
	public String getDisIncNonSrvParameterType() {
		return disIncNonSrvParameterType;
	}
	/**
	 * @param disIncNonSrvParameterType the disIncNonSrvParameterType to set
	 */
	public void setDisIncNonSrvParameterType(String disIncNonSrvParameterType) {
		this.disIncNonSrvParameterType = disIncNonSrvParameterType;
	}
	/**
	 * @return the disIncNonSrvParameterCode
	 */
	public String getDisIncNonSrvParameterCode() {
		return disIncNonSrvParameterCode;
	}
	/**
	 * @param disIncNonSrvParameterCode the disIncNonSrvParameterCode to set
	 */
	public void setDisIncNonSrvParameterCode(String disIncNonSrvParameterCode) {
		this.disIncNonSrvParameterCode = disIncNonSrvParameterCode;
	}
	/**
	 * @return the disIncNonSrvParameterValue
	 */
	public String getDisIncNonSrvParameterValue() {
		return disIncNonSrvParameterValue;
	}
	/**
	 * @param disIncNonSrvParameterValue the disIncNonSrvParameterValue to set
	 */
	public void setDisIncNonSrvParameterValue(String disIncNonSrvParameterValue) {
		this.disIncNonSrvParameterValue = disIncNonSrvParameterValue;
	}
	/**
	 * @return the disIncBothSrvParameterType
	 */
	public String getDisIncBothSrvParameterType() {
		return disIncBothSrvParameterType;
	}
	/**
	 * @param disIncBothSrvParameterType the disIncBothSrvParameterType to set
	 */
	public void setDisIncBothSrvParameterType(String disIncBothSrvParameterType) {
		this.disIncBothSrvParameterType = disIncBothSrvParameterType;
	}
	/**
	 * @return the disIncBothSrvParameterCode
	 */
	public String getDisIncBothSrvParameterCode() {
		return disIncBothSrvParameterCode;
	}
	/**
	 * @param disIncBothSrvParameterCode the disIncBothSrvParameterCode to set
	 */
	public void setDisIncBothSrvParameterCode(String disIncBothSrvParameterCode) {
		this.disIncBothSrvParameterCode = disIncBothSrvParameterCode;
	}
	/**
	 * @return the disIncBothSrvParameterValue
	 */
	public String getDisIncBothSrvParameterValue() {
		return disIncBothSrvParameterValue;
	}
	/**
	 * @param disIncBothSrvParameterValue the disIncBothSrvParameterValue to set
	 */
	public void setDisIncBothSrvParameterValue(String disIncBothSrvParameterValue) {
		this.disIncBothSrvParameterValue = disIncBothSrvParameterValue;
	}
	/**
	 * @return the parameterType
	 */
/*	public String getParameterType() {
		return parameterType;
	}*/
	/**
	 * @param parameterType the parameterType to set
	 */
	/*public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}*/
	/**
	 * @return the parameterCode
	 */
	/*public String getParameterCode() {
		return parameterCode;
	}*/
	/**
	 * @param parameterCode the parameterCode to set
	 */
	/*public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}*/
	/**
	 * @return the parameterValue
	 */
	/*public String getParameterValue() {
		return parameterValue;
	}*/
	/**
	 * @param parameterValue the parameterValue to set
	 */
	/*public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}*/
	/**
	 * @return the excludeFlag
	 */
	public String getExcludeFlag() {
		return excludeFlag;
	}
	/**
	 * @param excludeFlag the excludeFlag to set
	 */
	public void setExcludeFlag(String excludeFlag) {
		this.excludeFlag = excludeFlag;
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
	 * @return the srvExcludeFlag
	 */
	public String getSrvExcludeFlag() {
		return srvExcludeFlag;
	}
	/**
	 * @param srvExcludeFlag the srvExcludeFlag to set
	 */
	public void setSrvExcludeFlag(String srvExcludeFlag) {
		this.srvExcludeFlag = srvExcludeFlag;
	}
	/**
	 * @return the nonSrvExcludeFlag
	 */
	public String getNonSrvExcludeFlag() {
		return nonSrvExcludeFlag;
	}
	/**
	 * @param nonSrvExcludeFlag the nonSrvExcludeFlag to set
	 */
	public void setNonSrvExcludeFlag(String nonSrvExcludeFlag) {
		this.nonSrvExcludeFlag = nonSrvExcludeFlag;
	}
	/**
	 * @return the bothSrvExcludeFlag
	 */
	public String getBothSrvExcludeFlag() {
		return bothSrvExcludeFlag;
	}
	/**
	 * @param bothSrvExcludeFlag the bothSrvExcludeFlag to set
	 */
	public void setBothSrvExcludeFlag(String bothSrvExcludeFlag) {
		this.bothSrvExcludeFlag = bothSrvExcludeFlag;
	}



}
