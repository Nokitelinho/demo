/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailMasterDataFilterVO.java
 *
 *	Created by	:	204082
 *	Created on	:	17-Oct-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailMasterDataFilterVO extends AbstractVO {

	private String companyCode;
	private String masterType;
	private int noOfDaysToConsider;
	private int lastScanTime;
    private int recordSize;

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the masterType
	 */
	public String getMasterType() {
		return masterType;
	}

	/**
	 * @param masterType to set
	 */
	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}

	/**
	 * @return the noOfDaysToConsider
	 */
	public int getNoOfDaysToConsider() {
		return noOfDaysToConsider;
	}

	/**
	 * @param noOfDaysToConsider to set
	 */
	public void setNoOfDaysToConsider(int noOfDaysToConsider) {
		this.noOfDaysToConsider = noOfDaysToConsider;
	}

	/**
	 * @return the lastScanTime
	 */
	public int getLastScanTime() {
		return lastScanTime;
	}

	/**
	 * @param lastScanTime to set
	 */
	public void setLastScanTime(int lastScanTime) {
		this.lastScanTime = lastScanTime;
	}
	public int getRecordSize() {
		return recordSize;
	}
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	
}
