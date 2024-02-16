/*
 * MRAInterfacedDetailsPK.java Created on Sep 2, 2019
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 * @author A-5526
 *
 */

@Embeddable
@SequenceKeyGenerator(name="ID_GEN",sequence="MALMRAINTFCD_SEQ")
public class MRAInterfacedDetailsPK  implements Serializable{
	/**
     * 
     */
	private String companyCode;    
    
	/**
     * The mailSequenceNumber
     */
	private long sequenceNumber;
	
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
				
				append(sequenceNumber).
				toString().hashCode();
	}

	
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return this.companyCode;
	}
	
	
	 @Key(generator = "ID_GEN", startAt = "1" )
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:52 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(205);
		sbul.append("MRAInterfacedDetailsPK [ ");
		sbul.append("companyCode '").append(this.companyCode);

		sbul.append("sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
