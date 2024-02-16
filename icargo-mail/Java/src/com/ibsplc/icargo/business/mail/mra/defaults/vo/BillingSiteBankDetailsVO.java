/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteBankDetailsVO.java
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteBankDetailsVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public class BillingSiteBankDetailsVO extends AbstractVO{
	
	/** The operational flag. */
	private String operationalFlag;
	
	/** The currency. */
	private String currency;
	
	/** The bank name. */
	private String bankName;
	
	/** The branch. */
	private String branch;
	
	/** The acc no. */
	private String accNo;
	
	/** The city. */
	private String city;
	
	/** The country. */
	private String country;
	
	/** The swift code. */
	private String swiftCode;
	
	/** The iban no. */
	private String ibanNo;
	
	/** The company code. */
	private String companyCode;
	
	/** The billing site code. */
	private String billingSiteCode;
	
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
	 * Getter for currency
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency.
	 *
	 * @param currency the currency to set
	 * Setter for currency
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Getter for bankName
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the bank name
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Sets the bank name.
	 *
	 * @param bankName the bankName to set
	 * Setter for bankName
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Getter for branch
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * Sets the branch.
	 *
	 * @param branch the branch to set
	 * Setter for branch
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * Getter for accNo
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the acc no
	 */
	public String getAccNo() {
		return accNo;
	}

	/**
	 * Sets the acc no.
	 *
	 * @param accNo the accNo to set
	 * Setter for accNo
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	/**
	 * Getter for city
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the city to set
	 * Setter for city
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Getter for country
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the country to set
	 * Setter for country
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Getter for swiftCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the swift code
	 */
	public String getSwiftCode() {
		return swiftCode;
	}

	/**
	 * Sets the swift code.
	 *
	 * @param swiftCode the swiftCode to set
	 * Setter for swiftCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * Getter for ibanNo
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the iban no
	 */
	public String getIbanNo() {
		return ibanNo;
	}

	/**
	 * Sets the iban no.
	 *
	 * @param ibanNo the ibanNo to set
	 * Setter for ibanNo
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setIbanNo(String ibanNo) {
		this.ibanNo = ibanNo;
	}

	/**
	 * Getter for companyCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	
	/**
	 * Sets the companyCode.
	 *
	 * @param companyCode the ibanNo to set
	 * Setter for companyCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
