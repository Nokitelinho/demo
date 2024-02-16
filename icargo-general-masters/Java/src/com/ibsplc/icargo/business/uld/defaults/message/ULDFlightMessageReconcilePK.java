/*
 * ULDFlightMessageReconcilePK.java Created on Jul 20, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-2048
 *
 */
@KeyTable(table = "ULDFLTMSGRECKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "ULDFLTMSGRECKEY", key = "SEQNUM")
@Embeddable
public class ULDFlightMessageReconcilePK implements Serializable{


	    /**
	     * @param 
	     */
	    private int flightCarrierIdentifier;
	    /**
	     * 
	     */

	    private String flightNumber;
	    /**
	     * 
	     */

	    private int flightSequenceNumber;
	    /**
	     * 
	     */
	    
	    

	    private String airportCode;
	    /**
	     * 
	     */

	    private String companyCode;
	    /**
	     * 
	     */

	    private String messageType;
	    /**
	     * 
	     */

	    private long sequenceNumber;
	    /**
	     * 
	     *
	     */
	    public ULDFlightMessageReconcilePK() {
	    	
	    }
	    /**
	     * 
	     * @param flightCarrierIdentifier
	     * @param flightNumber
	     * @param flightSequenceNumber
	     * @param legSerialNumber
	     * @param airportCode
	     * @param companyCode
	     * @param messageType
	     * @param sequenceNumber
	     */
	    public ULDFlightMessageReconcilePK(int flightCarrierIdentifier,
	    		                           String flightNumber,
	    		                           int flightSequenceNumber,
	    		                           String airportCode,
	    		                           String companyCode,
	    		                           String messageType,
	    		                           long sequenceNumber) {
	    	
	    	this.flightCarrierIdentifier =flightCarrierIdentifier;
	    	this.flightNumber = flightNumber;
	    	this.flightSequenceNumber = flightSequenceNumber;
	    	
	    	this.airportCode = airportCode;
	    	this.companyCode = companyCode;
	    	this.messageType = messageType;
	    	this.sequenceNumber = sequenceNumber;
	    	
	    }
	    /**
		 * 
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
			return new StringBuilder().append(flightCarrierIdentifier)
			                          .append(flightNumber)
			                          .append(flightSequenceNumber)
			                          .append(airportCode)
			                          .append(companyCode)
			                          .append(messageType)
			                          .append(sequenceNumber)
			                          .toString().hashCode();
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
	    @KeyCondition(column = "FLTNUM")
	public java.lang.String getFlightNumber() {
		return this.flightNumber;
	}
	    /**
		 * @param messageType The messageType to set.
		 */
	public void setMessageType(java.lang.String messageType) {
		this.messageType=messageType;
	}
	/**
	 * @return Returns the messageType.
	 */
	    @KeyCondition(column = "MSGTYP")   
	public java.lang.String getMessageType() {
		return this.messageType;
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
	    @KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}
	    /**
		 * @param sequenceNumber The sequenceNumber to set.
		 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber=sequenceNumber;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	
	    @Key(generator = "ID_GEN", startAt = "1")
	public long getSequenceNumber() {
		return this.sequenceNumber;
	}
	    /**
		 * @param airportCode The airportCode to set.
		 */
	public void setAirportCode(java.lang.String airportCode) {
		this.airportCode=airportCode;
	}
	/**
	 * @return Returns the airportCode.
	 */
	    @KeyCondition(column = "ARPCOD")
	public java.lang.String getAirportCode() {
		return this.airportCode;
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
	    @KeyCondition(column = "FLTSEQNUM")
	public int getFlightSequenceNumber() {
		return this.flightSequenceNumber;
	}
	    /**
		 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
		 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier=flightCarrierIdentifier;
	}
	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	    @KeyCondition(column = "FLTCARIDR")
	public int getFlightCarrierIdentifier() {
		return this.flightCarrierIdentifier;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:17 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(213);
		sbul.append("ULDFlightMessageReconcilePK [ ");
		sbul.append("airportCode '").append(this.airportCode);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', flightCarrierIdentifier '").append(
				this.flightCarrierIdentifier);
		sbul.append("', flightNumber '").append(this.flightNumber);
		sbul.append("', flightSequenceNumber '").append(
				this.flightSequenceNumber);
		sbul.append("', messageType '").append(this.messageType);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
