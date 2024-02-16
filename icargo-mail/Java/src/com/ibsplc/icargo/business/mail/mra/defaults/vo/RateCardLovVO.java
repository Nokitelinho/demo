/*
 * RateCardLovVO.java Created on Feb 1, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;



import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class RateCardLovVO extends AbstractVO {

    private String companyCode;
    private String rateCardID;
    private String description;

    /**
     *
     */
    public RateCardLovVO() {

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
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
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

}
