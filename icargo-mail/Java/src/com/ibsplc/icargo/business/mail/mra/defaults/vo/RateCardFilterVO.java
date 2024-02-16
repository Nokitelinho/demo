/*
 * RateCardFilterVO.java Created on Jan 19, 2007
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
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Jan 8, 2007   Philip 		            Initial draft
 *  0.2         Jan 28,2007   Kiran 					Added the field displayPage
 *
 */

public class RateCardFilterVO extends AbstractVO {


    private String companyCode;
    private String rateCardID;
    private String rateCardStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int displayPage;
    private int totalRecordCount;
    


	/**
	 * 	Getter for totalRecordCount 
	 *	Added by : A-5175 on 16-Oct-2012
	 * 	Used for : ICRD-21098
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 *  @param totalRecordCount the totalRecordCount to set
	 * 	Setter for totalRecordCount 
	 *	Added by : A-5175 on 16-Oct-2012
	 * 	Used for : ICRD-21098
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	/**
     *
     */
    public RateCardFilterVO() {

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
     * @return Returns the rateCardStatus.
     */
    public String getRateCardStatus() {
        return rateCardStatus;
    }
    /**
     * @param rateCardStatus The rateCardStatus to set.
     */
    public void setRateCardStatus(String rateCardStatus) {
        this.rateCardStatus = rateCardStatus;
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

	/**
	 * @return Returns the displayPage.
	 */
	public int getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}
}
