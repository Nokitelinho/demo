/*
 * AssignedFlightAuditPK.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-3109 The AssignedFlightAuditPK class for the AssignedFlightAudit
 */
@KeyTable(table = "MALFLTAUDKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "AUDSEQ_GEN", table = "MALFLTAUDKEY", key = "FLTAUD_SEQNUM")
@Embeddable
public class AssignedFlightAuditPK implements Serializable {

	/**
	 * Company Code
	 */

	private String companyCode;

	/**
	 * The airportCode
	 */

	private String airportCode;

	/**
	 * The carrierId
	 */

	private int carrierId;

	/**
	 * The flightNumber
	 */

	private String flightNumber;

	/**
	 * The legSerialNumber
	 */

	private int legSerialNumber;

	/**
	 * The flightSequenceNumber
	 */

	private long flightSequenceNumber;

	/**
	 * The key generated SequenceNumber
	 */

	private long sequenceNumber;

	/**
	 * @param other
	 * @return
	 */

	public boolean equals(Object other) {
		boolean isEqual = false;
		if (other != null) {
			isEqual = hashCode() == other.hashCode();
		}
		return isEqual;
	}

	/**
	 * @return
	 */
	public int hashCode() {

		return new StringBuffer(companyCode).append(airportCode)
				.append(carrierId).append(flightNumber).append(legSerialNumber)
				.append(flightSequenceNumber).append(sequenceNumber).toString()
				.hashCode();
	}

	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	@KeyCondition(column = "LEGSERNUM")
	public int getLegSerialNumber() {
		return this.legSerialNumber;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode = companyCode;
	}

	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Key(generator = "AUDSEQ_GEN", startAt = "1")
	public long getSequenceNumber() {
		return this.sequenceNumber;
	}

	public void setAirportCode(java.lang.String airportCode) {
		this.airportCode = airportCode;
	}

	@KeyCondition(column = "ARPCOD")
	public java.lang.String getAirportCode() {
		return this.airportCode;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	@KeyCondition(column = "FLTSEQNUM")
	public long getFlightSequenceNumber() {
		return this.flightSequenceNumber;
	}

	public void setFlightNumber(java.lang.String flightNumber) {
		this.flightNumber = flightNumber;
	}

	@KeyCondition(column = "FLTNUM")
	public java.lang.String getFlightNumber() {
		return this.flightNumber;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	@KeyCondition(column = "FLTCARIDR")
	public int getCarrierId() {
		return this.carrierId;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:52 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(197);
		sbul.append("AssignedFlightAuditPK [ ");
		sbul.append("airportCode '").append(this.airportCode);
		sbul.append("', carrierId '").append(this.carrierId);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', flightNumber '").append(this.flightNumber);
		sbul.append("', flightSequenceNumber '").append(
				this.flightSequenceNumber);
		sbul.append("', legSerialNumber '").append(this.legSerialNumber);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}