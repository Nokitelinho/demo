/*
 * UPUCalendarPK.java Created on Sep 11, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-2521
 *
 */
@Embeddable
public class UPUCalendarPK implements Serializable {
	
	/** company code */
	private String companyCode;

	/** clearance period */
	private String clrPeriod;
   
    /** 
     * constructor 
     */
    public UPUCalendarPK(){}
    
    /**
     * constructor
     * @param companyCode
     * @param clearingHouse
     * @param clrPeriod
     * @param iataCalendarVO
     */
    public UPUCalendarPK( String companyCode, String clrPeriod ){
    	
    	this.companyCode	= companyCode;
    	this.clrPeriod 	= clrPeriod;
    }
    
    /**
     * @param other
     * @return boolean
     */
    public boolean equals(Object other) {
    	
    	if(other != null){
    		return (hashCode() == other.hashCode());
    		
    	}else{
    		return false;
    	}
		
	}
    
    /**
     * @return hashCode
     */
	public int hashCode() {
		return new StringBuffer(companyCode).append(clrPeriod).toString().hashCode();
	}	

	/**
	 * 
	 * @param clrPeriod
	 */
	public void setClrPeriod(java.lang.String clrPeriod) {
		this.clrPeriod=clrPeriod;
	}

	/**
	 * 
	 * @return
	 */
	public java.lang.String getClrPeriod() {
		return this.clrPeriod;
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
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:53 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(57);
		sbul.append("UPUCalendarPK [ ");
		sbul.append("clrPeriod '").append(this.clrPeriod);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
