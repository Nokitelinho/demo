/*
 * MailBagForSegmentPK.java Created on Jan 16, 2007
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
public class MailBagForSegmentPK implements Serializable {
    
    private String companyCode;
    private int carrierId;
    private String flightNumber;    
    private long flightSequenceNumber;
    private int segmentSerialNumber;
    private String mailbagId;
    
    /**
     * Default constructor
     */
    public MailBagForSegmentPK() {
        
    }
    
    /**
     * 
     * @param companyCode
     * @param carrierId
     * @param flightNumber
     * @param flightSequenceNumber
     * @param segmentSerialNumber
     * @param mailbagId
     */
    
    public MailBagForSegmentPK(String companyCode, int carrierId,
            String flightNumber, long flightSequenceNumber,
            int segmentSerialNumber, String mailbagId) {
        
        this.companyCode = companyCode;
        this.carrierId = carrierId;
        this.flightNumber = flightNumber;
        this.flightSequenceNumber = flightSequenceNumber;
        this.segmentSerialNumber = segmentSerialNumber;
        this.mailbagId = mailbagId;
    }
    /**
     * @param other
     * @return boolean
     */
    public boolean equals(Object other) {
    	boolean isEqual = false;
		if(other != null){
			isEqual = this.hashCode() == other.hashCode();
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
				append(mailbagId).
				toString().hashCode();
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
	 * @param mailbagId
	 */
	public void setMailbagId(java.lang.String mailbagId) {
		this.mailbagId=mailbagId;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getMailbagId() {
		return this.mailbagId;
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
		StringBuilder sbul = new StringBuilder(171);
		sbul.append("MailBagForSegmentPK [ ");
		sbul.append("carrierId '").append(this.carrierId);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', flightNumber '").append(this.flightNumber);
		sbul.append("', flightSequenceNumber '").append(
				this.flightSequenceNumber);
		sbul.append("', mailbagId '").append(this.mailbagId);
		sbul.append("', segmentSerialNumber '")
				.append(this.segmentSerialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
