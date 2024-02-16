/*
 * BlackListStockPK.java Created on Sep 13, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.io.Serializable;


import javax.persistence.Embeddable;

/**
 * @author A-1366
 *
 */

@Embeddable
public class BlackListStockPK implements Serializable {
    /**
     * company code
     */
    private String companyCode;
    /**
     *
     */
    private int airlineIdentifier;
    /**
     * document type
     */
    private String documentType;
    /**
     * document sub type
     */
    private String documentSubType;
    /**
     * startRange
     */
    private String startRange;
    /**
     * endRange
     */
    private String endRange;
    
    /**
     * status
     */
    private String status;
    
    /**
     * Constructor
     *
     */
    public BlackListStockPK(){

    }
    /**
     * @param other
     * @return
     */
    public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
    /**
     * @return int
     */
	public int hashCode() {
		return new StringBuffer(companyCode).append(documentType).
		append(documentSubType).append(startRange).append(endRange).
		append(airlineIdentifier).toString().hashCode();
	}
	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
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
	 * @return Returns the documentSubType.
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}
	/**
	 * @param documentSubType The documentSubType to set.
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}
	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	/**
	 * @return Returns the endRange.
	 */
	public String getEndRange() {
		return endRange;
	}
	/**
	 * @param endRange The endRange to set.
	 */
	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}
	/**
	 * @return Returns the startRange.
	 */
	public String getStartRange() {
		return startRange;
	}
	/**
	 * @param startRange The startRange to set.
	 */
	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}
	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:15 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(179);
		sbul.append("BlackListStockPK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', documentSubType '").append(this.documentSubType);
		sbul.append("', documentType '").append(this.documentType);
		sbul.append("', endRange '").append(this.endRange);
		sbul.append("', startRange '").append(this.startRange);
		sbul.append("', status '").append(this.status);
		sbul.append("' ]");
		return sbul.toString();
	}
}