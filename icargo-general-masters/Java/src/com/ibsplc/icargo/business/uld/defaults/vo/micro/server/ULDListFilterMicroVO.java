/*
 * ULDListFilterVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 * @author 
 *
 */

public class ULDListFilterMicroVO extends AbstractVO{

    private String companyCode;

    private String uldNumber;

    private String uldTypeCode;

    private String airlineCode;

    private String overallStatus;

 	private int airlineidentifier;

 	private int displayPage;

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

 	/**
	 * @return Returns the airlineidentifier.
	 */
	public int getAirlineidentifier() {
		return airlineidentifier;
	}
	/**
	 * @param airlineidentifier The airlineidentifier to set.
	 */
	public void setAirlineidentifier(int airlineidentifier) {
		this.airlineidentifier = airlineidentifier;
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
     * @return Returns the overallStatus.
     */
    public String getOverallStatus() {
        return overallStatus;
    }
    /**
     * @param overallStatus The overallStatus to set.
     */
    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    /**
     * @return Returns the uldNumber.
     */
    public String getUldNumber() {
        return uldNumber;
    }
    /**
     * @param uldNumber The uldNumber to set.
     */
    public void setUldNumber(String uldNumber) {
        this.uldNumber = uldNumber;
    }

    /**
     * @return Returns the uldTypeCode.
     */
    public String getUldTypeCode() {
        return uldTypeCode;
    }
    /**
     * @param uldTypeCode The uldTypeCode to set.
     */
    public void setUldTypeCode(String uldTypeCode) {
        this.uldTypeCode = uldTypeCode;
    }
	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
}
