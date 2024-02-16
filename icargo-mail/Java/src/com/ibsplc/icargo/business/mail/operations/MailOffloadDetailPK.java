/*
 * MailOffloadDetailPK.java Created on Jun 27, 2016
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
 * 
 * @author A-5991
 * The PK Class for OffloadDetail
 *
 */
@KeyTable(table = "MALOFLDTLKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "MALOFLDTLKEY", key = "OFL_SERNUM")
@Embeddable
public class MailOffloadDetailPK implements Serializable  {
     
	 /**
	  * The companyCode
	  */

	 private String companyCode;    
	
	 
	 /**
	  * The CarrierId
	  */

	 private int carrierId;
	 
	 /**
	  * The FlightNumber
	  */

     private String flightNumber;
	 
	 /**
	  * The FlightSequenceNumber 
	  */

     private long flightSequenceNumber;
	 
	 /**
	  * The segmentSerialNumber
	  */

     private int segmentSerialNumber;   
	 
	 /**
	  * The Offload SerialNumber
	  */

     private int offloadSerialNumber;
     
     /**
	  * The mailSequenceNumber
	  */
    // private long mailSequenceNumber;
    
    /**
     * 
     * @param other
     * @return
     * The Equals method
     */
    public boolean equals(Object other) {
 		return (other != null) && ((hashCode() == other.hashCode()));
 	}

    /**
     * @return
     * The Hashcode implementation for the Equals Method
     */
      public int hashCode() {
 	    
 		return new StringBuffer(companyCode). 			
 				append(carrierId).
 				append(flightNumber).
 				append(flightSequenceNumber).
 				append(segmentSerialNumber).
 				append(offloadSerialNumber). 
 				toString().hashCode();
 	}  

	public void setFlightNumber(java.lang.String flightNumber) {
		this.flightNumber=flightNumber;
	}
	 @KeyCondition(column ="FLTNUM")
	public java.lang.String getFlightNumber() {
		return this.flightNumber;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	 @KeyCondition(column ="CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setOffloadSerialNumber(int offloadSerialNumber) {
		this.offloadSerialNumber=offloadSerialNumber;
	}
	 @Key(generator = "ID_GEN", startAt = "1")
	public int getOffloadSerialNumber() {
		return this.offloadSerialNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber=flightSequenceNumber;
	}
	 @KeyCondition(column ="FLTSEQNUM")
	public long getFlightSequenceNumber() {
		return this.flightSequenceNumber;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId=carrierId;
	}
	 @KeyCondition(column ="FLTCARIDR")
	public int getCarrierId() {
		return this.carrierId;
	}

	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber=segmentSerialNumber;
	}
	 @KeyCondition(column ="SEGSERNUM")
	public int getSegmentSerialNumber() {
		return this.segmentSerialNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:53 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(181);
		sbul.append("MailOffloadDetailPK [ ");
		sbul.append("carrierId '").append(this.carrierId);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', flightNumber '").append(this.flightNumber);
		sbul.append("', flightSequenceNumber '").append(
				this.flightSequenceNumber);
		sbul.append("', offloadSerialNumber '")
				.append(this.offloadSerialNumber);
		sbul.append("', segmentSerialNumber '")
				.append(this.segmentSerialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}