/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Calendar;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public class BillingSiteGPACountriesVO extends AbstractVO{
	
	/** The billing site code. */
	private String billingSiteCode;
	
	/** The gpa country. */
	private String gpaCountry;
	
	/** The company code. */
	private String companyCode;
	
	/** The operational flag. */
	private String operationalFlag;
	
	/** The serial number. */
	private int serialNumber;
	
	/** The last updated user. */
	private String lastUpdatedUser;
	
	/** The last updated time. */
	private Calendar lastUpdatedTime;

	/**
	 * Getter for operationalFlag
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the operational flag
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}

	/**
	 * Sets the operational flag.
	 *
	 * @param operationalFlag the operationalFlag to set
	 * Setter for operationalFlag
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}

	/**
	 * Sets the gpa country.
	 *
	 * @param gpaCountry the gpaCountry to set
	 * Setter for gpaCountry
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setGpaCountry(String gpaCountry) {
		this.gpaCountry = gpaCountry;
	}

	/**
	 * Getter for gpaCountry
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the gpa country
	 */
	public String getGpaCountry() {
		return gpaCountry;
	}

	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber the serialNumber to set
	 * Setter for serialNumber
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Getter for serialNumber
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the serial number
	 */
	public int getSerialNumber() {
		return serialNumber;
	}
	
	/**
	 * Gets the company code.
	 *
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Sets the billing site code.
	 *
	 * @param billingSiteCode the billingSiteCode to set
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}

	/**
	 * Gets the billing site code.
	 *
	 * @return the billingSiteCode
	 */
	public String getBillingSiteCode() {
		return billingSiteCode;
	}

	/**
	 * Sets the last updated user.
	 *
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * Gets the last updated user.
	 *
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * Sets the last updated time.
	 *
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * Gets the last updated time.
	 *
	 * @return the lastUpdatedTime
	 */
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

}
