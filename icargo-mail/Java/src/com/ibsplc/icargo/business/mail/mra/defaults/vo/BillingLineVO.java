/*
 * BillingLineVO.java created on Feb 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2280
 * 
 */
public class BillingLineVO extends AbstractVO {

	private String companyCode;

	private int billingLineSequenceNumber;

	private String billingLineStatus;

	private LocalDate validityStartDate;

	private LocalDate validityEndDate;

	private String currencyCode;

	private String airlineCode;

	private int airlineIdentifier;

	private String billingCategory;

	private String poaCode;

	private double applicableRate;

	private String revenueExpenditureFlag;

	private String billingSector;

	private String billingBasis;	

	private Collection<BillingLineParameterVO> billingLineParameters;

	private String operationFlag;

	private String billingMatrixId;
	
	private String lastUpdatedUser;
	
	private LocalDate lastUpdatedTime;
	//Added for CRQ 12578 by A 4823
	private String unitCode;
	private boolean isTaxIncludedInRateFlag;
	
	private String surchargeIndicator;
	
	private String weightUnit ;
	
	private Collection<BillingLineDetailVO> billingLineDetails;
	
	private Collection<BillingLineParameterVO> oldBlgLinPars;
	
	private Collection<BillingLineParameterVO> newBlgLinPars;
	
	/**
	 * 
	 * @return unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}
	/**
	 * 
	 * @param unitCode
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	/**
	 * 
	 * @return isTaxIncludedInRateFlag
	 */
	public boolean isTaxIncludedInRateFlag() {
		return isTaxIncludedInRateFlag;
	}
	/**
	 * 
	 * @param isTaxIncludedInRateFlag
	 */
	public void setTaxIncludedInRateFlag(boolean isTaxIncludedInRateFlag) {
		this.isTaxIncludedInRateFlag = isTaxIncludedInRateFlag;
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
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the applicableRate.
	 */
	public double getApplicableRate() {
		return applicableRate;
	}

	/**
	 * @param applicableRate
	 *            The applicableRate to set.
	 */
	public void setApplicableRate(double applicableRate) {
		this.applicableRate = applicableRate;
	}

	/**
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis
	 *            The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return Returns the billingLineSequenceNumber.
	 */
	public int getBillingLineSequenceNumber() {
		return billingLineSequenceNumber;
	}

	/**
	 * @param billingLineSequenceNumber
	 *            The billingLineSequenceNumber to set.
	 */
	public void setBillingLineSequenceNumber(int billingLineSequenceNumber) {
		this.billingLineSequenceNumber = billingLineSequenceNumber;
	}

	/**
	 * @return Returns the billingLineStatus.
	 */
	public String getBillingLineStatus() {
		return billingLineStatus;
	}

	/**
	 * @param billingLineStatus
	 *            The billingLineStatus to set.
	 */
	public void setBillingLineStatus(String billingLineStatus) {
		this.billingLineStatus = billingLineStatus;
	}

	/**
	 * @return Returns the billingSector.
	 */
	public String getBillingSector() {
		return billingSector;
	}

	/**
	 * @param billingSector
	 *            The billingSector to set.
	 */
	public void setBillingSector(String billingSector) {
		this.billingSector = billingSector;
	}

	/**
	 * @return Returns the currencyCode.
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 *            The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode
	 *            The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the revenueExpenditureFlag.
	 */
	public String getRevenueExpenditureFlag() {
		return revenueExpenditureFlag;
	}

	/**
	 * @param revenueExpenditureFlag
	 *            The revenueExpenditureFlag to set.
	 */
	public void setRevenueExpenditureFlag(String revenueExpenditureFlag) {
		this.revenueExpenditureFlag = revenueExpenditureFlag;
	}

	/**
	 * @return Returns the validityEndDate.
	 */
	public LocalDate getValidityEndDate() {
		return validityEndDate;
	}

	/**
	 * @param validityEndDate
	 *            The validityEndDate to set.
	 */
	public void setValidityEndDate(LocalDate validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	/**
	 * @return Returns the validityStartDate.
	 */
	public LocalDate getValidityStartDate() {
		return validityStartDate;
	}

	/**
	 * @param validityStartDate
	 *            The validityStartDate to set.
	 */
	public void setValidityStartDate(LocalDate validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	/**
	 * @return Returns the billingLineParameters.
	 */
	public Collection<BillingLineParameterVO> getBillingLineParameters() {
		return billingLineParameters;
	}

	/**
	 * @param billingLineParameters
	 *            The billingLineParameters to set.
	 */
	public void setBillingLineParameters(
			Collection<BillingLineParameterVO> billingLineParameters) {
		this.billingLineParameters = billingLineParameters;
	}

	/**
	 * @return Returns the billingCategory.
	 */
	public String getBillingCategory() {
		return billingCategory;
	}

	/**
	 * @param billingCategory
	 *            The billingCategory to set.
	 */
	public void setBillingCategory(String billingCategory) {
		this.billingCategory = billingCategory;
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
	 * @return Returns the billingMatrixId.
	 */
	public String getBillingMatrixId() {
		return billingMatrixId;
	}

	/**
	 * @param billingMatrixId
	 *            The billingMatrixId to set.
	 */
	public void setBillingMatrixId(String billingMatrixId) {
		this.billingMatrixId = billingMatrixId;
	}
	/**
	 * @return the billingLineDetails
	 */
	public Collection<BillingLineDetailVO> getBillingLineDetails() {
		return billingLineDetails;
	}
	/**
	 * @param billingLineDetails the billingLineDetails to set
	 */
	public void setBillingLineDetails(
			Collection<BillingLineDetailVO> billingLineDetails) {
		this.billingLineDetails = billingLineDetails;
	}
	/**
	 * @return the surchargeIndicator
	 */
	public String getSurchargeIndicator() {
		return surchargeIndicator;
	}
	/**
	 * @param surchargeIndicator the surchargeIndicator to set
	 */
	public void setSurchargeIndicator(String surchargeIndicator) {
		this.surchargeIndicator = surchargeIndicator;
	}
	public Collection<BillingLineParameterVO> getOldBlgLinPars() {
		return oldBlgLinPars;
	}
	public void setOldBlgLinPars(Collection<BillingLineParameterVO> oldBlgLinPars) {
		this.oldBlgLinPars = oldBlgLinPars;
	}
	public Collection<BillingLineParameterVO> getNewBlgLinPars() {
		return newBlgLinPars;
	}
	public void setNewBlgLinPars(Collection<BillingLineParameterVO> newBlgLinPars) {
		this.newBlgLinPars = newBlgLinPars;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	
	
	

}
