/*
 * ULDDamagePicturePK.java Created on Dec 21, 2005
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
 */
@KeyTable(table="ULDDMGPICKEY", keyColumn="KEYTYP", valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN", table="ULDDMGPICKEY", key="DMGPIC_SEQNUM")


@Embeddable
public class ULDDamagePicturePK implements Serializable {
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
	private long damageSequenceNumber;
	
	@KeyCondition(column = "DMGSEQNUM")
	public long getDamageSequenceNumber() {
		return damageSequenceNumber;
	}

	public void setDamageSequenceNumber(long damageSequenceNumber) {
		this.damageSequenceNumber = damageSequenceNumber;
	}

	private int imageSequenceNumber;

	/**
	 *
	 * Constructor
	 */
	public ULDDamagePicturePK() {
	}

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
		return new StringBuilder().append(companyCode).append(uldNumber)
				.append(damageSequenceNumber).append(imageSequenceNumber)
				.toString().hashCode();
	}
	
	public void setImageSequenceNumber(int imageSequenceNumber) {
		this.imageSequenceNumber=imageSequenceNumber;
	}

	@Key(generator = "ID_GEN", startAt = "1")
	public int getImageSequenceNumber() {
		return this.imageSequenceNumber;
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
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:17 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(88);
		sbul.append("ULDDamagePicturePK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', sequenceNumber '").append(this.damageSequenceNumber);
		sbul.append("', uldNumber '").append(this.uldNumber);
		sbul.append("', imageSequenceNumber '").append(
				this.imageSequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}