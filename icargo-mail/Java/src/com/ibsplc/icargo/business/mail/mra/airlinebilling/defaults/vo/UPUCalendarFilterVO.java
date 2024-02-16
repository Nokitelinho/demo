/*
 * UPUCalendarFilterVO.java Created on Sep 11, 2005
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
 * @author A-2521
 *
 */
public class UPUCalendarFilterVO extends AbstractVO {
   
	private String companyCode;
    private String clearingHouse;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String clearancePeriod;

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
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}
	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}
}
