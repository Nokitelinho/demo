/*
 * SurchargeProrationDetailsVO.java Created on Jul 7, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5255 
 * @version	0.1, Jul 7, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jul 7, 2015 A-5255
 * First draft
 */

public class SurchargeProrationDetailsVO extends AbstractVO {
	private int sequenceNumber;
	private int csgSeqNumber;
	private String csgDocumentNumber;
	private String billingBasis;
	private String companyCode;
	private String poaCode;
	private String chargeHead;
	private Money prorationValue;
	private Money prorationAmtInUsd;
	private Money prorationAmtInSdr;
	private Money prorationAmtInBaseCurr;
	private Money totalProrationValue;
	private Money totalProrationAmtInUsd;
	private Money totalProrationAmtInSdr;
	private Money totalProrationAmtInBaseCurr;
	
	private double surchargeRate;
	private double  surchargeRevisedRate;
	
	
	/**
	 * @return the surchargeRate
	 */
	public double getSurchargeRate() {
		return surchargeRate;
	}

	/**
	 * @param surchargeRate the surchargeRate to set
	 */
	public void setSurchargeRate(double surchargeRate) {
		this.surchargeRate = surchargeRate;
	}

	/**
	 * @return the surchargeRevisedRate
	 */
	public double getSurchargeRevisedRate() {
		return surchargeRevisedRate;
	}

	/**
	 * @param surchargeRevisedRate the surchargeRevisedRate to set
	 */
	public void setSurchargeRevisedRate(double surchargeRevisedRate) {
		this.surchargeRevisedRate = surchargeRevisedRate;
	}

	private String baseCurrency;

	/**
	 * @return the chargeHead
	 */
	public String getChargeHead() {
		return chargeHead;
	}

	/**
	 * @param chargeHead
	 *            the chargeHead to set
	 */
	public void setChargeHead(String chargeHead) {
		this.chargeHead = chargeHead;
	}

	/**
	 * @return the prorationValue
	 */
	public Money getProrationValue() {
		return prorationValue;
	}

	/**
	 * @param prorationValue
	 *            the prorationValue to set
	 */
	public void setProrationValue(Money prorationValue) {
		this.prorationValue = prorationValue;
	}

	/**
	 * @return the prorationAmtInUsd
	 */
	public Money getProrationAmtInUsd() {
		return prorationAmtInUsd;
	}

	/**
	 * @param prorationAmtInUsd
	 *            the prorationAmtInUsd to set
	 */
	public void setProrationAmtInUsd(Money prorationAmtInUsd) {
		this.prorationAmtInUsd = prorationAmtInUsd;
	}

	/**
	 * @return the prorationAmtInSdr
	 */
	public Money getProrationAmtInSdr() {
		return prorationAmtInSdr;
	}

	/**
	 * @param prorationAmtInSdr
	 *            the prorationAmtInSdr to set
	 */
	public void setProrationAmtInSdr(Money prorationAmtInSdr) {
		this.prorationAmtInSdr = prorationAmtInSdr;
	}

	/**
	 * @return the prorationAmtInBaseCurr
	 */
	public Money getProrationAmtInBaseCurr() {
		return prorationAmtInBaseCurr;
	}

	/**
	 * @param prorationAmtInBaseCurr
	 *            the prorationAmtInBaseCurr to set
	 */
	public void setProrationAmtInBaseCurr(Money prorationAmtInBaseCurr) {
		this.prorationAmtInBaseCurr = prorationAmtInBaseCurr;
	}

	/**
	 * @return the sequenceNumber
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            the sequenceNumber to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the csgSeqNumber
	 */
	public int getCsgSeqNumber() {
		return csgSeqNumber;
	}

	/**
	 * @param csgSeqNumber
	 *            the csgSeqNumber to set
	 */
	public void setCsgSeqNumber(int csgSeqNumber) {
		this.csgSeqNumber = csgSeqNumber;
	}

	/**
	 * @return the csgDocumentNumber
	 */
	public String getCsgDocumentNumber() {
		return csgDocumentNumber;
	}

	/**
	 * @param csgDocumentNumber
	 *            the csgDocumentNumber to set
	 */
	public void setCsgDocumentNumber(String csgDocumentNumber) {
		this.csgDocumentNumber = csgDocumentNumber;
	}

	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis
	 *            the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode
	 *            the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the baseCurrency
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}

	/**
	 * @param baseCurrency the baseCurrency to set
	 */
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	/**
	 * @return the totalProrationValue
	 */
	public Money getTotalProrationValue() {
		return totalProrationValue;
	}

	/**
	 * @param totalProrationValue the totalProrationValue to set
	 */
	public void setTotalProrationValue(Money totalProrationValue) {
		this.totalProrationValue = totalProrationValue;
	}

	/**
	 * @return the totalProrationAmtInUsd
	 */
	public Money getTotalProrationAmtInUsd() {
		return totalProrationAmtInUsd;
	}

	/**
	 * @param totalProrationAmtInUsd the totalProrationAmtInUsd to set
	 */
	public void setTotalProrationAmtInUsd(Money totalProrationAmtInUsd) {
		this.totalProrationAmtInUsd = totalProrationAmtInUsd;
	}

	/**
	 * @return the totalProrationAmtInSdr
	 */
	public Money getTotalProrationAmtInSdr() {
		return totalProrationAmtInSdr;
	}

	/**
	 * @param totalProrationAmtInSdr the totalProrationAmtInSdr to set
	 */
	public void setTotalProrationAmtInSdr(Money totalProrationAmtInSdr) {
		this.totalProrationAmtInSdr = totalProrationAmtInSdr;
	}

	/**
	 * @return the totalProrationAmtInBaseCurr
	 */
	public Money getTotalProrationAmtInBaseCurr() {
		return totalProrationAmtInBaseCurr;
	}

	/**
	 * @param totalProrationAmtInBaseCurr the totalProrationAmtInBaseCurr to set
	 */
	public void setTotalProrationAmtInBaseCurr(Money totalProrationAmtInBaseCurr) {
		this.totalProrationAmtInBaseCurr = totalProrationAmtInBaseCurr;
	}

}
