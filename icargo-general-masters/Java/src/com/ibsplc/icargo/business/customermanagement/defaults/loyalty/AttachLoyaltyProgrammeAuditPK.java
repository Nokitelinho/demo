/*
	 * AttachLoyaltyProgrammeAuditPK.java Created on may 11th, 2006
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

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
@KeyTable(table="CUSLTYPRGAUDKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="AUDSEQ_GEN",table="CUSLTYPRGAUDKEY",key="CUSLTYPRGAUD_SEQNUM")
@Embeddable
public class AttachLoyaltyProgrammeAuditPK implements Serializable {


		/**
		 * Company Code
		 */

		private String companyCode;

		/**
		 * Loyalty programme name
		 */

		private String loyaltyProgrammeCode;



	    /**
	     * Audit Seriel number
	     *
	     */

	    private long sequenceNumber;

	    public AttachLoyaltyProgrammeAuditPK(){

	    }
	    /**
	     *
	     * @param companyCode
	     * @param loyaltyProgrammeCode
	     * @param sequenceNumber
	     */
	    public AttachLoyaltyProgrammeAuditPK(
	    		String companyCode,
	            String loyaltyProgrammeCode ,
	            long sequenceNumber){

			 this.companyCode = companyCode;

			 this.loyaltyProgrammeCode = loyaltyProgrammeCode;
			 this.sequenceNumber = sequenceNumber;

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
						.append(loyaltyProgrammeCode)
						.append(sequenceNumber)
						.toString().hashCode();
		}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
		@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setLoyaltyProgrammeCode(java.lang.String loyaltyProgrammeCode) {
		this.loyaltyProgrammeCode=loyaltyProgrammeCode;
	}
		@KeyCondition(column = "LTYPRGCOD")
	public java.lang.String getLoyaltyProgrammeCode() {
		return this.loyaltyProgrammeCode;
	}

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber=sequenceNumber;
	}
	    @Key(generator="AUDSEQ_GEN",startAt="1")
	public long getSequenceNumber() {
		return this.sequenceNumber;
	}
		/**
		 * generated by xibase.tostring plugin at 1 October, 2014 1:13:49 PM IST
		 */
		@Override
		public String toString() {
			StringBuilder sbul = new StringBuilder(110);
			sbul.append("AttachLoyaltyProgrammeAuditPK [ ");
			sbul.append("companyCode '").append(this.companyCode);
			sbul.append("', loyaltyProgrammeCode '").append(
					this.loyaltyProgrammeCode);
			sbul.append("', sequenceNumber '").append(this.sequenceNumber);
			sbul.append("' ]");
			return sbul.toString();
		}
}
