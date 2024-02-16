/*
 * RateLineFilterVO.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class RateLineFilterVO extends AbstractVO {


    private String companyCode;
    private String rateCardID;
    private String ratelineStatus;
    private String origin;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private int pageNumber;
    private int totalRecordCount;
    private String orgDstLevel;
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
     *
     */
    public RateLineFilterVO() {
        super();
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
     * @return Returns the destination.
     */
    public String getDestination() {
        return destination;
    }
    /**
     * @param destination The destination to set.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    /**
     * @return Returns the endDate.
     */
    public LocalDate getEndDate() {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    /**
     * @return Returns the origin.
     */
    public String getOrigin() {
        return origin;
    }
    /**
     * @param origin The origin to set.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    /**
     * @return Returns the rateCardID.
     */
    public String getRateCardID() {
        return rateCardID;
    }
    /**
     * @param rateCardID The rateCardID to set.
     */
    public void setRateCardID(String rateCardID) {
        this.rateCardID = rateCardID;
    }
    /**
     * @return Returns the ratelineStatus.
     */
    public String getRatelineStatus() {
        return ratelineStatus;
    }
    /**
     * @param ratelineStatus The ratelineStatus to set.
     */
    public void setRatelineStatus(String ratelineStatus) {
        this.ratelineStatus = ratelineStatus;
    }
    /**
     * @return Returns the startDate.
     */
    public LocalDate getStartDate() {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	/**
	 * 	Getter for orgDstLevel 
	 *	Added by : A-5219 on 23-Oct-2020
	 * 	Used for :
	 */
	public String getOrgDstLevel() {
		return orgDstLevel;
	}

	/**
	 *  @param orgDstLevel the orgDstLevel to set
	 * 	Setter for orgDstLevel 
	 *	Added by : A-5219 on 23-Oct-2020
	 * 	Used for :
	 */
	public void setOrgDstLevel(String orgDstLevel) {
		this.orgDstLevel = orgDstLevel;
	}
}
