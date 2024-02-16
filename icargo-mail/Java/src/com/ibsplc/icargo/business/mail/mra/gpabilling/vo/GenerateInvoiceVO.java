/*
 * GenerateInvoiceVO.java Created on Mar 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-3108
 *
 */
public class GenerateInvoiceVO extends AbstractVO {

    private String companyCode;

    private String gpaCode;

    private String gpaName;

    private String countryCode;

    private LocalDate billingPeriodFrom;

    private LocalDate billingPeriodTo;

    private String billingPeriodIndicator;

	/**
	 * @return Returns the billingPeriodFrom.
	 */
	public LocalDate getBillingPeriodFrom() {
		return billingPeriodFrom;
	}

	/**
	 * @param billingPeriodFrom The billingPeriodFrom to set.
	 */
	public void setBillingPeriodFrom(LocalDate billingPeriodFrom) {
		this.billingPeriodFrom = billingPeriodFrom;
	}

	/**
	 * @return Returns the billingPeriodTo.
	 */
	public LocalDate getBillingPeriodTo() {
		return billingPeriodTo;
	}

	/**
	 * @param billingPeriodTo The billingPeriodTo to set.
	 */
	public void setBillingPeriodTo(LocalDate billingPeriodTo) {
		this.billingPeriodTo = billingPeriodTo;
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
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	public String getBillingPeriodIndicator() {
		return billingPeriodIndicator;
	}

	public void setBillingPeriodIndicator(String billingPeriodIndicator) {
		this.billingPeriodIndicator = billingPeriodIndicator;
	}



}
