/*
 * ULDDamageDeleteVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDDamageDeleteVO extends AbstractVO{

	private String companyCode;
	private String uldNumber;
	private long damageSequenceNumber;
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the damageSequenceNumber.
	 */
	public long getDamageSequenceNumber() {
		return damageSequenceNumber;
	}
	/**
	 * @param damageSequenceNumber The damageSequenceNumber to set.
	 */
	public void setDamageSequenceNumber(long damageSequenceNumber) {
		this.damageSequenceNumber = damageSequenceNumber;
	}
	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
}
