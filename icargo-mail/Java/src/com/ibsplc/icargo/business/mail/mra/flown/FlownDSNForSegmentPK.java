/*
 * FlownDSNForSegmentPK.java Created on Jan 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-1556
 *
 */
@Embeddable
public class FlownDSNForSegmentPK implements Serializable {

    private String companyCode;
    private int carrierId;
    private String flightNumber;
    private long flightSequenceNumber;
    private int segmentSerialNumber;
    private String originExchangeOffice;
    private String destinationExchangeOffice;
    private String mailCategoryCode;
    private String mailSubclass;
    private String dsn;
    private int year;

    /**
     *
     */
    public FlownDSNForSegmentPK() {
    }

    /**
     *
     * @param companyCode
     * @param carrierId
     * @param flightNumber
     * @param flightSequenceNumber
     * @param segmentSerialNumber
     * @param originExchangeOffice
     * @param destinationExchangeOffice
     * @param mailCategoryCode
     * @param mailSubclass
     * @param dsn
     * @param year
     */

    public FlownDSNForSegmentPK(String companyCode, int carrierId,
            String flightNumber, long flightSequenceNumber,
            int segmentSerialNumber, String originExchangeOffice,
            String destinationExchangeOffice, String mailCategoryCode,
            String mailSubclass, String dsn, int year) {

        this.companyCode = companyCode;
        this.carrierId = carrierId;
        this.flightNumber = flightNumber;
        this.flightSequenceNumber = flightSequenceNumber;
        this.segmentSerialNumber = segmentSerialNumber;
        this.originExchangeOffice = originExchangeOffice;
        this.destinationExchangeOffice = destinationExchangeOffice;
        this.mailCategoryCode = mailCategoryCode;
        this.mailSubclass = mailSubclass;
        this.dsn = dsn;
        this.year = year;
    }
    /**
     * @param other
     * @return boolean
     */
    public boolean equals(Object other) {
    	boolean isEqual = false;
    	if(other != null){
    		isEqual = hashCode() == other.hashCode();
    	}
    	return isEqual;
	}

	/**
	 * @return int
	 */
	public int hashCode() {

 		return new StringBuffer(companyCode).
 				append(carrierId).
 				append(flightNumber).
 				append(flightSequenceNumber).
 				append(segmentSerialNumber).
 				append(originExchangeOffice).
 				append(destinationExchangeOffice).
 				append(mailCategoryCode).
 				append(mailSubclass).
 				append(dsn).
 				append(year).
 				toString().hashCode();
 	}


	/**
	 *
	 * @param mailSubclass
	 */
	public void setMailSubclass(java.lang.String mailSubclass) {
		this.mailSubclass=mailSubclass;
	}

	/**
	 *
	 * @return
	 */
	public java.lang.String getMailSubclass() {
		return this.mailSubclass;
	}

	/**
	 *
	 * @param flightNumber
	 */
	public void setFlightNumber(java.lang.String flightNumber) {
		this.flightNumber=flightNumber;
	}

	/**
	 *
	 * @return
	 */
	public java.lang.String getFlightNumber() {
		return this.flightNumber;
	}
	/**
	 *
	 * @param carrierId
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId=carrierId;
	}

	/**
	 *
	 * @return
	 */
	public int getCarrierId() {
		return this.carrierId;
	}

	/**
	 *
	 * @param originExchangeOffice
	 */
	public void setOriginExchangeOffice(java.lang.String originExchangeOffice) {
		this.originExchangeOffice=originExchangeOffice;
	}

	/**
	 *
	 * @return
	 */
	public java.lang.String getOriginExchangeOffice() {
		return this.originExchangeOffice;
	}

	/**
	 *
	 * @param mailCategoryCode
	 */
	public void setMailCategoryCode(java.lang.String mailCategoryCode) {
		this.mailCategoryCode=mailCategoryCode;
	}

	/**
	 *
	 * @return
	 */
	public java.lang.String getMailCategoryCode() {
		return this.mailCategoryCode;
	}

	/**
	 *
	 * @param year
	 */
	public void setYear(int year) {
		this.year=year;
	}

	/**
	 *
	 * @return
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 *
	 * @param segmentSerialNumber
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber=segmentSerialNumber;
	}

	/**
	 *
	 * @return
	 */
	public int getSegmentSerialNumber() {
		return this.segmentSerialNumber;
	}

	/**
	 *
	 * @param destinationExchangeOffice
	 */
	public void setDestinationExchangeOffice(java.lang.String destinationExchangeOffice) {
		this.destinationExchangeOffice=destinationExchangeOffice;
	}

	/**
	 *
	 * @return
	 */
	public java.lang.String getDestinationExchangeOffice() {
		return this.destinationExchangeOffice;
	}

	/**
	 *
	 * @param dsn
	 */
	public void setDsn(java.lang.String dsn) {
		this.dsn=dsn;
	}

	/**
	 *
	 * @return
	 */
	public java.lang.String getDsn() {
		return this.dsn;
	}

	/**
	 *
	 * @param flightSequenceNumber
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber=flightSequenceNumber;
	}

	/**
	 *
	 * @return
	 */
	public long getFlightSequenceNumber() {
		return this.flightSequenceNumber;
	}

	/**
	 *
	 * @param companyCode
	 */
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	/**
	 *
	 * @return
	 */
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(303);
		sbul.append("FlownDSNForSegmentPK [ ");
		sbul.append("carrierId '").append(this.carrierId);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', destinationExchangeOffice '").append(
				this.destinationExchangeOffice);
		sbul.append("', dsn '").append(this.dsn);
		sbul.append("', flightNumber '").append(this.flightNumber);
		sbul.append("', flightSequenceNumber '").append(
				this.flightSequenceNumber);
		sbul.append("', mailCategoryCode '").append(this.mailCategoryCode);
		sbul.append("', mailSubclass '").append(this.mailSubclass);
		sbul.append("', originExchangeOffice '").append(
				this.originExchangeOffice);
		sbul.append("', segmentSerialNumber '")
				.append(this.segmentSerialNumber);
		sbul.append("', year '").append(this.year);
		sbul.append("' ]");
		return sbul.toString();
	}
}
