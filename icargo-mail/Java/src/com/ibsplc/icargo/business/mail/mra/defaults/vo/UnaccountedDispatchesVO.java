/*
 * UnaccountedDispatchesVO.java created on Aug 20, 2008
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;


import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2107
 * 
 */
public class UnaccountedDispatchesVO extends AbstractVO {

	private String noOfDispatches;
	
	private Money propratedAmt;
	
	private String currency;
	
	private String companyCode;
	
	private Double rate;
	
	private Double weight;
	
	private LocalDate acceptedDate;
	
	private String reasonCode;
	
	private Page<UnaccountedDispatchesDetailsVO> unaccountedDispatchesDetails;
	
	private Collection<UnaccountedDispatchesDetailsVO> unaccountedDispatchesRptDetails;

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the noOfDispatches
	 */
	public String getNoOfDispatches() {
		return noOfDispatches;
	}

	/**
	 * @param noOfDispatches the noOfDispatches to set
	 */
	public void setNoOfDispatches(String noOfDispatches) {
		this.noOfDispatches = noOfDispatches;
	}

	/**
	 * @return the propratedAmt
	 */
	public Money getPropratedAmt() {
		return propratedAmt;
	}

	/**
	 * @param propratedAmt the propratedAmt to set
	 */
	public void setPropratedAmt(Money propratedAmt) {
		this.propratedAmt = propratedAmt;
	}

	/**
	 * @return the unaccountedDispatchesDetails
	 */
	public Page getUnaccountedDispatchesDetails() {
		return unaccountedDispatchesDetails;
	}

	/**
	 * @param unaccountedDispatchesDetails the unaccountedDispatchesDetails to set
	 */
	public void setUnaccountedDispatchesDetails(
			Page unaccountedDispatchesDetails) {
		this.unaccountedDispatchesDetails = unaccountedDispatchesDetails;
	}

	/**
	 * @return the unaccountedDispatchesRptDetails
	 */
	public Collection<UnaccountedDispatchesDetailsVO> getUnaccountedDispatchesRptDetails() {
		return unaccountedDispatchesRptDetails;
	}

	/**
	 * @param unaccountedDispatchesRptDetails the unaccountedDispatchesRptDetails to set
	 */
	public void setUnaccountedDispatchesRptDetails(
			Collection<UnaccountedDispatchesDetailsVO> unaccountedDispatchesRptDetails) {
		this.unaccountedDispatchesRptDetails = unaccountedDispatchesRptDetails;
	}

	/**
	 * @return the acceptedDate
	 */
	public LocalDate getAcceptedDate() {
		return acceptedDate;
	}

	/**
	 * @param acceptedDate the acceptedDate to set
	 */
	public void setAcceptedDate(LocalDate acceptedDate) {
		this.acceptedDate = acceptedDate;
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
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}


}
