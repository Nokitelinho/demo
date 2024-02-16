/*
 * ULDFlightMessagePK.java Created on Jul 7, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-1945
 *
 */
@Embeddable
public class ULDFlightMessagePK implements Serializable{
    /**
     * companyCode
     */
    private String companyCode;
    /**
     * stationCode
     */
    private String stationCode;
    /**
     * legSerialNumber
     */
    private int legSerialNumber;
    /**
     * flightSequenceNumber
     */
    private int flightSequenceNumber;
    /**
     * flightCarrierId
     */
    private String flightNumber;
    /**
     * flightCarrierId
     */
    private int flightCarrierId;
    /**
     *
     */
    public ULDFlightMessagePK() {
    }
    /**
     *
     * @param companyCode
     * @param stationCode
     * @param legSerialNumber
     * @param flightSequenceNumber
     * @param flightNumber
     * @param flightCarrierId
     */
    public ULDFlightMessagePK(String companyCode, String stationCode, int legSerialNumber,
                   int flightSequenceNumber, String flightNumber,
                   int flightCarrierId) {
        this.companyCode = companyCode;
        this.stationCode = stationCode;
        this.legSerialNumber = legSerialNumber;
        this.flightSequenceNumber = flightSequenceNumber;
        this.flightNumber = flightNumber;
        this.flightCarrierId = flightCarrierId;
    }

    /**
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        return (other != null) && (hashCode() == other.hashCode());
    }

    /**
     * @return
     */
    public int hashCode() {
        return new StringBuffer(companyCode).append(stationCode)
                .append(legSerialNumber).append(flightSequenceNumber)
                .append(flightNumber).append(flightCarrierId)
                .toString().hashCode();
    }
    /**
	 * @param flightCarrierId The flightCarrierId to set.
	 */
	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId=flightCarrierId;
	}
	/**
	 * @return Returns the flightCarrierId.
	 */
	public int getFlightCarrierId() {
		return this.flightCarrierId;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(java.lang.String flightNumber) {
		this.flightNumber=flightNumber;
	}
	/**
	 * @return Returns the flightNumber.
	 */
	public java.lang.String getFlightNumber() {
		return this.flightNumber;
	}
	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber=flightSequenceNumber;
	}
	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public int getFlightSequenceNumber() {
		return this.flightSequenceNumber;
	}
	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(java.lang.String stationCode) {
		this.stationCode=stationCode;
	}
	/**
	 * @return Returns the stationCode.
	 */
	public java.lang.String getStationCode() {
		return this.stationCode;
	}
	/**
	 * @param legSerialNumber The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber=legSerialNumber;
	}
	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return this.legSerialNumber;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:17 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(174);
		sbul.append("ULDFlightMessagePK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', flightCarrierId '").append(this.flightCarrierId);
		sbul.append("', flightNumber '").append(this.flightNumber);
		sbul.append("', flightSequenceNumber '").append(
				this.flightSequenceNumber);
		sbul.append("', legSerialNumber '").append(this.legSerialNumber);
		sbul.append("', stationCode '").append(this.stationCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
