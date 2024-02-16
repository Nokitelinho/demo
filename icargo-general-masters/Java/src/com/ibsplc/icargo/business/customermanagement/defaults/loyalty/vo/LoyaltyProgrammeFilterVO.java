/*
 * LoyaltyProgrammeFilterVO.java Created on Dec 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;



import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class LoyaltyProgrammeFilterVO  extends AbstractVO implements Serializable{

    private String companyCode;
    private String loyaltyProgrammeCode;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String loyaltyDescription;
    private int pageNumber;
    
    //added by a-5203
    private int totalRecords;
    public int getTotalRecords()
    {
        return totalRecords;
    }

    public void setTotalRecords(int i)
    {
        totalRecords = i;
    }
    //end
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
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
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
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return Returns the loyaltyDescription.
	 */
	public String getLoyaltyDescription() {
		return this.loyaltyDescription;
	}
	/**
	 * @param loyaltyDescription The loyaltyDescription to set.
	 */
	public void setLoyaltyDescription(String loyaltyDescription) {
		this.loyaltyDescription = loyaltyDescription;
	}
	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}
