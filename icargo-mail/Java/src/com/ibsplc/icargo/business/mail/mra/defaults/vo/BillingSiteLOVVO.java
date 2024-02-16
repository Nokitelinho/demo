/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVVO.java
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
 * The Class BillingSiteLOVVO.
 *
 * @author A-5219
 */
public class BillingSiteLOVVO extends AbstractVO {

	/** The company code. */
	private String companyCode;
	
	/** The billing site code. */
	private String billingSiteCode;
	
	/** The billing site description. */
	private String billingSiteDescription;
	
	/**
	 * Gets the billing site code.
	 *
	 * @return Returns the billingSiteCode.
	 */
	public String getBillingSiteCode() {
		return billingSiteCode;
	}
	
	/**
	 * Sets the billing site code.
	 *
	 * @param billingSiteCode The billingSiteCode to set.
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}
	
	/**
	 * Gets the billing site description.
	 *
	 * @return Returns the billingSiteDescription.
	 */
	public String getBillingSiteDescription() {
		return billingSiteDescription;
	}
	
	/**
	 * Sets the billing site description.
	 *
	 * @param billingSiteDescription The billingSiteDescription to set.
	 */
	public void setBillingSiteDescription(String billingSiteDescription) {
		this.billingSiteDescription = billingSiteDescription;
	}
	
	/**
	 * Gets the company code.
	 *
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	
	/**
	 * Sets the company code.
	 *
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


}
