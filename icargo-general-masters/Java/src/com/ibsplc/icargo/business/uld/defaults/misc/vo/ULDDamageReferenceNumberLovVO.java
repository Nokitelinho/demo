/*
 * ULDDamageReferenceNumberLovVO.java  Created on Dec 21, 2005
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
public class ULDDamageReferenceNumberLovVO extends AbstractVO{

	private String uldNumber;

	private long damageReferenceNumber;

	/**
	 * @return Returns the damageRefernceNumber.
	 */
	public long getDamageReferenceNumber() {
		return damageReferenceNumber;
	}

	/**
	 * @param damageReferenceNumber The damageRefernceNumber to set.
	 */
	public void setDamageReferenceNumber(long damageReferenceNumber) {
		this.damageReferenceNumber = damageReferenceNumber;
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
