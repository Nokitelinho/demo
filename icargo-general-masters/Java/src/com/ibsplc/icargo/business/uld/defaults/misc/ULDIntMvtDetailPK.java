/*
 * ULDIntMvtDetailPK.java Created on Mar 26, 2008
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
 * @author A-2412
 * 
 */
@KeyTable(table = "ULDINTMVTKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "ULDINTMVTKEY", key = "MVTSEQNUM")
@Embeddable
public class ULDIntMvtDetailPK implements Serializable {

	private String companyCode;

	private String uldNumber;

	private int intSequenceNumber;

	private long intSerialNumber;

	/**
	 * Constructor
	 */
	public ULDIntMvtDetailPK() {

	}

	/**
	 * @param companyCode
	 * @param intSequenceNumber
	 * @param uldNumber
	 */
	public ULDIntMvtDetailPK(String companyCode, int intSequenceNumber,
			String uldNumber) {
		this.companyCode = companyCode;
		this.intSequenceNumber = intSequenceNumber;
		this.uldNumber = uldNumber;
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
		return new StringBuilder().append(companyCode)
				.append(intSequenceNumber).append(uldNumber).append(
						intSerialNumber).toString().hashCode();
	}

	/**
	 * @return the companyCode
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the intSequenceNumber
	 */
	@KeyCondition(column = "INTSEQNUM")
	public int getIntSequenceNumber() {
		return intSequenceNumber;
	}

	/**
	 * @param intSequenceNumber
	 *            the intSequenceNumber to set
	 */
	public void setIntSequenceNumber(int intSequenceNumber) {
		this.intSequenceNumber = intSequenceNumber;
	}

	/**
	 * @return the intSerialNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1")
	public long getIntSerialNumber() {
		return intSerialNumber;
	}

	/**
	 * @param intSerialNumber
	 *            the intSerialNumber to set
	 */
	public void setIntSerialNumber(long intSerialNumber) {
		this.intSerialNumber = intSerialNumber;
	}

	/**
	 * @return the uldNumber
	 */
	
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:17 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(117);
		sbul.append("ULDIntMvtDetailPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', intSequenceNumber '").append(this.intSequenceNumber);
		sbul.append("', intSerialNumber '").append(this.intSerialNumber);
		sbul.append("', uldNumber '").append(this.uldNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

}
