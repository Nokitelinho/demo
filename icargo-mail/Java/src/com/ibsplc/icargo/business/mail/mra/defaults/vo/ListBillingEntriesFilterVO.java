/*
 * ListBillingEntriesFilterVO.java Created on Nov 20, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author Sandeep.T 
 * extends AbstractVO
 * ListBillingEntriesFilterVO
 * Revision History
 * 
 * Version   Date       Author   Description
 * 
 * 0.1    20/11/2007  Sandeep.T   Initial draft
 */
public class ListBillingEntriesFilterVO extends AbstractVO {
   

    /**
     * Company Code
     */
    private String companyCode;

    /**
     * from date
     */
    private LocalDate fromDate;

    /**
     *  to date
     */
    private LocalDate toDate;
    
    
    private String fromDateString;
    
    private String toDateString;

	/**
	 * @return the fromDateString
	 */
	public String getFromDateString() {
		return fromDateString;
	}

	/**
	 * @param fromDateString the fromDateString to set
	 */
	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	/**
	 * @return the toDateString
	 */
	public String getToDateString() {
		return toDateString;
	}

	/**
	 * @param toDateString the toDateString to set
	 */
	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
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
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

   	
}