/*
 * SLAFilterVO.java Created on Apr 2, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2521
 *
 */
public class SLAFilterVO extends AbstractVO {
	
	
	private String companyCode;
	
	private String slaID;
	
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
	 * @return Returns the slaID.
	 */
	public String getSlaID() {
		return slaID;
	}

	/**
	 * @param slaID The slaID to set.
	 */
	public void setSlaID(String slaID) {
		this.slaID = slaID;
	}
	
		
	
}
