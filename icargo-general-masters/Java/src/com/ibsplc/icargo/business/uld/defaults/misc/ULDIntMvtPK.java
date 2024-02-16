/*
 * ULDIntMvtPK.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.io.Serializable;

/**
 * @author A-2412
 * 
 */
public class ULDIntMvtPK implements Serializable {
	/**
	 * The companyCode
	 */
	private String companyCode;

	/**
	 * ULDNumber
	 */
	private String uldNumber;

	/**
	 * movementsequenceNumber
	 */
	private int intSequenceNumber;

	/**
	 * 
	 * Constructor
	 */
	public ULDIntMvtPK() {

	}

	/**
	 * @return the companyCode
	 */
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
	 * @param companyCode
	 * @param uldNumber
	 * @param intSequenceNumber
	 */
	public ULDIntMvtPK(String companyCode, String uldNumber,
			int intSequenceNumber) {
		this.companyCode = companyCode;
		this.uldNumber = uldNumber;
		this.intSequenceNumber = intSequenceNumber;
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
				.append(intSequenceNumber).toString().hashCode();
	}
}
