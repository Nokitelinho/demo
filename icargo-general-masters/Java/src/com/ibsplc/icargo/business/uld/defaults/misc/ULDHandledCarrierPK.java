/*
 * ULDHandledCarrierPK.java Created on Dec 6, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.io.Serializable;

import javax.persistence.Embeddable;


/**
 * @author A-2883
 *
 * 
 */


@Embeddable
public class ULDHandledCarrierPK  implements Serializable{
	
	  	private String airlineCode;
	  	private String stationCode;
	  	private String companyCode;
	    
	    public ULDHandledCarrierPK() {

		}
	    
	    public ULDHandledCarrierPK(String airlineCode,String stationCode,String companyCode) {
	    	this.airlineCode=airlineCode;
	    	this.stationCode=stationCode;
	    	this.companyCode=companyCode;

		}

	    
	    /**
	     * This method tests for equality of one instance of this class with
	     * the other.
	     * @param other - another object to test for equality
	     * @return boolean - returns true if equal
	     */
		public boolean equals(Object other) {
			return (other != null) && ((hashCode() == other.hashCode()));
		}
		/**
		 * This method generates the hashcode of an instance
		 * @return int - returns the hashcode of the instance
		 */
		 public int hashCode() {
			return new StringBuilder(companyCode)
			.append(stationCode)
			.append(airlineCode)
			.toString().hashCode();
		 }
		 
		
	    public void setCompanyCode(java.lang.String companyCode) {
			this.companyCode=companyCode;
		}
	  //  @KeyCondition(column = "CMPCOD")
	    public java.lang.String getCompanyCode() {
			return this.companyCode;
		}

	    
		public void setStationCode(java.lang.String stationCode) {
				this.stationCode=stationCode;
		}
		//@KeyCondition(column = "STNCOD")
		public java.lang.String getStationCode() {
				return this.stationCode;
		}
		
		public void setAirlineCode(java.lang.String airlineCode) {
			this.airlineCode=airlineCode;
		}
		//@KeyCondition(column = "ARLCOD")
		public java.lang.String getAirlineCode() {
				return this.airlineCode;
		}

		/**
		 * generated by xibase.tostring plugin at 1 October, 2014 1:14:17 PM IST
		 */
		@Override
		public String toString() {
			StringBuilder sbul = new StringBuilder(88);
			sbul.append("ULDHandledCarrierPK [ ");
			sbul.append("airlineCode '").append(this.airlineCode);
			sbul.append("', companyCode '").append(this.companyCode);
			sbul.append("', stationCode '").append(this.stationCode);
			sbul.append("' ]");
			return sbul.toString();
		}
		

}
