/*
 * ULDRepairPK.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-1347
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@KeyTable(table = "ULDDMGKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "ULDDMGKEY", key = "ULDRPRSEQNUM")
@Embeddable
public class ULDRepairPK implements Serializable{
    /**
     * 
     */

    private String companyCode;
	/**
	 * 
	 */

    private String uldNumber;
	/**
	 * 
	 */

    private long repairSequenceNumber;   
    
	/**
	 * @param other
	 * @return boolean
	 */
    public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
    
    /**
     * hashCode
     * @return int
     */
	public int hashCode() {
		return new StringBuilder().append(companyCode).
				append(uldNumber).
				append(repairSequenceNumber).
				toString().hashCode();
	}    

	public void setRepairSequenceNumber(long repairSequenceNumber) {
		this.repairSequenceNumber=repairSequenceNumber;
	}
	@Key(generator = "ID_GEN", startAt = "1")
	public long getRepairSequenceNumber() {
		return this.repairSequenceNumber;
	}

	public void setUldNumber(java.lang.String uldNumber) {
		this.uldNumber=uldNumber;
	}
	@KeyCondition(column = "ULDNUM")
	public java.lang.String getUldNumber() {
		return this.uldNumber;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	@KeyCondition(column = "CMPCOD") 
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:18 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(87);
		sbul.append("ULDRepairPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', repairSequenceNumber '").append(
				this.repairSequenceNumber);
		sbul.append("', uldNumber '").append(this.uldNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
