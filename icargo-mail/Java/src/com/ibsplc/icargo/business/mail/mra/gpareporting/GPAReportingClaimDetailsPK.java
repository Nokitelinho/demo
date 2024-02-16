/*
 * GPAReportingClaimDetailsPK.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-1453
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */

@KeyTable(table="MTKGPARPTCLMKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN",table="MTKGPARPTCLMKEY",key="MTKGPARPTCLM_SEQ")
@Embeddable
public class GPAReportingClaimDetailsPK implements Serializable{


    /**
     * <code>companyCode</code>
     */

    private String companyCode;
    /**
     * <code>poaCode</code>
     */

    private String poaCode;
    /**
     * <code>billingBasis</code>
     */

    private String billingBasis;
    /**
     * <code>reportingPeriodFrom</code>
     */

    private String  reportingPeriodFromstring;
    /**
     * <code>reportingPeriodTo</code>
     */

    private String reportingPeriodToString;
    /**
     * <code>exceptionSequenceNumber</code>
     */

    private int exceptionSequenceNumber;


    /**
     * Default Constructor
     *
     */
    public GPAReportingClaimDetailsPK() {

    }
    /**
     * @author A-2280
     * @param companyCode
     * @param poaCode
     * @param billingBasis
     * @param reportingPeriodFrmString
     * @param reportingPeriodToString
     */
    
    public GPAReportingClaimDetailsPK(String companyCode,String poaCode,
    		String billingBasis,String reportingPeriodFrmString,String reportingPeriodToString){
    	this.companyCode=companyCode;
    	this.poaCode=poaCode;
    	this.billingBasis=billingBasis;
    	this.reportingPeriodFromstring=reportingPeriodFrmString;
    	this.reportingPeriodToString=reportingPeriodToString;
    	
    }
   /**
    * @author A-2280
    * @param companyCode
    * @param poaCode
    * @param billingBasis
    * @param reportingPeriodFrmString
    * @param reportingPeriodToString
    * @param exceptionSequenceNumber
    */
    public GPAReportingClaimDetailsPK(String companyCode,String poaCode,
    		String billingBasis,String reportingPeriodFrmString,String reportingPeriodToString,int exceptionSequenceNumber){
    	this.companyCode=companyCode;
    	this.poaCode=poaCode;
    	this.billingBasis=billingBasis;
    	this.reportingPeriodFromstring=reportingPeriodFrmString;
    	this.reportingPeriodToString=reportingPeriodToString;
    	this.exceptionSequenceNumber=exceptionSequenceNumber;
    	
    }
    
    /**
     * @return
     */
    public int hashCode() {
        return new StringBuilder().append(companyCode)
            .append(poaCode).append(billingBasis)
            .append(reportingPeriodFromstring).append(reportingPeriodToString).toString().hashCode();
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
     * 
     * @param exceptionSequenceNumber
     */
	public void setExceptionSequenceNumber(int exceptionSequenceNumber) {
		this.exceptionSequenceNumber=exceptionSequenceNumber;
	}
	/**
	 * 
	 * @return
	 */
    @Key(generator="ID_GEN",startAt="1")
	public int getExceptionSequenceNumber() {
		return this.exceptionSequenceNumber;
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
	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 
	 * @param billingBasis
	 */
	public void setBillingBasis(java.lang.String billingBasis) {
		this.billingBasis=billingBasis;
	}
	/**
	 * 
	 * @return
	 */
	@KeyCondition(column = "BLGBAS")
	public java.lang.String getBillingBasis() {
		return this.billingBasis;
	}

	/**
	 * 
	 * @param reportingPeriodFromstring
	 */
	public void setReportingPeriodFromstring(java.lang.String reportingPeriodFromstring) {
		this.reportingPeriodFromstring=reportingPeriodFromstring;
	}
	/**
	 * 
	 * @return
	 */
	@KeyCondition(column = "REPPRDFRMSTR")
	public java.lang.String getReportingPeriodFromstring() {
		return this.reportingPeriodFromstring;
	}

	/**
	 * 
	 * @param reportingPeriodToString
	 */
	public void setReportingPeriodToString(java.lang.String reportingPeriodToString) {
		this.reportingPeriodToString=reportingPeriodToString;
	}
	/**
	 * 
	 * @return
	 */
	@KeyCondition(column = "REPPRDTOSTR")
	public java.lang.String getReportingPeriodToString() {
		return this.reportingPeriodToString;
	}

	/**
	 * 
	 * @param poaCode
	 */
	public void setPoaCode(java.lang.String poaCode) {
		this.poaCode=poaCode;
	}
	/**
	 * 
	 * @return
	 */
	@KeyCondition(column = "GPACOD")
	public java.lang.String getPoaCode() {
		return this.poaCode;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(199);
		sbul.append("GPAReportingClaimDetailsPK [ ");
		sbul.append("billingBasis '").append(this.billingBasis);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', exceptionSequenceNumber '").append(
				this.exceptionSequenceNumber);
		sbul.append("', poaCode '").append(this.poaCode);
		sbul.append("', reportingPeriodFromstring '").append(
				this.reportingPeriodFromstring);
		sbul.append("', reportingPeriodToString '").append(
				this.reportingPeriodToString);
		sbul.append("' ]");
		return sbul.toString();
	}
}
