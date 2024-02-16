/*
 * IncentiveConfigurationVO.java Created on SEP 10, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-6986
 *
 */
public class IncentiveConfigurationVO extends AbstractVO{

	private String companyCode;
	private String paCode;
	private int incentiveSerialNumber;
	private String incentiveFlag;//Incentive(Y) or Disincentive(N)
	private String serviceRespFlag;
	private String formula;
	private String basis;
	/*private Double percentage;
	private String validFrom;
	private String validTo;*/
	private Double incPercentage;
	private String incValidFrom;
	private String incValidTo;
	private Double disIncPercentage;
	private String disIncValidFrom;
	private String disIncValidTo;
	Collection<IncentiveConfigurationDetailVO> incentiveConfigurationDetailVOs;
	private String incOperationFlags;
	private String disIncOperationFlags;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private String disIncSrvOperationFlags;
	private String disIncNonSrvOperationFlags;
	private String disIncBothSrvOperationFlags;

	
	private String srvFormula;
	private String srvBasis;
	private Double disIncSrvPercentage;
	private String disIncSrvValidFrom;
	private String disIncSrvValidTo;
	
	private String nonSrvFormula;
	private String nonSrvBasis;
	private Double disIncNonSrvPercentage;
	private String disIncNonSrvValidFrom;
	private String disIncNonSrvValidTo;

	
	private String bothSrvFormula;
	private String bothSrvBasis;
	private Double disIncBothSrvPercentage;
	private String disIncBothSrvValidFrom;
	private String disIncBothSrvValidTo;


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
	 * @return the incntiveSerialNumber
	 */
	public int getIncentiveSerialNumber() {
		return incentiveSerialNumber;
	}
	/**
	 * @param incntiveSerialNumber the incntiveSerialNumber to set
	 */
	public void setIncentiveSerialNumber(int incentiveSerialNumber) {
		this.incentiveSerialNumber = incentiveSerialNumber;
	}
	/**
	 * @return the paCode
	 */
	public String getPaCode() {
		return paCode;
	}
	/**
	 * @param paCode the paCode to set
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	/**
	 * @return the incentiveFlag
	 */
	public String getIncentiveFlag() {
		return incentiveFlag;
	}
	/**
	 * @param incentiveFlag the incentiveFlag to set
	 */
	public void setIncentiveFlag(String incentiveFlag) {
		this.incentiveFlag = incentiveFlag;
	}
	/**
	 * @return the serviceRespFlag
	 */
	public String getServiceRespFlag() {
		return serviceRespFlag;
	}
	/**
	 * @param serviceRespFlag the serviceRespFlag to set
	 */
	public void setServiceRespFlag(String serviceRespFlag) {
		this.serviceRespFlag = serviceRespFlag;
	}
	/**
	 * @return the formula
	 */
	public String getFormula() {
		return formula;
	}
	/**
	 * @param formula the formula to set
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}
	/**
	 * @return the basis
	 */
	public String getBasis() {
		return basis;
	}
	/**
	 * @param basis the basis to set
	 */
	public void setBasis(String basis) {
		this.basis = basis;
	}
	/**
	 * @return the percentage
	 */
/*	public Double getPercentage() {
		return percentage;
	}*/
	/**
	 * @param percentage the percentage to set
	 */
	/*public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}*/
	/**
	 * @return the validFrom
	 */
	/*public String getValidFrom() {
		return validFrom;
	}*/
	/**
	 * @param validFrom the validFrom to set
	 */
	/*public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}*/
	/**
	 * @return the validTo
	 */
	/*public String getValidTo() {
		return validTo;
	}*/
	/**
	 * @param validTo the validTo to set
	 */
	/*public void setValidTo(String validTo) {
		this.validTo = validTo;
	}*/
	/**
	 * @return the incPercentage
	 */
	public Double getIncPercentage() {
		return incPercentage;
	}
	/**
	 * @param incPercentage the incPercentage to set
	 */
	public void setIncPercentage(Double incPercentage) {
		this.incPercentage = incPercentage;
	}
	/**
	 * @return the incValidFrom
	 */
	public String getIncValidFrom() {
		return incValidFrom;
	}
	/**
	 * @param incValidFrom the incValidFrom to set
	 */
	public void setIncValidFrom(String incValidFrom) {
		this.incValidFrom = incValidFrom;
	}
	/**
	 * @return the incValidTo
	 */
	public String getIncValidTo() {
		return incValidTo;
	}
	/**
	 * @param incValidTo the incValidTo to set
	 */
	public void setIncValidTo(String incValidTo) {
		this.incValidTo = incValidTo;
	}
	/**
	 * @return the disIncPercentage
	 */
	public Double getDisIncPercentage() {
		return disIncPercentage;
	}
	/**
	 * @param disIncPercentage the disIncPercentage to set
	 */
	public void setDisIncPercentage(Double disIncPercentage) {
		this.disIncPercentage = disIncPercentage;
	}
	/**
	 * @return the disIncValidFrom
	 */
	public String getDisIncValidFrom() {
		return disIncValidFrom;
	}
	/**
	 * @param disIncValidFrom the disIncValidFrom to set
	 */
	public void setDisIncValidFrom(String disIncValidFrom) {
		this.disIncValidFrom = disIncValidFrom;
	}
	/**
	 * @return the disIncValidTo
	 */
	public String getDisIncValidTo() {
		return disIncValidTo;
	}
	/**
	 * @param disIncValidTo the disIncValidTo to set
	 */
	public void setDisIncValidTo(String disIncValidTo) {
		this.disIncValidTo = disIncValidTo;
	}
	/**
	 * @return the incOperationFlag
	 */
	public String getIncOperationFlags() {
		return incOperationFlags;
	}
	/**
	 * @param incOperationFlag the incOperationFlag to set
	 */
	public void setIncOperationFlags(String incOperationFlags) {
		this.incOperationFlags = incOperationFlags;
	}
	/**
	 * @return the disIncOperationFlags
	 */
	public String getDisIncOperationFlags() {
		return disIncOperationFlags;
	}
	/**
	 * @param disIncOperationFlags the disIncOperationFlags to set
	 */
	public void setDisIncOperationFlags(String disIncOperationFlags) {
		this.disIncOperationFlags = disIncOperationFlags;
	}
	/**
	 * @return the disIncSrvOperationFlags
	 */
	public String getDisIncSrvOperationFlags() {
		return disIncSrvOperationFlags;
	}
	/**
	 * @param disIncSrvOperationFlags the disIncSrvOperationFlags to set
	 */
	public void setDisIncSrvOperationFlags(String disIncSrvOperationFlags) {
		this.disIncSrvOperationFlags = disIncSrvOperationFlags;
	}
	/**
	 * @return the disIncNonSrvOperationFlags
	 */
	public String getDisIncNonSrvOperationFlags() {
		return disIncNonSrvOperationFlags;
	}
	/**
	 * @param disIncNonSrvOperationFlags the disIncNonSrvOperationFlags to set
	 */
	public void setDisIncNonSrvOperationFlags(String disIncNonSrvOperationFlags) {
		this.disIncNonSrvOperationFlags = disIncNonSrvOperationFlags;
	}
	/**
	 * @return the disIncBothSrvOperationFlags
	 */
	public String getDisIncBothSrvOperationFlags() {
		return disIncBothSrvOperationFlags;
	}
	/**
	 * @param disIncBothSrvOperationFlags the disIncBothSrvOperationFlags to set
	 */
	public void setDisIncBothSrvOperationFlags(String disIncBothSrvOperationFlags) {
		this.disIncBothSrvOperationFlags = disIncBothSrvOperationFlags;
	}
	/**
	 * @return the incentiveConfigurationDetailVO
	 */
	public Collection<IncentiveConfigurationDetailVO> getIncentiveConfigurationDetailVOs() {
		return incentiveConfigurationDetailVOs;
	}
	/**
	 * @return the srvFormula
	 */
	public String getSrvFormula() {
		return srvFormula;
	}
	/**
	 * @param srvFormula the srvFormula to set
	 */
	public void setSrvFormula(String srvFormula) {
		this.srvFormula = srvFormula;
	}
	/**
	 * @return the srvBasis
	 */
	public String getSrvBasis() {
		return srvBasis;
	}
	/**
	 * @param srvBasis the srvBasis to set
	 */
	public void setSrvBasis(String srvBasis) {
		this.srvBasis = srvBasis;
	}
	/**
	 * @return the disIncSrvPercentage
	 */
	public Double getDisIncSrvPercentage() {
		return disIncSrvPercentage;
	}
	/**
	 * @param disIncSrvPercentage the disIncSrvPercentage to set
	 */
	public void setDisIncSrvPercentage(Double disIncSrvPercentage) {
		this.disIncSrvPercentage = disIncSrvPercentage;
	}
	/**
	 * @return the disIncSrvValidFrom
	 */
	public String getDisIncSrvValidFrom() {
		return disIncSrvValidFrom;
	}
	/**
	 * @param disIncSrvValidFrom the disIncSrvValidFrom to set
	 */
	public void setDisIncSrvValidFrom(String disIncSrvValidFrom) {
		this.disIncSrvValidFrom = disIncSrvValidFrom;
	}
	/**
	 * @return the disIncSrvValidTo
	 */
	public String getDisIncSrvValidTo() {
		return disIncSrvValidTo;
	}
	/**
	 * @param disIncSrvValidTo the disIncSrvValidTo to set
	 */
	public void setDisIncSrvValidTo(String disIncSrvValidTo) {
		this.disIncSrvValidTo = disIncSrvValidTo;
	}
	/**
	 * @return the nonSrvFormula
	 */
	public String getNonSrvFormula() {
		return nonSrvFormula;
	}
	/**
	 * @param nonSrvFormula the nonSrvFormula to set
	 */
	public void setNonSrvFormula(String nonSrvFormula) {
		this.nonSrvFormula = nonSrvFormula;
	}
	/**
	 * @return the nonSvBasis
	 */
	public String getNonSrvBasis() {
		return nonSrvBasis;
	}
	/**
	 * @param nonSvBasis the nonSvBasis to set
	 */
	public void setNonSrvBasis(String nonSrvBasis) {
		this.nonSrvBasis = nonSrvBasis;
	}
	/**
	 * @return the disIncNonSrvPercentage
	 */
	public Double getDisIncNonSrvPercentage() {
		return disIncNonSrvPercentage;
	}
	/**
	 * @param disIncNonSrvPercentage the disIncNonSrvPercentage to set
	 */
	public void setDisIncNonSrvPercentage(Double disIncNonSrvPercentage) {
		this.disIncNonSrvPercentage = disIncNonSrvPercentage;
	}
	/**
	 * @return the disIncNonSrvValidFrom
	 */
	public String getDisIncNonSrvValidFrom() {
		return disIncNonSrvValidFrom;
	}
	/**
	 * @param disIncNonSrvValidFrom the disIncNonSrvValidFrom to set
	 */
	public void setDisIncNonSrvValidFrom(String disIncNonSrvValidFrom) {
		this.disIncNonSrvValidFrom = disIncNonSrvValidFrom;
	}
	/**
	 * @return the disIncNonSrvValidTo
	 */
	public String getDisIncNonSrvValidTo() {
		return disIncNonSrvValidTo;
	}
	/**
	 * @param disIncNonSrvValidTo the disIncNonSrvValidTo to set
	 */
	public void setDisIncNonSrvValidTo(String disIncNonSrvValidTo) {
		this.disIncNonSrvValidTo = disIncNonSrvValidTo;
	}
	/**
	 * @return the bothSrvFormula
	 */
	public String getBothSrvFormula() {
		return bothSrvFormula;
	}
	/**
	 * @param bothSrvFormula the bothSrvFormula to set
	 */
	public void setBothSrvFormula(String bothSrvFormula) {
		this.bothSrvFormula = bothSrvFormula;
	}
	/**
	 * @return the bothSrvBasis
	 */
	public String getBothSrvBasis() {
		return bothSrvBasis;
	}
	/**
	 * @param bothSrvBasis the bothSrvBasis to set
	 */
	public void setBothSrvBasis(String bothSrvBasis) {
		this.bothSrvBasis = bothSrvBasis;
	}
	/**
	 * @return the disIncBothSrvPercentage
	 */
	public Double getDisIncBothSrvPercentage() {
		return disIncBothSrvPercentage;
	}
	/**
	 * @param disIncBothSrvPercentage the disIncBothSrvPercentage to set
	 */
	public void setDisIncBothSrvPercentage(Double disIncBothSrvPercentage) {
		this.disIncBothSrvPercentage = disIncBothSrvPercentage;
	}
	/**
	 * @return the disIncBothSrvValidFrom
	 */
	public String getDisIncBothSrvValidFrom() {
		return disIncBothSrvValidFrom;
	}
	/**
	 * @param disIncBothSrvValidFrom the disIncBothSrvValidFrom to set
	 */
	public void setDisIncBothSrvValidFrom(String disIncBothSrvValidFrom) {
		this.disIncBothSrvValidFrom = disIncBothSrvValidFrom;
	}
	/**
	 * @return the disIncBothSrvValidTo
	 */
	public String getDisIncBothSrvValidTo() {
		return disIncBothSrvValidTo;
	}
	/**
	 * @param disIncBothSrvValidTo the disIncBothSrvValidTo to set
	 */
	public void setDisIncBothSrvValidTo(String disIncBothSrvValidTo) {
		this.disIncBothSrvValidTo = disIncBothSrvValidTo;
	}
	/**
	 * @param incentiveConfigurationDetailVO the incentiveConfigurationDetailVO to set
	 */
	public void setIncentiveConfigurationDetailVOs(
			Collection<IncentiveConfigurationDetailVO> incentiveConfigurationDetailVOs) {
		this.incentiveConfigurationDetailVOs = incentiveConfigurationDetailVOs;
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

}
