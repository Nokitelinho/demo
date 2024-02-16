/*
 * RateCardPK.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 * @author A-1556
 *
 */
@Embeddable
public class RateCardPK implements Serializable {

    private String companyCode;
    private String rateCardID;

    /**
     *
     */
    public RateCardPK() {
    }

    /**
     *
     * @param companyCode
     * @param rateCardID
     */
    public RateCardPK(String companyCode, String rateCardID) {
        super();
        this.companyCode = companyCode;
        this.rateCardID = rateCardID;
    }

    /**
     * @param other
     * @return boolean
     */
	public boolean equals(Object other) {
		if(other != null ){
			return (hashCode() == other.hashCode());
		}else {
			return false;
		}
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(rateCardID).
				toString().hashCode();
	}

	/**
	 * @return Returns the companyCode.
	 */
	@KeyCondition(column="CMPCOD")
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
	 * @return Returns the rateCardID.
	 */
	@KeyCondition(column="RATCRDCOD")
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
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(55);
		sbul.append("RateCardPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', rateCardID '").append(this.rateCardID);
		sbul.append("' ]");
		return sbul.toString();
	}
	
	
}