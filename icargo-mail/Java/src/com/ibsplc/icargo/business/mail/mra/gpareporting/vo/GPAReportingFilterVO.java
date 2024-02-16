/*
 * GPAReportingFilterVO.java Created on Aug 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

import com.ibsplc.icargo.framework.util.time.LocalDate;

/**
 * @author A-1453
 *
 */
public class GPAReportingFilterVO extends AbstractVO {

	private String companyCode;

	private String poaCode;

	private String poaName;

	private String country;

	private LocalDate reportingPeriodTo;

	private LocalDate reportingPeriodFrom;
	
	/*
	 * added by divya
	 * for Assign exceptions screen
	 */
	private String exceptionCode;
	
	private String assignee;
	
	private int pageNumber;

    //Added by A-1945
    private int absoluteIndex;
    
    //added by a-2270
    private String lastUpdatedUser;
    
    private LocalDate lastUpdatedTime;
    
    // flag that shows if all the despatches of a GPA for a reporting period are processed 
    private String processedFlag;
	
    /**
	 * @return Returns the processedFlag.
	 */
	public String getProcessedFlag() {
		return processedFlag;
	}

	/**
	 * @param processedFlag The processedFlag to set.
	 */
	public void setProcessedFlag(String processedFlag) {
		this.processedFlag = processedFlag;
	}

	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
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

	/**
	 * @return Returns the assignee.
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee The assignee to set.
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return Returns the exceptionCode.
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * @param exceptionCode The exceptionCode to set.
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
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
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the poaName.
	 */
	public String getPoaName() {
		return poaName;
	}

	/**
	 * @param poaName The poaName to set.
	 */
	public void setPoaName(String poaName) {
		this.poaName = poaName;
	}

	/**
	 * @return Returns the reportingPeriodFrom.
	 */
	public LocalDate getReportingPeriodFrom() {
		return reportingPeriodFrom;
	}

	/**
	 * @param reportingPeriodFrom The reportingPeriodFrom to set.
	 */
	public void setReportingPeriodFrom(LocalDate reportingPeriodFrom) {
		this.reportingPeriodFrom = reportingPeriodFrom;
	}

	/**
	 * @return Returns the reportingPeriodTo.
	 */
	public LocalDate getReportingPeriodTo() {
		return reportingPeriodTo;
	}

	/**
	 * @param reportingPeriodTo The reportingPeriodTo to set.
	 */
	public void setReportingPeriodTo(LocalDate reportingPeriodTo) {
		this.reportingPeriodTo = reportingPeriodTo;
	}

    /**
     *
     * @return
     */
    public int getAbsoluteIndex() {
        return absoluteIndex;
    }

    /**
     *
     * @param absoluteIndex
     */
    public void setAbsoluteIndex(int absoluteIndex) {
        this.absoluteIndex = absoluteIndex;
    }
}
