/*
 * ULDForSegmentPK.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author a-5991
 *
 */
@Embeddable
public class ULDForSegmentPK implements Serializable {
     
	/**
	 * The companyCode
	 */
	private String companyCode; 
	/**
	 * The carrierId
	 */
     private int carrierId;
     /**
      * The flightNumber
      */
     private String flightNumber;
     /**
      * The flightSequenceNumber
      */
     private long flightSequenceNumber;
     /**
      * The segmentSerialNumber
      */
     private int segmentSerialNumber;
     /**
      * For Bulk , uldNumber is BULK/assignmentPort
      */
     private String uldNumber;  
     
   //  private long mailSequenceNumber;
     
     /**
      * @param other
      * @return
      */
    public boolean equals(Object other) {
 		return (other != null) && ((hashCode() == other.hashCode()));
 	}
    /**
     * @return
     */
 	public int hashCode() {
 	    
 		return new StringBuffer(companyCode). 			
 				append(carrierId).
 				append(flightNumber).
 				append(flightSequenceNumber).
 				append(segmentSerialNumber).
 				append(uldNumber). 								
 				toString().hashCode();
 	}    

	public void setFlightNumber(java.lang.String flightNumber) {
		this.flightNumber=flightNumber;
	}

	public java.lang.String getFlightNumber() {
		return this.flightNumber;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId=carrierId;
	}

	public int getCarrierId() {
		return this.carrierId;
	}

	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber=segmentSerialNumber;
	}

	public int getSegmentSerialNumber() {
		return this.segmentSerialNumber;
	}

	public void setUldNumber(java.lang.String uldNumber) {
		this.uldNumber=uldNumber;
	}

	public java.lang.String getUldNumber() {
		return this.uldNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber=flightSequenceNumber;
	}

	public long getFlightSequenceNumber() {
		return this.flightSequenceNumber;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}
	
	

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:53 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(167);
		sbul.append("ULDForSegmentPK [ ");
		sbul.append("carrierId '").append(this.carrierId);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', flightNumber '").append(this.flightNumber);
		sbul.append("', flightSequenceNumber '").append(
				this.flightSequenceNumber);
		sbul.append("', segmentSerialNumber '")
				.append(this.segmentSerialNumber);
		sbul.append("', uldNumber '").append(this.uldNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
