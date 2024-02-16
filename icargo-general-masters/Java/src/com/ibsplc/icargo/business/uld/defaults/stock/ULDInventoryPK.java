/*
 * ULDInventoryPK.java Created on Mar 31,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.stock;

import java.io.Serializable;
import javax.persistence.Embeddable;
import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * 
 * @author a-2883
 *
 */

@Embeddable
@KeyTable(table = "ULDINVPLNKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "ULDINVPLNKEY", key = "INVPLN_SEQNUM")
public class ULDInventoryPK implements Serializable{

	/**
	 * 
	 */
	private String companyCode;
	private String airportCode;
	private String uldType;
	private String sequenceNumber;
	
	/**
	 * 
	 *Constructor
	 */
	public ULDInventoryPK(){
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param airportCode
	 * @param uldType
	 * @param sequenceNumber
	 */
	 public ULDInventoryPK(
		    	String companyCode, String airportCode ,String uldType, String sequenceNumber) {
		    	this.companyCode = companyCode;
		    	this.airportCode = airportCode;
		    	this.uldType = uldType;
		    	this.sequenceNumber = sequenceNumber;
			}
	
	 /**
	  * 
	  */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	
	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode)
				.append(airportCode)
				.append(uldType)
				.append(sequenceNumber).
				toString().hashCode();
	}  

	
	/**
	 * @return the airportCode
	 */
	@KeyCondition(column = "ARPCOD")
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return the companyCode
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the uldType
	 */
	@KeyCondition(column = "ULDTYP")
	public String getUldType() {
		return uldType;
	}
	/**
	 * @param uldType the uldType to set
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}
	
	/**
	 * @return the sequenceNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1")    
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:18 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(105);
		sbul.append("ULDInventoryPK [ ");
		sbul.append("airportCode '").append(this.airportCode);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("', uldType '").append(this.uldType);
		sbul.append("' ]");
		return sbul.toString();
	}
	
}