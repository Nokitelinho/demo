/*
 * MailHandoverTimePK.java Created on Jul 02, 2018
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
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 * @author A-6986
 *

 */
@SequenceKeyGenerator(name="ID_GEN",sequence="MALHNDTIM_SEQ")
@Embeddable
public class MailHandoverTimePK implements Serializable{

	private String companyCode;
	private int serialNumber;
	
	
	/**
	 * @return the companyCode
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the serialNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1" )
	public int getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return Returns the hashCode.
	 */
	public int hashCode() {
		return new StringBuffer().append(companyCode).append(serialNumber).toString().hashCode();
	}
	/**
	 * @param other 
	 * @return 
	 * 
	 */
	@Override
	public boolean equals(Object other) {
		return (hashCode() == other.hashCode());
	}

}
