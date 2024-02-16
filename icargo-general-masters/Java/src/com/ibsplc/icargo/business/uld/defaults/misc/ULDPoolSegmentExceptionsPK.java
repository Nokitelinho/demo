/*
 * ULDPoolSegmentExceptionsPK.java Created on AUG 11,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.io.Serializable;
import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * 
 * 
 * @author A-3429
 *
 */

@KeyTable(table = "ULDPOLSEGEXPKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "ULDPOLSEGEXPKEY", key = "MAX_POLSEQNUM")
@Embeddable
public class ULDPoolSegmentExceptionsPK implements Serializable {

	private String companyCode;

	private int airlineIdentifierOne;

	private int airlineIdentifierTwo;

	//private String airport;

	private String sequenceNumber;

	private long serialNumber; 

	/**
	 * Default constructor
	 */

	public ULDPoolSegmentExceptionsPK() {
	}

	/**
	 * 
	 * @param companyCode
	 * @param airlineOne
	 * @param airlineTwo
	 * @param airport
	 * @param sequenceNumber
	 */
	public ULDPoolSegmentExceptionsPK(String companyCode, int airlineOne,
			int airlineTwo, String airport, String sequenceNumbe, long serialNumber) {
		this.companyCode = companyCode;
		this.airlineIdentifierOne = airlineOne;
		this.airlineIdentifierTwo = airlineTwo;
		//this.airport = airport;
		this.sequenceNumber = sequenceNumber;
		this.serialNumber = serialNumber;

	}
	
	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode)
				.append(airlineIdentifierOne).append(airlineIdentifierTwo)
				//.append(airport)
				.append(serialNumber)
				.append(sequenceNumber).toString().hashCode();
	}

	/**
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

	/**
	 * @return the airlineIdentifierOne
	 */
	@KeyCondition(column = "ARLONE")
	public int getAirlineIdentifierOne() {
		return airlineIdentifierOne;
	}

	/**
	 * @param airlineIdentifierOne the airlineIdentifierOne to set
	 */
	public void setAirlineIdentifierOne(int airlineIdentifierOne) {
		this.airlineIdentifierOne = airlineIdentifierOne;
	}

	/**
	 * @return the airlineIdentifierTwo
	 */
	@KeyCondition(column = "ARLTWO")
	public int getAirlineIdentifierTwo() {
		return airlineIdentifierTwo;
	}

	/**
	 * @param airlineIdentifierTwo the airlineIdentifierTwo to set
	 */
	public void setAirlineIdentifierTwo(int airlineIdentifierTwo) {
		this.airlineIdentifierTwo = airlineIdentifierTwo;
	}

	/**
	 * @return the airport
	 */
	/*@KeyCondition(column = "ARPCOD")
	public String getAirport() {
		return airport;
	}*/

	/**
	 * @param airport the airport to set
	 */
	/*public void setAirport(String airport) {
		this.airport = airport;
	}*/

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
	 * @return the serialNumber 
	 */
	@KeyCondition(column = "SERNUM")
	public long getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}

	
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:18 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(158);
		sbul.append("ULDPoolSegmentExceptionsPK [ ");
		sbul.append("airlineIdentifierOne '").append(this.airlineIdentifierOne);
		sbul.append("', airlineIdentifierTwo '").append(
				this.airlineIdentifierTwo);
		//sbul.append("', airport '").append(this.airport);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

}
