/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteFilterVO.java
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

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteFilterVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public class BillingSiteFilterVO extends AbstractVO{
	
	/** The company code. */
	private String companyCode;
	
	/** The billing site code. */
	private String billingSiteCode;
	
	/** The billing site. */
	private String billingSite;
	
	/** The serial number. */
	private int serialNumber;
	
	/**
	 * Getter for companyCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the company code
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the companyCode to set
	 * Setter for companyCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Getter for billingSiteCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the billing site code
	 */
	public String getBillingSiteCode() {
		return billingSiteCode;
	}

	/**
	 * Sets the billing site code.
	 *
	 * @param billingSiteCode the billingSiteCode to set
	 * Setter for billingSiteCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}

	/**
	 * Getter for billingSite
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the billing site
	 */
	public String getBillingSite() {
		return billingSite;
	}

	/**
	 * Sets the billing site.
	 *
	 * @param billingSite the billingSite to set
	 * Setter for billingSite
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setBillingSite(String billingSite) {
		this.billingSite = billingSite;
	}

	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Gets the serial number.
	 *
	 * @return the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

}
