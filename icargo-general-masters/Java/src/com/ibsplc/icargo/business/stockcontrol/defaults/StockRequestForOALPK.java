/*
 * StockRequestForOALPK.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.io.Serializable;


import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-1619
 *
 *
 **/
 @KeyTable(table="STKREQOALKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
 @TableKeyGenerator(name="ID_GEN",table="STKREQOALKEY",key="STREQOAL_SER")
 @Embeddable
 
public class StockRequestForOALPK implements Serializable {

    /**
     * 
     */
   
    private String companyCode;
    
    /**
     * 
     */
   
    private String airportCode;
    
    /**
     * 
     */
   
    private int airlineIdentifier;
    
    /**
     * 
     */
    
    private String documentType;
    
    /**
     * 
     */
   
    private String documentSubType;
    
    /**
     * 
     */
   
    private int serialNumber;
    
    /**
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	/**
	 * @return int
	 * 
	 */
	public int hashCode() {
		return new StringBuffer(companyCode)
		.append(airportCode)
		.append(documentType).append(documentSubType)
		.append(serialNumber).
		toString().hashCode();
	}
	/**
	 * @return Returns the airlineIdentifier.
	 */
	 @KeyCondition(column = "ARLIDR")
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
	 * @return Returns the airportCode.
	 */
	 @KeyCondition(column = "ARPCOD")
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return Returns the companyCode.
	 */
	 @KeyCondition(column = "CMPCOD")
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
	 @KeyCondition(column = "DOCSUBTYP")
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
	@KeyCondition(column = "DOCTYP")
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
	 * @return Returns the serialNumber.
	 */
	 @Key(generator = "ID_GEN", startAt = "1")
	public int getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:15 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(170);
		sbul.append("StockRequestForOALPK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', airportCode '").append(this.airportCode);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', documentSubType '").append(this.documentSubType);
		sbul.append("', documentType '").append(this.documentType);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}    
    
}