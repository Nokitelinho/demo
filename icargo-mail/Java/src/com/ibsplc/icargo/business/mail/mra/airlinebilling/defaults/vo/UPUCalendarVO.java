/*
 * UPUCalendarVO.java Created on Sep 11, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1387
 *
 */
public class UPUCalendarVO extends AbstractVO {
    
	private String companyCode;
    private String clearingHouse;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String billingPeriod;    
    private LocalDate submissionDate;
    private int generateAfterToDate;
    private String operationalFlag;
    private String lastUpdateUser;
    private LocalDate lastUpdateTime;
    

    /**
	 * @return Returns the operationFlag.
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 * @return Returns the billingPeriod.
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}
	/**
     * @return Returns the clearingHouse.
     */
    public String getClearingHouse() {
        return clearingHouse;
    }
    /**
     * @param clearingHouse The clearingHouse to set.
     */
    public void setClearingHouse(String clearingHouse) {
        this.clearingHouse = clearingHouse;
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
     * @return Returns the generateAfterToDate.
     */
    public int getGenerateAfterToDate() {
        return generateAfterToDate;
    }
    /**
     * @param generateAfterToDate The generateAfterToDate to set.
     */
    public void setGenerateAfterToDate(int generateAfterToDate) {
        this.generateAfterToDate = generateAfterToDate;
    }
   
    /**
     * @param billingPeriod The billingPeriod to set.
     */
    public void setBillingPeriod(String billingPeriod) {
        this.billingPeriod = billingPeriod;
    }
    /**
     * @return Returns the submissionDate.
     */
    public LocalDate getSubmissionDate() {
        return submissionDate;
    }
    /**
     * @param submissionDate The submissionDate to set.
     */
    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
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
	 * @return the lastUpdateTime
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return the lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
}
